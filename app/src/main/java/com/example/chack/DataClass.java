package com.example.chack;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataClass
{
    public static String searchText = ""; //검색어 저장할 변수
    @SerializedName("totalResults")
    @Expose
    private String totalResults;
    @SerializedName("startIndex")
    @Expose
    private String startIndex;
    @SerializedName("item")
    @Expose
    private Item[] item;
    @SerializedName("itemsPerPage")
    @Expose
    private String itemsPerPage;
    @SerializedName("searchCategoryName")
    @Expose
    private String searchCategoryName;
    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("pubDate")
    @Expose
    private String pubDate;
    @SerializedName("searchCategoryId")
    @Expose
    private String searchCategoryId;

    public String getTotalResults () { return totalResults; }
    public void setTotalResults (String totalResults) { this.totalResults = totalResults; }
    public String getStartIndex () { return startIndex; }
    public void setStartIndex (String startIndex) { this.startIndex = startIndex; }
    public Item[] getItem () { return item; }
    public void setItem (Item[] item) { this.item = item; }
    public String getItemsPerPage () { return itemsPerPage; }
    public void setItemsPerPage (String itemsPerPage) { this.itemsPerPage = itemsPerPage; }
    public String getSearchCategoryName () { return searchCategoryName; }
    public void setSearchCategoryName (String searchCategoryName) { this.searchCategoryName = searchCategoryName; }
    public String getQuery () { return query; }
    public void setQuery (String query) { this.query = query; }
    public String getLink () { return link; }
    public void setLink (String link) { this.link = link; }
    public String getLogo () { return logo; }
    public void setLogo (String logo) { this.logo = logo; }
    public String getTitle () { return title; }
    public void setTitle (String title) { this.title = title; }
    public String getVersion () { return version; }
    public void setVersion (String version) { this.version = version; }
    public String getPubDate () { return pubDate; }
    public void setPubDate (String pubDate) { this.pubDate = pubDate; }
    public String getSearchCategoryId () { return searchCategoryId; }
    public void setSearchCategoryId (String searchCategoryId) { this.searchCategoryId = searchCategoryId; }
    @Override
    public String toString() { return "ClassPojo [totalResults = "+totalResults+", startIndex = "+startIndex+", item = "+item+", itemsPerPage = "+itemsPerPage+", searchCategoryName = "+searchCategoryName+", query = "+query+", link = "+link+", logo = "+logo+", title = "+title+", version = "+version+", pubDate = "+pubDate+", searchCategoryId = "+searchCategoryId+"]"; }
}


class Item
{
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("isbn")
    @Expose
    private String isbn;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("pubDate")
    @Expose
    private String pubDate;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("fixedPrice")
    @Expose
    private String fixedPrice;
    @SerializedName("mallType")
    @Expose
    private String mallType;
    @SerializedName("customerReviewRank")
    @Expose
    private String customerReviewRank;
    @SerializedName("cover")
    @Expose
    private String cover;
    @SerializedName("itemId")
    @Expose
    private String itemId;

