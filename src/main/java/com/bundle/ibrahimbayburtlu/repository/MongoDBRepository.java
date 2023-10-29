package com.bundle.ibrahimbayburtlu.repository;

import com.bundle.ibrahimbayburtlu.entity.MongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoDBRepository extends MongoRepository<MongoEntity,String> {

}
