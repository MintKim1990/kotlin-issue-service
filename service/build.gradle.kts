// 해당 프로젝트에서 JPA 관련 내용을 진행하므로 플러그인 활성화
apply(plugin = "kotlin-jpa")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
}
