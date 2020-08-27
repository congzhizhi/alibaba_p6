package com.luban.netty.xian_26.handle;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.util.ReferenceCountUtil;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 采集数据上报帧处理器
 */
public class DataUploadFrameProcessor implements IFrameProcessor {
    static private final Logger LOGGER = LoggerFactory.getLogger(DataUploadFrameProcessor.class);

    private byte frameType = 3;
    /**
     * 采集数据上报帧处理
     * @param frame
     */
    @Override
    public void handle(ByteBuf frame){

//        LOGGER.info("载荷"+ ByteBufUtil.hexDump(frame));
        ByteBuf lianlu = null;
        try {
            frame.readByte();//读取数据补传标志
            lianlu =  frame.readRetainedSlice(14);
           byte liuliang = frame.readByte();
           if (liuliang == 1){
              int pps =  frame.readIntLE();
              int Bps = frame.readIntLE();
              LOGGER.info("包流量："+pps+"----字节流量："+Bps);
           }
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ReferenceCountUtil.release(lianlu);
        ReferenceCountUtil.release(frame);

    }

    /**
     * 获取日志帧类型
     */
    @Override
    public byte getFrameType() {
        return frameType;
    }

}
