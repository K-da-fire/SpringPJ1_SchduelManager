# API 명세서 작성하기

---


| 기능           | Method | URL              | request param                                                 | request body                                                              | response body                                                                                                                                        | 상태코드         | 
| -------------- | ------ | ---------------- | ------------------------------------------------------------- | ------------------------------------------------------------------------- |------------------------------------------------------------------------------------------------------------------------------------------------------|--------------| 
| 일정 생성      | POST   | /schedule        |                                                               | {userId : long, scheduleTitle : String, name : String, password : String} | {scheduleId : long, userId : long, name : String, password : String, createdDate : LocalDate, updatedDate : LocalDate, scheduleTitle : String}       | 201: created |
| 전체 일정 조회 | GET    | /schedules       | userId = {userId}, name = {name}, updatedDate = {updatedDate} |                                                                           | \[ {scheduleId : long, userId : long, name : String, password : String, createdDate : LocalDate, updatedDate : LocalDate, scheduleTitle : String} \] | 200: OK  |
| 선택 일정 조회 | GET    | /schedules/ {id} | scheduleId = {scheduleId}                                     |                                                                           | {scheduleId : long, userId : long, name : String, password : String, createdDate : LocalDate, updatedDate : LocalDate, scheduleTitle : String}       | 200: OK  |
| 선택 일정 수정 | PATCH  | /schedule/ {id}  | scheduleId = {scheduleId}                                     | {userId : long, scheduleTitle : String, name : String, password : String} | {scheduleId : long, userId : long, name : String, password : String, createdDate : LocalDate, updatedDate : LocalDate, scheduleTitle : String}       | 200: OK  |
| 선택 일정 삭제 | DELETE | /schedule/ {id}  | scheduleId = {scheduleId}, password = {password}              |                                                                           | {message : 일정을 삭제하였습니다.}                                                                                                                             | 200: OK  |

---



# ERD작성

![img.png](img.png)

---
