package com.dataProcessProject.ibrahimbayburtlu.repository;

import com.dataProcessProject.ibrahimbayburtlu.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
}
