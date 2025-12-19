package com.example.dodum_android.network.misc

enum class MiscCategory(
    val id: String
) {
    LECTURE_RECOMMENDATION("lecture"),
    TOOL_RECOMMENDATION("tool"),
    PLATFORM_RECOMMENDATION("platform"),
    SCHOOL_SUPPORT("school_support");

    companion object {
        fun fromId(id: String): MiscCategory =
            entries.first { it.id == id }
    }
}

enum class MiscCriteria {
    LATEST,
    LIKES,
    VIEWS
}