package cn.MrZhang.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.MrZhang.form.CategoryForm;
import cn.MrZhang.model.ProductCategory;
import cn.MrZhang.service.ProductCategoryService;

/**
 * 
 * Title:SellerCategoryController
 * @Description: TODO 卖家端 商品类目 操作
 * @author MrZhang
 * @date 2017年9月25日 上午10:29:00 
 * @version V1.0
 */
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 
    * @Title: list
    * @Description: TODO 商品类型列表
    * @param model
    * @param map
    * @return String
     */
    @GetMapping("/list")
    public String list(Model model, Map<String, Object> map) {
        List<ProductCategory> productCategories = productCategoryService.findAll();
        map.put("productCategories", productCategories);
        model.addAllAttributes(map);
        return "/category/list";
    }

    /**
     * 
    * @Title: index
    * @Description: TODO 商品类型展示
    * @param categoryId
    * @param model
    * @return String
     */
    @GetMapping("/index")
    public String index(@RequestParam(value = "categoryId", required = false) Integer categoryId, Model model) {
        Map<String, Object> map = new HashMap<>();
        if (categoryId != null) {
            ProductCategory productCategory = productCategoryService.findOne(categoryId);
            map.put("category", productCategory);
            model.addAllAttributes(map);
        }
        return "/category/index";
    }

    /**
     * 
    * @Title: save
    * @Description: TODO 新增类目
    * @param categoryForm
    * @param bindingResult
    * @param model
    * @return String
     */
    @PostMapping("/save")
    public String save(@Valid CategoryForm categoryForm, BindingResult bindingResult, Model model) {
        Map<String, Object> map = new HashMap<>();
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/seller/category/index");
            model.addAllAttributes(map);
            return "/common/error";
        }

        ProductCategory category = new ProductCategory();
        try {
            // 如果category为null, 说明是新增
            if (categoryForm.getCategoryId() != null) {
                category = productCategoryService.findOne(categoryForm.getCategoryId());
            }
            BeanUtils.copyProperties(categoryForm, category);
            productCategoryService.save(category);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/seller/category/index");
            model.addAllAttributes(map);
            return "common/error";
        }

        map.put("url", "/seller/category/list");
        model.addAllAttributes(map);
        return "/common/success";
    }

}
