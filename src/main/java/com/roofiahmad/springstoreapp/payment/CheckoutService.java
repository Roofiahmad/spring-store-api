package com.roofiahmad.springstoreapp.payment;

import com.roofiahmad.springstoreapp.address.Address;
import com.roofiahmad.springstoreapp.address.dto.AddressDto;
import com.roofiahmad.springstoreapp.address.AddressMapper;
import com.roofiahmad.springstoreapp.address.AddressRepository;
import com.roofiahmad.springstoreapp.auth.UserPrincipal;
import com.roofiahmad.springstoreapp.cart.CartItem;
import com.roofiahmad.springstoreapp.cart.CartRepository;
import com.roofiahmad.springstoreapp.cart.CartService;
import com.roofiahmad.springstoreapp.common.exception.BadRequestException;
import com.roofiahmad.springstoreapp.common.exception.NotFoundException;
import com.roofiahmad.springstoreapp.order.*;
import com.roofiahmad.springstoreapp.order.exception.CartEmptyException;
import com.roofiahmad.springstoreapp.order.exception.CartNotFoundException;
import com.roofiahmad.springstoreapp.payment.dto.CheckoutRequest;
import com.roofiahmad.springstoreapp.payment.dto.CheckoutResponse;
import com.roofiahmad.springstoreapp.payment.dto.WebhookRequest;
import com.roofiahmad.springstoreapp.product.Product;
import com.roofiahmad.springstoreapp.product.ProductRepository;
import com.roofiahmad.springstoreapp.common.service.payment.PaymentGateway;
import com.roofiahmad.springstoreapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class CheckoutService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final CartRepository cartRepository;
    private final PaymentGateway paymentGateway;
    private final OrderService orderService;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Value("${order.shippingFee}")
    private BigDecimal defaultShippingFee;

    @Value("${order.valueAddedTax}")
    private BigDecimal vatRate;


    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request, UserPrincipal principal) throws PaymentException {
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElseThrow(CartNotFoundException::new);

        if(cart.isEmpty()) {
           throw new CartEmptyException();
        }

        var customer = userRepository.findById(principal.getId()).orElseThrow();
        Address userAddress = addressRepository.findById(request.getShippingAddressId())
                .orElseThrow(() -> new NotFoundException("Address not found"));
        AddressDto addressSnapshot = addressMapper.toAddressDto(userAddress);

        var order = Order.fromCart(cart, customer, addressSnapshot, defaultShippingFee, vatRate);
        order.setCustomerEmail(request.getEmail());
        order.setCustomerPhoneNumber(request.getPhoneNumber());

        List<Long> productIds = cart.getItems().stream()
                .map(item -> item.getProduct().getId())
                .sorted() // Aligns with ORDER BY for deadlock safety
                .toList();

        List<Product> lockedProducts = productRepository.findAllByIdsWithLock(productIds);

        Map<Long, Product> productMap = lockedProducts.stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        for (CartItem item : cart.getItems()) {
            Product product = productMap.get(item.getProduct().getId());
            if (product == null || product.getStock() < item.getQuantity()) {
                throw new BadRequestException("Out of stock: " + (product != null ? product.getName() : "Unknown"));
            }
            product.setStock(product.getStock() - item.getQuantity());
        }
        
        productRepository.saveAll(lockedProducts);
        orderRepository.save(order);

      try{
          var session = paymentGateway.createCheckoutSession(order);
          cartService.clearCart(cart.getId());
          return new CheckoutResponse(order.getId(), session.getCheckoutUrl());

      } catch (PaymentException e) {
          orderRepository.delete(order);
          throw e;
      }
    }

    public void handleWebhookEvent(WebhookRequest request) {
        log.info("✅ WebhookHandler triggered: {}", request);
       paymentGateway.parseWebhookRequest(request).ifPresent(orderService::updatePaymentStatus);
    }
}
