package com.unt.untstore.controller;

import com.unt.untstore.dto.PaginationResponse;
import com.unt.untstore.dto.ProductDto;
import com.unt.untstore.dto.ProductSearchCriteriaDto;
import com.unt.untstore.dto.Response;
import com.unt.untstore.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping("/list/{pageNo}/{pageSize}")
    public PaginationResponse<ProductDto> getProductListingsCriteria(@PathVariable int pageNo,
                                                                      @PathVariable int pageSize,
                                                                      @RequestBody ProductSearchCriteriaDto productSearchCriteriaDto) {
        return productService.getProductsByCriteria(productSearchCriteriaDto, pageNo, pageSize);
    }

    @PostMapping("/unsafe/list/{pageNo}/{pageSize}")
    public PaginationResponse<ProductDto> getProductListingsCriteriaSQLInjection(@PathVariable int pageNo,
                                                                     @PathVariable int pageSize,
                                                                     @RequestBody ProductSearchCriteriaDto productSearchCriteriaDto)  throws SQLException {
        return productService.getProductsByCriteriaUnsafe(productSearchCriteriaDto, pageNo, pageSize);
    }

    @PostMapping("/save")
    public Response<Boolean> saveProduct(@RequestBody ProductDto productDto) {
        return new Response<>(productService.saveOrUpdateProductDetails(productDto));
    }

    @PostMapping("/update")
    public Response<Boolean> updateProduct(@RequestBody ProductDto productDto) {
        return new Response<>(productService.saveOrUpdateProductDetails(productDto));
    }

    @DeleteMapping("/delete/{productUid}")
    public Response<Boolean> deleteProductUnsafe(@PathVariable String productUid){
        return new Response<>(productService.deleteProduct(productUid));
    }

    @GetMapping("/unsafe/delete/{productUid}")
    public Response<Boolean> deleteProduct(@PathVariable String productUid){
        return new Response<>(productService.deleteProduct(productUid));
    }

    @GetMapping("/{productUid}")
    public Response<ProductDto> getProductDetails(@PathVariable String productUid) {
        ProductDto productDto = productService.getProductDetails(productUid);
        return new Response<>(productDto);
    }

    @GetMapping("/{userUid}/{pageNo}/{pageSize}")
    public PaginationResponse<ProductDto> getProductListingsByUser(@PathVariable String userUid,
                                                                    @PathVariable int pageNo, @PathVariable int pageSize) {
        return productService.getProductsByUserUid(userUid, pageNo, pageSize);
    }

}
