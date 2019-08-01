package com.xiaohe.hservice;

import com.xiaohe.entity.DevSysConfig;
import com.xiaohe.entity.DevUserConfig;

public interface NotifyService {
		

	/**
	 * 控制机器出水应答
	 * @param controlId 控制Id
	 * @param status 控制结果
	 * @return
	 */
	public void notifyTurnOnStatus(int controlId, int status);
	
	/**
	 * 设置开关机时间段应答
	 * @param IMEI
	 * @param status 控制结果
	 * @return
	 */
	public void notifySetSwitchTime(String IMEI, int status);
	
	/**
	 * 机型设置应答
	 * @param status
	 */
	public void notifySetModel(int status);
	
	/**
	 * 恢复默认值应答
	 * @param status
	 */
	public void notifyRestoreDefaults(int status);
	
	/**
	 * 定时启动和取消应答
	 * @param status
	 */
	public void notifyTimedStartAndCancel(int status);
	
	/**
	 * 手动换水应答
	 * @param status
	 */
	public void notifyChangeWater(int status);
	
	/**
	 * 手动消毒应答
	 * @param status
	 */
	public void notifyDisinfection(int status);
	
	/**
	 * 手动冲洗应答
	 * @param status
	 */
	public void notifyFlushing(int status);
	
	/**
	 * 手动复位净水量应答
	 * @param status
	 */
	public void notifyResetWaterVolume(int status);
	
	/**
	 * 系统参数设置（节能型）应答
	 * @param status
	 */
	public void notifySetSysEnergy(int status);
	
	/**
	 * 系统参数设置（步进型）应答
	 * @param status
	 */
	public void notifySetSysStep(int status);
	
	/**
	 * 用户参数设置应答
	 * @param status
	 */
	public void notifySetUserSetting(int status);
	
	/**
	 * 系统参数设置
	 * @param sysConfig
	 */
	public int notifySetSysConfig(DevSysConfig sysConfig);
	
	/**
	 * 用户参数设置
	 * @param userConfig
	 */
	public int notifySetUserConfig(DevUserConfig userConfig);
	
	/**
	 * 获取用户参数
	 * @param userConfig
	 * @return
	 */
	public int notifyGetUserSetting(DevUserConfig userConfig);
	
	/**
	 * 硬件上报显示温度
	 * @param userConfig
	 * @return
	 */
	public int notifyGetShowTemp(DevUserConfig userConfig);

	/**
	 * 硬件上报水量
	 * @param water
	 * @return
	 */
	public int notifyGetWaterAmount(WaterBaseData water);
	
	/**
	 * 下发通道信息应答
	 * @param result
	 * @return
	 */
	public int notifySetPassageInfo(int result);
	
	/**
	 * 手动开关机应答
	 * @param result
	 * @return
	 */
	public int notifyCtlMichineStatus(int result);
}
