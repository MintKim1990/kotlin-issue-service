package issueservice.model

import issueservice.domain.Comment

data class CommentRequest(
    val body: String
)

data class CommentResponse(
    val id: Long,
    val issueId: Long,
    val userId: Long,
    val body: String,
    val username: String? = null
)

// DTO를 확장함수 형태로 사용하는 방식
fun Comment.toResponse(): CommentResponse = CommentResponse(
    id = id!!,
    issueId = issue.id!!,
    userId = userId,
    body = body,
    username = username
)