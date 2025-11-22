package com.n1netails.n1netails.cipher.kitsune.repository;

import com.n1netails.n1netails.cipher.kitsune.model.entity.NotificationConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationConfigRepository extends JpaRepository<NotificationConfigEntity, Long> {
}
