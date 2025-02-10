package com.android.modularmvi.data.local.database.entity

import com.android.modularmvi.domain.model.Quote

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "quotes")
data class QuoteEntity(
    @PrimaryKey @SerializedName("_id") val id: String,
    @SerializedName("author") val author: String,
    @SerializedName("content") val content: String,
    @SerializedName("tags") val tags: List<String>,
    @SerializedName("authorSlug") val authorSlug: String,
    @SerializedName("length") val length: Int?,
    @SerializedName("dateAdded") val dateAdded: String,
    @SerializedName("dateModified") val dateModified: String
) {
    fun toDomainModel() = Quote(id, author, content, ArrayList(tags), authorSlug, length, dateAdded, dateModified)

    companion object {
        fun fromDomainModel(quote: Quote) = QuoteEntity(
            quote.id, quote.author, quote.content, quote.tags, quote.authorSlug, quote.length, quote.dateAdded, quote.dateModified
        )
    }
}

