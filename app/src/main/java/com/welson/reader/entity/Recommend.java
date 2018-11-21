package com.welson.reader.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Recommend implements Serializable{

    private ArrayList<BookEntity> books;
    private boolean ok;

    public ArrayList<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<BookEntity> books) {
        this.books = books;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
