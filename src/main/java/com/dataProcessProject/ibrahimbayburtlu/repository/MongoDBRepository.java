package com.dataProcessProject.ibrahimbayburtlu.repository;

import com.dataProcessProject.ibrahimbayburtlu.entity.MongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoDBRepository extends MongoRepository<MongoEntity,String> {

}
