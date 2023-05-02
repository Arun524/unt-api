package com.unt.untstore.mapper;

import com.unt.untstore.dto.ProductDto;
import com.unt.untstore.model.Product;
import com.unt.untstore.model.ProductCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong("id"));
        product.setUid(rs.getString("uid"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("desc"));
        product.setProductCategory(ProductCategory.valueOf(rs.getString("PRODUCT_CT")));
        product.setPictures(rs.getString("pictures"));
        product.setPrice(rs.getString("price"));
        return product;
    }
}

