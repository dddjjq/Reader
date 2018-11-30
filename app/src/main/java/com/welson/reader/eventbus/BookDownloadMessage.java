package com.welson.reader.eventbus;

import java.util.List;

public class BookDownloadMessage {
    private String bookId;
    private String message;

    private boolean isComplete = false;

    public BookDownloadMessage(){
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

}
