package com.base.base_project.core.endpoints;


import com.base.base_project.core.constants.Constant;
import com.base.base_project.core.entities.dto.ApiOutput;
import com.base.base_project.core.entities.dto.IdEntity;
import com.base.base_project.core.entities.event.Event;
import com.base.base_project.core.services.CrudService;
import com.base.base_project.core.utils.CommonUtil;
import com.base.base_project.core.utils.ObjectMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hungtd
 * Date: 14/10/2024
 * Time: 4:14 CH
 * for all issues, contact me: hungtd2180@gmail.com
 */

public abstract class CrudEndpoint<T extends IdEntity, ID extends Serializable> {
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(CrudEndpoint.class);

    protected Class<T> entityClass;
    protected CrudService<T , ID> service;

    protected String baseUrl;

    public CrudEndpoint(CrudService service) {
        this.service = service;
    }
    @GetMapping(value = "{id}")
    public ApiOutput get(@PathVariable ID id) {
        Map<String, String> entityId = new HashMap<>();
        entityId.put("id", id.toString());
        Event event = new Event();
        event.method = Constant.Method.GET_ONE;
        event.payload = ObjectMapperUtil.objectMapper(entityId.toString(), entityClass);
        service.process(event);
        ApiOutput apiOutput = CommonUtil.packing(event);
        return apiOutput;
    }

    @GetMapping
    public ApiOutput getAll() {
        Event event = new Event();
        event.method = Constant.Method.GET_ALL;
        service.process(event);
        ApiOutput apiOutput = CommonUtil.packing(event);
        return apiOutput;
    }

    @PostMapping
    public ApiOutput create(@RequestBody T entity) {
        Event event = new Event();
        event.method = Constant.Method.CREATE;
        event.payload = ObjectMapperUtil.objectMapper(entity.toString(), entityClass);
        service.process(event);
        ApiOutput apiOutput = CommonUtil.packing(event);
        return apiOutput;
    }

    @PutMapping
    public ApiOutput update(@RequestBody T entity) {
        Event event = new Event();
        event.method = Constant.Method.UPDATE;
        event.payload = ObjectMapperUtil.objectMapper(entity.toString(), entityClass);
        service.process(event);
        ApiOutput apiOutput = CommonUtil.packing(event);
        return apiOutput;
    }

    @DeleteMapping(value = "{id}")
    public ApiOutput delete(@PathVariable ID id) {
        Map<String, String> entityId = new HashMap<>();
        entityId.put("id", id.toString());
        Event event = new Event();
        event.method = Constant.Method.DELETE;
        event.payload = ObjectMapperUtil.objectMapper(entityId.toString(), entityClass);
        service.process(event);
        ApiOutput apiOutput = CommonUtil.packing(event);
        return apiOutput;
    }

}