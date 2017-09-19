package cn.MrZhang.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.MrZhang.model.ProductCategory;
import cn.MrZhang.model.ProductInfo;
import cn.MrZhang.service.ProductCategoryService;
import cn.MrZhang.service.ProductInfoService;
import cn.MrZhang.util.ResultVoUtil;
import cn.MrZhang.vo.ProductInfoVo;
import cn.MrZhang.vo.ProductVo;
import cn.MrZhang.vo.ResultVo;

/**
 * 
 * Title:BuyProductController
 * @Description: TODO 卖家商品
 * @author MrZhang
 * @date 2017年9月19日 上午10:39:31 
 * @version V1.0
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyProductController {

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
    public ResultVo list() {

        // 查询所有上架商品
        List<ProductInfo> productInfos = productInfoService.findUpAll();
        // 查询类别
        // for each循环 遍历
        // List<Integer> categoryTypeList = new ArrayList<>();
        // for (ProductInfo productInfo : productInfos) {
        // categoryTypeList.add(productInfo.getCategoryType());
        // }
        // lambda 表达式
        List<Integer> categoryTypeList = productInfos.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> productCategories = productCategoryService.findByCategoryTypeIn(categoryTypeList);

        // 数据拼装
        List<ProductVo> productVos = new ArrayList<>();
        for (ProductCategory productCategory : productCategories) {
            ProductVo productVo = new ProductVo();
            productVo.setCategoryName(productCategory.getCategoryName());
            productVo.setCategoryType(productCategory.getCategoryType());
            // 封装商品详情
            List<ProductInfoVo> productInfoVos = new ArrayList<>();
            for (ProductInfo productInfo : productInfos) {
                // 判断要封装的商品类目是否和本层类目是否相等
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {

                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    // spring apache commens 都 提供的属性复制方法 省去了 先productInfo get 再 productInfoVoset
                    BeanUtils.copyProperties(productInfo, productInfoVo);
                    productInfoVos.add(productInfoVo);

                }
            }
            productVo.setData(productInfoVos);
            productVos.add(productVo);
        }

        return ResultVoUtil.success(productInfos);
    }
}
