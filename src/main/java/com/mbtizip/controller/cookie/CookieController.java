package com.mbtizip.controller.cookie;

import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cookie")
public class CookieController {

    public static final String INTERACTION_COOKIE = "interaction_cookie";

    @GetMapping("/get")
    public ResponseEntity<String> getCookie(HttpServletResponse response, HttpServletRequest request){

        Cookie reqCookie = WebUtils.getCookie(request, INTERACTION_COOKIE);

        if(reqCookie == null) {
            Cookie cookie = new Cookie(INTERACTION_COOKIE, UUID.randomUUID().toString());
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 365 * 10);
            response.addCookie(cookie);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/test")
    public String getCookieTest(@CookieValue(CookieController.INTERACTION_COOKIE) String intercationCookie){

        return intercationCookie;
    }
}
