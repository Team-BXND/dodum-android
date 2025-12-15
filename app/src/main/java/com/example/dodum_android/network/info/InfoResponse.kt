package com.example.dodum_android.network.info

// GET /info
data class InfoListResponse(
    val status: Int,
    val data: List<InfoListData>
)

data class InfoListData(
    val id: Int,
    val title: String,
    val author: String,
    val likes: Int,
    val view: Int,
    val comment: Int,
    val image: String
)

// GET /info/{Category}
data class InfoCategoryResponse(
    val status: Int,
    val data: List<InfoCategoryData>
)

data class InfoCategoryData(
    val title: String,
    val author: String,
    val likes: Int,
    val view: Int,
    val comment: Int
)

// POST /info
data class InfoPostResponse(
    val status: Int,
    val data: String
)

// DELETE /info/{id}
data class InfoDeleteResponse(
    val status: Int,
    val data: String
)

// PUT /info/{id}
data class InfoPutResponse(
    val status: Int,
    val data: String
)

// GET /info/{id}
data class InfoDetailResponse(
    val status: Int,
    val data: InfoDetailData
)

data class InfoDetailData(
    val title: String,
    val content: String,
    val author: String,
    val date: String // LocalDateTime → String
)

// POST /info/{id}/comments
data class InfoCommentResponse(
    val status: Int,
    val data: String
)

// POST /info/{id}/like
data class InfoLikeResponse(
    val success: Boolean
)

// POST /info/{id}/approve
data class InfoApproveResponse(
    val success: Boolean
)

// POST /info/{id}/disapprove
data class InfoDisapproveResponse(
    val success: Boolean
)
