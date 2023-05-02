package com.unt.untstore.dto;

import com.unt.untstore.model.ProductCategory;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {

    private String uid;

    private String name;
    private ProductCategory productCategory;
    private String code;
    private String description;
    private String price;
    private String pictures;
    private String createdBy;
    private Date updatedOn;

}
