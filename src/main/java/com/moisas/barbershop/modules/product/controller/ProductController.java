package com.moisas.barbershop.modules.product.controller;

import com.moisas.barbershop.modules.product.dto.CreateProductDTO;
import com.moisas.barbershop.modules.product.dto.FindAllProductDTO;
import com.moisas.barbershop.modules.product.dto.ProductDTO;
import com.moisas.barbershop.modules.product.service.CreateProductService;
import com.moisas.barbershop.modules.product.service.DeleteProductService;
import com.moisas.barbershop.modules.product.service.FindAllProductService;
import com.moisas.barbershop.modules.shared.dto.PaginationMultipleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/services")
@RequiredArgsConstructor
public class ProductController {
    private final CreateProductService createProductService;
    private final FindAllProductService findAllProductService;
    private final DeleteProductService deleteProductService;

    @PostMapping()
    public ResponseEntity<ProductDTO> create(@RequestBody @Valid CreateProductDTO service) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createProductService.execute(service));
    }

    @GetMapping()
    public ResponseEntity<PaginationMultipleResponse<ProductDTO>> listAll(@Valid FindAllProductDTO params) {
        return ResponseEntity.status(HttpStatus.OK).body(new PaginationMultipleResponse<>(findAllProductService.execute(params)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        deleteProductService.execute(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

