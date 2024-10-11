package com.base.base_project.core.entities.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.MappedSuperclass;

import org.javers.core.metamodel.annotation.*;
import java.io.Serializable;

/**
 * Created by hungtd
 * Date: 11/10/2024
 * Time: 10:40 SA
 * for all issues, contact me: hungtd2180@gmail.com
 */

@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown = true)
public class AbstractEntity implements Serializable {
    private Long created;

    @DiffIgnore
    private Long updated;
    private String createdBy;
    @DiffIgnore
    private String updatedBy;
    public Integer active;

    public Long getCreated() {
        return created;
    }
    public void setCreated(Long created) {
        this.created = created;
    }
    public Long getUpdated() {
        return updated;
    }
    public void setUpdated(Long updated) {
        this.updated = updated;
    }
    public String getCreatedBy(){
        return createdBy;
    }
    public void setCreatedBy(String createdBy){
        this.createdBy = createdBy;
    }
    public String getUpdatedBy(){
        return updatedBy;
    }
    public void setUpdatedBy(String updatedBy){
        this.updatedBy = updatedBy;
    }
    public Integer getActive() {
        return active;
    }
    public void setActive(Integer active) {
        this.active = active;
    }
}
