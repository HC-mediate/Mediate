package com.ko.mediate.HC.community.application;

public enum ArticleSort {
    LATEST("최신순"),
    POPULARITY("인기순");
    private String order;

    ArticleSort(String order) {
        this.order = order;
    }
}
