package com.unt.untstore.repository;


import com.unt.untstore.model.ProductWishlist;
import com.unt.untstore.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductWishlistRepository extends CrudRepository<ProductWishlist, Long> {

	@Query("select p.uid from ProductWishlist pf join pf.property p join pf.user u where u.uid = :userUid")
	List<String> findAllProductUidByUserUid(String userUid);

	List<ProductWishlist> findAllByUserUid(String userUid);
	
	Page<ProductWishlist> findByUser(User user, Pageable pageable);

	ProductWishlist findByUserUidAndPropertyUid(String userUid, String propertyUid);
}
