package com.mindhub.ms_product.services;

import com.mindhub.ms_product.exceptions.NotFoundException;

import java.util.List;

public interface GenericService<E> {
    E findById(Long id) throws Exception;
    List<E> findAll();
    void deleteById(Long id);
    E save(E entity);
    void existsById(Long id) throws NotFoundException;
}
