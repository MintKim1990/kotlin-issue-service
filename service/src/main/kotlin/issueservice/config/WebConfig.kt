package issueservice.config

import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

@Configuration
class WebConfig(
    private val authUserHandlerArgumentResolver: AuthUserHandlerArgumentResolver
) : WebMvcConfigurationSupport() {

    // 인증을 위한 리졸버 추가
    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        argumentResolvers.apply { add(authUserHandlerArgumentResolver) }
    }
}

@Component
class AuthUserHandlerArgumentResolver : HandlerMethodArgumentResolver {

    // 인증 처리를 위한 리졸버로 요청 메서드 파라미터에 AuthUser클래스가 존재하는지 체크하는 로직
    override fun supportsParameter(parameter: MethodParameter): Boolean =
        AuthUser::class.java.isAssignableFrom(parameter.parameterType)

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        //TODO 인증로직 추가 예정
        return AuthUser(
            userId = 1,
            username = "테스트"
        )
    }
}

data class AuthUser(
    val userId: Long,
    val username: String,
    val profileUrl: String? = null
)