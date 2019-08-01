package com.xiaohe.ysjspt.jpa;


import com.xiaohe.ysjspt.entity.HistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


/**
 * 
 * 历史记录Dao
 * 
 * @version 
 * <pre>
 * @Author	Version		Date		Changes
 * admin 	1.0  		2018年05月03日 	Created
 *
 * </pre>
 * @since 1.
 */
@Transactional(rollbackFor = { RuntimeException.class })
public interface HistoryRepository extends JpaRepository<HistoryEntity,Long> {
    /**
     * findAll
     * @param spec
     * @param pageable
     * @return
     */
    Page<HistoryEntity> findAll(Specification<HistoryEntity> spec, Pageable pageable);

    /*HistoryEntity save(HistoryEntity entity);

    void update(HistoryEntity entity);

    List<HistoryEntity> findAll();*/


}