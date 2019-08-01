package com.xiaohe.ysjspt.controller;

import com.xiaohe.ysjspt.entity.MerchantEntity;
import com.xiaohe.ysjspt.entity.PassageEntity;
import com.xiaohe.ysjspt.service.MerchantService;
import com.xiaohe.ysjspt.service.PassageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


/**
 * 商家管理
 * @author wzq
 * @since 2018-10-15
 */
@RestController
@RequestMapping(value = "/merchantMng")
public class MerchantMngController {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private PassageService passageService;

    /**
     * 保存商家对象<br/>
     *
     * @param merchant
     * @throws Exception
     */
    @RequestMapping(value = "/merchant/save")
    public void save(MerchantEntity merchant) {
        merchant.setCreateTime(new Date());
        merchantService.save(merchant);
    }

    /**
     * 删除商家对象
     *
     * @param tbmerchant
     * @throws Exception
     */
    @RequestMapping(value = "/merchant/delete")
    public void delete(MerchantEntity tbmerchant) {
        merchantService.delete(tbmerchant);
    }

    /**
     * 商家分页查询
     *
     * @param page
     * @param pageSize
     * @return Page<Merchant>
     * @throws Exception
     */
    @RequestMapping(value = "/merchant/findAll")
    public Page<MerchantEntity> findAllMerchant(@RequestParam(value = "merchant_name",required = false)String merchantName,
                                   @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                   @RequestParam(value = "page_size", required = false, defaultValue = "1") Integer pageSize) {
        return merchantService.findAll(merchantName,page, pageSize);
    }

    /**
     * 保存通道对象<br/>
     *
     * @param passage
     * @throws Exception
     */
    @RequestMapping(value = "/passage/save")
    public void save(PassageEntity passage) {
        passage.setCreateTime(new Date());
        passageService.save(passage);
    }

    /**
     * 删除通道对象
     *
     * @param passage
     * @throws Exception
     */
    @RequestMapping(value = "/passage/delete")
    public void delete(PassageEntity passage) {
        passageService.delete(passage);
    }

    /**
     * 通道id分页查询
     *
     * @param page
     * @param pageSize
     * @return Page<Passage>
     * @throws Exception
     */
    @RequestMapping(value = "/passage/findPassageById")
    public Page<PassageEntity> findPassageById(@RequestParam(value = "ids",required = false)String ids,
                                 @RequestParam(value = "mer_name",required = false)String merName,
                                 @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                 @RequestParam(value = "page_size", required = false, defaultValue = "1") Integer pageSize) {
        Page<PassageEntity> all =  passageService.findAll(ids,merName,page, pageSize);
        return all;
    }

}
