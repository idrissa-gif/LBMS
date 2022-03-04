package com.example.lbms;

public class Book {
    private String BookID , BookTitle , BookCell , BookAuthor, ISBN, bibionumber, status;

    public Book(String bookID, String bookTitle, String bookCell, String bookAuthor, String ISBN, String bibionumber, String status) {
        BookID = bookID;
        BookTitle = bookTitle;
        BookCell = bookCell;
        BookAuthor = bookAuthor;
        ISBN = ISBN;
        this.bibionumber = bibionumber;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBookTitle() {
        return BookTitle;
    }

    public void setBookTitle(String bookTitle) {
        BookTitle = bookTitle;
    }

    public String getBookCell() {
        return BookCell;
    }

    public void setBookCell(String bookCell) {
        BookCell = bookCell;
    }

    public String getBookID() {
        return BookID;
    }

    public void setBookID(String bookID) {
        BookID = bookID;
    }

    public String getBookAuthor() {
        return BookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        BookAuthor = bookAuthor;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setIBSN(String IBSN) {
        this.ISBN = IBSN;
    }

    public String getBibionumber() {
        return bibionumber;
    }

    public void setBibionumber(String bibionumber) {
        this.bibionumber = bibionumber;
    }
}
