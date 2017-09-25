package cn.MrZhang.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

/**
 * 
 * Title:ProductCategory
 * @Description: TODO 商品类目
 * @author MrZhang
 * @date 2017年9月19日 下午2:33:36 
 * @version V1.0
 */
@Entity
@Data
@Log4j
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
public class ProductCategory implements Serializable {
    /*
     * 类目ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer categoryId;
    /**
     * 类目名称
     */
    private String categoryName;
    /*
     * 类目编号
     */
    private Integer categoryType;

    private Date updateTime;

    private Date createdTime;

}
