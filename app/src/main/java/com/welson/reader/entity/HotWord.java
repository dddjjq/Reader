package com.welson.reader.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class HotWord implements Serializable{
    private ArrayList<String> hotWords;
    private ArrayList<NewHotWords> newHotWords;
    private boolean ok;

    public ArrayList<String> getHotWords() {
        return hotWords;
    }

    public void setHotWords(ArrayList<String> hotWords) {
        this.hotWords = hotWords;
    }

    public ArrayList<NewHotWords> getNewHotWords() {
        return newHotWords;
    }

    public void setNewHotWords(ArrayList<NewHotWords> newHotWords) {
        this.newHotWords = newHotWords;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    class NewHotWords{
        private String word;
        private String book;

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public String getBook() {
            return book;
        }

        public void setBook(String book) {
            this.book = book;
        }
    }
}
