# 리더스 클럽

>리더스 클럽은 책을 검색하고, 당신만의 후기를 작성하여 다양한 **독자들과 생각을 나누는 공간**입니다.
>
>댓글을 통해 서로의 의견을 주고받고, 좋아요로 공감을 표현하세요.
>
>책에 대한 깊이 있는 토론과 새로운 관점을 얻을 수 있는 이곳에서 독서의 즐거움을 함께 느껴보세요.

<br>

###

```
├── 게시글
│   ├── 게시글 작성 / 수정 / 삭제
│   ├── 네이버 API 를 이용한 도서 검색
│   ├── 게시글 읽기
│   ├── 게시글 검색
│   ├── 게시글 정렬
│   ├── 전체 게시글 읽기
│
├── 댓글
│   ├── 댓글 작성 / 수정 / 삭제
│
├── 좋아요
│   ├── 좋아요 선택/해제
│
├── 회원
│   ├── 회원가입 / 로그인
│   ├── 회원 정보 수정
│   ├── 작성한 게시글 조회
│   ├── 작성한 댓글 조회
│   ├── 선택한 좋아요 조회
│

```

## 화면 구성
|메인 페이지|개별 게시글|
|:----------------:|:---------------:|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://github.com/user-attachments/assets/28487e07-3c44-4666-85f9-bf67c110958e">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|<img src="https://github.com/user-attachments/assets/cba9396f-e48f-4bdf-b43c-d65e8155504e">|
|도서 검색|마이 페이지|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://github.com/user-attachments/assets/84964e4c-688c-446f-a6a5-979f9db7a257">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|<img src="https://github.com/user-attachments/assets/eda17502-7953-4a16-9f0a-82cbe455b869">|

<br>

## 사용 기술
<div align=center>
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
  <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> 
  <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> 
  <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> 
  <br>
  
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> 
  <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> 
  <img src="https://img.shields.io/badge/bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white">
  <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
  <br>
  
</div>

<br>
<br>

## 프로젝트 구조
```
│
├── config
│   └── auth
│   ├── EncryptConfig
│   ├── JpaAuditingConfig
│   ├── WebSecurityConfig
│
├── controller
│   ├── BoardController
│   ├── CommentController
│   ├── LikeController
│   ├── UserController
│
├── repository
│   ├── BoardRepository
│   ├── CommentRepository
│   ├── LikeRepository
│   ├── UserRepository
│
├── service
│   ├── BoardService
│   ├── CommentService
│   ├── LikeService
│   ├── UserService
│
├── domain
│   └── board
│   └── comment
│   └── like
│   └── user
│
```
