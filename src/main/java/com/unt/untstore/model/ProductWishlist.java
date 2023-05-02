package com.unt.untstore.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCT_WISHLIST")
public class ProductWishlist extends BaseModel {

	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID", nullable = false)
	@Getter
	@Setter
	private Product property;

	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	@Getter
	@Setter
	private User user;

}