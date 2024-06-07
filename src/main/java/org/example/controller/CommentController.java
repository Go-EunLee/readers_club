package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.CommentCreateRequest;
import org.example.domain.board.Board;
import org.example.service.BoardService;
import org.example.service.CommentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class CommentController {
    private final BoardService boardService;
    private final CommentService commentService;

    @PostMapping("/{boardId}")
    public String addComments(@PathVariable Long boardId, @ModelAttribute CommentCreateRequest req,
                              Authentication auth, Model model) {
        log.info("댓글의 게시글 번호 : " + boardId.toString());
        commentService.writeComment(boardId, req, auth.getName());
        int commentCnt = boardService.getCommentCount(boardId);
        log.info("끝나고 댓글 수 : " + commentCnt);

        model.addAttribute("message", "댓글이 추가되었습니다.");
        model.addAttribute("nextUrl", "/boards/" + boardService.getCategory(boardId) + "/" + boardId);
        return "printMessage";
    }

    // 댓글 수정
    @PostMapping("/{commentId}/edit")
    public String editComment(@PathVariable Long commentId, @RequestParam(name = "newBody") String newBody,
                              Authentication auth, Model model) {
        log.info("수정 전 새 댓글 내용 : " + newBody);
        Long boardId = commentService.editComment(commentId, newBody, auth.getName());
        log.info("컨트롤러 댓글 내용 : " + commentService.findComment(commentId).getBody());
        model.addAttribute("message", boardId == null? "잘못된 요청입니다." : "댓글이 수정 되었습니다.");
        model.addAttribute("nextUrl", "/boards/" + boardService.getCategory(boardId) + "/" + boardId);
        return "printMessage";
    }

    @GetMapping("/{commentId}/delete")
    public String deleteComment(@PathVariable Long commentId, Authentication auth, Model model) {
        Long boardId = commentService.deleteComment(commentId, auth.getName());
        model.addAttribute("message", boardId == null? "작성자만 삭제 가능합니다." : "댓글이 삭제 되었습니다.");
        model.addAttribute("nextUrl", "/boards/" + boardService.getCategory(boardId) + "/" + boardId);
        return "printMessage";
    }
}
