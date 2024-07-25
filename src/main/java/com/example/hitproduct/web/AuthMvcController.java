package com.example.hitproduct.web;
/*
 * @author HongAnh
 * @created 25 / 07 / 2024 - 8:56 AM
 * @project pro-l7-server
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.hitproduct.domain.dto.global.GlobalResponse;
import com.example.hitproduct.domain.dto.global.Meta;
import com.example.hitproduct.domain.dto.global.Status;
import com.example.hitproduct.domain.dto.request.LoginRequest;
import com.example.hitproduct.domain.dto.response.AuthResponse;
import com.example.hitproduct.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class AuthMvcController {
    AuthService authService;

    @GetMapping("/")
    public String showLogin(){
        return "login";
    }

    @GetMapping("/user")
    public String index(){
        return "user";
    }

    @GetMapping("/manage")
    public String manage(){
        return "manage";
    }

    @PostMapping("/")
    public String login(@RequestParam String studentCode, @RequestParam String password, Model model, HttpSession session){
        LoginRequest request = new LoginRequest(studentCode, password);
        GlobalResponse<Meta, AuthResponse> response = authService.login(request);
        if(response.getMeta().getStatus().equals(Status.ERROR)) {
            model.addAttribute("message","Sai tên đăng nhập hoặc mật khẩu");
            return "login";
        }

        String accessToken = response.getData().getAccessToken();
        session.setAttribute("token", accessToken);

        if(response.getData().getRoles().contains("USER")) {
            return "redirect:/user";
        }

        return "redirect:/manage";
    }
}
