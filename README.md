# 일정 관리 앱 만들기


API 명세서 리스트업
---
|기능          |Method|URL        |request param                                 |request body                                      |response body|error response body|상태코드|실패코드|
|--------------|------|---------|-----------------------------------------------|---------------------------------------------------|-------------|-------------------|--------|--------|
|일정 생성     |POST  |/schedule |                                              |{name : String, todo : String, details : String ...}|{message : "일정 생성에 성공하였습니다."}|{message : "오류코드 400 생성에 실패하였습니다."}|201: 정상 생성|400:생성 실패|
|전체 일정 조회|GET    |/schedules|user-id={user-id}                           |                                                    |{schduel_id : INT, details : Text, Created_Date : DATETIME, UPDATE_DATE : DATETIME}||{message : "오류코드 400 조회에 실패하였습니다."}|200: 정상 조회|400:조회 실패|
|선택 일정 조회|GET    |/schedules|user-id={user-id}&schedule-id={schedule-id} |                                                    |{schduel_id : INT, details : Text, Created_Date : DATETIME, UPDATE_DATE : DATETIME}|{message : "오류코드 400 조회에 실패하였습니다."}|200: 정상 조회|400:조회 실패|
|선택 일정 수정|PUT    |/schedule |                                            |{name : String, todo : String, details : String ...}|{message : "일정이 수정되었습니다."}|{message : "오류코드 400 수정에 실패하였습니다."}|200: 정상 수정|400:수정 실패|
|선택 일정 삭제|DELETE  |/schedule|                                            |{name : String, todo : String, details : String ...}|{message : "일정이 삭제되었습니다."}|{message : "오류코드 400 삭제에 실패하였습니다."}|200: 정상 삭제|400:삭제 실패|

ERD 구상
---
![image](https://github.com/user-attachments/assets/f42a820c-7b31-4b20-9e21-d743bb8f2682)
---
