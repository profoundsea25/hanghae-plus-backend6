# 항해 플러스 백엔드 6기 사전스터디 - 프레임워크 스터디
- 게시판 만들기

### Architecture
- 클린 아키텍처 적용
- 모듈 설명
  - `bootstrap` : 스프링 애플리케이션 구동 모듈
  - `domain` : 각 도메인에 해당하는 모듈
    - `{도메인명}-adapter-in` : In Port
    - `{도메인명}-adapter-out` : Out Port
    - `{도메인명}-application` : In&Out Port 조합, 서비스를 제외한 클래스는 플레인한 코틀린 클래스 (외부 의존성 결합도를 낮추기 위함)

### Stack
- Kotlin 1.9
- Spring Boot 3.3.2
  - Spring Web
  - Spring Data JPA

### UseCase
- 각 모듈별 http 파일 참조 (.http 파일 검색)
- 기능
  - 회원 생성
  - 로그인
  - 게시판 CRUD
  - 댓글 CRUD
