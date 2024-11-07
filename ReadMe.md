일정 관리 api 구현하기
===

## 주요 기능

---

# 일정
### 일정 생성
* 식별자(ID), 할 일, 이름, 비밀번호, 작성/수정일을 일정 테이블에 생성
### 일정 조회
* 이름과 수정일을 기준으로 일정 다건 조회
* 식별자(ID)를 기준으로 일정 단건 조회
### 일정 수정
* 식별자(ID)와 비밀번호를 바탕으로 할 일, 이름 수정
### 일정 삭제
* 식별자(ID)와 비밀번호를 바탕으로 일정 삭제

# 유저
### 유저 생성
* 식별자(ID), 이름, 이메일, 비밀번호, 작성/수정일을 유저 테이블에 생성
### 유저 조회
* 전체 유저 다건 조회
* 식별자(ID)를 기준으로 유저 단건 조회
### 유저 수정
* 식별자(ID)와 비밀번호를 바탕으로 이름, 이메일 수정
### 유저 삭제
* 식별자(ID)와 비밀번호를 바탕으로 유저 삭제

---

# API 명세서 작성하기

---


| 기능       | Method | URL              | request param                                                 | request body                                                         | response body                                                                                                                                   | 상태코드        | 
|----------|--------|------------------|---------------------------------------------------------------|----------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------|-------------| 
| 일정 생성    | POST   | /schedules       |                                                               | {userId : long, todoList : String, name : String, password : String} | {scheduleId : long, userId : long, name : String, password : String, createdDate : LocalDate, updatedDate : LocalDate, todoList : String}       | 201: created |
| 전체 일정 조회 | GET    | /schedules       | userId = {userId}, name = {name}, updatedDate = {updatedDate} |                                                                      | \[ {scheduleId : long, userId : long, name : String, password : String, createdDate : LocalDate, updatedDate : LocalDate, todoList : String} \] | 200: OK     |
| 선택 일정 조회 | GET    | /schedules/{id} | scheduleId = {scheduleId}                                     |                                                                      | {scheduleId : long, userId : long, name : String, password : String, createdDate : LocalDate, updatedDate : LocalDate, todoList : String}       | 200: OK     |
| 선택 일정 수정 | PATCH  | /schedules/{id} | scheduleId = {scheduleId}                                     | {userId : long, todoList : String, name : String, password : String} | {scheduleId : long, userId : long, name : String, password : String, createdDate : LocalDate, updatedDate : LocalDate, todoList : String}       | 200: OK     |
| 선택 일정 삭제 | DELETE | /schedules/{id} | scheduleId = {scheduleId}                                     | {password : String}                                                  | {message : 일정을 삭제하였습니다.}                                                                                                                        | 200: OK     |
| 유저 생성    | POST   | /users           |                                                               | {email : String, name : String, password : String}                   | {userId : long, email : String, name : String, password : String, createdDate : LocalDate, updatedDate : LocalDate}                             | 201: created |
| 전체 유저 조회 | GET    | /users           |                                                               |                                                                      | \[ {userId : long, email : String, name : String, password : String, createdDate : LocalDate, updatedDate : LocalDate} \]                       | 200: OK     |
| 선택 유저 조회 | GET    | /users/{id}     | userId = {userId}                                             |                                                                      | {userId : long, email : String, name : String, password : String, createdDate : LocalDate, updatedDate : LocalDate}                             | 200: OK     |
| 선택 유저 수정 | PATCH  | /users/{id}     | userId = {userId}                                             | {email : String, name : String, password : String}                   | {userId : long, email : String, name : String, password : String, createdDate : LocalDate, updatedDate : LocalDate}                             | 200: OK     |
| 선택 유저 삭제 | DELETE | /users/{id}     | userId = {userId}                                             | {password : String}                                                  | {message : 유저를 삭제하였습니다.}                                                                                                                        | 200: OK     |
---



# ERD작성

![img.png](img.png)

---