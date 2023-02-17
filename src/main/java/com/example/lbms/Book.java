package com.example.lbms;

public class Book {
    private Integer BookID , BookNum;
    private String BookTitle , Copyrightdate , BookAuthor;

    public Book(Integer bookID, String bookTitle,  String bookAuthor,String copyright,Integer bookNum) {
        BookID = bookID;
        BookTitle = bookTitle;
        Copyrightdate  = copyright;
        BookAuthor = bookAuthor;
        BookNum = bookNum;
    }

    public Integer getBookID() {
        return BookID;
    }

    public void setBookID(Integer bookID) {
        BookID = bookID;
    }

    public String getBookTitle() {
        return BookTitle;
    }

    public void setBookTitle(String bookTitle) {
        BookTitle = bookTitle;
    }

    public String getCopyrightdate() {
        return Copyrightdate;
    }

    public void setCopyrightdate(String copyrightdate) {
        Copyrightdate = copyrightdate;
    }

    public String getBookAuthor() {
        return BookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        BookAuthor = bookAuthor;
    }

    public Integer getBookNum() {
        return BookNum;
    }

    public void setBookNum(Integer bookNum) {
        BookNum = bookNum;
    }
}
