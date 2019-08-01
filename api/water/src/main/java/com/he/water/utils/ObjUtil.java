package com.he.water.utils;


import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.he.water.entity.CommonResult;
import com.he.water.entity.DeviceEntity;
import com.he.water.entity.PassageAmount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.LinkedList;
import java.util.List;

/**
 * @author hzh
 * @date 2018/11/16
 */
public class ObjUtil {

    private static Logger log = LoggerFactory.getLogger(ObjUtil.class);

    /**
     * 通过对象列表获取id集合
     *
     * @param objects
     * @return
     */
    public static List<Integer> getIds(List objects) {
        List<Integer> ids = new LinkedList<>();
        if (!CollectionUtils.isEmpty(objects)) {
            for (Object object : objects) {
                Field[] fields = object.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (field.getName().contains("id")) {
                        Object fieldValueByName = EntityUtils.getFieldValueByName(field.getName(), object);
                        ids.add((Integer) fieldValueByName);
                        continue;
                    }
                }
            }
        }
        return ids;
    }

    /**
     * 关闭锁
     *
     * @param randomAccessFile RandomAccessFile
     * @param fileLock         fileLock
     * @param fileChannel      fileChannel
     * @throws IOException 异常
     */
    public static void closeLock(RandomAccessFile randomAccessFile, FileLock fileLock, FileChannel fileChannel) throws IOException {
        if (fileLock != null) {
            fileLock.release();
        }
        if (fileChannel != null) {
            fileChannel.close();
        }
        if (randomAccessFile != null) {
            randomAccessFile.close();
        }
    }

    /**
     * 验证参数的合法性
     *
     * @param passageAmount 下单参数
     * @param com           返回前端结果集
     * @param deviceEntity  查询数据库的设备
     * @return 校验结果集
     */
    public static CommonResult validateParam(PassageAmount passageAmount, CommonResult com, DeviceEntity deviceEntity) {
        if (deviceEntity == null) {
            com.error(1, "设备不存在");
            return com;
        }
        if (passageAmount.getPassageType() == 1 || passageAmount.getPassageType() == 2) {
            log.info("进入水温判断，当前温度为：{}", deviceEntity.getShowTemp());
            if (ConsantUtil.BOILING_TEMPERATURE > deviceEntity.getShowTemp()) {
                com.error(1, "当前水温未达饮用，暂不能售水");
                return com;
            }
        }
        if (passageAmount.getOpenId() == null) {
            com.error(1, "登录超时，请重试");
            return com;
        }
        int totalFee = passageAmount.getPrice() - passageAmount.getCouponPrice();
        if (totalFee < 0) {
            com.error(1, "支付金额错误");
            return com;
        }
        return com;
    }
}
