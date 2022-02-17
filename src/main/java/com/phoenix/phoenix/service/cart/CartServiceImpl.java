

package com.phoenix.phoenix.service.cart;

import com.phoenix.phoenix.data.dto.CartRequestDto;
import com.phoenix.phoenix.data.dto.CartResponseDto;
import com.phoenix.phoenix.data.models.AppUser;
import com.phoenix.phoenix.data.models.Cart;
import com.phoenix.phoenix.data.models.Item;
import com.phoenix.phoenix.data.models.Product;
import com.phoenix.phoenix.data.repository.AppUserRepository;
import com.phoenix.phoenix.data.repository.CartRepository;
import com.phoenix.phoenix.data.repository.ProductRepository;
import com.phoenix.phoenix.web.exceptions.BusinessLogicException;
import com.phoenix.phoenix.web.exceptions.ProductDoesNotExistException;
import com.phoenix.phoenix.web.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private ProductRepository productRepository;

    private boolean quantityIsNotValid(Product product, int quantity){
        return product.getQuantity() <= quantity;
    }


    private CartResponseDto buildCartResponse(Cart cart){
        return CartResponseDto.builder().itemList(cart.getItemList()).totalPrice(cart.getTotalPrice()).build();
    }


    private Double calculateItemPrice(Item item){
        return item.getProduct().getPrice() * item.getQuantityAdded();
    }


    private AppUser getUserFromRequestDto(CartRequestDto cartRequestDto) throws UserNotFoundException {
        Optional<AppUser> query = appUserRepository.findById(cartRequestDto.getUserId());

        if ( query.isEmpty() ){
            throw new UserNotFoundException("User with ID "+ cartRequestDto.getUserId()+ " not found");
        }
        return query.get();
    }



    private Product getProductFromRequestDto(CartRequestDto cartRequestDto)
            throws ProductDoesNotExistException, BusinessLogicException {

        Product product = productRepository.findById(13L).orElse(null);
        if ( product ==null ){
            throw new ProductDoesNotExistException("Product with ID "+
                    cartRequestDto.getProductId()+" does not exist");
        }

        if ( quantityIsNotValid(product, cartRequestDto.getQuantity()) )
            throw new BusinessLogicException("Quantity too large");
        return product;
    }


    private void calculateNewTotalPriceOfCart(CartRequestDto cartRequestDto, Cart cart, Product product) {
        Item cartItem = new Item(product, cartRequestDto.getQuantity());
        cart.addItem(cartItem);
        cart.setTotalPrice(cart.getTotalPrice()+ calculateItemPrice(cartItem));
    }


    @Override
    public CartResponseDto addItemToCart(CartRequestDto cartRequestDto) throws UserNotFoundException, ProductDoesNotExistException, BusinessLogicException {

        AppUser user = getUserFromRequestDto(cartRequestDto);
        Product product = getProductFromRequestDto(cartRequestDto);
        Cart cart = user.getCart();

        calculateNewTotalPriceOfCart(cartRequestDto, cart, product);
        cartRepository.save(cart);
        return buildCartResponse(cart);
    }

    @Override
    public Cart viewCart(Long id) {
        return cartRepository.findById(id).orElse(null);
    }
}
