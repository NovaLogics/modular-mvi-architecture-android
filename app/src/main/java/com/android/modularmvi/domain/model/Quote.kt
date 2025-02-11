package com.android.modularmvi.domain.model

import com.android.modularmvi.util.emptyString
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class Quote(
    @SerializedName("_id") var id: String = emptyString(),
    @SerializedName("author") var author: String = emptyString(),
    @SerializedName("content") var content: String = emptyString(),
    @SerializedName("tags") var tags: ArrayList<String> = arrayListOf(),
    @SerializedName("authorSlug") var authorSlug: String = emptyString(),
    @SerializedName("length") var length: Int? = 0,
    @SerializedName("dateAdded") var dateAdded: String = emptyString(),
    @SerializedName("dateModified") var dateModified: String = emptyString(),
) {
    fun toJson(): String = Gson().toJson(this)

    companion object {
        fun fromJson(jsonQuote: String): Quote? =
            runCatching {
                Gson().fromJson(jsonQuote, Quote::class.java)
            }.getOrNull()
    }
}


