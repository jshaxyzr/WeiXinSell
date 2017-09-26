package cn.MrZhang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.MrZhang.service.SeckillService;
import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/skill")
@Log4j
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    /**
     * 
    * @Title: query
    * @Description: TODO 查询 秒杀商品信息
    * @param productId
    * @return String
     */
    @GetMapping(value = "/query/{productId}", produces = "text/plain;charset=UTF-8")
    public String query(@PathVariable String productId) {

        return seckillService.querySeckillProductInfo(productId);
    }

    /**
     * 
    * @Title: kill
    * @Description: TODO 秒杀 ，没有抢到返回“我靠，太火爆了！你好像错过了一个亿！”，抢到了会返回剩余的库存量
    * @param productId
    * @return String
     */
    @GetMapping(value = "/order/{productId}", produces = "text/plain;charset=UTF-8")
    public String kill(@PathVariable String productId) {
        log.info("@skill request ,productId:" + productId);
        seckillService.orderProductMockDiffUser(productId);

        return seckillService.querySeckillProductInfo(productId);
    }

}
