package com.welson.reader.eventbus;


import java.util.List;

public class DownloadEvent {

    private String bookId;
    private List<Integer> chapters;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public List<Integer> getChapters() {
        return chapters;
    }

    public void setChapters(List<Integer> chapters) {
        this.chapters = chapters;
    }
}
