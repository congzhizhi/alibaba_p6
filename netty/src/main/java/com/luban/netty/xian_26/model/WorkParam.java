package com.luban.netty.xian_26.model;

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
@Data
public class WorkParam {

    /**
     * ID
     */
    private int id;

    /**
     * 网卡IP,4字节
     */
    private  int ethIP;

    /**
     * 源IP,4字节
     */
    private  int srcIP;

    /**
     * 源端口,2字节
     */
    private   int  srcPort;

    /**
     * 目的IP,4字节
     */
    private  int desIP;

    /**
     * 目的端口,2字节
     */
    private   int  desPort;

    /**
     * 通信协议,1字节
     */
    private   byte  protocol;

    /**
     * 工作模式,1字节
     */
    private   byte  mode;

}
