package cn.MrZhang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.MrZhang.model.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

}
