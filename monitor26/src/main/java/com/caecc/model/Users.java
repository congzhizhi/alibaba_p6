package com.caecc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author Cong ZhiZzhi
 * @Date 2020-09-05 22:23
 * @Version 1.0
 */
@TableName("users")
@Data
public class Users {
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;
    private String name;
    private Integer age;
    private String email;


    //查询字段，不用跟数据库列进行关联
    @TableField(exist = false)
    private String query;
}
