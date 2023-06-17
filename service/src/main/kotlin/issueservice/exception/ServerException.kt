package issueservice.exception

// sealed 키워드를 붙여 해당 클래스를 상속한 클래스를 컴파일러가 인지
sealed class ServerException(
    val code: Int,
    override val message: String
) : RuntimeException(message)

data class NotFoundException(
    override val message: String
) : ServerException(404, message)

data class UnauthorizedException(
    override val message: String = "인증 정보가 잘못되었습니다."
) : ServerException(401, message)