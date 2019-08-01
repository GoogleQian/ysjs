package com.xiaohe.ysjspt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.xiaohe.ysjspt.entity.WaterMngEntity;
import com.xiaohe.ysjspt.jpa.WaterMngRepository;


/**
 * <p>
 *  TbWaterMng服务类
 * </p>
 *
 * @author wzq
 * @since 2018-10-16
 */
 
@Service
public class WaterMngService {

    @Autowired
  	private WaterMngRepository watermngRepository;
    
    /**
	 * 保存对象
	 * 
	 * @param watermng
	 * 持久对象，或者对象集合
	 * @throws Exception
	 */
    public void save(WaterMngEntity watermng) {
        watermngRepository.save(watermng);
    }

	/**
	 * 删除对象
	 * 
	 * @param watermng
	 * @throws Exception
	 */
	 
    public void delete(WaterMngEntity watermng) {
        watermngRepository.delete(watermng);
    }
    
    /**
	 * 返回可用实体的数量
	 * 
	 * @throws Exception
	 */
    public long count() {
      return  watermngRepository.count();
    }

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param pageSize  
	 * @return Page<TbWaterMng>      
	 * @throws Exception
	 *	properties 字符串为需要排序的字段，可以传多个
	 */
 	public Page<WaterMngEntity> findAll(int page, int pageSize) {
        Pageable pageable = new PageRequest(page-1, pageSize, Sort.Direction.DESC, "properties");
        return  watermngRepository.findAll(pageable);
    }

}


