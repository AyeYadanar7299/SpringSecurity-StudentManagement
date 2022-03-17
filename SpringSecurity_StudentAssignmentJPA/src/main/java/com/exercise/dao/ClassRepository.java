package com.exercise.dao;

import org.springframework.data.repository.CrudRepository;

import com.exercise.dto.Class;

public interface ClassRepository extends CrudRepository<Class,String> {

}
