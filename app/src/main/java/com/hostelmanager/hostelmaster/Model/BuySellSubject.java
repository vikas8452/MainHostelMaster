package com.hostelmanager.hostelmaster.Model;

public class BuySellSubject {

    private String bookName;
    private String authorName;
    private String bookDescription;
    private String bookPrice;
    private String bookCondition;

    public BuySellSubject(){}

    public BuySellSubject(String bookName, String authorName, String bookDescription, String bookPrice, String bookCondition) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.bookDescription = bookDescription;
        this.bookPrice = bookPrice;
        this.bookCondition = bookCondition;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public String getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookCondition() {
        return bookCondition;
    }

    public void setBookCondition(String bookCondition) {
        this.bookCondition = bookCondition;
    }
}



