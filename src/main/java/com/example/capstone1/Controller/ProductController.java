package com.example.capstone1.Controller;


import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/get")
    public ResponseEntity getAllProducts(){
        if (productService.getAllProducts().isEmpty())
        {
            return ResponseEntity.status(400).body(new ApiResponse("List is empty"));
        }
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> add_Product(@RequestBody @Valid Product product, Errors errors){

        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getAllErrors().get(0).getDefaultMessage()));
        }
        if(productService.addProduct(product) == 1) {
            return ResponseEntity.status(200).body(new ApiResponse("Product added successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("this Category not in system, please add Category than try again"));
    }


    @PutMapping("/update/{index}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable int index, @RequestBody @Valid Product product,Errors errors)
    {
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse( errors.getAllErrors().get(0).getDefaultMessage()));
        }

        if(productService.updateProduct(index,product)){
            return ResponseEntity.status(200).body(new ApiResponse("Product updated successfully"));

        }
        return ResponseEntity.status(403).body(new ApiResponse("Product not found"));
    }

    @DeleteMapping("/delete/{index}")
    public ResponseEntity deleteProduct(@PathVariable int index){
        if (productService.deleteProduct(index))
        {
            return ResponseEntity.status(200).body(new ApiResponse("Product delete successfully"));
        }

        return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
    }

    @GetMapping("/get-productByID/{index}")
    public ResponseEntity getProductByID(@PathVariable int index){
        if (productService.getProductById(index).isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
        }
        return ResponseEntity.ok(productService.getProductById(index));
    }

    @GetMapping("/get-productByCategory/{index}")
    public ResponseEntity getProductByCategory(@PathVariable int index){
        if (productService.getProductById(index).isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
        }
        return ResponseEntity.ok(productService.getProductByCategory(index));
    }

    @GetMapping("/get-balance/{index}")
    public ResponseEntity checkProductBalance(@PathVariable int index) {
        double price = productService.checkProductBalance(index);
        if (price > 0.0) {
            return ResponseEntity.ok(price);
        }
        return ResponseEntity.status(400).body(new ApiResponse("product not found"));
    }


}