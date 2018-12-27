package com.welson.reader.entity;

public class BookDetailEntity {
    private BookDetail bookDetail;
    private HotReview hotReview;
    private RecommendBookList recommendBookList;

    public BookDetail getBookDetail() {
        return bookDetail;
    }

    public void setBookDetail(BookDetail bookDetail) {
        this.bookDetail = bookDetail;
    }

    public HotReview getHotReview() {
        return hotReview;
    }

    public void setHotReview(HotReview hotReview) {
        this.hotReview = hotReview;
    }

    public RecommendBookList getRecommendBookList() {
        return recommendBookList;
    }

    public void setRecommendBookList(RecommendBookList recommendBookList) {
        this.recommendBookList = recommendBookList;
    }
}
