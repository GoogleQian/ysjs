package com.xiaohe.ysjspt.service.impl;

import com.xiaohe.ysjspt.entity.AdvertisingEntity;
import com.xiaohe.ysjspt.entity.Result;
import com.xiaohe.ysjspt.jpa.AdvertisingRepository;
import com.xiaohe.ysjspt.modules.sys.untils.Constant;
import com.xiaohe.ysjspt.service.AdvertisingService;
import com.xiaohe.ysjspt.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @program: ysjsApi
 * @description: 广告实现类
 * @author: Gmq
 * @date: 2018-11-21 10:28
 **/
@Service
public class AdvertisingServiceImpl implements AdvertisingService {
    @Autowired
    private AdvertisingRepository advertisingRepository;
    @Autowired
    private Environment env;

    @Override
    public AdvertisingEntity save(AdvertisingEntity advertisingEntity) {
        return advertisingRepository.save(advertisingEntity);
    }

    @Override
    public AdvertisingEntity findByDevId(Long devId) {
        return null;
    }

    @Override
    public void delete(Integer integer) {
        advertisingRepository.delete(integer);
    }

    @Override
    public boolean exists(Integer integer) {
        return advertisingRepository.exists(integer);
    }

    @Override
    public long count() {
        return advertisingRepository.count();
    }

    @Override
    public AdvertisingEntity findById(Integer integer) {
        return advertisingRepository.findOne(integer);
    }

    @Override
    public Page<AdvertisingEntity> findAll(int page, int pageSize) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "id");
        return advertisingRepository.findAll(pageable);
    }

    @Override
    public int countAllByTitle(String title) {
        return advertisingRepository.countAllByTitle(title);
    }

    @Override
    public List<String> findTitleByUserId(Long aLong) {
        return advertisingRepository.findTitleByUserId(aLong);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class})
    public Result saveAdv(AdvertisingEntity adv, String path, MultipartFile file) {
        Result result = new Result();
        BufferedOutputStream out = null;
        try {
            Long userId = adv.getUserId();
            String url = adv.getLinkUrl();
            String content = adv.getContent();
            Integer useHyperLink = adv.getUseHyperLink();
            UUID uuid = UUID.randomUUID();
            //修改不一定更新图片
            if (file != null) {
                //删除旧图片
                if(StringUtils.isNotBlank(adv.getImgUrl())&&adv.getId()!=null){
                    Result result1 = deleteImge(adv);
                    if(result1.getRet()!=0){
                        return result1;
                    }
                }
                Result upload = upload(file, path + uuid + userId);
                if (upload.getRet() != 0) {
                    return upload;
                }
                adv.setImgUrl(Constant.ADV_PREFIX + uuid + userId + file.getOriginalFilename());
            }
            adv.setLinkUrl(0==useHyperLink ? (Constant.ADV_PREFIX + uuid + Constant.HTML) : url);
            if (StringUtils.isNotBlank(content)&&0==useHyperLink) {
                uploadHtml(content, path+uuid);
            }
            adv.setContent(content);
            //图片超链接地址
            adv.setUploadTime(new Date());
            adv.setUserId(userId);
            advertisingRepository.save(adv);
        } catch (Exception e) {
            throw e;
        }
        return result.ok();
    }

    /**
     * 上传HTML
     *
     * @param htmlStr
     * @param attr
     * @return
     */
    public boolean uploadHtml(String htmlStr, String attr) {
        StringBuilder stringHtml = new StringBuilder(Constant.HTMLHEAD+htmlStr);
        try {
            //创建文件
            PrintStream printStream = new PrintStream(new FileOutputStream(
                    new File( attr + Constant.HTML)));
            //将HTML文件内容写入文件中
            printStream.println(stringHtml.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 上传广告图片
     *
     * @param file
     * @return
     */
    @Override
    public Result upload(MultipartFile file, String path) {
        Result result = new Result();
        BufferedOutputStream out = null;
        /*
         * 这段代码执行完毕之后，图片上传到了工程的跟路径； 大家自己扩散下思维，如果我们想把图片上传到
         * d:/files大家是否能实现呢？ 等等;
         * 这里只是简单一个例子,请自行参考，融入到实际中可能需要大家自己做一些思考，比如： 1、文件路径； 2、文件名；
         * 3、文件格式; 4、文件大小的限制;
         */
        try {
            out = new BufferedOutputStream(
                    new FileOutputStream(new File(path + file.getOriginalFilename())));
            out.write(file.getBytes());
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return result.error(2000, "文件不能找到");
        } catch (IOException e) {
            e.printStackTrace();
            return result.error(2000, "上传失败");
        }
        return result.ok();
    }

    @Override
    public Page<AdvertisingEntity> findAll(Integer page, Integer pageSize, Long userId) {
        //过滤自己的广告
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "id");
        //查询条件构造
        Specification<AdvertisingEntity> spec = new Specification<AdvertisingEntity>() {
            @Override
            public Predicate toPredicate(Root<AdvertisingEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (userId != null) {
                    //管理员可以看所有
                    if (1 != userId) {
                        predicate.getExpressions().add(cb.equal(root.get("userId").as(Long.class), userId));
                    }
                }
                return predicate;
            }
        };
        return advertisingRepository.findAll(spec, pageable);
    }

    @Override
    public Result deleteImge(AdvertisingEntity adv) {
        Result result=new Result();
        if(adv==null){
            return result.error(2000, "广告不存在，删除失败");
        }
        String imgUrl = adv.getImgUrl();
        if(StringUtils.isNotBlank(imgUrl)&&imgUrl.contains(Constant.ADV_PREFIX)){
            String realPath = env.getProperty("cbs.uploadPath")+imgUrl.replace(Constant.ADV_PREFIX, "");
            File file = new File(realPath);
            if(file.exists()){
                boolean delete = file.delete();
                if(!delete){
                    return result.error(2000, "删除图片失败");
                }
            }
        }
        return result.ok();
    }
}
