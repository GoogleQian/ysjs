package com.xiaohe.hservice;

import java.io.Serializable;

public class VihBaseData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1856015401360880674L;
	
	private String deviceId = "";
	private Integer gpsState = 0;
	private double lng= 0;
	private double lat = 0;
	private int alm_id= 0;
	private Short gpsSpeed = 0;
	private double Direction = 0;
	private double battery_voltage = 0;
	private Integer total_mileage_category = 0;
	private Integer total_mileage = 0;
	private Integer original_total_mileage = 0;
	private Integer total_fuel_consumption = 0;
	private short engine_running_time = 0;
	private int running_time = 0;
	private Short malfunction_mileage = 0;
	private double remaining_oil = 0;
	private Integer total_instrument_mileage = 0;
	private Integer total_vehicle_operation_time = 0;
	private String car_type = "";
	private Integer faultCodeTotal = 0;
	private Integer coolant_temp = 0;
	private Integer safety_status = 0;
	private Integer door_status = 0;
	private Integer lock_status = 0;
	private Integer window_status = 0;
	private Integer light_status = 0;
	private Short tire_pressure_warning = 0;
	private Integer f_brake_block_w = 0;
	private Integer b_brake_block_w = 0;
	private Integer satellite = 0;
	private double PDOP = 0;
	private String acc_on = "";
	private String acc_off = "";
	private Integer idling = 0;
	private double ave_oil_consum = 0;
	private double ave_speed = 0;
	private String vehicle_number = "";
	private double speed = 0;
	private Short engine_speed = 0;
	private String sys_time = "";
	private String WarningContent = "";
	private Integer travelMile = 0;
	private Integer travelFuel = 0;
	private Integer tboxMile = 0;
	private Integer warningType = 0xffff;
	
	public Integer getWarningType() {
		return warningType;
	}
	public void setWarningType(Integer warningType) {
		this.warningType = warningType;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public Integer getGpsState() {
		return gpsState;
	}
	public void setGpsState(Integer gpsState) {
		this.gpsState = gpsState;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}	
	public int getAlm_Id() {
		return alm_id;
	}
	public void setAlm_Id(int MalfunctionId) {
		this.alm_id = MalfunctionId;
	}
	public Short getGpsSpeed() {
		return gpsSpeed;
	}
	public void setGpsSpeed(Short gpsSpeed) {
		this.gpsSpeed = gpsSpeed;
	}
	public double getDirection() {
		return Direction;
	}
	public void setDirection(double Direction) {
		this.Direction = Direction;
	}
	public double getBattery_voltage() {
		return battery_voltage;
	}
	public void setBattery_voltage(double battery_voltage) {
		this.battery_voltage = battery_voltage;
	}
	public Integer getTotal_mileage_category() {
		return total_mileage_category;
	}
	public void setTotal_mileage_category(Integer total_mileage_category) {
		this.total_mileage_category = total_mileage_category;
	}
	public Integer geTtotal_mileage() {
		return total_mileage;
	}
	public void setTotal_mileage(Integer total_mileage) {
		this.total_mileage = total_mileage;
	}
	public Integer geToriginal_total_mileage() {
		return original_total_mileage;
	}
	public void setoriginal_total_mileage(Integer original_total_mileage) {
		this.original_total_mileage = original_total_mileage;
	}	
	public Integer getTotal_fuel_consumption() {
		return total_fuel_consumption;
	}
	public void setTotal_fuel_consumption(Integer total_fuel_consumption) {
		this.total_fuel_consumption = total_fuel_consumption;
	}
	public short getEngine_running_time() {
		return engine_running_time;
	}	
	public void setEngine_running_time(short engine_running_time) {
		this.engine_running_time = engine_running_time;
	}
	public int getrunning_time() {
		return running_time;
	}	
	public void setrunning_time(int running_time) {
		this.running_time = running_time;
	}
	public Short getMalfunction_mileage() {
		return malfunction_mileage;
	}	
	public void setMalfunction_mileage(Short malfunction_mileage) {
		this.malfunction_mileage = malfunction_mileage;
	}
	public double getRemaining_oil() {
		return remaining_oil;
	}	
	public void setRemaining_oil(double remaining_oil) {
		this.remaining_oil = remaining_oil;
	}
	public Integer getTotal_instrument_mileage() {
		return total_instrument_mileage;
	}	
	public void setTotal_instrument_mileage(Integer total_instrument_mileage) {
		this.total_instrument_mileage = total_instrument_mileage;
	}	
	public Integer getTotal_vehicle_operation_time() {
		return total_vehicle_operation_time;
	}	
	public void setTotal_vehicle_operation_time(Integer total_vehicle_operation_time) {
		this.total_vehicle_operation_time = total_vehicle_operation_time;
	}	
	public String getCar_type() {
		return car_type;
	}	
	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}
	public Integer getfaultCodeTotal() {
		return faultCodeTotal;
	}
	public void setfaultCodeTotal(Integer faultCodeTotal) {
		this.faultCodeTotal = faultCodeTotal;
	}
	public Integer getcoolant_temp() {
		return coolant_temp;
	}
	public void setcoolant_temp(Integer coolant_temp) {
		this.coolant_temp = coolant_temp;
	}
	public Integer getsafety_status() {
		return safety_status;
	}
	public void setsafety_status(Integer safety_status) {
		this.safety_status = safety_status;
	}
	public Integer getdoor_status() {
		return door_status;
	}
	public void setdoor_status(Integer door_status) {
		this.door_status = door_status;
	}
	public Integer getlock_status() {
		return lock_status;
	}
	public void setlock_status(Integer lock_status) {
		this.lock_status = lock_status;
	}	
	public Integer getwindow_status() {
		return window_status;
	}
	public void setwindow_status(Integer window_status) {
		this.window_status = window_status;
	}
	public Integer getlight_status() {
		return light_status;
	}
	public void setlight_status(Integer light_status) {
		this.light_status = light_status;
	}
	public Short gettire_pressure_warning() {
		return tire_pressure_warning;
	}	
	public void settire_pressure_warning(Short tire_pressure_warning) {
		this.tire_pressure_warning = tire_pressure_warning;
	}
	public Integer getf_brake_block_w() {
		return f_brake_block_w;
	}
	public void setf_brake_block_w(Integer f_brake_block_w) {
		this.f_brake_block_w = f_brake_block_w;
	}
	public Integer getb_brake_block_w() {
		return b_brake_block_w;
	}
	public void setb_brake_block_w(Integer b_brake_block_w) {
		this.b_brake_block_w = b_brake_block_w;
	}
	public Integer getsatellite() {
		return satellite;
	}
	public void setsatellite(Integer satellite) {
		this.satellite = satellite;
	}
	public double getPDOP() {
		return PDOP;
	}	
	public void setPDOP(double PDOP) {
		this.PDOP = PDOP;
	}
	public String setacc_on() {
		return acc_on;
	}
	public void setacc_on(String acc_on) {
		this.acc_on = acc_on;
	}
	public String setacc_off() {
		return acc_off;
	}
	public void setacc_off(String acc_off) {
		this.acc_off = acc_off;
	}
	public Integer getidling() {
		return idling;
	}
	public void setidling(Integer idling) {
		this.idling = idling;
	}
	public double getave_oil_consum() {
		return ave_oil_consum;
	}	
	public void setave_oil_consum(double ave_oil_consum) {
		this.ave_oil_consum = ave_oil_consum;
	}
	public double getave_speed() {
		return ave_speed;
	}	
	public void setave_speed(double ave_speed) {
		this.ave_speed = ave_speed;
	}
	public String getvehicle_numberr() {
		return vehicle_number;
	}
	public void setvehicle_number(String vehicle_number) {
		this.vehicle_number = vehicle_number;
	}
	public double getspeed() {
		return speed;
	}	
	public void setspeed(double speed) {
		this.speed = speed;
	}
	public short getEngine_speed() {
		return engine_speed;
	}	
	public void setEngine_speed(short engine_speed) {
		this.engine_speed = engine_speed;
	}
	public String getsys_time() {
		return sys_time;
	}
	public void setsys_time(String sys_time) {
		this.sys_time = sys_time;
	}
	public String getWarningContent() {
		return WarningContent;
	}
	public void setWarningContent(String WarningContent) {
		this.WarningContent = WarningContent;
	}
	public Integer gettravelMile() {
		return travelMile;
	}
	public void settravelMile(Integer travelMile) {
		this.travelMile = travelMile;
	}
	public Integer gettravelFuel() {
		return travelFuel;
	}
	public void settravelFuel(Integer travelFuel) {
		this.travelFuel = travelFuel;
	}
	public Integer gettboxMile() {
		return tboxMile;
	}
	public void settboxMile(Integer tboxMile) {
		this.tboxMile = tboxMile;
	}
	
	
	
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder(500);		
		builder.append("device id:").append(deviceId).append(",");
		builder.append("上报时间:").append(sys_time).append(",");
		builder.append("总里程类别:").append(total_mileage_category).append(",");		
		builder.append("里程:").append(original_total_mileage).append(",");		
		builder.append("总耗油量:").append(total_fuel_consumption).append(",");
		builder.append("怠速时长:").append(idling).append(",");		
		builder.append("总行程时间:").append(running_time).append(",");
		builder.append("卫星数:").append(satellite).append(",");
		builder.append("位置精度:").append(PDOP).append(",");		
		builder.append("door_status:").append(door_status).append(",");	
		builder.append("lock_status:").append(lock_status).append(",");	
		builder.append("window_status:").append(window_status).append(",");	
		builder.append("light_status:").append(light_status).append(",");			
		builder.append("经度:").append(lng).append(",");		
		builder.append("纬度:").append(lat).append(",");		
		builder.append("gps状态:").append(gpsState).append(",");		
		builder.append("gps速度:").append(gpsSpeed).append(",");	
		builder.append("方向:").append(Direction).append(",");	
		builder.append("车辆速度:").append(speed).append(",");
		builder.append("引擎转速:").append(engine_speed).append(",");
		builder.append("车型ID:").append(car_type).append(",");		
		builder.append("报警ID:").append(alm_id).append(",");		
		builder.append("报警信息:").append(WarningContent).append(",");	
		builder.append("故障码个数:").append(faultCodeTotal).append(",");
		builder.append("故障行驶里程:").append(malfunction_mileage).append(",");		
		builder.append("电压:").append(battery_voltage).append(",");		
		builder.append("运行时间:").append(engine_running_time).append(",");				
		builder.append("剩余油量:").append(remaining_oil).append(",");	
		builder.append("冷却液温度:").append(coolant_temp);
	
	
		
		return builder.toString();
		
