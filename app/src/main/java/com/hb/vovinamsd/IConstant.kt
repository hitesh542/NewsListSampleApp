package com.hb.vovinamsd

interface IConstant {
    companion object {
        const val API_KEY = "2326f6505c94411ab5860e73d33cd7e6"
        const val DB_NAME = "hb_vovinam_sd.db"
    }

    enum class SortBy(val key: String) {
        PUBLISHED_AT("publishedAt"),
        POPULARITY("popularity")
    }

    enum class Source(val key: String) {
        TECH_CRUNCH("techcrunch")
    }

    enum class ApiState(var errorCode: Int, var message: String?) {
        LOADING(200, "SHOW_LOADER"),
        ERROR(500, "ERROR"),
        SUCCESS(200, "SUCCESS")
    }
}