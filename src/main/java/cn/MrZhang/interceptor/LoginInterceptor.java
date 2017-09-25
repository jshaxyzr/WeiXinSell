package cn.MrZhang.interceptor;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.MrZhang.constant.CookieConstant;
import cn.MrZhang.constant.RedisConstant;
import cn.MrZhang.exception.ServiceException;
import cn.MrZhang.service.RedisService;
import cn.MrZhang.util.CookieUtil;
import cn.MrZhang.util.JsonUtils;
import cn.MrZhang.vo.ResultVo;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    private final Logger logger = Logger.getLogger(LoginInterceptor.class);
    @Autowired
    private RedisService redisService;

    @Value("${spring.profiles.active}")
    private String env;// 当前激活的配置文件

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 开发环境直接放行
        if ("dev".equals(env)) {
            return true;
        } else {

            // 查询cookie
            Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
            if (cookie == null) {
                logger.warn("【登录校验】Cookie中查不到token");
                throw new ServiceException("请先登录！");
            }
            // 去redis里查询
            String tokenValue = (String) redisService.get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
            if (StringUtils.isEmpty(tokenValue)) {
                logger.warn("【登录校验】Redis中查不到token");
                throw new ServiceException("请先登录！");
            }
            // response.sendRedirect("/login?next=".concat(request.getRequestURI())); //重定向到一个url
            // JsonResult result = new JsonResult();
            // result.setCode(ResultCode.NOT_LOGIN);
            // result.setMessage("请先登录");
            // responseResult(response, result);
            return true;
        }

    }

    private void responseResult(HttpServletResponse response, ResultVo result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JsonUtils.objectToJson(result));
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }
}
