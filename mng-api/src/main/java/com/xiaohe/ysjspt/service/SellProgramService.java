package com.xiaohe.ysjspt.service;

import com.xiaohe.ysjspt.entity.SellProgramEntity;
import com.xiaohe.ysjspt.jpa.SellProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


/**
 * <p>
 *  TbSellProgram服务类
 * </p>
 *
 * @author wzq
 * @since 2018-08-28
 */
 
@Service
public class SellProgramService {

    @Autowired
  	private SellProgramRepository sellprogramRepository;
    
    /**
	 * 保存对象
	 * 
	 * @param tbsellprogram
	 * 持久对象，或者对象集合
	 * @throws Exception
	 */
    public SellProgramEntity save(SellProgramEntity tbsellprogram) {
      	return sellprogramRepository.save(tbsellprogram);
    }

	/**
	 * 删除对象
	 * 
	 * @param tbsellprogram
	 * @throws Exception
	 */
	 
    public void delete(SellProgramEntity tbsellprogram) {
		  sellprogramRepository.delete(tbsellprogram);
    }
    

    

    
    /**
	 * 返回可用实体的数量
	 * 
	 * @throws Exception
	 */
    public long count() {
      return  sellprogramRepository.count();
    } 
        


	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param pageSize  
	 * @return Page<TbSellProgram>      
	 * @throws Exception
	 *	properties 字符串为需要排序的字段，可以传多个
	 */
 	public Page<SellProgramEntity> findAll(int page, int pageSize) {
        Pageable pageable = new PageRequest(page-1, pageSize, Sort.Direction.DESC, "id");
        return  sellprogramRepository.findAll(pageable);
    }

}


