package com.moisas.barbershop.modules.product.controller;

import com.moisas.barbershop.modules.product.dto.ProductRequestDTO;
import com.moisas.barbershop.modules.product.dto.FindAllProductDTO;
import com.moisas.barbershop.modules.product.dto.ProductDTO;
import com.moisas.barbershop.modules.product.service.CreateProductService;
import com.moisas.barbershop.modules.product.service.DeleteProductService;
import com.moisas.barbershop.modules.product.service.FindAllProductService;
import com.moisas.barbershop.modules.product.service.FindOneProductService;
import com.moisas.barbershop.modules.product.service.UpdateProductService;
import com.moisas.barbershop.modules.shared.dto.PaginationMultipleResponse;
import com.moisas.barbershop.modules.shared.dto.PaginationSingleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/products")
@RequiredArgsConstructor
public class ProductController {
    private final CreateProductService createProductService;
    private final FindAllProductService findAllProductService;
    private final FindOneProductService findOneProductService;
    private final DeleteProductService deleteProductService;
    private final UpdateProductService updateProductService;

    @PostMapping()
    public ResponseEntity<ProductDTO> create(@RequestBody @Valid ProductRequestDTO service) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createProductService.execute(service));
    }

    @GetMapping()
    public ResponseEntity<PaginationMultipleResponse<ProductDTO>> listAll(@Valid FindAllProductDTO params) {
        return ResponseEntity.status(HttpStatus.OK).body(new PaginationMultipleResponse<>(findAllProductService.execute(params)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaginationSingleResponse<ProductDTO>> findById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(new PaginationSingleResponse<>(findOneProductService.execute(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable String id, @RequestBody @Valid ProductRequestDTO service) {
        return ResponseEntity.status(HttpStatus.OK).body(updateProductService.execute(id, service));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        deleteProductService.execute(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

