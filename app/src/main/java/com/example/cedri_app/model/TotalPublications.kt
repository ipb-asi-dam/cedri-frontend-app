package com.example.cedri_app.model

import com.google.gson.annotations.SerializedName

data class TotalPublications (
    @SerializedName("book")
    var book: Int,
    @SerializedName("book_chapter")
    var bookChapter: Int,
    @SerializedName("editorial")
    var editorial: Int,
    @SerializedName("proceeding")
    var proceeding: Int,
    @SerializedName("journal")
    var journal: Int
)