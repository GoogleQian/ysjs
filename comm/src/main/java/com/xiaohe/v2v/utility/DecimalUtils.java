package com.xiaohe.v2v.utility;

import java.math.BigDecimal;

public class DecimalUtils {
	
	public static float getThreeDecimal(float number) {
		
		BigDecimal bigDecimal = new BigDecimal(number);
		float num = bigDecimal.setScale(3,BigDecimal.ROUND_HALF_UP).floatValue();
		System.out.println(number + "=" + num);
		return num;
	}

	
}
