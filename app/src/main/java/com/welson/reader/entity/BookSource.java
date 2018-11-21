package com.welson.reader.entity;

import java.io.Serializable;

public class BookSource implements Serializable{
    /**
     * _id	"56f8da19176d03ac1983fa00"
     source	"zhuishuvip"
     name	"优质书源"
     link	"http://vip.zhuishushenqi.com/toc/56f8da19176d03ac1983fa00"
     lastChapter	"江南城隐居着大前辈 第2333章 我的愿望就是让胖球大佬体验到我的痛苦"
     isCharge	false
     chaptersCount	2340
     updated	"2018-11-19T01:24:22.319Z"
     starting	true
     host	"vip.zhuishushenqi.com"
     */
    private String _id;
    private String lastChapter;
    private String link;
    private String source;
    private String name;
    private boolean isCharge;
    private int chaptersCount;
    private String updated;
    private boolean starting;
    private String host;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getLastChapter() {
        return lastChapter;
    }

    public void setLastChapter(String lastChapter) {
        this.lastChapter = lastChapter;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCharge() {
        return isCharge;
    }

    public void setCharge(boolean charge) {
        isCharge = charge;
    }

    public int getChaptersCount() {
        return chaptersCount;
    }

    public void setChaptersCount(int chaptersCount) {
        this.chaptersCount = chaptersCount;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public boolean isStarting() {
        return starting;
    }

    public void setStarting(boolean starting) {
        this.starting = starting;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
