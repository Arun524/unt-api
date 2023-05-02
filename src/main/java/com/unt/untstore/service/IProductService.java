package com.unt.untstore.service;

import com.unt.untstore.dto.PaginationResponse;
import com.unt.untstore.dto.ProductDto;
import com.unt.untstore.dto.ProductSearchCriteriaDto;

import java.sql.SQLException;

public interface IProductService {

    public Boolean saveOrUpdateProductDetails(ProductDto productDto);

    public PaginationResponse<ProductDto> getProductsByCriteria(ProductSearchCriteriaDto productSearchCriteriaDto,
                                                                int pageNo, int pageSize);

    public PaginationResponse<ProductDto> getProductsByCriteriaUnsafe(ProductSearchCriteriaDto productSearchCriteriaDto,
                                                                      int pageNo, int pageSize)  throws SQLException;

    public PaginationResponse<ProductDto> getProductsByUserUid(String userUid, int pageNo, int pageSize);

    public ProductDto getProductDetails(String productUid);

    public Boolean deleteProduct(String productUid);

}
