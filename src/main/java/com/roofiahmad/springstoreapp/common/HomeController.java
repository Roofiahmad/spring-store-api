package com.roofiahmad.springstoreapp.common;

import com.roofiahmad.springstoreapp.orders.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final OrderRepository orderRepository;

    @RequestMapping("/")
    public String index(Model model) {
       model.addAttribute("name", "Roofiahmad");
       return "index";
   }

    @GetMapping("/checkout-success")
    public String paymentSuccess(@RequestParam(value = "orderId") Long orderId, Model model) {
        var order = orderRepository.getOneOrderById(orderId).orElseThrow();

        model.addAttribute("orderNumber", "ORD-" + orderId);
        model.addAttribute("customerName", order.getCustomer().getName());
        model.addAttribute("totalAmount", order.getTotalPrice());
        return "payment-success";
    }


    @GetMapping("/checkout-cancel")
    public String paymentCancel(Model model) {
        model.addAttribute("supportEmail", "support@roofiahmad.com");
        return "checkout-cancel";
    }

}
