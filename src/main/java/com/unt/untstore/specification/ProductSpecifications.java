package com.unt.untstore.specification;

import com.unt.untstore.model.Product;
import com.unt.untstore.model.ProductCategory;
import com.unt.untstore.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public class ProductSpecifications {

    public static Specification<Product> productCategoryEquals(ProductCategory productCategory) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("productCategory"), productCategory);
        };
    }

    public static Specification<Product> productIsDeletedEquals(Boolean isDeleted) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("isDeleted"), isDeleted);
        };
    }

    public static Specification<Product> productDescriptionLike(String productDescription) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(criteriaBuilder.upper(root.get("description")), "%"+productDescription.toUpperCase()+"%");
        };
    }

    public static Specification<Product> productNameLike(String name) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), "%"+name.toUpperCase()+"%");
        };
    }

    public static Specification<Product> createdByUserUidEquals(String createdByUserUid) {
        return (root, query, criteriaBuilder) -> {
            Join<Product, User> userJoin = root.join("createdBy");
            return criteriaBuilder.equal(userJoin.get("uid"), createdByUserUid);
        };
    }

    public static Specification<Product> orderByUpdatedOnDesc() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(root.get("updatedOn")));
            return criteriaBuilder.conjunction();
        };
    }

}
