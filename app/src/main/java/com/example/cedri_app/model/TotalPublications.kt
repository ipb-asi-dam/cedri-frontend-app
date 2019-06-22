package com.example.cedri_app.model

import com.google.gson.annotations.SerializedName

class TotalPublications(
    @SerializedName("book")
    val book: Int,
    @SerializedName("book_chapter")
    val bookChapter: Int,
    @SerializedName("editorial")
    val editorial: Int,
    @SerializedName("proceeding")
    val proceeding: Int,
    @SerializedName("journal")
    val journal: Int,
    total: Int

) : Total(total) {
    override fun getPairList(): List<Pair<String, Int>> {
        return listOf (
            Pair("Book", book),
            Pair("Book Chapters", bookChapter),
            Pair("Editorials", editorial),
            Pair("Proceedings", proceeding),
            Pair("Journals", journal))
    }
}