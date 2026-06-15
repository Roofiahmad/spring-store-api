package com.roofiahmad.springstoreapp.feature.cart;

import com.roofiahmad.springstoreapp.feature.address.Address;
import com.roofiahmad.springstoreapp.feature.address.AddressService;
import com.roofiahmad.springstoreapp.feature.cart.dto.CartDto;
import com.roofiahmad.springstoreapp.feature.cart.dto.CartItemDto;
import com.roofiahmad.springstoreapp.feature.order.exception.CartNotFoundException;
import com.roofiahmad.springstoreapp.feature.product.ProductNotFoundException;
import com.roofiahmad.springstoreapp.feature.product.ProductRepository;
import com.roofiahmad.springstoreapp.feature.shipping.ShippingProvider;
import com.roofiahmad.springstoreapp.feature.shipping.dto.AvailableRate;
import com.roofiahmad.springstoreapp.feature.shipping.dto.ShippingQuery;
import com.roofiahmad.springstoreapp.infra.shipping.biteship.ItemRequest;
import com.roofiahmad.springstoreapp.infra.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;
    private final ShippingProvider shippingProvider;
    private final AddressService addressService;

    @Value("${order.storePostalCode}")
    private int storePostalCode;


    @Value("${order.valueAddedTax}")
    private BigDecimal vatRate;

    public CartDto create() {
        var cart = new Cart();
        cartRepository.save(cart);
       return cartMapper.toDto(cart);
    }

    public CartItemDto addToCart(UUID cartId, Long productId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        var product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException();
        }

        var cartItem = cart.addItem(product);
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }

    public CartDto getCart(long addressId) {
        var user = Utils.getUserPrincipal();
        var cart = cartRepository.getCartWithUserId(user.getId())
                .orElseThrow(CartNotFoundException::new);

        CartDto cartDto = cartMapper.toDto(cart);

        Address shippingAddress;
        if(addressId > 0){
            shippingAddress = addressService.getAddressEntity(addressId);
        } else {
            shippingAddress = addressService.getCustomerMainAddress();
        }

        int productTotalWeight = cart.getItems().stream()
                .mapToInt(item -> item.getProduct().getWeight() * item.getQuantity())
                .sum();

        List<ItemRequest> items = cart.getItems().stream().map((cartItem)->
           new ItemRequest(
                    cartItem.getProduct().getName(),
                    cartItem.getProduct().getDescription(),
                    cartItem.getProduct().getPrice().longValue(),
                    cartItem.getProduct().getLength(),
                    cartItem.getProduct().getWidth(),
                    cartItem.getProduct().getHeight(),
                    cartItem.getProduct().getWeight(),
                    cartItem.getQuantity()
                    )
        ).toList();

        ShippingQuery shippingQuery = new ShippingQuery(storePostalCode,Integer.parseInt(shippingAddress.getZip()),items, productTotalWeight);
        AvailableRate rate = shippingProvider.fetchAvailableRates(shippingQuery).stream()
                .filter(r -> r.serviceType().equalsIgnoreCase("reguler"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No reguler shipping services available at all"));


        BigDecimal subTotal = cart.getSubTotal();
        BigDecimal calculatedVat = subTotal.multiply(vatRate);
        BigDecimal finalTotalPrice = subTotal.add(BigDecimal.valueOf(rate.costIdr())).add(calculatedVat);

        cartDto.setSubTotal(subTotal);
        cartDto.setShippingFee(BigDecimal.valueOf(rate.costIdr()));
        cartDto.setVatAmount(calculatedVat);
        cartDto.setTotalPrice(finalTotalPrice);

        return cartDto;
    }

    public CartItemDto updateCart(UUID cartId, Long productId, Integer productQuantity) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        var cartItem = cart.getCartItem(productId);
        if (cartItem == null) {
            throw new ProductNotFoundException();
        }

        cartItem.setQuantity(productQuantity);
        cartRepository.save(cart);

        return cartMapper.toDto(cartItem);
    }

    public void deleteProduct(UUID cartId, Long productId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        var product = cart.getCartItem(productId);
        if (product == null) {
            throw new ProductNotFoundException();
        }

        cart.removeItem(product);
        cartRepository.save(cart);
    }

    public void clearCart(UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
           throw new CartNotFoundException();
        }

        cart.clear();
        cartRepository.save(cart);
    }
}
