## 📍 Project 소개
Wssrs [Work Scholarship Student Recruitment Service] 

2024 국민대학교 생활협동조합 일반 근로 장학생 모집 서비스입니다.

## 📍 기술 스택
- java : 17
- Spring boot : 3.3.1
- build : Gradle
- DB : PostgreSQL, Redis
- Cloud
    - AWS EC2
    - AWS S3
    - AWS RDS
    - Docker
- CI / CD : GitHub Actions
- Collaboration : Notion, Swagger-ui

## 🗓️ 개발 기간
2024.07 ~ 2024.08

## 👨🏻‍💻 팀원

- [20223098 신진욱 (FrontEnd)](https://github.com/jen454)
- [20203103 류건 (BackEnd)](https://github.com/U-Geon)


## 📍 사용 라이브러리
- ```org.springframework.boot:spring-boot-starter-data-jpa```
- ```org.springframework.boot:spring-boot-starter-security```
- ```org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0```
- ```org.springframework.boot:spring-boot-starter-validation```
- ```com.auth0:java-jwt:4.4.0```
- ```org.springframework.boot:spring-boot-starter-data-redis```
- ```org.springframework.cloud:spring-cloud-starter-aws```

## 📍 상세 기술
### 1. Auth
Spring Security + JWT 활용하여 구현.

- 로그인
  - 로그인 시 accessToken, RefreshToken 발급. refreshToken은 Redis에 저장. 
- 로그아웃
  - Redis 내 refresh Token 파기
  - 받은 access token을 Redis 안에 **JWT BlackList**로써 삽입 (request header에 BlackList 토큰이 있으면 UnAuthorization Exception 처리)
- 회원조회
- 비밀번호 재설정
- 회원가입
  - 비밀번호 암호화 (PasswordEncoder)
- Access Token 재발급
  - Refresh Token을 통해 유효성 검사 후 new Access Token 발급

### 2. Admin

관리자 페이지 기능

- 공고 생성
  - 생성 시 이미지 파일은 S3에 upload
- 공고 삭제
  - S3 내 이미지 파일도 같이 제거
- 공고 목록
- 지원자 목록
- 근무 확정

### 3. User

일반 회원 페이지 기능

- 공고 목록
- 상세 공고 확인
- 해당 공고 근로 지원

## 📍 프로젝트 설치 및 실행 방법

1. 리포지토리를 클론합니다.

```
git clone https://github.com/U-Geon/wssrs-backend.git
```

2. main/resource/application-private.yml을 생성해줍니다.
```
redis:
  host: {ip}
  port: 6379
  password: {password}

db:
  url: {url}
  username: {username}
  password: {password}

jwt:
  secret-key: {secret-key}
  access-token:
    expiration: 12
  refresh-token:
    expiration: 24

s3:
  bucket: {bucket-name}
  accessKey: {access-key}
  secretKey: {secret-key}
  region: ap-northeast-2

admin:
  email: admin
  password: admin12!@
```

3. 프로젝트를 실행합니다.
