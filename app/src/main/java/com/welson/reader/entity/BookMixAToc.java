package com.welson.reader.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class BookMixAToc implements Serializable{

    private MixToc mixToc;
    private boolean ok;

    public MixToc getMixToc() {
        return mixToc;
    }

    public void setMixToc(MixToc mixToc) {
        this.mixToc = mixToc;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public class MixToc{
        private String _id;
        private String book;
        private int chaptersCount1;
        private ArrayList<Chapter> chapters;
        private String updated;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getBook() {
            return book;
        }

        public void setBook(String book) {
            this.book = book;
        }

        public int getChaptersCount1() {
            return chaptersCount1;
        }

        public void setChaptersCount1(int chaptersCount1) {
            this.chaptersCount1 = chaptersCount1;
        }

        public ArrayList<Chapter> getChapters() {
            return chapters;
        }

        public void setChapters(ArrayList<Chapter> chapters) {
            this.chapters = chapters;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }
    }

    public class Chapter{
        private String title;
        private String link;
        private boolean unreadble;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public boolean isUnreadble() {
            return unreadble;
        }

        public void setUnreadble(boolean unreadble) {
            this.unreadble = unreadble;
        }
    }
}
