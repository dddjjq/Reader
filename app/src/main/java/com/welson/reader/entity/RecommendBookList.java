package com.welson.reader.entity;

import java.util.ArrayList;

public class RecommendBookList {

    private boolean ok;
    private ArrayList<BookList> booklists;
    private int total;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public ArrayList<BookList> getBooklists() {
        return booklists;
    }

    public void setBooklists(ArrayList<BookList> booklists) {
        this.booklists = booklists;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public class BookList{
        private String id;
        private String title;
        private String author;
        private String desc;
        private int bookCount;
        private String cover;
        private int collectorCount;
        private String[] covers;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getBookCount() {
            return bookCount;
        }

        public void setBookCount(int bookCount) {
            this.bookCount = bookCount;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getCollectorCount() {
            return collectorCount;
        }

        public void setCollectorCount(int collectorCount) {
            this.collectorCount = collectorCount;
        }

        public String[] getCovers() {
            return covers;
        }

        public void setCovers(String[] covers) {
            this.covers = covers;
        }
    }
}
