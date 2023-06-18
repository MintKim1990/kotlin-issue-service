package issueservice.web

import issueservice.config.AuthUser
import issueservice.model.CommentRequest
import issueservice.service.CommentService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/issues/{issueId}/comments")
class CommentController(
    private val commentService: CommentService
) {

    @PostMapping
    fun create(
        authUser: AuthUser,
        @PathVariable issueId: Long,
        @RequestBody request: CommentRequest
    ) = commentService.create(issueId, authUser.userId, authUser.username, request)



}