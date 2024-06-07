package org.example.config.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.example.domain.User.User;
import org.example.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;

@AllArgsConstructor
public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 세션 유지 시간 = 3600초
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(3600);

        User loginUser = userRepository.findByEmail(authentication.getName()).get();

        // 성공 시 메시지 출력 후 홈 화면으로 redirect
        PrintWriter pw = response.getWriter();
        String prevPage = (String) request.getSession().getAttribute("prevPage");

        if (prevPage != null) {
            pw.println("<script>alert('" + loginUser.getNickname() + "님 반갑습니다!'); location.href='" + prevPage + "';</script>");
        } else {
            pw.println("<script>alert('" + loginUser.getNickname() + "님 반갑습니다!'); location.href='/';</script>");
        }
        pw.flush();  // 출력
    }
}
