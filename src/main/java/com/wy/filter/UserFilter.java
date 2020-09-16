package com.wy.filter;


import com.wy.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "userFilter")
public class UserFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("user");
        //如果未登录，返回登录页面
        if (user==null){
            response.sendRedirect("/login");
        }else {
            //如果已登录，放行
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }
}
