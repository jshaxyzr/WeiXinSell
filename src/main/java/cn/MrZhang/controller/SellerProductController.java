package cn.MrZhang.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.MrZhang.form.ProductForm;
import cn.MrZhang.model.ProductCategory;
import cn.MrZhang.model.ProductInfo;
import cn.MrZhang.service.ProductCategoryService;
import cn.MrZhang.service.ProductInfoService;
import cn.MrZhang.util.IDUtils;

/**
 * 
 * Title:SellerProductController
 * @Description: TODO 卖家端商品
 * @author MrZhang
 * @date 2017年9月22日 下午5:35:41 
 * @version V1.0
 */
@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 列表
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public String list(@RequestParam(value = "pageNo", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "3") Integer size, Model model) {
        PageRequest request = new PageRequest(page - 1, size);
        Page<ProductInfo> productInfoPage = productInfoService.findAll(request);
        Map<String, Object> map = new HashMap<>();
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        model.addAllAttributes(map);
        return "/product/list.html";
    }

    /**
    * 商品上架
    * @param productId
    * @param map
    * @return
    */
    @RequestMapping("/on_sale")
    public String onSale(@RequestParam("productId") String productId, Model model) {
        Map<String, Object> map = new HashMap<>();
        try {
            productInfoService.onSale(productId);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            model.addAllAttributes(map);
            return "/common/error.html";
        }

        map.put("url", "/seller/product/list");
        model.addAllAttributes(map);
        return "/common/success.html";
    }

    /**
    * 商品下架
    * @param productId
    * @param map
    * @return
    */
    @RequestMapping("/off_sale")
    public String offSale(@RequestParam("productId") String productId, Model model) {
        Map<String, Object> map = new HashMap<>();
        try {
            productInfoService.offSale(productId);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            model.addAllAttributes(map);
            return "/common/error.html";
        }

        map.put("url", "/seller/product/list");
        model.addAllAttributes(map);
        return "/common/success.html";
    }

    @GetMapping("/index")
    public String index(@RequestParam(value = "productId", required = false) String productId, Model model) {
        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.isEmpty(productId)) {
            ProductInfo productInfo = productInfoService.findOne(productId);
            map.put("productInfo", productInfo);
            model.addAllAttributes(map);
        }
        // 查询所有的类目
        List<ProductCategory> categoryList = productCategoryService.findAll();
        map.put("categoryList", categoryList);
        model.addAllAttributes(map);
        return "/product/index.html";
    }

    /**
    * 保存/更新
    * @param form
    * @param bindingResult
    * @param map
    * @return
    */
    @PostMapping("/save")
    public String save(@Valid ProductForm form, BindingResult bindingResult, Model model) {
        Map<String, Object> map = new HashMap<>();
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/seller/product/index");
            model.addAllAttributes(map);
            return "/common/error.html";
        }

        ProductInfo productInfo = new ProductInfo();
        try {
            // 如果productId为空, 说明是新增
            if (!StringUtils.isEmpty(form.getProductId())) {
                productInfo = productInfoService.findOne(form.getProductId());
            } else {
                form.setProductId(IDUtils.genId());
            }
            BeanUtils.copyProperties(form, productInfo);
            productInfoService.save(productInfo);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/seller/product/index");
            model.addAllAttributes(map);
            return "common/error.html";
        }

        map.put("url", "/seller/product/list");
        model.addAllAttributes(map);
        return "/common/success.html";
    }
}
