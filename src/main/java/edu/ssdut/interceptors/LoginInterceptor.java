package edu.ssdut.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Gaomj on 2017/7/17.
 */
public class LoginInterceptor implements HandlerInterceptor {
        Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
            // TODO Auto-generated method stub
            logger.info("------preHandle------");
            //获取session
            logger.info("URI:"+request.getRequestURI());
            HttpSession session = request.getSession(true);
            //判断用户ID是否存在，不存在就跳转到登录界面
            if(session.getAttribute("user") == null){
                logger.info("------:跳转到login页面！");
                response.sendRedirect("/login");
                return false;
            }else{
                session.setAttribute("user", session.getAttribute("user"));
                return true;
            }
        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                ModelAndView modelAndView) throws Exception {
            // TODO Auto-generated method stub

        }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }

}
