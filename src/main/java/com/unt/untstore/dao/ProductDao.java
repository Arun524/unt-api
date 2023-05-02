package com.unt.untstore.dao;

import com.unt.untstore.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {
    public List<Product> getProductsByCategoryAndDescription(String productCategory, String description)  throws SQLException;
}
