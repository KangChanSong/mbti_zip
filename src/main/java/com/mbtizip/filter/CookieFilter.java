package com.mbtizip.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@Order(1)
public class CookieFilter implements Filter {

    public static final String INTERACTION_COOKIE = "interaction_cookie";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        Cookie reqCookie = WebUtils.getCookie((HttpServletRequest) request, INTERACTION_COOKIE);

        if(reqCookie == null) {
            Cookie cookie = new Cookie(INTERACTION_COOKIE, UUID.randomUUID().toString());
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 365 * 10);
            ((HttpServletResponse)response).addCookie(cookie);
        }

        chain.doFilter(request, response);
    }
}
