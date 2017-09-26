package cn.MrZhang.service.Impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.MrZhang.exception.ServiceException;
import cn.MrZhang.service.SeckillService;
import cn.MrZhang.util.IDUtils;

@Service
public class SeckillServiceImpl implements SeckillService {

    private static final int TIMEOUT = 3000; // redis 锁 超时时间

    @Autowired
    private RedisLock redisLock;

    /**
     *  秒杀的商品 名称  数量
     */
    static Map<String, Integer> products;
    static Map<String, Integer> stock;
    static Map<String, String> orders;

    static {
        /**
         * 模拟多个表， 商品信息表，库存表，秒杀成功订单表
         */
        products = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();

        products.put("123", 1000);
        stock.put("123", 1000);
    }

    private String QueryMap(String productId) {

        return "【开业大酬宾】，【红烧排骨】特价秒杀，限量【1000】份,还剩【" + stock.get(productId) + "】份" + "该商品成功抢购顾客【" + orders.size() + "】人";
    }

    @Override
    public String querySeckillProductInfo(String productId) {
        // TODO Auto-generated method stub
        return this.QueryMap(productId);
    }

    @Override
    public void orderProductMockDiffUser(String productId) {
        // 加redis 分布式锁
        Long time = System.currentTimeMillis() + TIMEOUT;
        if (!redisLock.lock(productId, String.valueOf(time))) {
            throw new ServiceException("我靠，太火爆了！你好像错过了一个亿！");
        }

        // 1、查询商品库存
        int stoctNum = stock.get(productId);

        if (stoctNum == 0) {
            throw new ServiceException("秒杀结束了！");
        } else {
            // 2、下单(模拟不同用户的id 实际应该是传过来)
            orders.put(IDUtils.genId(), productId);

            // 3、 减库存

            stoctNum = stoctNum - 1;
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            stock.put(productId, stoctNum);

        }

        // 当前线程秒杀结束后 解锁
        redisLock.unLock(productId, String.valueOf(time));

    }

}
