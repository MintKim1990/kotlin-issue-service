package issueservice.domain.enums

enum class IssueType {

    BUG, TASK;

    companion object {
        // operator fun invoke 문법 사용시 IssueType("BUG") 형식으로 사용가능
        operator fun invoke(type: String) = valueOf(type.uppercase())
    }

}
