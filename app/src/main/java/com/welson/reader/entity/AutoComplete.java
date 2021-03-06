package com.welson.reader.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class AutoComplete implements Serializable{
    private ArrayList<String> keywords;
    private boolean ok;

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
