package issueservice.domain.enums

enum class IssuePriority {

    LOW, MEDIUM, HIGH;

    companion object {
        // operator fun invoke 문법 사용시 IssuePriority("LOW") 형식으로 사용가능
        operator fun invoke(priority: String) = valueOf(priority.uppercase())
    }

}
