# README

본 프로젝트는 통합 알림 시스템 구축에 관련한 내용입니다.

### What is this repository for?

* 스프링 부트 기반의 프로젝트 입니다.

사용되는 오픈 소스는 아래와 같습니다.

* Spring Boot 1.5.7 (Spring Framework 4.3)
* Spring Data JPA
* Spring Security 4
* Thymeleaf 2
* mariaDB
* Gradle
* Lombok

## Coding Convention

#### 메소드 명
Layer | Read | Create | Update | Delete | 예
--- | --- | --- | --- | --- | ---
HTML Button ID | get | add | mod | del | member-add
Service/Controller | get | register | modify | remove | getMember
JPA Repository | find | save | save | delete | findByMemberName
Native Query | select | insert | update | delete | selectMember

#### 용어사전
영문 | 한글 | 비고
--- | --- | ---
alert | 알림 | 
content | 내용 | 
count | 건수 | 
datetime | 일시 | 
email | 이메일 | 
limit | 제한 | 
member | 멤버 | 알림 수신 대상
message | 메세지 | 
mobile | 휴대폰 | 
mod | 변경 |   
name | 명/이름 | 
no | 번호 | 
push | 푸시 |
reg | 등록 |
remarks | 비고 | 
resend | 재발송 | 
send | 발송 | 
smartPhone | 스마트폰 | 
staff | 사원 | 
status | 상태 | 
subject | 제목 | 
type | 유형/종류/타입 | 
yn | 여부 | 
