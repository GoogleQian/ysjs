package com.xiaohe.ysjspt.service;

import com.xiaohe.ysjspt.entity.MerchantEntity;
import com.xiaohe.ysjspt.jpa.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;


/**
 * <p>
 *  TbMerchant服务类
 * </p>
 *
 * @author wzq
 * @since 2018-10-15
 */
 
@Service
public class MerchantService {

    @Autowired
  	private MerchantRepository merchantRepository;
    
    /**
	 * 保存对象
	 * 
	 * @param merchant
	 * 持久对象，或者对象集合
	 * @throws Exception
	 */
    public void save(MerchantEntity merchant) {
        merchantRepository.save(merchant);
    }

	/**
	 * 删除对象
	 * 
	 * @param merchant
	 * @throws Exception
	 */
	 
    public void delete(MerchantEntity merchant) {
        merchantRepository.delete(merchant);
    }
    

    
    /**
	 * 返回可用实体的数量
	 * 
	 * @throws Exception
	 */
    public long count() {
      return  merchantRepository.count();
    } 
        


	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param pageSize  
	 * @return Page<TbMerchant>      
	 * @throws Exception
	 *	properties 字符串为需要排序的字段，可以传多个
	 */
 	public Page<MerchantEntity> findAll(String merchantName,int page, int pageSize) {
        Pageable pageable =  new PageRequest(page-1, pageSize, Sort.Direction.DESC, "id");
        return  merchantRepository.findAll(pageable);
    }

	/**
	 * 查询经销商集合
	 * @return
	 */
	public List<MerchantEntity> findAll(){
		return merchantRepository.findAll();
	}

	/**
	 * 查询单个经销商
	 * @return
	 */
	public MerchantEntity getOne(Integer integer){
		return merchantRepository.getOne(integer);
	}

}


