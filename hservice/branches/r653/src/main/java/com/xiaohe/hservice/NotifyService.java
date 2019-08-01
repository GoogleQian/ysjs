package com.xiaohe.hservice;

public interface NotifyService {
		
	/**
	 * notify remote control result to control side
	 * @param tboxId
	 * @param result
	 */
	public void notifyResul(String tboxId, int result, short msgId);
	
	/**
	 * notify latest vechile data to control side
	 * @param tboxId
	 * @param carData
	 */
	public void notifyVihData(String tboxId, VihBaseData carData);
	
	
	
	/**
	 * notify warning information to control side
	 * @param tboxId
	 * @param type
	 */
	public void notifyWarning(String tboxId, int type, String warningContent);
	
	
	public void notifyVichDataPassive(String tboxId, VihBaseData carData);
}
