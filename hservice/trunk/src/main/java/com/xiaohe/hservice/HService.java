package com.xiaohe.hservice;

import java.util.List;
import java.util.Map;

import com.xiaohe.entity.PassageEntitys;

public interface HService {

    /**
     * 向硬件发送出水指令
     *
     * @param controlId 控制id
     * @param devMac    设备mac地址
     * @param type      类型
     * @param amount    水量
     * @return
     */
    int turnOnWater(int controlId, String imei, int type, int amount);
    
    
    /**
     * 向硬件发送出水指令 - 新
     * @param controlId
     * @param passage
     * @param type
     * @return
     */
    int controlWater(int controlId,String imei,int passage,int type);


    /**
     * 查询设备是否在线
     *
     * @param devMac 设备mac地址
     * @return
     */
    int selectDevOnline(String devMac);

    /**
     * 向硬件发送开关机时间指令
     *
     * @param devMac 设备mac地址
     * @param on     开机时间
     * @param off    关机时间
     * @return
     */
    int setSwitchTime(String devMac, String on, String off);

    /**
     * 设置机器开关机日
     *
     * @param IMEI
     * @param on
     * @param off
     * @return
     */
    int setSwitchDay(String IMEI, int on, int off);

    /**
     * 向硬件发送设置机型指令
     *
     * @param IMEI
     * @param model
     * @return
     */
    int setModel(String IMEI, int model);

    /**
     * 恢复默认值
     *
     * @param IMEI
     * @return
     */
    int restoreDefaults(String IMEI);

    /**
     * 定时启动和取消
     *
     * @param IMEI
     * @param type
     * @return
     */
    int timedStartAndCancel(String IMEI, int type);

    /**
     * 手动换水
     *
     * @param IMEI
     * @param type
     * @return
     */
    int changeWater(String IMEI, int type);

    /**
     * 手动消毒
     *
     * @param IMEI
     * @param type
     * @return
     */
    int disinfection(String IMEI, int type);

    /**
     * 手动冲洗
     *
     * @param IMEI
     * @param type
     * @return
     */
    int flushing(String IMEI, int type);

    /**
     * 手动复位净水量
     *
     * @param IMEI
     * @return
     */
    int resetWaterVolume(String IMEI);

    /**
     * 系统参数的设置(节能型)
     *
     * @param IMEI
     * @param stopTemp(停止加热温度)
     * @param outWaterType(出水方式)
     * @param disinfectionFUN(消毒功能)
     * @param changeWaterFUN(换水功能)
     * @param flushFUN(自冲洗功能)
     * @param filterMng(滤芯管理功能)
     * @param logo(厂商LOGO是否展示)
     * @param inWaterTempDiff(进水温差)
     * @param inWaterReturnDiff(进水回差)
     * @param changeType(换水方式)
     * @param waterLevelDetected(水位一直检测)
     * @param waterLevelSensitivity(水位检测灵敏度（L）)
     * @return
     */
    int setSysEnergy(String type,String IMEI, short stopTemp, int outWaterType, int disinfectionFUN,
                     int changeWaterFUN, int flushFUN, int filterMng, int logo, short inWaterTempDiff,
                     short inWaterReturnDiff, int changeType, int waterLevelDetected, short waterLevelSensitivity);


    /**
     * 系统参数的设置(步进型)
     *
     * @param IMEI
     * @param stopTemp(停止加热温度)
     * @param emptyingFUN(排空功能)
     * @param flushFUN(自冲洗功能)
     * @param filterMng(滤芯管理功能)
     * @param logo(厂商LOGO是否展示)
     * @param inWaterTempDiff(进水温差)
     * @param insulationTempDiff(保温温差（步进）)
     * @param insulationReturnDiff(保温回差（步进）)
     * @param waterInletSpeed(进水速度（步进）)
     * @param waterLevelSensitivity(水位检测灵敏度（L）)
     * @return
     */
    int setSysStep(String type,String IMEI, short stopTemp, int emptyingFUN, int flushFUN, int filterMng, int logo,
                   short inWaterTempDiff, short insulationTempDiff, short insulationReturnDiff,
                   int waterInletSpeed, short waterLevelSensitivity);

    /**
     * 用户参数的设置
     *
     * @param IMEI
     * @param day(当前日)
     * @param hour(当前时间（小时）)
     * @param min(当前时间（分钟）)
     * @param openDay(开机日)
     * @param closeDay(关机日)
     * @param firOpenTime(定时第一段开时间)
     * @param firCloseTime(定时第一段关时间)
     * @param secOpenTime(定时第二段开时间)
     * @param secCloseTime(定时第二段关时间)
     * @param thirdOpenTime(定时第三段开时间)
     * @param thirdCloseTime(定时第三段关时间)
     * @param firDisTime(定时消毒第一段时间)
     * @param secDisTime(定时消毒第二段时间)
     * @param thirdDisTime(定时消毒第三段时间)
     * @param changeWaterTime(定时换水时间)
     * @param changeWaterDuration(换水时长（分钟))
     * @param changeWaterCycle(换水周期（天）)
     * @param filterLife(滤芯寿命（m³）)
     * @param flushTime(定时冲洗时间)
     * @param flushDuration(冲洗时长)
     * @param cleanDuration(消毒时长)
     * @return
     */
    int setUserSetting(String type,String IMEI, int day, int hour, int min, int openDay, int closeDay,
                       String firOpenTime, String firCloseTime, String secOpenTime, String secCloseTime,
                       String thirdOpenTime, String thirdCloseTime, String firDisTime, String secDisTime,
                       String thirdDisTime, String changeWaterTime, int changeWaterDuration, int changeWaterCycle,
                       int filterLife,String flushTime,int flushDuration,int cleanDuration);

    /**
     * 获取用户参数
     * @param type
     * @return
     */
    int getUserSetting(int type,String imei);
    
    /**
     * 下发通道信息
     * @param info
     * @return
     */
    int setPassageInfo(String imei,List<PassageEntitys> info);
    
    /**
     * 手动开关机
     * @param imei
     * @param type
     * @return
     */
    int setCtlMachineStatus(String imei,int type);
}
