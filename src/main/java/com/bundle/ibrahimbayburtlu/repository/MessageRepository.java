package com.bundle.ibrahimbayburtlu.repository;

import com.bundle.ibrahimbayburtlu.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
}
