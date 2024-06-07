package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.User.User;
import org.example.domain.User.UserDto;
import org.example.domain.User.UserJoinRequest;
import org.example.domain.User.UserLoginRequest;
import org.example.service.BoardService;
import org.example.service.CommentService;
import org.example.service.GreatService;
import org.example.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final BoardService boardService;
    private final CommentService commentService;
    private final GreatService greatService;


    // 유저 가입
    @GetMapping("/join")
    public String joinPage(Model model){
        model.addAttribute("userJoinRequest", new UserJoinRequest());
        return "users/join";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute UserJoinRequest req, BindingResult bindingResult, Model model){
        // Validation
        if (userService.joinValid(req, bindingResult).hasErrors()) {
            return "users/join";
        }

        userService.join(req);
        model.addAttribute("message", "회원가입에 성공했습니다!\n로그인 후 사용 가능합니다!");
        model.addAttribute("nextUrl", "/users/login");
        return "printMessage";
    }


    // 유저 로그인
    @GetMapping("/login")
    public String loginPage(Model model){

        model.addAttribute("userLoginRequest", new UserLoginRequest());

        return "users/login";
    }

    // 유저 상세 페이지
    @GetMapping("/my-page")
    public String myPageBoard(Authentication auth, Model model){
        model.addAttribute("boards", boardService.findMyBoard(auth.getName()));
        model.addAttribute("comments", commentService.findMyComment(auth.getName()));
        model.addAttribute("greats", greatService.findMyGreat(auth.getName()));
        model.addAttribute("user", userService.myInfo(auth.getName()));
        return "users/myPage/myPage";
    }


    // 유저 수정
    @GetMapping("/edit")
    public String userEditPage(Authentication authentication, Model model){
        User user = userService.myInfo(authentication.getName());
        model.addAttribute("userDto", UserDto.of(user));
        return "users/edit";
    }

    @PostMapping("/edit")
    public String userEdit(@Valid @ModelAttribute UserDto dto, BindingResult bindingResult, Authentication auth, Model model){
        // Validation
        if(userService.editValid(dto, bindingResult, auth.getName()).hasErrors()){
            return "users/edit";
        }

        userService.edit(dto, auth.getName());

        model.addAttribute("message", "정보가 수정되었습니다.");
        model.addAttribute("nextUrl", "/users/edit");

        return "printMessage";
    }

    // 유저 삭제
    @GetMapping("/delete")
    public String userDeletePage(Authentication auth, Model model){
        User user = userService.myInfo(auth.getName());
        model.addAttribute("userDto", UserDto.of(user));
        return "users/delete";
    }

    @PostMapping("/delete")
    public String userDelete(@ModelAttribute UserDto userDto, Authentication auth, Model model){
        Boolean deleteSuccess = userService.delete(auth.getName(), userDto.getNowPassword());
        if (deleteSuccess){
            model.addAttribute("message", "탈퇴 되었습니다");
            model.addAttribute("nextUrl", "/users/logout");
            return "printMessage";
        } else{
            model.addAttribute("message", "비밀번호가 일치하지 않습니다");
            model.addAttribute("nextUrl", "/users/delete");
            return "printMessage";
        }
    }

}
