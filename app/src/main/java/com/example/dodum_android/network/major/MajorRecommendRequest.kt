package com.example.dodum_android.network.major

data class ObjItem(
    val num: Int,
    val select: Boolean
)

data class SubjItem(
    val num: Int,
    val text: String
)


data class MajorRecommendRequest(
    val obj: List<ObjItem>,
    val subj: List<SubjItem>
)