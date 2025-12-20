package com.roofiahmad.springstoreapp.services;

import com.roofiahmad.springstoreapp.entities.Address;
import com.roofiahmad.springstoreapp.entities.Product;
import com.roofiahmad.springstoreapp.entities.User;
import com.roofiahmad.springstoreapp.repositories.AddressRepository;
import com.roofiahmad.springstoreapp.repositories.CategoryRepository;
import com.roofiahmad.springstoreapp.repositories.ProductRepository;
import com.roofiahmad.springstoreapp.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProfileRepository profileRepository;

    @Transactional

    public void deleteRelated() {
        userRepository.deleteById(4L);
    }

    @Transactional
    public void crateCategoryWithProducts() {
//        Category fruits = categoryRepository.findById(1).orElse(null);
//        System.out.println(fruits);

//        var apple = Product.builder().name("Apple").price(2.5F).category(fruits).build();
//        var mango = Product.builder().name("Mango").price(1.5F).category(fruits).build();
//        var durian = Product.builder().name("Durian").price(5.5F).category(fruits).build();
//
//        fruits.addProduct(apple);
//        fruits.addProduct(mango);
//        fruits.addProduct(durian);
//
//        productRepository.save(apple);
//        productRepository.save(mango);
//        productRepository.save(durian);
    }

    @Transactional
    public void createFullUser() {
        var user = User.builder().name("Sherlock Holmes").password("dududu").email("sherlock@email.com").build();
        var address = Address.builder().street("212 Baker Street").city("London").state("UK").zip("666666").build();
//        var profile = Profile.builder().bio("famous detective").loyaltyPoints(200).build();

        user.addAddress(address);
//        user.setProfile(profile);
        userRepository.save(user);

    }

    @Transactional
    public void addProductToWishlist() {
        User user = userRepository.findById(10L).orElse(null);
        if (user != null) {
            productRepository.findAll().forEach(user::addWishlist);
            userRepository.save(user);
        }
    }

    @Transactional
    public void deleteProduct() {
        Product product = productRepository.findById(5L).orElse(null);
        if (product != null) {
            productRepository.delete(product);
        } else {
            System.out.println("Product not found.");
        }
    }

    @Transactional
    public void fetchUsers(){
       var users = userRepository.findAllWithAddresses();
       users.forEach(u -> {
           System.out.println(u);
           System.out.println(u.getAddresses());
       });
    }
}
