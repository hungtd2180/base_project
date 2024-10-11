package com.base.base_project.core.services;


import com.base.base_project.core.configuration.IgnoreLogConfiguration;
import com.base.base_project.core.constants.Constant;
import com.base.base_project.core.entities.dto.IdEntity;
import com.base.base_project.core.entities.event.Event;
import com.base.base_project.core.repositories.CustomJpaRepository;
import com.base.base_project.core.utils.ObjectMapperUtil;
import com.base.base_project.core.utils.SecurityUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hungtd
 * Date: 11/10/2024
 * Time: 2:49 CH
 * for all issues, contact me: hungtd2180@gmail.com
 */
@SuppressWarnings({"Duplicates", "unchecked"})
@Transactional
public abstract class CrudService<T extends IdEntity, ID extends Serializable> {
    private static final Logger logger = LoggerFactory.getLogger(CrudService.class);
    protected CustomJpaRepository<T, ID> repository;
    protected final Class<T> typeParameterClass;

    @PersistenceContext
    protected EntityManager entityManager;
    protected MessageSource messageSource;

    @Autowired
    protected IgnoreLogConfiguration ignoreLogConfiguration;

    public CrudService(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public Event process(Event event) {
        event.errorCode = Constant.ResultStatus.SUCCESS;
        writeLogToFile(typeParameterClass.getSimpleName(), event.method, event.payload, "start");
        switch (event.method) {
            case Constant.Method.CREATE:
                event = processCreate(event);
                break;
            case Constant.Method.UPDATE:
                break;
            case Constant.Method.DELETE:
                break;
            case Constant.Method.GET_ONE:
                break;
            case Constant.Method.GET_ALL:
                break;
            default:
                event.errorCode = Constant.ResultStatus.ERROR;
        }
        writeLogToFile(typeParameterClass.getSimpleName(), event.method, event.payload, "end");
        return event;
    }


    protected void writeLogToFile(String className, String methodName, String payload, String status) {
        Long currentId = 0L; //Thay đổi khi có id thực tế
        String ignoreLogValue = ignoreLogConfiguration.getIgnoreLog().get(className);
        if (ignoreLogValue != null && ignoreLogValue.equals(methodName)) {
            //Bỏ qua ghi log
            return;
        }
        logger.info("ClassName {} userId {} Method {} {} Entity #{}", className, currentId, methodName, status, payload);
    }

    public Event processCreate(Event event) {
        T entity = ObjectMapperUtil.objectMapper(event.payload, typeParameterClass);
        event.payload = ObjectMapperUtil.toJsonString(create(entity));
        event.errorCode = Constant.ResultStatus.SUCCESS;
        return event;
    }

    public Event processUpdate(Event event) {
        T entity = ObjectMapperUtil.objectMapper(event.payload, typeParameterClass);
        if (entity.getId() == null || get((ID) entity.getId()) == null ) {
            return event; //Thêm xử lí lỗi handle ErrorMessage
        }
        event.payload = ObjectMapperUtil.toJsonString(update((ID) entity.getId(), entity));
        event.errorCode = Constant.ResultStatus.SUCCESS;
        return event;
    }

    public T get(ID id) {
        return repository.findById(id).orElse(null);
    }

    public List<T> getAll() {
        return repository.findAll();
    }

    public T create(T entity) {
        beforeCreate(entity);
        repository.save(entity);
        afterCreate(entity);
        return entity;
    }

    public T update(ID id, T entity) {
        beforeUpdate(entity);
        T old = get(id);
        if (old == null) {
            throw new EntityNotFoundException("No entity with id: " + id);
        }
        if (entity.getCreated() == null) {
            entity.setCreated(old.getCreated());
        }
        if (entity.getCreatedBy() == null) {
            entity.setCreatedBy(old.getCreatedBy());
        }
        repository.save(entity);
        afterUpdate(old, entity);
        return entity;
    }

    public void delete(T entity) {
        beforeDelete(entity);
        repository.delete(entity);
        afterDelete(entity);
    }

    public void deleteById(ID id) {
        T entity = get(id);
        delete(entity);
    }

    protected void beforeCreate(T entity) {
        entity.setCreated(System.currentTimeMillis());
        if (entity.getCreatedBy() == null) {
            String currentUsername = SecurityUtil.getCurrentUserLogin();
            entity.setCreatedBy(currentUsername);
        }
        if (entity.getActive() == null) {
            entity.setActive(Constant.EntityStatus.ACTIVE);
        }
    }

    protected void afterCreate(T entity) {
        // Làm gì đó thì override
    }

    protected void beforeUpdate(T entity) {
        entity.setUpdated(System.currentTimeMillis());
        entity.setUpdatedBy(SecurityUtil.getCurrentUserLogin());
        if (entity.getActive() == null) {
            entity.setActive(Constant.EntityStatus.ACTIVE);
        }
    }
    protected void afterUpdate(T old, T entity) {
        // Làm gì đó thì override
    }

    protected void beforeDelete(T entity) {
        // Làm gì đó thì override
    }

    protected void afterDelete(T entity) {
        // Làm gì đó thì override
    }
}
