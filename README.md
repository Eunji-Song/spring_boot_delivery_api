# programmers_spring_api


프로그래머스_스진초5기 배달 어플 API
* 1차 개발 : 회원관리, 매장관리, 상품관리, 주문, 배달 관련 기능 개발
* 추후 관리자용, 사용자용 API 분리 개발 예정 

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

### product : 음식(상품) 테이블 
| Field       | Type           | Null | key | Default | Extra          |
|-------------|----------------|------|-----|------|----------------|
| id          | int(11)        | No   | PRI |      | auto_increment |
| name        | varchar(255)   | No   |     |      |                |
| description | text           | Yes  |     | NULL |                |
| store_id    | int            | No  |     |      |                |
| category_id | int            | No  |     |      |                |
| price       | DECIMAL(10, 2) | No  |     | 0    |                |
| created_at  | TIMESTAMP      | No  |     | CURRENT_TIMESTAMP     |                |
| updated_at  | TIMESTAMP      | No  |     | CURRENT_TIMESTAMP     |                |
