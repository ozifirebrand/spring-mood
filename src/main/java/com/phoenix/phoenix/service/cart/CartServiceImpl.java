

package com.phoenix.phoenix.service.cart;

import com.phoenix.phoenix.data.dto.CartRequestDto;
import com.phoenix.phoenix.data.dto.CartResponseDto;
import com.phoenix.phoenix.data.dto.CartUpdateDto;
import com.phoenix.phoenix.data.dto.QuantityOperation;
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
import java.util.function.Predicate;

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
        return CartResponseDto.builder()
                .itemList(cart.getItemList())
                .totalPrice(cart.getTotalPrice())
                .build();
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


    private Optional<Item> findCartItem(Long itemId, Cart cart){
        Predicate<Item> itemPredicate = i -> i.getId().equals(itemId);
        return cart.getItemList().stream().filter(itemPredicate).findFirst();
    }


    @Override
    public CartResponseDto addItemToCart(CartRequestDto cartRequestDto) throws BusinessLogicException {

        AppUser user = getUserFromRequestDto(cartRequestDto);
        Product product = getProductFromRequestDto(cartRequestDto);
        Cart cart = user.getCart();

        calculateNewTotalPriceOfCart(cartRequestDto, cart, product);
        cartRepository.save(cart);
        return buildCartResponse(cart);
    }

    @Override
    public CartResponseDto viewCart(Long userId) throws BusinessLogicException {
        AppUser user = appUserRepository.findById(userId).orElse(null);
        if ( user == null ) throw new UserNotFoundException("User with id "+userId +" not found");

        Cart cart = user.getCart();
        return buildCartResponse(cart);
    }

    @Override
    public CartResponseDto updateCartItem(CartUpdateDto cartUpdateDto) throws BusinessLogicException {
        //get user by id
        AppUser appUser = appUserRepository.findById(cartUpdateDto.getUserId()).orElse(null);
        if ( appUser == null )
            throw new UserNotFoundException("User with id "
                    +cartUpdateDto.getUserId() +" does not exist");
        //get a cart by userId
        Cart cart = appUser.getCart();
        //Find an item within cart within itemId
        Item item = findCartItem(cartUpdateDto.getItemId(), cart).orElse(null);
        if ( item == null ) throw new BusinessLogicException("Item not in cart");

        //Perform update to item

        if ( cartUpdateDto.getQuantityOperation() == QuantityOperation.INCREASE ) {
            item.setQuantityAdded(item.getQuantityAdded() + 1);
            cart.setTotalPrice(cart.getTotalPrice() + item.getProduct().getPrice());
        }
        else if ( cartUpdateDto.getQuantityOperation() == QuantityOperation.DECREASE ) {
            item.setQuantityAdded(item.getQuantityAdded() - 1);
            cart.setTotalPrice(cart.getTotalPrice() - item.getProduct().getPrice());

        }

        cartRepository.save(cart);

        return buildCartResponse(cart);
    }
}
