package com.unt.untstore.service.impl;

import com.unt.untstore.dto.PaginationResponse;
import com.unt.untstore.dto.ProductDto;
import com.unt.untstore.model.CartItem;
import com.unt.untstore.model.Product;
import com.unt.untstore.model.User;
import com.unt.untstore.repository.CartRepository;
import com.unt.untstore.repository.ProductRepository;
import com.unt.untstore.repository.UserRepository;
import com.unt.untstore.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public PaginationResponse<ProductDto> getCartItemsByPage(String userUid, int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Optional<User> user = userRepository.findByUid(userUid);
        Page<CartItem> propertyShortlistpage = cartRepository.findByUser(user.get(), pageRequest);
        List<CartItem> propertyFavorites = propertyShortlistpage.getContent();
        return buildPaginationResponse(propertyShortlistpage, propertyFavorites);
    }

    @Override
    public List<ProductDto> getCartItemsByUser(String userUid) {
        List<CartItem> propertyFavorites = cartRepository.findAllByUserUid(userUid);
        List<ProductDto> propertyFavoritesDtoList = propertyFavorites
                .stream().map(propertyFavouritesItem -> ProductDto.builder().uid(propertyFavouritesItem.getProduct().getUid())
                        .productCategory(propertyFavouritesItem.getProduct().getProductCategory())
                        .description(propertyFavouritesItem.getProduct().getDescription())
                        .price(propertyFavouritesItem.getProduct().getPrice())
                        .pictures(propertyFavouritesItem.getProduct().getPictures())
                        .updatedOn(propertyFavouritesItem.getProduct().getUpdatedOn())
                        .name(propertyFavouritesItem.getProduct().getName())
                        .build())
                .collect(Collectors.toList());
        return propertyFavoritesDtoList;
    }

    @Override
    public Boolean saveCartItem(String productUid, String userUid) {
        CartItem propertyShortlistItem = new CartItem();
        CartItem dbPropertyShortlist = cartRepository.findByUserUidAndProductUid(userUid,
                productUid);
        if (dbPropertyShortlist == null) {
            Optional<Product> optionalProperty = productRepository.findByUid(productUid);
            Optional<User> optionalUser = userRepository.findByUid(userUid);
            propertyShortlistItem.setProduct(optionalProperty.get());
            propertyShortlistItem.setUser(optionalUser.get());
            cartRepository.save(propertyShortlistItem);
        }
        return true;
    }

    @Override
    public Boolean deleteCartItem(String productUid, String userUid) {
        cartRepository
                .delete(cartRepository.findByUserUidAndProductUid(userUid, productUid));
        return true;
    }

    private PaginationResponse<ProductDto> buildPaginationResponse(Page<CartItem> propertyFavouritesPage,
                                                                   List<CartItem> propertyFavorites) {
        PaginationResponse<ProductDto> paginationResponse = new PaginationResponse<>();
        List<ProductDto> propertyShortlistDtoList = propertyFavorites.stream().map(propertyFavouritesItem -> ProductDto.builder().uid(propertyFavouritesItem.getProduct().getUid())
                        .productCategory(propertyFavouritesItem.getProduct().getProductCategory())
                        .description(propertyFavouritesItem.getProduct().getDescription())
                        .price(propertyFavouritesItem.getProduct().getPrice())
                        .pictures(propertyFavouritesItem.getProduct().getPictures())
                        .name(propertyFavouritesItem.getProduct().getName())
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
