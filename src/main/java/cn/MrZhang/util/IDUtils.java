package cn.MrZhang.util;

import java.util.Random;

/**
 * 
 * Title:IDUtils
 * @Description: TODO 各种id生成策略
 * @author MrZhang
 * @date 2017年7月24日 下午3:09:43 
 * @version V1.0
 */
public class IDUtils {

    /**
     * 图片名生成
     */
    public static String genImageName() {
        // 取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        // long millis = System.nanoTime();
        // 加上三位随机数
        Random random = new Random();
        int end3 = random.nextInt(999);
        // 如果不足三位前面补0
        String str = millis + String.format("%03d", end3);

        return str;
    }

    /**
     * id生成 
     * synchronized 防止多线程并发情况下 重复出现可能
     */
    public static synchronized String genId() {
        // 取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        // long millis = System.nanoTime();
        // 加上两位随机数
        Random random = new Random();
        Integer number = random.nextInt(90000) + 10000;
        // 如果不足两位前面补0
        String str = millis + String.valueOf(number);

        return str;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++)
            System.out.println(genId());
    }
}
