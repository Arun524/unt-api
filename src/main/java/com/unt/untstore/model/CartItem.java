package com.unt.untstore.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "CART_ITEM")
@Getter
@Setter
public class CartItem extends BaseModel {

	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID", nullable = false)
	private Product product;

	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

}