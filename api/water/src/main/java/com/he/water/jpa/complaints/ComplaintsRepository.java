package com.he.water.jpa.complaints;

import com.he.water.entity.complaints.Complaints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 投诉建议表 jpa 接口
 *
 * @author gmq
 * @since 2018-12-10
 */

@Transactional(rollbackFor = Exception.class)
public interface ComplaintsRepository extends JpaRepository<Complaints, Integer> {


}