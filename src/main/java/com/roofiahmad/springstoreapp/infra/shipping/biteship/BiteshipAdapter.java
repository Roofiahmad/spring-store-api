package com.roofiahmad.springstoreapp.infra.shipping.biteship;

import com.roofiahmad.springstoreapp.feature.shipping.ShippingProvider;
import com.roofiahmad.springstoreapp.feature.shipping.dto.AvailableRate;
import com.roofiahmad.springstoreapp.feature.shipping.dto.ShippingQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BiteshipAdapter implements ShippingProvider {
    private final BiteshipShippingService biteshipService;

    public BiteshipAdapter(BiteshipShippingService biteshipService) {
        this.biteshipService = biteshipService;
    }

    @Override
    public List<AvailableRate> fetchAvailableRates(ShippingQuery query) {
        BiteshipRateRequest biteshipRequest = new BiteshipRateRequest(query.originPostalCode(), query.destinationPostalCode(), "jne", query.items());

        ObjectMapper objectMapper = new ObjectMapper();

        String prettyRequestJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(biteshipRequest);
        log.info("Sending Outbound Payload to Biteship API:\n{}", prettyRequestJson);

        BiteshipRateResponse response = biteshipService.getShippingRates(biteshipRequest);

        String prettyResponseJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
        log.info("Received Inbound Response from Biteship API:\n{}", prettyResponseJson);

        return response.pricing().stream().map(p -> new AvailableRate(p.courier_name(), p.courier_service_name(), p.price())).collect(Collectors.toList());
    }
}
