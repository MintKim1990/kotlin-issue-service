package userservice.exception

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import mu.KotlinLogging
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Configuration
class GlobalExceptionHandler(private val objectMapper: ObjectMapper) : ErrorWebExceptionHandler {

    private val logger = KotlinLogging.logger {}

    // WebFlux API 기반 글로벌 Exception 에러 핸들링 메서드 오버라이딩
    // 코루틴에서 리액터를 지원하는 기능으로 mono 코루틴 스코프를 사용하여 리턴은 리액터 Mono를 리턴
    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> = mono {
        logger.error { ex.message }

        val errorResponse = if (ex is ServerException) {
            ErrorResponse(code = ex.code, message = ex.message)
        } else {
            ErrorResponse(code = 500, message = "Internal Server Error")
        }

        with(exchange.response) {
            statusCode = HttpStatus.OK
            headers.contentType = MediaType.APPLICATION_JSON
            writeWith(bufferFactory().wrap(objectMapper.writeValueAsBytes(errorResponse)).toMono()).awaitSingle()
        }
    }

}