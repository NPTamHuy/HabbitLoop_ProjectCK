package com.habitloop.controller;

import com.habitloop.dto.*;
import com.habitloop.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ===== REGISTER =====
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute RegisterRequest request,
                           BindingResult bindingResult,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }
        try {
            authService.register(request);
            redirectAttributes.addFlashAttribute("success",
                "Đăng ký thành công! Hãy đăng nhập.");
            return "redirect:/auth/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }

    // ===== LOGIN =====
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginRequest request,
                        BindingResult bindingResult,
                        Model model,
                        HttpServletRequest httpRequest,
                        HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "auth/login";
        }
        try {
            AuthResponse authResponse = authService.login(request);

            // Lưu JWT vào Cookie
            Cookie cookie = new Cookie("jwt", authResponse.getToken());
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(86400);
            response.addCookie(cookie);

            // Tạo session và lưu authentication
            org.springframework.security.core.userdetails.UserDetails userDetails =
                org.springframework.security.core.context.SecurityContextHolder
                    .getContext().getAuthentication() != null ?
                (org.springframework.security.core.userdetails.UserDetails)
                    org.springframework.security.core.context.SecurityContextHolder
                        .getContext().getAuthentication().getPrincipal() : null;

            // Lưu username vào session để JwtAuthFilter đọc được
            httpRequest.getSession(true).setAttribute("username", authResponse.getUsername());

            return "redirect:/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
            return "auth/login";
        }
    }

    // ===== LOGOUT =====
    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Xóa cookie
        response.addCookie(cookie);
        return "redirect:/auth/login";
    }
}