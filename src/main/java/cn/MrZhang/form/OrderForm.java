package cn.MrZhang.form;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

/**
 * 
 * Title:OrderForm
 * @Description: TODO 前台表单参数 验证
 * @author MrZhang
 * @date 2017年9月20日 上午11:11:42 
 * @version V1.0
 */
@Data
public class OrderForm {

    /** 买家姓名 */
    @NotEmpty(message = "姓名不能为空")
    private String name;

    /** 买家地址 */
    @NotEmpty(message = "地址不能为空")
    private String address;

    /** 买家电话 */
    @NotEmpty(message = "电话不能为空")
    private String phone;

    /** 买家微信openid*/
    @NotEmpty(message = "openid不能为空")
    private String openid;

    /** 要买的商品 */
    @NotEmpty(message = "商品不能为空")
    private String items;

}
