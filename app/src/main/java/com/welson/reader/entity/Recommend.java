package com.welson.reader.entity;

import java.util.ArrayList;

public class Recommend {

    private ArrayList<Book> books;
    private boolean ok;

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public class Book{
        private String _id;
        private String title;
        private String author;
        private String shortIntro;
        private String majorCate;
        private String cover;
        private String contentType;
        private boolean allowMonthly;
        private boolean hasCp;
        private int latelyFollower;
        private float retentionRatio;
        private String updated;
        private int chaptersCount;
        private String lastChapter;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
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

        public String getShortIntro() {
            return shortIntro;
        }

        public void setShortIntro(String shortIntro) {
            this.shortIntro = shortIntro;
        }

        public String getMajorCate() {
            return majorCate;
        }

        public void setMajorCate(String majorCate) {
            this.majorCate = majorCate;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public boolean isAllowMonthly() {
            return allowMonthly;
        }

        public void setAllowMonthly(boolean allowMonthly) {
            this.allowMonthly = allowMonthly;
        }

        public boolean isHasCp() {
            return hasCp;
        }

        public void setHasCp(boolean hasCp) {
            this.hasCp = hasCp;
        }

        public int getLatelyFollower() {
            return latelyFollower;
        }

        public void setLatelyFollower(int latelyFollower) {
            this.latelyFollower = latelyFollower;
        }

        public float getRetentionRatio() {
            return retentionRatio;
        }

        public void setRetentionRatio(float retentionRatio) {
            this.retentionRatio = retentionRatio;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public int getChaptersCount() {
            return chaptersCount;
        }

        public void setChaptersCount(int chaptersCount) {
            this.chaptersCount = chaptersCount;
        }

        public String getLastChapter() {
            return lastChapter;
        }

        public void setLastChapter(String lastChapter) {
            this.lastChapter = lastChapter;
        }
    }
}
