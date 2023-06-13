package com.example.chack;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.Inflater;

public class BarcodeScan extends FragmentActivity {

    private PreviewView mPreviewView;
    private ListenableFuture cameraProviderFuture;
    private ExecutorService cameraExecutor;

    private MyImageAnalyzer myImageAnalyzer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barcode_scanner);

        init();
    }

    private void init(){
        mPreviewView = findViewById(R.id.camerax_preview);

        cameraExecutor = Executors.newSingleThreadExecutor();
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        myImageAnalyzer = new MyImageAnalyzer(this.getSupportFragmentManager());

        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider processCameraProvider = (ProcessCameraProvider) cameraProviderFuture.get();
                    bindPreview(processCameraProvider);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor(this));


    }

    private void bindPreview(ProcessCameraProvider processCameraProvider) {
        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        ImageCapture imageCapture =new ImageCapture.Builder().build();
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setTargetRotation(Surface.ROTATION_270)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();
        preview.setSurfaceProvider(mPreviewView.getSurfaceProvider());

        imageAnalysis.setAnalyzer(cameraExecutor, myImageAnalyzer);

        processCameraProvider.unbindAll();
        processCameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture, imageAnalysis);

    }

    private void scanBarcode(ImageProxy image) {

        //input image
        @SuppressLint("UnsafeOptInUsageError") Image image1 = image.getImage();
        InputImage inputImage = InputImage.fromMediaImage(image1, image.getImageInfo().getRotationDegrees());

        BarcodeScannerOptions options =
                new BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(
                                Barcode.FORMAT_EAN_13).build();

        //Get an instance of BarcodeScanner
        com.google.mlkit.vision.barcode.BarcodeScanner scanner = BarcodeScanning.getClient(options);

        //process the image
        Task<List<Barcode>> result = scanner.process(inputImage)
                .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                    @Override
                    public void onSuccess(List<Barcode> barcodes) {
                        readerBarcodeData(barcodes);
                        Log.d("cameraSuccess::", barcodes.toString());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Task failed with an exception
                        // ...
                        Log.d("cameraFail::", e.toString());
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<List<Barcode>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Barcode>> task) {
                        image.close();
                    }
                });
    }


    //get information barcode
    private void readerBarcodeData(List<Barcode> barcodes) {
        for (Barcode barcode: barcodes) {
            Rect bounds = barcode.getBoundingBox();
            Point[] corners = barcode.getCornerPoints();

            String rawValue = barcode.getRawValue();

            int valueType = barcode.getValueType();

            // See API reference for complete list of supported types
            switch (valueType) {
                case Barcode.TYPE_ISBN:
                    //int isbn = barcode.getValueType();
                    //String isbn_str = Integer.toString(isbn);

                    // 인식한 바코드 정보 저장 isbn
                    String isbn ;
                    isbn = barcode.getRawValue();
                    Toast.makeText(this, isbn, Toast.LENGTH_SHORT).show();
                    DataClass.searchText = isbn;
                    MainActivity.main.onFragmentChange(1);
                    searchBookFragment.addBookfragment();
                    this.finish();

                    cameraExecutor.shutdownNow();
                    break;

                case Barcode.TYPE_WIFI:
                    Toast.makeText(this, "wifi", Toast.LENGTH_SHORT).show();


                    String ssid = barcode.getWifi().getSsid();
                    String password = barcode.getWifi().getPassword();
                    int type = barcode.getWifi().getEncryptionType();

                    cameraExecutor.shutdownNow();

                    break;
                case Barcode.TYPE_URL:
                    Toast.makeText(this, "url", Toast.LENGTH_SHORT).show();
                    String title = barcode.getUrl().getTitle();
                    String url = barcode.getUrl().getUrl();

                    cameraExecutor.shutdownNow();

                    break;
            }
        }
    }

    private void closeCamera(){
        if (cameraProviderFuture != null && cameraExecutor != null){
            cameraProviderFuture.cancel(true);
            cameraProviderFuture = null;
            cameraExecutor.shutdown();
            cameraExecutor = null;
        }
    }

    public class MyImageAnalyzer implements ImageAnalysis.Analyzer{
        private FragmentManager fragmentManager;

        public MyImageAnalyzer(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
        }

        @Override
        public void analyze(@NonNull ImageProxy image) {
            scanBarcode(image);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeCamera();
    }
}
