package com.sysco.product.controllers;

import com.sysco.product.entities.Product;
import com.sysco.product.exceptions.ErrorResponse;
import com.sysco.product.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequestMapping(value = "/api/v1")
@RestController
@ApiResponse(responseCode = "500", description = "Server breakdown or maintenance", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @Operation(summary = "Add a new product to the shop", description = "New product details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The product has been created", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        Product _product = productService.addProduct(product);
        return new ResponseEntity<>(_product, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all products", description = "All product details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Product.class))) }),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(){
        return ResponseEntity.ok(productService.getProducts());
    }

    @Operation(summary = "Find product by ID", description = "Product details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Product.class))) }),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId){
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @Operation(summary = "Find product by product name", description = "Product details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Product.class))) }),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @GetMapping("/productsByName")
    public ResponseEntity<Product> getProductByProductName(@RequestParam String productName){
        return ResponseEntity.ok(productService.getProductByProductName(productName));
    }

    @Operation(summary = "Update an existing product", description = "Updated product details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Product.class))) }),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable Long productId){
        return ResponseEntity.ok(productService.udpateProduct(product, productId));
    }

    @Operation(summary = "Delete a product", description = "Delete a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId){
        boolean isDeleted = productService.deleteProduct(productId);
        if(isDeleted)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.notFound().build();
    }
}
