package com.xiaohe.hservice;

public interface HService {
			
	
	/**
	 *  send remote ctl cmd to device 
	 * @param tboxId
	 * @param cmd 1: open door
	 * 			  2: close door 
	 *            3: open window 
	 *            4: close window 
	 *            5: turn on light 
	 *            6: shut down light 
	 *            7: tooting
	 * @param msgId:msg id
	 *         
	 * @return 
	 * 			false :device offline
	 * 			true: device online
	 */
	public boolean  controlDevice(String tboxId, int cmd, short msgId);
	
	/**
	 *  get latest vechile data 
	 * @param tboxId
	 * 
	 * @return 
	 * 			false: devcie offline 
	 * 			true: device online
	 */
	public boolean getVichData(String tboxId);

}
