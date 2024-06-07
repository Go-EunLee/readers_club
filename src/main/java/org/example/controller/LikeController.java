package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.service.BoardService;
import org.example.service.GreatService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/greats")
@RequiredArgsConstructor
public class LikeController {

    private final GreatService greatService;
    private final BoardService boardService;

    @GetMapping("/add/{boardId}")
    public String addLike(@PathVariable Long boardId, Authentication auth) {
        greatService.addLike(auth.getName(), boardId);
        return "redirect:/boards/" + boardService.getCategory(boardId) + "/" + boardId;
    }

    @GetMapping("/delete/{boardId}")
    public String deleteLike(@PathVariable Long boardId, Authentication auth) {
        greatService.deleteLike(auth.getName(), boardId);
        return "redirect:/boards/" + boardService.getCategory(boardId) + "/" + boardId;
    }
}