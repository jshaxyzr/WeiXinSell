package cn.MrZhang.controller;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.MrZhang.config.ProjectUrlConfig;
import cn.MrZhang.constant.CookieConstant;
import cn.MrZhang.constant.RedisConstant;
import cn.MrZhang.model.SellerInfo;
import cn.MrZhang.service.RedisService;
import cn.MrZhang.service.SellerInfoService;
import cn.MrZhang.util.CookieUtil;

/**
 * 卖家用户
 */
@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerInfoService sellerService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/login")
    public String login(@RequestParam("openid") String openid, HttpServletResponse response, Model model,
            Map<String, Object> map) {
        // 1. openid去和数据库里的数据匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if (sellerInfo == null) {
            map.put("msg", "登录失败");
            map.put("url", "/seller/order/list");
            model.addAllAttributes(map);
            return "/common/error";
        }
        // 2. 设置token至redis
        String token = UUID.randomUUID().toString();
        // 过期时间 秒 默认
        Long expire = RedisConstant.EXPIRE;
        redisService.set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire);
        // redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);
        // 3. 设置token至cookie
        CookieUtil.set(response, CookieConstant.TOKEN, token, expire.intValue());
        return "redirect:/seller/order/list";

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map, Model model) {
        // 1. 从cookie里查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null) {
            // 2. 清除redis
            // redisService.removePattern("token_*");//批量删除 token_ 开头的所有key
            redisService.remove(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
            // 3. 清除cookie
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
        }

        map.put("msg", "登出成功！");
        map.put("url", "/seller/order/list");
        model.addAllAttributes(map);
        return "/common/success";
    }
}
