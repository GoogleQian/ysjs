package com.xiaohe.ysjspt.service;

import com.xiaohe.ysjspt.entity.AdvRich;

import java.util.List;


/**
 * <p>
 *  AdvRich服务类
 * </p>
 *
 * @author gmq
 * @since 2018-11-23
 */
 
public interface AdvRichService extends IBaseService<AdvRich,Integer>{

	List<AdvRich> saveList(List<AdvRich> list);
}


