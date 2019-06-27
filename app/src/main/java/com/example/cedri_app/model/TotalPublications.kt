package com.example.cedri_app.model

import com.google.gson.annotations.SerializedName

class TotalPublications(
    @SerializedName("books")
    val books: Int,
    @SerializedName("bookChapters")
    val bookChapters: Int,
    @SerializedName("editorials")
    val editorials: Int,
    @SerializedName("proceedings")
    val proceedings: Int,
    @SerializedName("journals")
    val journals: Int,
    total: Int

) : Total(total) {
    override fun getPairList(): List<Pair<String, Int>> {
        return listOf (
            Pair("Book", books),
            Pair("Book Chapters", bookChapters),
            Pair("Editorials", editorials),
            Pair("Proceedings", proceedings),
            Pair("Journals", journals))
    }
}