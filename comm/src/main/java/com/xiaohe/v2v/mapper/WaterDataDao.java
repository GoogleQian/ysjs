package com.xiaohe.v2v.mapper;

import org.apache.ibatis.annotations.Param;

import com.xiaohe.hservice.WaterBaseData;;

public interface WaterDataDao {		
	int updateCreateTime(WaterBaseData waterBaseData);		
	int updateStatus(WaterBaseData waterBaseData);
	int insertWaterQuality(WaterBaseData waterBaseData);
	int insertStatus(WaterBaseData waterBaseData);	
	String selectDevIdByIMEI(@Param("imei")String imei);
	String selectUrl(@Param("key")String key);
	String selectVersionByImei(@Param("imei")String imei);
	int isInDB(@Param("imei")String imei);
}







