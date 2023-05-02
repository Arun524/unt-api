package com.unt.untstore.service.impl;

import com.unt.untstore.dto.PaginationResponse;
import com.unt.untstore.dto.ProductDto;
import com.unt.untstore.model.Product;
import com.unt.untstore.model.ProductWishlist;
import com.unt.untstore.model.User;
import com.unt.untstore.repository.ProductWishlistRepository;
import com.unt.untstore.repository.ProductRepository;
import com.unt.untstore.repository.UserRepository;
import com.unt.untstore.service.IProductWishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductWishlistServiceImpl implements IProductWishlistService {

    @Autowired
    private ProductWishlistRepository productWishlistRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public PaginationResponse<ProductDto> getProductWishlistByPage(String userUid, int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Optional<User> user = userRepository.findByUid(userUid);
        Page<ProductWishlist> propertyShortlistpage = productWishlistRepository.findByUser(user.get(), pageRequest);
        List<ProductWishlist> propertyFavorites = propertyShortlistpage.getContent();
        return buildPaginationResponse(propertyShortlistpage, propertyFavorites);
    }

    @Override
    public List<ProductDto> getPropertyListByUser(String userUid) {
        List<ProductWishlist> propertyFavorites = productWishlistRepository.findAllByUserUid(userUid);
        List<ProductDto> propertyFavoritesDtoList = propertyFavorites
                .stream().map(propertyFavouritesItem -> ProductDto.builder().uid(propertyFavouritesItem.getProperty().getUid())
                        .productCategory(propertyFavouritesItem.getProperty().getProductCategory())
                        .description(propertyFavouritesItem.getProperty().getDescription())
                        .price(propertyFavouritesItem.getProperty().getPrice())
                        .pictures(propertyFavouritesItem.getProperty().getPictures())
                        .updatedOn(propertyFavouritesItem.getProperty().getUpdatedOn())
                        .name(propertyFavouritesItem.getProperty().getName())
                        .build())
                .collect(Collectors.toList());
        return propertyFavoritesDtoList;
    }

    @Override
    public Boolean saveProductWishlistItem(String propertyUid, String userUid) {
        ProductWishlist propertyShortlistItem = new ProductWishlist();
        ProductWishlist dbPropertyShortlist = productWishlistRepository.findByUserUidAndPropertyUid(userUid,
                propertyUid);
        if (dbPropertyShortlist == null) {
            Optional<Product> optionalProperty = productRepository.findByUid(propertyUid);
            Optional<User> optionalUser = userRepository.findByUid(userUid);
            propertyShortlistItem.setProperty(optionalProperty.get());
            propertyShortlistItem.setUser(optionalUser.get());
            productWishlistRepository.save(propertyShortlistItem);
        }
        return true;
    }

    @Override
    public Boolean deleteProductWishlistItem(String propertyUid, String userUid) {
        productWishlistRepository
                .delete(productWishlistRepository.findByUserUidAndPropertyUid(userUid, propertyUid));
        return true;
    }

    private PaginationResponse<ProductDto> buildPaginationResponse(Page<ProductWishlist> propertyFavouritesPage,
                                                                   List<ProductWishlist> propertyFavorites) {
        PaginationResponse<ProductDto> paginationResponse = new PaginationResponse<>();
        List<ProductDto> propertyShortlistDtoList = propertyFavorites.stream().map(propertyFavouritesItem -> ProductDto.builder().uid(propertyFavouritesItem.getProperty().getUid())
                        .productCategory(propertyFavouritesItem.getProperty().getProductCategory())
                        .description(propertyFavouritesItem.getProperty().getDescription())
                        .price(propertyFavouritesItem.getProperty().getPrice())
                        .pictures(propertyFavouritesItem.getProperty().getPictures())
                        .name(propertyFavouritesItem.getProperty().getName())
                        .build())
                .collect(Collectors.toList());
        paginationResponse.setBody(propertyShortlistDtoList);
        paginationResponse.setNumberOfElements(propertyFavouritesPage.getNumberOfElements());
        paginationResponse.setPageNumber(propertyFavouritesPage.getNumber());
        paginationResponse.setPageSize(propertyFavouritesPage.getSize());
        paginationResponse.setTotalElements(propertyFavouritesPage.getTotalElements());
        paginationResponse.setTotalPages(propertyFavouritesPage.getTotalPages());
        return paginationResponse;
    }

}
