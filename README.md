# 🟣 ItemBay - 상품 관리 API

[![Build](https://github.com/rlawodnv2/itembay/actions/workflows/gradle.yml/badge.svg)](https://github.com/rlawodnv2/itembay/actions/workflows/gradle.yml)
[![Coverage](https://img.shields.io/badge/coverage-100%25-brightgreen)]()  
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-blue)](https://spring.io/projects/spring-boot)  
[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)  

Spring Boot로 구현된 **Item 관리 REST API** 프로젝트입니다.  
상품 CRUD, 검색/필터, 페이징, 정렬, 유효성 검사, 캐싱, 동시성 처리까지 포함된 프로젝트입니다.

---

## 🔹 프로젝트 개요

- Spring Boot 3.2.1 + Java 17
- Spring Data JPA + H2 In-Memory Database
- Spring Cache 적용 (상품 목록 조회 캐싱)
- JUnit 5 + MockMvc 통합 테스트
- Optimistic Locking 적용 (동시 수정 처리)
- 초기 데이터(Seed Data) 포함

---

## 🔹 기술 스택

| 구분        | 기술/라이브러리                       |
| ----------- | ------------------------------------ |
| 언어        | Java 17                              |
| 프레임워크  | Spring Boot 3.x                       |
| DB          | H2 In-Memory                          |
| JPA         | Spring Data JPA                       |
| 캐싱       | Spring Cache (ConcurrentMap)          |
| 테스트      | JUnit 5, MockMvc                       |
| 빌드        | Gradle                                 |

---

## 🔹 프로젝트 구조
com.itembay.item
├─ controller # REST API 컨트롤러
├─ domain # 엔티티, enum
├─ dto # 요청/응답 DTO
├─ repository # JPA Repository
├─ service # 비즈니스 로직
├─ specification # 동적 검색/필터 조건
└─ config # 초기 데이터 삽입 (ItemSeedDataRunner)

---

## 🔹 주요 기능

### 1️⃣ 상품 CRUD

- **Create**: `POST /api/items`  
  - 필수 필드 검증
  - 수량/가격 양수만 허용
  - `ItemType` enum 값만 허용
  - 실패 시 `400 Bad Request` + 상세 메시지
  - 성공 시 `201 Created` + 생성된 상품 반환

- **Read**: `GET /api/items`
  - 페이징, 정렬, 검색 가능
  - 캐싱 적용 (조회 성능 향상)

- **Update**: `PUT /api/items/{id}`
  - 상품 수정
  - Optimistic Locking 적용 (동시 수정 방지)

- **Delete**: `DELETE /api/items/{id}`
  - 상품 삭제

### 2️⃣ 검색/필터/정렬

- `keyword`로 상품명 검색
- `sortTypes`: `PRICE`, `CREATED_AT`
- `directions`: `ASC`, `DESC`
- 페이징: `page`, `size` 파라미터

### 3️⃣ 캐싱

- 상품 목록 조회 API에 Spring Cache 적용
- 동일 조회 요청 시 DB 부하 감소

### 4️⃣ 동시성 처리

- JPA **Optimistic Locking** 적용
- 동일 상품 동시 수정 시 `OptimisticLockException` 발생

### 5️⃣ 초기 데이터(Seed Data)

- 애플리케이션 실행 시 기본 상품 데이터 자동 삽입
- `ItemSeedDataRunner` 클래스 사용
- 위치: `src/main/java/com/itembay/item/seed/ItemSeedDataRunner.java`

---

## 🔹 API 예시

### 1️⃣ 상품 등록
**POST /api/items**  

```json
{
  "server": "라엘08",
  "sellerName": "아리",
  "itemType": "GAME_MONEY",
  "title": "1,000,000다이아 일괄 판매합니다.",
  "price": 100000,
  "quantity": 1000000
}

Response (201 Created):
{
  "id": 1,
  "server": "라엘08",
  "sellerName": "아리",
  "itemType": "GAME_MONEY",
  "title": "1,000,000다이아 일괄 판매합니다.",
  "price": 100000,
  "quantity": 1000000,
  "createdAt": "2026-01-08T12:00:00"
}
```

### 2️⃣ 상품 목록 조회
GET /api/items?page=0&size=10&sortTypes=PRICE&directions=ASC&keyword=다야

Response (200 OK):
```json
{
  "content": [
    {
      "id": 1,
      "title": "다야 팝니다",
      "price": 10000,
      "quantity": 1000
    },
    {
      "id": 2,
      "title": "아이템 판매",
      "price": 50000,
      "quantity": 1
    }
  ],
  "page": 0,
  "size": 10,
  "totalElements": 2,
  "totalPages": 1
}
```
### 3️⃣ 상품 수정
PUT /api/items/{id}

Request:
```json
{
  "server": "라엘08",
  "sellerName": "아리",
  "itemType": "GAME_MONEY",
  "title": "수정된 제목",
  "price": 20000,
  "quantity": 500
}
```
Response (200 OK):
```json
{
  "id": 1,
  "title": "수정된 제목",
  "price": 20000
}
```

### 4️⃣ 상품 삭제

DELETE /api/items/{id}
Response (200 OK):
```json
{
  "id": 1,
  "title": "삭제된 상품",
  "price": 20000
}
```

# 🔹 테스트
```
실행 방법
./gradlew test
## 또는
./mvnw test
```

상품 등록, 조회, 수정, 삭제 API 테스트 포함
성공/실패 케이스 모두 검증
Optimistic Locking 테스트 포함

# 🔹 실행 방법
프로젝트 클론
git clone https://github.com/<username>/itembay.git
cd itembay

Gradle 빌드 및 실행
./gradlew bootRun

H2 콘솔 접속
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:itemdb
Username: sa
Password: (빈 값)

## 🔹 배포 및 환경
개발/테스트용: H2 In-Memory DB
프로덕션 배포 시 MySQL/PostgreSQL 등 RDBMS 적용 가능
캐싱: Spring Cache + ConcurrentMap (프로덕션 시 Redis로 변경 가능)

## 🔹 참고
### 초기 데이터(Seed Data)
- `src/main/java/com/itembay/item/config/ItemSeedDataRunner.java` 포함
- 애플리케이션 실행 시 기본 상품 데이터 자동 삽입
- **캐싱**: 상품 목록 조회 API에 Spring Cache 적용 (조회 성능 향상)
- **동시성 처리**: Optimistic Locking 적용 (동일 상품 동시 수정 시 `OptimisticLockException` 발생)
- **검증**: 상품 생성/수정 요청 시 필수 필드, 양수 가격/수량, Enum 검증 포함 (실패 시 400 Bad Request)

### 테스트 코드
- 위치: `src/test/java/com/itembay/item/controller/ItemControllerTest.java`, `ItemCreateRequestTest.java`, `ItemServiceTest.java`
- 상품 등록/조회/수정/삭제, Validation, Optimistic Locking 테스트 포함
- 실행: `./gradlew test`
