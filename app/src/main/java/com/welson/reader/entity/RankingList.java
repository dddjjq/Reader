package com.welson.reader.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class RankingList implements Serializable{

    private ArrayList<Rank> female;
    private ArrayList<Rank> epub;
    private ArrayList<Rank> male;
    private ArrayList<Rank> picture;
    private boolean ok;

    public ArrayList<Rank> getFemale() {
        return female;
    }

    public void setFemale(ArrayList<Rank> female) {
        this.female = female;
    }

    public ArrayList<Rank> getEpub() {
        return epub;
    }

    public void setEpub(ArrayList<Rank> epub) {
        this.epub = epub;
    }

    public ArrayList<Rank> getMale() {
        return male;
    }

    public void setMale(ArrayList<Rank> male) {
        this.male = male;
    }

    public ArrayList<Rank> getPicture() {
        return picture;
    }

    public void setPicture(ArrayList<Rank> picture) {
        this.picture = picture;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public class Rank{
        private String _id;
        private String title;
        private String cover;
        private boolean collapse;
        private String monthRank;
        private String totalRank;
        private String shortTitle;

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

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public boolean isCollapse() {
            return collapse;
        }

        public void setCollapse(boolean collapse) {
            this.collapse = collapse;
        }

        public String getMonthRank() {
            return monthRank;
        }

        public void setMonthRank(String monthRank) {
            this.monthRank = monthRank;
        }

        public String getTotalRank() {
            return totalRank;
        }

        public void setTotalRank(String totalRank) {
            this.totalRank = totalRank;
        }

        public String getShortTitle() {
            return shortTitle;
        }

        public void setShortTitle(String shortTitle) {
            this.shortTitle = shortTitle;
        }
    }
}