//		return "CarBaseData [tboxId=" + deviceId + ", gps状态=" + gpsState + ", 经度=" + lng 
//				+ ", 纬度=" + lat + ", 报警ID=" + alm_id + ", gps速度="+gpsSpeed+", 方向="+Direction
//				+", 电压="+battery_voltage+", 总里程类别="+total_mileage_category
//				+", 总里程="+total_mileage+", 总耗油量="+total_fuel_consumption
//				+", 运行时间="+engine_running_time+", 故障行驶里程="+malfunction_mileage
//				+", 剩余油量="+remaining_oil+", 汽车仪表总里程="+total_instrument_mileage
//				+", 车辆总运行时间="+total_vehicle_operation_time+", 车型ID="+car_type
//				+", 故障码个数="+faultCodeTotal	+", 冷却液温度="+coolant_temp
//				+", 安全状态="+safety_status+", 车门状态="+door_status
//				+", 车窗状态="+window_status+", 灯光状态="+light_status
//				+", 胎压报警="+tire_pressure_warning+", 前刹车片磨损="+f_brake_block_w
//				+", 后刹车片磨损="+b_brake_block_w+", 卫星数="+satellite
//				+", 位置精度="+PDOP+", ACC_ON="+acc_on+", ACC_OFF="+acc_off+", 平均油耗="+ave_oil_consum
//				+", 平均速度="+ave_speed+", 怠速时长="+idling+", speed="+speed+", 上报时间 ="+sys_time+", 报警信息="+WarningContent+" ]";
	
		}

	public String TravelToString()
	{
		StringBuilder builder = new StringBuilder(500);
		builder.append("device id:").append(deviceId).append(",");
		builder.append("ACC_ONTime:").append(acc_on).append(",");
		builder.append("ACC_OFFTime:").append(acc_off).append(",");	
		builder.append("里程类别:").append(total_mileage_category).append(",");		
		builder.append("里程:").append(total_mileage).append(",");		
		builder.append("总耗油量:").append(total_fuel_consumption).append(",");
		builder.append("怠速时长:").append(idling).append(",");		
		builder.append("总行程时间:").append(total_vehicle_operation_time).append(",");
//		builder.append("平均速度:").append(ave_speed);	
//		builder.append("平均耗油:").append(ave_oil_consum);	
		return 	builder.toString();
	}
	
}
