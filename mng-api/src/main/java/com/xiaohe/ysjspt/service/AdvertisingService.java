package com.xiaohe.ysjspt.service;

import com.xiaohe.ysjspt.entity.AdvertisingEntity;
import com.xiaohe.ysjspt.entity.Result;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 广告表 TbAdvertising服务类
 * </p>
 *
 * @author wzq
 * @since 2018-11-19
 */
public interface AdvertisingService extends IBaseService<AdvertisingEntity, Integer>{
    /**
     * 根据标题查询数量
     * @return
     */
    int countAllByTitle(String title);

    /**
     * 查询指定商户下的所有名称
     * @param aLong
     * @return
     */
    List<String> findTitleByUserId(Long aLong);

    /**
     * 保存上传广告
     * @param entity 实体
     * @param path 路径
     * @return
     */
    Result saveAdv(AdvertisingEntity entity,String path,MultipartFile file);

    /**
     * 上传图片
     * @param file
     * @param path
     * @return
     */
     Result upload(MultipartFile file, String path);

    /**
     * 按条件查询
     * @param page 页
     * @param pageSize  数量
     * @param aLong userId
     * @return Page<Coupon>
     */
    Page<AdvertisingEntity> findAll(Integer page, Integer pageSize, Long aLong);

    /**
     * 删除图片
     * @param entity
     * @return
     */
    Result deleteImge(AdvertisingEntity entity);

}
