package com.luban.netty.xian_26.shentong.mapper;


import com.luban.netty.xian_26.model.WorkParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IWorkParamDao {

     List<WorkParam> getAllWorkParams();

}
