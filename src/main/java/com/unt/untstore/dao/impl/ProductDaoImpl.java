package com.unt.untstore.dao.impl;

import com.unt.untstore.dao.ProductDao;
import com.unt.untstore.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private DataSource dataSource;

    @Override
    public List<Product> getProductsByCategoryAndDescription(String productCategory,String description) throws SQLException {
        String query = "select "
                + " id, uid, desc, price, pictures, created_by "
                + " from unt_product" ;
//                " where " +
//                + " product_ct = '"
//                + productCategory
//                +"'"
//                + " and " +
//                " desc = '"
//                + description
//                +"'";
        Connection c = dataSource.getConnection();
        Statement statement = c.createStatement();
        List<Product> productList = new ArrayList<>();
        try {
            Product product = new Product();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                product.setId(resultSet.getLong(1));
                product.setUid(resultSet.getString(2));
                product.setDescription(resultSet.getString(3));
                productList.add(product);
            }
        } finally {
            statement.close();
        }
        return productList;
    }
}
