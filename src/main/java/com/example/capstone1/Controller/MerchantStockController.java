package com.example.capstone1.Controller;


import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/merchantStock")
public class MerchantStockController {
    private final MerchantStockService merchantStockService;

    @GetMapping("/get")
    public ResponseEntity getMerchantStock() {
        if (merchantStockService.getAllMerchantStocks().isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("List of Merchant Stocks is Empty"));
        }
        return ResponseEntity.status(200).body(merchantStockService.getAllMerchantStocks());
    }

    @PostMapping("/add")
    public  ResponseEntity<ApiResponse> addMerchantStocks(@RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getAllErrors().get(0).getDefaultMessage()));
        }

        if(merchantStockService.addMerchantStocks(merchantStock) == 1){
            return ResponseEntity.status(400).body(new ApiResponse("ProductId Not found"));
        }
        else if (merchantStockService.addMerchantStocks(merchantStock)==2) {
            return ResponseEntity.status(402).body(new ApiResponse("MerchantId Not found "));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Merchant Stock Added"));
    }

    @PutMapping("/update/{index}")
    public ResponseEntity updateMerchantStocks(@PathVariable int index ,@RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getAllErrors().get(0).getDefaultMessage()));
        }
        if (merchantStockService.updateMerchantStocks(index, merchantStock)) {
            return ResponseEntity.status(200).body(new ApiResponse("Merchant Stock Updated"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Merchant Stock Not Updated"));
    }

    @DeleteMapping("/delete/{index}")
    public ResponseEntity deleteMerchantStocks(@PathVariable int index) {
        if (merchantStockService.deleteMerchantStocks(index)) {
            return ResponseEntity.status(200).body(new ApiResponse("Merchant Stock Deleted"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Merchant Stock Not Found"));
    }

    // this Endpoint to get-low-stock
    @GetMapping("/get-low-stock/{MerchantID}")
    public ResponseEntity getAllLowStock(@PathVariable int MerchantID)
    {
        if (merchantStockService.getAllLowStock(MerchantID).isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse("There is no Merchant Stock is low"));
        }
        return ResponseEntity.status(200).body(merchantStockService.getAllLowStock(MerchantID));
    }


}