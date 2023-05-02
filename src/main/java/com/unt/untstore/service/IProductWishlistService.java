package com.unt.untstore.service;


import com.unt.untstore.dto.PaginationResponse;
import com.unt.untstore.dto.ProductDto;

import java.util.List;

public interface IProductWishlistService {
	
	PaginationResponse<ProductDto> getProductWishlistByPage(String userUid, int pageNo, int pageSize);
	
	Boolean saveProductWishlistItem(String propertyUid, String userUid);
	
	Boolean deleteProductWishlistItem(String propertyUid, String userUid);
	
	List<ProductDto> getPropertyListByUser(String userUid);

}
