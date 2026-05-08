package com.example.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * ユーザーが認証済みか確認する Interceptor.
 *
 * @author shunsei
 */
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    /**
     * リクエストを処理する前に認証済みか確認する.
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @param handler  chosen handler to execute, for type and/or instance evaluation
     * @return Controller の実行を許可するか
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("administratorName") == null) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return false; // Controller の実行をキャンセル
        }

        return true; // Controller の実行を許可
    }
}