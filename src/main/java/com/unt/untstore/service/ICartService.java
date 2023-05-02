package com.unt.untstore.service;


import com.unt.untstore.dto.PaginationResponse;
import com.unt.untstore.dto.ProductDto;

import java.util.List;

public interface ICartService {
	
	PaginationResponse<ProductDto> getCartItemsByPage(String userUid, int pageNo, int pageSize);
	
	Boolean saveCartItem(String propertyUid, String userUid);
	
	Boolean deleteCartItem(String propertyUid, String userUid);
	
	List<ProductDto> getCartItemsByUser(String userUid);

}
