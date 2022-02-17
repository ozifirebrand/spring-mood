package com.phoenix.phoenix.service.cart;

import com.phoenix.phoenix.data.dto.CartItemDto;
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
    public void addItemToCart(CartItemDto cartItemDto)
            throws UserNotFoundException, ProductDoesNotExistException,
            BusinessLogicException {

        Optional<AppUser> query = appUserRepository.findById(cartItemDto.getUserId());

        if ( query.isEmpty() ){
            throw new UserNotFoundException("User with ID "+cartItemDto.getUserId()+ " not found");
        }
        AppUser user = query.get();

        //get user cart
        Cart cart = user.getCart();

        //check user exists
        Product product = productRepository.findById(13L).orElse(null);
        if ( product ==null ){
            throw new ProductDoesNotExistException("Product with ID "+
                    cartItemDto.getProductId()+" does not exist");
        }

        if ( quantityIsNotValid(product, cartItemDto.getQuantity()) )
            throw new BusinessLogicException("Quantity too large");

        //add product to cart
        Item cartItem = new Item(product, cartItemDto.getQuantity());
        cart.addItem(cartItem);

        //save cart
        cartRepository.save(cart);

    }

    private boolean quantityIsNotValid(Product product, int quantity){
        return product.getQuantity() <= quantity;
    }
}
