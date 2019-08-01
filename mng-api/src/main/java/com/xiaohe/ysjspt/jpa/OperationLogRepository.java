package com.xiaohe.ysjspt.jpa;

import com.xiaohe.ysjspt.entity.OperationLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * @author
 * wzq
 */

public interface OperationLogRepository extends JpaRepository<OperationLogEntity,Long> {
    /**
     * add
     * @param userName
     * @param description
     * @param param
     * @param creatDate
     * @return
     */
    @Modifying
    @Query(value = "insert into tb_operation_log(user_name,description,param,creat_date) values(?1,?2,?3,?4)",nativeQuery = true)
    int add(String userName,String description,String param,String creatDate);
}
