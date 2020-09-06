package com.caecc.model;

import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 神通数据库的主键生成策略
 *
 * @Author Cong ZhiZzhi
 * @Date 2020-09-06 7:27
 * @Version 1.0
 */
@Configuration
public class ShenTongSequence {
    @Bean
    public IKeyGenerator shenTongKeyGenerator() {
        return new IKeyGenerator() {
            @Override
            public String executeSql(String incrementerName) {
                {
//                    return "SELECT " + incrementerName + ".NEXTVAL FROM DUAL";
                    return "SELECT @@IDENTITY";
                }

            }
        };
    }
}