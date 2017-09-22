package cn.MrZhang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * Title:BeetlTestController
 * @Description: TODO beetl 模板测试
 * @author MrZhang
 * @date 2017年9月21日 下午4:06:51 
 * @version V1.0
 */
@Controller
@RequestMapping("/test")
public class BeetlTestController {

    @GetMapping("/create")
    public String test(Model model) {
        model.addAttribute("OK", "MrZhang");
        return "/Test.html";
    }
}
