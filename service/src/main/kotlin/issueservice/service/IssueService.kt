package issueservice.service

import issueservice.domain.Issue
import issueservice.domain.IssueRepository
import issueservice.domain.enums.IssueStatus
import issueservice.exception.NotFoundException
import issueservice.model.IssueRequest
import issueservice.model.IssueResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class IssueService (
    private val issueRepository: IssueRepository
){

    @Transactional
    fun create(userId: Long, request: IssueRequest) : IssueResponse {
        val issue = Issue(
            summary = request.summary,
            description = request.description,
            userId = userId,
            type = request.type,
            priority = request.priority,
            status = request.status
        )
        return IssueResponse(issueRepository.save(issue))
    }

    fun getAll(status: IssueStatus) =
        issueRepository.findAllByStatusOrderByCreatedAtDesc(status)
            ?.map { IssueResponse(it) }


    fun get(id: Long): IssueResponse {
        val issue = issueRepository.findByIdOrNull(id)
            ?: throw NotFoundException("이슈가 존재하지 않습니다.")

        return IssueResponse(issue)
    }

    @Transactional
    fun edit(userId: Long, id: Long, request: IssueRequest): IssueResponse {
        val issue = (issueRepository.findByIdOrNull(id)
            ?: throw NotFoundException("이슈가 존재하지 않습니다."))

        return with(issue) {
            summary = request.summary
            description = request.description
            this.userId = userId
            type = request.type
            priority = request.priority
            status = request.status
            // 변경감지가 동작하지만 명시적으로 보여주기 위해 save 호출
            IssueResponse(issueRepository.save(this))
        }
    }

    fun delete(id: Long) {
        issueRepository.deleteById(id)
    }


}