package com.unt.untstore.repository;

import com.unt.untstore.dto.ProductDto;
import com.unt.untstore.mapper.ProductRowMapper;
import com.unt.untstore.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductRepositoryUnsafe {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public List<Product> filterProductsByName(String name) {
        return jdbcTemplate.query("select * from unt_product where name ='" + name + "'", new ProductRowMapper());
    }

    public List<Product> filterProductsByNameSafe(String name) {

        final String SELECT_SQL = "select * from unt_product where name = ?";

        PreparedStatementCreator statementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(SELECT_SQL);
                ps.setString(1, name);
                return ps;
            }
        };

        return jdbcTemplate.query(statementCreator, new ProductRowMapper());
    }




}
