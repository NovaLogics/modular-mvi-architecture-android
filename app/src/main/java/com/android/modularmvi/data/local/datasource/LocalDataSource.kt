package com.android.modularmvi.data.local.datasource

import com.android.modularmvi.domain.model.Quote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalDataSource {

    // Hardcoded data (Mocked local data)
    private val quotes = listOf(
        Quote(
            id = "pNnzE7wpM0W",
            author = "Dee Hock",
            content = "An organization, no matter how well designed, is only as good as the people who live and work in it.",
            tags = arrayListOf("Business"),
            authorSlug = "dee-hock",
            length = 100,
            dateAdded = "2022-07-06",
            dateModified = "2023-04-14"
        ),
        Quote(
            id = "Vs-4YEGn",
            author = "Simone Weil",
            content = "I can, therefore I am.",
            tags = arrayListOf("Inspirational"),
            authorSlug = "simone-weil",
            length = 22,
            dateAdded = "2020-03-11",
            dateModified = "2023-04-14"
        ),
        Quote(
            id = "wQNfb7IAqrk",
            author = "Thích Nhất Hạnh",
            content = "There is no way to happiness, happiness is the way.",
            tags = arrayListOf("Famous Quotes"),
            authorSlug = "thich-nhat-hanh",
            length = 51,
            dateAdded = "2020-03-07",
            dateModified = "2023-04-14"
        ),
        Quote(
            id = "1wKZ34XOonuF",
            author = "Richard Bach",
            content = "Learning is finding out what you already know.",
            tags = arrayListOf("Famous Quotes"),
            authorSlug = "richard-bach",
            length = 46,
            dateAdded = "2020-03-11",
            dateModified = "2023-04-14"
        ),
        Quote(
            id = "oAtERLD0yyQR",
            author = "Laozi",
            content = "He who knows, does not speak. He who speaks, does not know.",
            tags = arrayListOf("Wisdom"),
            authorSlug = "laozi",
            length = 59,
            dateAdded = "2020-01-26",
            dateModified = "2023-04-14"
        ),
    )


    // Exposing as Flow
    fun getOfflineQuotes(): Flow<List<Quote>> = flow {
        emit(quotes)
    }
}
