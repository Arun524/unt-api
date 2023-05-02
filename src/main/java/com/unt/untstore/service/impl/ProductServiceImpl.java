package com.unt.untstore.service.impl;

import com.unt.untstore.dao.ProductDao;
import com.unt.untstore.dto.PaginationResponse;
import com.unt.untstore.dto.ProductDto;
import com.unt.untstore.dto.ProductSearchCriteriaDto;
import com.unt.untstore.model.Product;
import com.unt.untstore.repository.NotificationRepository;
import com.unt.untstore.repository.ProductRepository;
import com.unt.untstore.repository.ProductRepositoryUnsafe;
import com.unt.untstore.repository.UserRepository;
import com.unt.untstore.service.IEmailService;
import com.unt.untstore.service.IProductService;
import com.unt.untstore.service.ThymeleafService;
import com.unt.untstore.specification.ProductSpecifications;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {

    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ThymeleafService thymeleafService;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private IEmailService emailService;

    @Autowired
    private ProductRepositoryUnsafe productRepositoryUnsafe;

    @Override
    public Boolean saveOrUpdateProductDetails(ProductDto productDto) {
        Product product = null;
        if (productDto.getUid() != null) {
            Optional<Product> productOptional = productRepository.findByUid(productDto.getUid());
            if (productOptional.isPresent()) {
                product = productOptional.get();
            } else {
                throw new RuntimeException("ERROR.INVALID_PROPERTY_UID");
            }
        } else {
            product = new Product();
            product.setUpdatedOn(Calendar.getInstance().getTime());
        }

        product.setProductCategory(productDto.getProductCategory());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setPictures(productDto.getPictures());
        product.setName(productDto.getName());
        product.setIsDeleted(false);
        if (productDto.getCreatedBy() != null) {
            product.setCreatedBy(userRepository.findByUid(productDto.getCreatedBy()).get());
        }
        productRepository.save(product);

        return true;
    }

    @Override
    public PaginationResponse<ProductDto> getProductsByCriteria(ProductSearchCriteriaDto productSearchCriteriaDto,
                                                                int pageNo, int pageSize) {
        Specification<Product> productSpecification = buildProductSpecification(productSearchCriteriaDto);

        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Page<Product> productPage = productRepository.findAll(productSpecification, pageRequest);
        List<Product> properties = productPage.getContent();

        PaginationResponse<ProductDto> paginationResponse = buildPaginationResponse(productPage, properties);
        return paginationResponse;
    }

    @Override
    public PaginationResponse<ProductDto> getProductsByCriteriaUnsafe(ProductSearchCriteriaDto productSearchCriteriaDto, int pageNo, int pageSize) throws SQLException {
        List<Product> productList = productRepositoryUnsafe.filterProductsByName(productSearchCriteriaDto.getProductDescription());
        List<ProductDto> productDtoList = productList.stream()
                .map(product -> ProductDto.builder().uid(product.getUid())
                        .productCategory(product.getProductCategory())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .updatedOn(product.getUpdatedOn())
                        .pictures(product.getPictures())
                        .build())
                .collect(Collectors.toList());
        PaginationResponse<ProductDto> paginationResponse = new PaginationResponse<>();
        paginationResponse.setBody(productDtoList);
        return paginationResponse;
    }

    private PaginationResponse<ProductDto> buildPaginationResponse(Page<Product> productPage,
                                                                   List<Product> properties) {
        PaginationResponse<ProductDto> paginationResponse = new PaginationResponse<>();
        List<ProductDto> productDtoList = properties.stream()
                .map(product -> ProductDto.builder().uid(product.getUid())
                        .productCategory(product.getProductCategory())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .name(product.getName())
                        .updatedOn(product.getUpdatedOn())
                        .pictures(product.getPictures())
                        .createdBy(product.getCreatedBy().getUid())
                        .build())
                .collect(Collectors.toList());
        paginationResponse.setBody(productDtoList);
        paginationResponse.setNumberOfElements(productPage.getNumberOfElements());
        paginationResponse.setPageNumber(productPage.getNumber());
        paginationResponse.setPageSize(productPage.getSize());
        paginationResponse.setTotalElements(productPage.getTotalElements());
        paginationResponse.setTotalPages(productPage.getTotalPages());
        return paginationResponse;
    }

    private Specification<Product> buildProductSpecification(ProductSearchCriteriaDto productSearchCriteriaDto) {
        Specification<Product> productSpecification = Specification
                .where(ProductSpecifications.productCategoryEquals(productSearchCriteriaDto.getProductCategory()))
                .and(ProductSpecifications.productIsDeletedEquals(false))
                .and(ProductSpecifications.orderByUpdatedOnDesc());

        if (productSearchCriteriaDto.getProductDescription() != null && !StringUtils.isBlank(productSearchCriteriaDto.getProductDescription())) {
            productSpecification = productSpecification.and(ProductSpecifications.productDescriptionLike(productSearchCriteriaDto.getProductDescription())).and(ProductSpecifications.productNameLike(productSearchCriteriaDto.getProductDescription()));
        }

        return productSpecification;
    }

    @Override
    public PaginationResponse<ProductDto> getProductsByUserUid(String userUid, int pageNo, int pageSize) {
        Specification<Product> productSpecification = ProductSpecifications.createdByUserUidEquals(userUid)
                .and(ProductSpecifications.productIsDeletedEquals(false)).and(ProductSpecifications.orderByUpdatedOnDesc());
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Page<Product> productPage = productRepository.findAll(productSpecification, pageRequest);
        List<Product> properties = productPage.getContent();
        PaginationResponse<ProductDto> paginationResponse = buildPaginationResponse(productPage, properties);
        return paginationResponse;
    }

    @Override
    public ProductDto getProductDetails(String productUid) {
        Product product = null;
        Optional<Product> productOptional = productRepository.findByUid(productUid);
        if (productOptional.isPresent()) {
            product = productOptional.get();
        } else {
            throw new RuntimeException("ERROR.INVALID_PROPERTY");
        }
        ProductDto productDto = ProductDto.builder()
                .uid(product.getUid())
                .productCategory(product.getProductCategory())
                .description(product.getDescription())
                .price(product.getPrice())
                .pictures(product.getPictures())
                .updatedOn(product.getUpdatedOn())
                .createdBy(product.getCreatedBy().getUid())
                .name(product.getName())
                .build();

        logger.info("product " + product.toString());
        logger.info("print dto" + productDto.toString());

        return productDto;
    }

    @Override
    public Boolean deleteProduct(String productUid) {
        Product product = null;
        Optional<Product> productOptional = productRepository.findByUid(productUid);
        if (productOptional.isPresent()) {
            product = productOptional.get();
            product.setIsDeleted(true);
        } else {
            throw new RuntimeException("ERROR.INVALID_PROPERTY");
        }
        productRepository.save(product);
        return true;
    }

}
