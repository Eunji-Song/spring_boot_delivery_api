# 프로그래머스_스진초5기 배달 어플 API
<br/>
 

# API 목록

### 회원
1. 회원가입 : POST /users/join
2. 로그인 : POST /users/login
3. 로그아웃 : POST /users/logout
4. 회원탈퇴 : DELETE /users/withdrawal
5. 회원 정보 수정 : PUT /users

### 음식/상품
1. 등록 : POST /products
2. 상품 목록 조회 : GET /products
3. 상품 상세 조회 : GET /products/{id}
4. 수정 : PUT /products/{id}
5. 삭제 : DELETE /products/{id}

### 음식점
1. 음식점 등록 : POST /stores
2. 음식점 정보 수정 : PUT /stores/{id}
3. 음식점 삭제 : DELETE /stores/{id}
4. 음식점 정보 상세 조회 : GET /stores/{id}
5. 등록된 음식점 목록 조회 : GET /stores

### 주문
1. 주문 생성 : POST /orders
2. 주문 취소(삭제) : DELETE /orders/{id}
3. 주문 상태 조회 : GET /orders/{id}


### 배달 상태 
1. 배달 목록 : GET /admin/orders/delivering
2. 배달 현황 조회 : GET /admin/orders/delivering/{id}
3. 배달 시작(생성) : POST /admin/orders/delivering
4. 배달 상태 변경 : PUT /admin/orders/delivering/status/{id}


--- 
## DB

### user : 사용자
| Field      | Type         | Null | key | Default           | Extra          | comment                               |
|------------|--------------|------|-----|-------------------|----------------|---------------------------------------|
| id         | int(11)      | No   | PRI |                   | auto_increment | 고유값                                   |
| user_id    | varchar(255) | No   |     |                   |                | 계정 id                                 |
| name       | varchar(255) | No   |     |                   |                | 사용자 이름                                |
| password   | varchar(255) | No   |     |                   |                | 비밀번호                                  |
| email      | varchar(255) | No   |     |                   |                | 이름                                    |
| grade      | int(11)      | No   |     | 'Basic'           |                | 등급 (1:basic, 2:silver, 3:gold, 4:vip) |
| created_at | TIMESTAMP    | No   |     | CURRENT_TIMESTAMP |                | 가입일자                                  |
| updated_at | TIMESTAMP    | No   |     | CURRENT_TIMESTAMP |                | 수정일자                                  |

<br/>


### product : 음식(상품) 테이블 
| Field       | Type           | Null | key | Default           | Extra          |
|-------------|----------------|------|-----|-------------------|----------------|
| id          | int(11)        | No   | PRI |                   | auto_increment |
| name        | varchar(255)   | No   |     |                   |                |
| description | text           | Yes  |     | NULL              |                |
| store_id    | int            | No   |     |                   |                |
| category_id | int            | No   |     |                   |                |
| price       | DECIMAL(10, 2) | No   |     | 0                 |                |
| created_at  | TIMESTAMP      | No   |     | CURRENT_TIMESTAMP |                |
| updated_at  | TIMESTAMP      | No   |     | CURRENT_TIMESTAMP |                |

<br/>

### store : 매장 
| Field              | Type          | Null | key | Default           | Extra          | comment |
|--------------------|---------------|------|-----|-------------------|----------------|--------|
| id                 | int(11)       | No   | PRI |                   | auto_increment |        |
| name               | varchar(255)  | No   |     |                   |                | 매장명    |
| address            | varchar(255)  | No   |     |                   |                | 매장 주소  |
| detail_address     | varchar(255)  | No   |     |                   |                |        |
| del_yn             | char(1)       | NO   |     | N                 |                | Y or N |
| owner_name         | varchar(255)  | NO   |     |                   |                |        |
| owner_phone_number | varchar(255)  | NO   |     |                   |                |        |
| created_at         | TIMESTAMP     | No   |     | CURRENT_TIMESTAMP |                | 생성 일자  |
| updated_at         | TIMESTAMP     | No   |     | CURRENT_TIMESTAMP |                | 수정 일자  |

<br/>

### order : 주문 테이블 
| Field          | Type           | Null | key | Default           | Extra          | comment                                                  |
|----------------|----------------|------|-----|-------------------|----------------|----------------------------------------------------------|
| id             | int(11)        | No   | PRI |                   | auto_increment |                                                          |
| user_id        | int(11)        | No   |     |                   |                | 사용자 고유 번호                                                |
| store_id       | int(11)        | No   |     |                   |                | 매장 고유 번호                                                 |
| total_price    | DECIMAL(10, 2) | No   |     | 0                 |                |                                                          |
| status         | int(11)        | NO   |     | 1                 |                | 1:주문 생성, 2: 조리 시작, 3: 조리 완료, 4: 배달 시작, 5: 배달 완료, 6:주문 취소 |
| address        | varchar(255)   | No   |     |                   |                | 배달지 주소                                                   |
| detail_address | varchar(255)   | No   |     |                   |                | 배달지 상세 주소                                                |
| created_at     | TIMESTAMP      | No   |     | CURRENT_TIMESTAMP |                | 생성 일자                                                    |
| updated_at     | TIMESTAMP      | No   |     | CURRENT_TIMESTAMP |                | 수정 일자                                                    |

<br/>

### order_products : 주문한 상품 정보 
| Field              | Type         | Null | key | Default           | Extra          | comment  |
|--------------------|--------------|------|-----|-------------------|----------------|----------|
| id                 | int(11)      | No   | PRI |                   | auto_increment |          |
| order_id           | int(11)      | No   |     |                   |                | 주문 고유 번호 |
| product_id         | int(11)      | No   |     |                   |                | 상품 고유 번호 |
| del_yn             | char(1)      | NO   |     | N                 |                | Y or N   |
| created_at         | TIMESTAMP    | No   |     | CURRENT_TIMESTAMP |                | 생성 일자    |
| updated_at         | TIMESTAMP    | No   |     | CURRENT_TIMESTAMP |                | 수정 일자    |