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



## 사용 기술


### 아키텍처


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
