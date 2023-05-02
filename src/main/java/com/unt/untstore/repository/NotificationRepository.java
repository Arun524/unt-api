package com.unt.untstore.repository;

import com.unt.untstore.model.Notification;
import com.unt.untstore.model.NotificationStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends CrudRepository<Notification, Long>{

	Optional<Notification> findByUid(String uid);
	
	List<Notification> findByStatus(NotificationStatus status);
	
}
