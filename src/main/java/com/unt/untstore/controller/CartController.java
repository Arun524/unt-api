package com.unt.untstore.controller;

import com.unt.untstore.dto.PaginationResponse;
import com.unt.untstore.dto.ProductDto;
import com.unt.untstore.dto.Response;
import com.unt.untstore.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private ICartService cartService;
	
	@GetMapping("/save/{productUid}/{userUid}")
	public Response<Boolean> saveCartItems(@PathVariable String productUid, @PathVariable String userUid) {
		return new Response<>(cartService.saveCartItem(productUid, userUid));
	}

	@DeleteMapping("/delete/{productUid}/{userUid}")
	public Response<Boolean> deletePropertyCartItems(@PathVariable String productUid, @PathVariable String userUid) {
		return new Response<>(cartService.deleteCartItem(productUid,userUid));
	}

	@GetMapping("/bypage/{userUid}/{pageNo}/{pageSize}")
	public PaginationResponse<ProductDto> getProductCartByPage(@PathVariable String userUid,
															   @PathVariable int pageNo, @PathVariable int pageSize) {
		return cartService.getCartItemsByPage(userUid, pageNo, pageSize);
	}
	
	@GetMapping("/{userUid}")
	public Response<List<ProductDto>> findAllByUser(@PathVariable String userUid) {
		return new Response<>(cartService.getCartItemsByUser(userUid));
	}
	
}