    /*@SerializedName("subInfo")
    @Expose
    private String[] subInfo;*/
    @SerializedName("isbn13")
    @Expose
    private String isbn13;
    @SerializedName("stockStatus")
    @Expose
    private String stockStatus;
    @SerializedName("publisher")
    @Expose
    private String publisher;
    @SerializedName("priceScale")
    @Expose
    private String priceSales;
    @SerializedName("salesPoint")
    @Expose
    private String salesPoint;
    @SerializedName("adult")
    @Expose
    private String adult;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("priceStandard")
    @Expose
    private String priceStandard;
    @SerializedName("mileage")
    @Expose
    private String mileage;
    public String getAuthor () { return author; }
    public void setAuthor (String author) { this.author = author; }
    public String getIsbn () { return isbn; }
    public void setIsbn (String isbn) { this.isbn = isbn; }
    public String getLink () { return link; }
    public void setLink (String link) { this.link = link; }
    public String getDescription () { return description; }
    public void setDescription (String description) { this.description = description; }
    public String getTitle () { return title; }
    public void setTitle (String title) { this.title = title; }
    public String getPubDate () { return pubDate; }
    public void setPubDate (String pubDate) { this.pubDate = pubDate; }
    public String getCategoryName () { return categoryName; }
    public void setCategoryName (String categoryName) { this.categoryName = categoryName; }
    public String getFixedPrice () { return fixedPrice; }
    public void setFixedPrice (String fixedPrice) { this.fixedPrice = fixedPrice; }
    public String getMallType () { return mallType; }
    public void setMallType (String mallType) { this.mallType = mallType; }
    public String getCustomerReviewRank () { return customerReviewRank; }
    public void setCustomerReviewRank (String customerReviewRank) { this.customerReviewRank = customerReviewRank; }
    public String getCover () { return cover; }
    public void setCover (String cover) { this.cover = cover; }
    public String getItemId () { return itemId; }
    public void setItemId (String itemId) { this.itemId = itemId; }
    /*public String[] getSubInfo () { return subInfo; }
    public void setSubInfo (String[] subInfo) { this.subInfo = subInfo; }*/
    public String getIsbn13 () { return isbn13; }
    public void setIsbn13 (String isbn13) { this.isbn13 = isbn13; }
    public String getStockStatus () { return stockStatus; }
    public void setStockStatus (String stockStatus) { this.stockStatus = stockStatus; }
    public String getPublisher () { return publisher; }
    public void setPublisher (String publisher) { this.publisher = publisher; }
    public String getPriceSales () { return priceSales; }
    public void setPriceSales (String priceSales) { this.priceSales = priceSales; }
    public String getSalesPoint () { return salesPoint; }
    public void setSalesPoint (String salesPoint) { this.salesPoint = salesPoint; }
    public String getAdult () { return adult; }
    public void setAdult (String adult) { this.adult = adult; }
    public String getCategoryId () { return categoryId; }
    public void setCategoryId (String categoryId) { this.categoryId = categoryId; }
    public String getPriceStandard () { return priceStandard; }
    public void setPriceStandard (String priceStandard) { this.priceStandard = priceStandard; }
    public String getMileage () { return mileage; }
    public void setMileage (String mileage) { this.mileage = mileage; }

    /*@Override
    public String toString() { return "ClassPojo [author = "+author+", isbn = "+isbn+", link = "+link+", description = "+description+", title = "+title+", pubDate = "+pubDate+", categoryName = "+categoryName+", fixedPrice = "+fixedPrice+", mallType = "+mallType+", customerReviewRank = "+customerReviewRank+", cover = "+cover+", itemId = "+itemId+", subInfo = "+subInfo+", isbn13 = "+isbn13+", stockStatus = "+stockStatus+", publisher = "+publisher+", priceSales = "+priceSales+", salesPoint = "+salesPoint+", adult = "+adult+", categoryId = "+categoryId+", priceStandard = "+priceStandard+", mileage = "+mileage+"]";
    }*/

    @Override
    public String toString() { return "ClassPojo [author = "+author+", isbn = "+isbn+", link = "+link+", description = "+description+", title = "+title+", pubDate = "+pubDate+", categoryName = "+categoryName+", fixedPrice = "+fixedPrice+", mallType = "+mallType+", customerReviewRank = "+customerReviewRank+", cover = "+cover+", itemId = "+itemId+", isbn13 = "+isbn13+", stockStatus = "+stockStatus+", publisher = "+publisher+", priceSales = "+priceSales+", salesPoint = "+salesPoint+", adult = "+adult+", categoryId = "+categoryId+", priceStandard = "+priceStandard+", mileage = "+mileage+"]";
    }
}


class SeriesInfo
{
    @SerializedName("seriesLink")
    @Expose
    private String seriesLink;

    @SerializedName("seriesName")
    @Expose
    private String seriesName;

    @SerializedName("seriesId")
    @Expose
    private String seriesId;

    public String getSeriesLink ()
    {
        return seriesLink;
    }

    public void setSeriesLink (String seriesLink)
    {
        this.seriesLink = seriesLink;
    }

    public String getSeriesName ()
    {
        return seriesName;
    }

    public void setSeriesName (String seriesName)
    {
        this.seriesName = seriesName;
    }

    public String getSeriesId ()
    {
        return seriesId;
    }

    public void setSeriesId (String seriesId)
    {
        this.seriesId = seriesId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [seriesLink = "+seriesLink+", seriesName = "+seriesName+", seriesId = "+seriesId+"]";
    }
}