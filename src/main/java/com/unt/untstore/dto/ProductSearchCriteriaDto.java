package com.unt.untstore.dto;

import com.unt.untstore.model.ProductCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchCriteriaDto {

    private ProductCategory productCategory;

    private String productDescription;

}
