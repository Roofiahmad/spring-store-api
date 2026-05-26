package com.roofiahmad.springstoreapp.products;

import com.roofiahmad.springstoreapp.common.ApiResponseWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
@Tag(name = "Products")
public class ProductController {
    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<ApiResponseWrapper<PagedResponse<ProductDto>>> getProducts(
            @RequestParam(value = "categoryId", required = false) Short categoryId,
            @RequestParam(value = "badge", required = false) String badge,
            @RequestParam(value = "skip", defaultValue = "0") int skip,
            @RequestParam(value = "limit", defaultValue = "8") int limit,
            @RequestParam(value = "sortBy", defaultValue = "LATEST") ProductSort sortBy
    ) {

        Sort springSort = Sort.by(sortBy.getDirection(), sortBy.getDatabaseField());

        var pageable = PageRequest.of(skip / limit, limit,springSort );
        var pagedData = productService.findAllProducts(categoryId, badge, pageable);

        return ResponseEntity.ok(ApiResponseWrapper.success(pagedData));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id
    ) {
        return ResponseEntity.ok(productService.findProductById(id));
    }

    @PostMapping()
    public ResponseEntity<ProductDto> createProduct(@RequestBody CreateProductRequest request, UriComponentsBuilder uriBuilder
    ) {
        var productDto = productService.createProduct(request);
        var uri = uriBuilder.path("/products/{id}").buildAndExpand(productDto.getId()).toUri();
        return ResponseEntity.created(uri).body(productDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id,@RequestBody CreateProductRequest request
    ) {
        ProductDto updatedProduct = productService.updateProduct(id, request);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id
    ) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
