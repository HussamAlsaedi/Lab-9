package com.example.capstone1.Controller;


import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.User;
import com.example.capstone1.Service.MerchantService;
import com.example.capstone1.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchant")
public class MerchantController {

    private final MerchantService merchantService;
    private final MerchantStockService merchantStockService;

    @GetMapping("/get")
    public List<Merchant> getAllMerchants() {
        return merchantService.getAllMerchants();
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addMerchant(@RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getAllErrors().get(0).getDefaultMessage()));
        }
        merchantService.addMerchants(merchant);
        return ResponseEntity.status(200).body(new ApiResponse("Merchant Added"));
    }

    @PutMapping("/update/{merchantId}")
    public ResponseEntity<ApiResponse> updateMerchant(@PathVariable int merchantId,@RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getAllErrors().get(0).getDefaultMessage()));
        }

        Boolean isUpdated = merchantService.updateMerchant(merchantId, merchant);

        if (isUpdated) {
            return ResponseEntity.status(200).body(new ApiResponse("Merchant Updated"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Merchant Not Found"));
    }

    @DeleteMapping("/delete/{index}")
    public ResponseEntity<ApiResponse> deleteMerchant(@PathVariable int index ) {

         Boolean isDeleted = merchantService.deleteMerchant(index);
        if(isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant Deleted"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Merchant Not Found"));
    }

    @PutMapping("/addMoreStock/{indexMerchant}/{indexProductId}")
    public ResponseEntity<ApiResponse> addMoreStock(@PathVariable int indexMerchant, @PathVariable int indexProductId, MerchantStock merchantStock, Errors errors) {

        if (merchantService.checkMerchantId(indexMerchant) == 1) {
            return ResponseEntity.status(404).body(new ApiResponse("Merchant Not Found"));
        }

        if (merchantStockService.checkProductId(indexProductId) == 1) {
            return ResponseEntity.status(400).body(new ApiResponse("product Not Found"));
        }
                merchantStockService.updateMerchantStocks(indexProductId, merchantStock);
                return ResponseEntity.status(200).body(new ApiResponse("Stock added"));



    }

}
