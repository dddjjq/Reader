package com.welson.reader.entity;

import java.io.Serializable;

public class PostCount implements Serializable{
    private int count;
    private boolean ok;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
