package com.xiaohe.ysjspt.jpa;

import com.xiaohe.ysjspt.entity.DevStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author
 * wzq
 */

public interface DevStatusRepository extends JpaRepository<DevStatus, Integer> {

   /**
    * findAll
    * @param specification
    * @param pageable
    * @return
    */
   Page<DevStatus> findAll(Specification specification, Pageable pageable);
}