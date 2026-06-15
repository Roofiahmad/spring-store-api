package com.roofiahmad.springstoreapp.feature.shipping;

import com.roofiahmad.springstoreapp.feature.shipping.dto.AvailableRate;
import com.roofiahmad.springstoreapp.feature.shipping.dto.ShippingQuery;

import java.util.List;

public interface ShippingProvider {
    List<AvailableRate> fetchAvailableRates(ShippingQuery query);
}
