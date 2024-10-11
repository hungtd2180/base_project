package com.base.base_project.core.repositories;


import com.base.base_project.core.entities.dto.IdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

/**
 * Created by hungtd
 * Date: 11/10/2024
 * Time: 2:41 CH
 * for all issues, contact me: hungtd2180@gmail.com
 */

public interface CustomJpaRepository <T extends IdEntity, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    T findFistById(ID id);
}
