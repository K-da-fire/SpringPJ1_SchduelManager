# API 명세서 작성하기

---

|기능|Method|URL|request param| request body| response body                                                                                  | error response body                 | 상태코드        | 실패코드        |
|---|------|---|-------------|-------------|------------------------------------------------------------------------------------------------|-------------------------------------|-------------|-------------|
|일정 생성|POST|/schedule|| {userId : long, scheduleTitle : String, name : String, password : String, createdDate : LocalDate}    | {message : "일정 생성에 성공하였습니다."}                                                                  | {message : "오류코드 400 생성에 실패하였습니다."} | 201 : 정상 생성 | 400 : 생성 실패 |
|전체 일정 조회|GET|/schedules||| {scheduleId : long, scheduleTitle : String, createdDate : LocalDate, updatedDate : LocalDate}  | {message : "오류코드 400 조회에 실패하였습니다."} | 200: 정상 조회  | 400:조회 실패   |
|선택 일정 조회|GET|/schedules/ {id} |scheduleId = {scheduleId} ||  {scheduleId : long, scheduleTitle : String, createdDate : LocalDate, updatedDate : LocalDate} | {message : "오류코드 400 조회에 실패하였습니다."} | 200: 정상 조회  | 400:조회 실패   |
|선택 일정 수정|PATCH|/schedule/ {id} |scheduleId = {scheduleId} | {scheduleId : long, scheduleTitle : String, name : String, password : String, updatedDate : LocalDate} | {message : "일정이 수정되었습니다."}                                                                     | {message : "오류코드 400 수정에 실패하였습니다."} | 200: 정상 수정  | 400:수정 실패   |
|선택 일정 삭제|DELETE|/schedule/ {id}|scheduleId = {scheduleId}|                                                                                                       | {message : "일정이 삭제되었습니다."}                                                                     | {message : "오류코드 400 삭제에 실패하였습니다."} | 200: 정상 삭제  | 400:삭제 실패   |
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# ERD작성

![img.png](img.png)
