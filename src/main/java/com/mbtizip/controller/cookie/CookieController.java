package com.mbtizip.controller.cookie;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("/cookie")
public class CookieController {

    public static final String INTERACTION_COOKIE = "interaction_cookie";

    @GetMapping("/api/v1/get")
    public void getCookie(HttpServletResponse response){

        Cookie cookie = new Cookie(INTERACTION_COOKIE, UUID.randomUUID().toString());
        response.addCookie(cookie);
    }
}
