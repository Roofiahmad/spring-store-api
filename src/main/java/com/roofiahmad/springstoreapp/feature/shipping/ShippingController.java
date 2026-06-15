package com.roofiahmad.springstoreapp.feature.shipping;

import com.roofiahmad.springstoreapp.feature.shipping.dto.AvailableRate;
import com.roofiahmad.springstoreapp.feature.shipping.dto.ShippingQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shipping")
public class ShippingController {
    private final ShippingProvider shippingProvider;

    public ShippingController(ShippingProvider shippingProvider) {
        this.shippingProvider = shippingProvider;
    }

    @PostMapping("/calculate")
    public ResponseEntity<List<AvailableRate>> calculateCosts(@RequestBody ShippingQuery query) {
        List<AvailableRate> rates = shippingProvider.fetchAvailableRates(query);
        return ResponseEntity.ok(rates);
    }
}
