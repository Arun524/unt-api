package com.unt.untstore.repository;


import com.unt.untstore.model.CartItem;
import com.unt.untstore.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartRepository extends CrudRepository<CartItem, Long> {

	@Query("select p.uid from CartItem pf join pf.product p join pf.user u where u.uid = :userUid")
	List<String> findAllProductUidByUserUid(String userUid);

	List<CartItem> findAllByUserUid(String userUid);
	
	Page<CartItem> findByUser(User user, Pageable pageable);

	CartItem findByUserUidAndProductUid(String userUid, String propertyUid);
}
