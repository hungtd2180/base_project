package com.base.base_project.converters;

public interface EntityConvert <E, D>{
    E toEntity(D d);
    D toDto(E e);
}
