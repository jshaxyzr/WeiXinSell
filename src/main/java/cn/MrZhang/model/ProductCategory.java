package cn.MrZhang.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

@Entity
@Data
@Log4j
@NoArgsConstructor
@AllArgsConstructor
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
