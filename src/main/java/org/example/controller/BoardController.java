package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.CommentCreateRequest;
import org.example.domain.User.UserDto;
import org.example.domain.board.BoardCreateRequest;
import org.example.domain.board.BoardDto;
import org.example.domain.board.BoardSearchRequest;
import org.example.domain.board.BookNaverResult;
import org.example.domain.enums.BoardCategory;
import org.example.service.BoardService;
import org.example.service.CommentService;
import org.example.service.GreatService;
import org.example.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/boards")
@Controller
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;
    private final CommentService commentService;
    private final GreatService greatService;


    // 게시글 작성
    @GetMapping("/write")
    public String boardWritePage(Model model) {

        model.addAttribute("boardCreateRequest", new BoardCreateRequest());
        return "boards/write";
    }

    @PostMapping("/write")
    public String boardWrite(@ModelAttribute BoardCreateRequest req, Authentication auth, Model model) throws IOException {
        if(req.getCategory() == null){
            model.addAttribute("message", "카테고리가 존재하지 않습니다.");
            model.addAttribute("nextUrl", "/");
            return "printMessage";
        }

        Long savedBoardId = boardService.writeBoard(req, auth.getName(), auth);
        model.addAttribute("message", savedBoardId + "번 글이 등록되었습니다.");
        model.addAttribute("nextUrl", "/boards/" + req.getCategory() + "/" + savedBoardId);
        return "printMessage";
    }

    @GetMapping("/book-search")
    public String bookSearchPage(Model model){
        String keyword = "";
        model.addAttribute("keyword", keyword);

        return "boards/bookSearch";
    }

    @PostMapping("/book-search")
    public String bookSearch(@ModelAttribute("keyword") String keyword, Model model) {
        if (keyword == null) {
            model.addAttribute("message", "검색어가 존재하지 않습니다.");
            model.addAttribute("nextUrl", "/");
            return "printMessage";
        }
        List<BookNaverResult> books = boardService.getBookTitle(keyword);
        model.addAttribute("books", books);

        return "boards/bookSearch";
    }

    // 게시글 상세 페이지
    @GetMapping("/{category}/{boardId}")
    public String boardDetailPage(@PathVariable String category, @PathVariable Long boardId, Model model,
                                  Authentication auth){
        if (auth != null) {
            model.addAttribute("loginUserEmail", auth.getName());
            model.addAttribute("greatCheck", greatService.checkGreat(auth.getName(), boardId));
        }

        BoardDto boardDto = boardService.getBoard(boardId, category);

        // id에 해당하는 게시글이 없거나 카테고리가 일치하지 않는 경우
        if (boardDto == null) {
            model.addAttribute("message", "해당 게시글이 존재하지 않습니다");
            model.addAttribute("nextUrl", "/boards/" + category);
            log.info("빈 값");
            return "printMessage";
        }

        model.addAttribute("boardDto", boardDto);
        model.addAttribute("category", category);

        model.addAttribute("commentCreateRequest", new CommentCreateRequest());
        model.addAttribute("commentList", commentService.findAll(boardId));
        return "boards/detail";
    }

    // 게시글 수정 페이지
    @GetMapping("/{category}/{boardId}/edit")
    public String boardEditPage(@PathVariable String category, @PathVariable Long boardId, Model model){

        BoardDto boardDto = boardService.getBoard(boardId, category);

        model.addAttribute("boardDto", boardDto);
        model.addAttribute("category", category);

        return "boards/edit";
    }

    @PostMapping("/{category}/{boardId}/edit")
    public String boardEdit(@PathVariable String category, @PathVariable Long boardId,
                            @ModelAttribute BoardDto dto, Model model) throws IOException {
        Long editedBoardId = boardService.editBoard(boardId, category, dto);

        if (editedBoardId == null) {
            model.addAttribute("message", "해당 게시글이 존재하지 않습니다.");
            model.addAttribute("nextUrl", "/boards/" + category);
        } else {
            model.addAttribute("message", editedBoardId + "번 글이 수정되었습니다.");
            model.addAttribute("nextUrl", "/boards/" + category + "/" + boardId);
        }
        return "printMessage";
    }


    // 게시글 전체 리스트
    @GetMapping("/list")
    public String boardListPage(Model model,
                                @RequestParam(required = false, defaultValue = "1") int page,
                                @RequestParam(required = false) String category,
                                @RequestParam(required = false) String sortType,
                                @RequestParam(required = false) String keyword){

        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("id").descending());
        if (sortType != null) {
            pageRequest = switch (sortType) {
                case "date" -> PageRequest.of(page - 1, 10, Sort.by("publishYear").descending());
                case "great" -> PageRequest.of(page - 1, 10, Sort.by("greatCount").descending());
                case "comment" -> PageRequest.of(page - 1, 10, Sort.by("commentCount").descending());
                default -> pageRequest;
            };
        }

        BoardCategory boardCategory = BoardCategory.of(category);
        System.out.println(boardCategory);

        model.addAttribute("boards", boardService.getBoardList(boardCategory, pageRequest, keyword));
        model.addAttribute("boardSearchRequest", new BoardSearchRequest(sortType, keyword, category));

        return "boards/list";
    }

    // 게시글 삭제
    @GetMapping("/{category}/{boardId}/delete")
    public String boardDelete(@PathVariable String category, @PathVariable Long boardId, Model model) throws IOException {
        if (category.equals("greeting")) {
            model.addAttribute("message", "가입인사는 삭제할 수 없습니다.");
            model.addAttribute("nextUrl", "/boards/greeting");
            return "printMessage";
        }

        Long deletedBoardId = boardService.deleteBoard(boardId, category);

        // id에 해당하는 게시글이 없거나 카테고리가 일치하지 않으면 에러 메세지 출력
        // 게시글이 존재해 삭제했으면 삭제 완료 메세지 출력
        model.addAttribute("message", deletedBoardId == null ? "해당 게시글이 존재하지 않습니다" : deletedBoardId + "번 글이 삭제되었습니다.");
        model.addAttribute("nextUrl", "/boards/list");
        return "printMessage";
    }


}
