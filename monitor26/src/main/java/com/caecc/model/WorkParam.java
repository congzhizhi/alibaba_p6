package com.caecc.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 工作参数下发实体
 *
 *  CREATE TABLE MONITOR.WORK_PARAM (
 * 	ETH_IP INT4,
 * 	ID _INT4 NOT NULL,
 * 	SRC_IP INT4,
 * 	SRC_PORT INT4,
 * 	DES_IP INT4,
 * 	DES_PORT INT4,
 * 	PROTOCOL INT1,
 * 	MODE INT1
 * )
 * TABLESPACE SYSTEM INIT 64K NEXT 64K MAXSIZE UNLIMITED  PCTFREE 10 PCTUSED 40;
 */
/**
 * @description KeySequence的value填入主键序列名称即可
 * @author Cong ZhiZzhi
 * @date 2020-09-06 7:31
 */
@Data
@TableName("work_param")
@KeySequence(value = "congzhizhi",clazz = Long.class)
public class WorkParam {

    /**
     * ID
     */
    @TableId(type = IdType.INPUT)
    private Long id;

    /**
     * 网卡IP,4字节
     */
    private  Integer ethIP;

    /**
     * 源IP,4字节
     */
    private  Integer srcIP;

    /**
     * 源端口,2字节
     */
    private   Integer  srcPort;

    /**
     * 目的IP,4字节
     */
    private  Integer desIP;

    /**
     * 目的端口,2字节
     */
    private   Integer  desPort;

    /**
     * 通信协议,1字节
     */
    private   Byte  protocol;

    /**
     * 工作模式,1字节
     */
    private   Byte  mode;




}
