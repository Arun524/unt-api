package com.unt.untstore.controller;

import com.unt.untstore.dto.PaginationResponse;
import com.unt.untstore.dto.ProductDto;
import com.unt.untstore.dto.Response;
import com.unt.untstore.service.IProductWishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-wishlist")
public class ProductWishlistController {

	@Autowired
	private IProductWishlistService productWishlistService;
	
	@GetMapping("/save/{propertyUid}/{userUid}")
	public Response<Boolean> saveProductWishlist(@PathVariable String propertyUid, @PathVariable String userUid) {
		return new Response<>(productWishlistService.saveProductWishlistItem(propertyUid, userUid));
	}

	@DeleteMapping("/delete/{propertyUid}/{userUid}")
	public Response<Boolean> deleteProductWishlist(@PathVariable String propertyUid, @PathVariable String userUid) {
		return new Response<>(productWishlistService.deleteProductWishlistItem(propertyUid,userUid));
	}

	@GetMapping("/bypage/{userUid}/{pageNo}/{pageSize}")
	public PaginationResponse<ProductDto> getProductWishlistByPage(@PathVariable String userUid,
																   @PathVariable int pageNo, @PathVariable int pageSize) {
		return productWishlistService.getProductWishlistByPage(userUid, pageNo, pageSize);
	}
	
	@GetMapping("/{userUid}")
	public Response<List<ProductDto>> findAllByUser(@PathVariable String userUid) {
		return new Response<>(productWishlistService.getPropertyListByUser(userUid));
	}
	
}
