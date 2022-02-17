package com.phoenix.phoenix.service.cart;

import com.phoenix.phoenix.data.dto.CartItemDto;
import com.phoenix.phoenix.data.models.AppUser;
import com.phoenix.phoenix.data.models.Cart;
import com.phoenix.phoenix.data.models.Item;
import com.phoenix.phoenix.data.models.Product;
import com.phoenix.phoenix.data.repository.AppUserRepository;
import com.phoenix.phoenix.data.repository.CartRepository;
import com.phoenix.phoenix.data.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private AppUser appUser;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void addItemToCart(CartItemDto cartItemDto) {
        AppUser user = appUserRepository.findById(cartItemDto.getUserId()).orElse(null);

        //get user cart
        Cart cart = user.getCart();

        //check user exists
        Product product = productRepository.findById(13L).orElse(null);

        //add product to cart
        Item cartItem = new Item(product, cartItemDto.getQuantity());
        cart.addItem(cartItem);

        //save cart
        cartRepository.save(cart);

    }
}
