package com.example.capstone1.Controller;

import com.example.capstone1.ApiResponse.ApiResponse;
import com.example.capstone1.Model.User;
import com.example.capstone1.Service.MerchantService;
import com.example.capstone1.Service.MerchantStockService;
import com.example.capstone1.Service.ProductService;
import com.example.capstone1.Service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;



    @GetMapping("/get")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addUser(@RequestBody @Valid User user, Errors errors) {

        if (errors.hasErrors()) {
            String msg = errors.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(msg));
        }

        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("User added successfully"));
    }

    @PutMapping("/update/{index}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable int index, @RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            String msg = errors.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(msg));
        }
        Boolean isUpdated = userService.updateUser(index, user);

        if (isUpdated) {
            return ResponseEntity.status(200).body(new ApiResponse("User updated successfully"));
        }
          return ResponseEntity.status(403).body(new ApiResponse("User not found"));
    }

    @DeleteMapping("/delete/{index}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable int index) {
        Boolean isDeleted = userService.deleteUser(index);
        if (isDeleted) {
            return ResponseEntity.status(200).body(new ApiResponse("User delete successfully"));
        }

        return ResponseEntity.status(400).body(new ApiResponse("User not found"));
    }

    // method get parmetter and send to another method  called buyProduct
    @GetMapping("buy/{userID}/{MerchantID}/{productID}")
    public ResponseEntity<ApiResponse> buyProduct(@PathVariable int userID, @PathVariable int productID, @PathVariable int MerchantID) {

        int status = userService.userBuy(userID, MerchantID, productID);

        return switch (status) {
            case 1 -> ResponseEntity.status(403).body(new ApiResponse("User not found"));
            case 2 -> ResponseEntity.status(403).body(new ApiResponse("Product not found"));
            case 3 -> ResponseEntity.status(403).body(new ApiResponse("Merchant not found"));
            case 4 -> ResponseEntity.status(403).body(new ApiResponse("Merchant does not have enough stock"));
            case 5 -> ResponseEntity.status(403).body(new ApiResponse("User does not have enough balance"));
            default -> ResponseEntity.status(200).body(new ApiResponse("User Purchases successfully"));
        };


    }


    @GetMapping("/updateUserBalance/{index}/{newBalance}")
    public ResponseEntity<ApiResponse> updateUserBalance(@PathVariable int index, @PathVariable double newBalance) {
        if (userService.updateUserBalance(index, newBalance)) {
            return ResponseEntity.status(200).body(new ApiResponse("User's Balance updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("User not found"));
    }

    @GetMapping("get-balance/{index}")
    public ResponseEntity getUserBalance(@PathVariable int index) {
        Double balance = userService.checkUserBalance(index);
        if (balance != null) {
            return ResponseEntity.ok(balance);
        }
        return ResponseEntity.status(400).body("User not found");
    }

    // this Endpoint transfer the money from  user to another user
    @GetMapping("/transfer/{fromUserId}/{toUserId}/{amount}")
    public ResponseEntity<ApiResponse> transferBalance(@PathVariable int fromUserId, @PathVariable int toUserId, @PathVariable double amount) {

        int status = userService.transferBalance(fromUserId, toUserId, amount);

        return switch (status) {
            case 1 -> ResponseEntity.status(403).body(new ApiResponse("user send the money not found"));
            case 2 -> ResponseEntity.status(403).body(new ApiResponse("user receive the money not found"));
            case 3 -> ResponseEntity.status(403).body(new ApiResponse("Insufficient balance"));
            default -> ResponseEntity.status(200).body(new ApiResponse("Money transfer completed successfully."));
        };
    }

    // this Endpoint add productId to Wishlist
    @GetMapping("/addToWishlist/{userId}/{productId}")
    public ResponseEntity<ApiResponse> addToWishlist(@PathVariable int userId, @PathVariable int productId) {

        int status = userService.addToWishlist(userId, productId);

        return switch (status) {
            case 1 -> ResponseEntity.status(403).body(new ApiResponse("user not found"));
            case 2 -> ResponseEntity.status(403).body(new ApiResponse("product not found"));
            default -> ResponseEntity.status(200).body(new ApiResponse("add ToWishlist successfully."));
        };

    }

    // Endpoint to get the wishlist
    @GetMapping("/getWishlist")
    public ResponseEntity getWishlist() {
        return ResponseEntity.ok(userService.getWishlist());
    }


    // Endpoint to remove from Wishlist by productId
    @GetMapping("/remove/{productId}")
    public ResponseEntity<ApiResponse> removeWishlist(@PathVariable int productId) {
        int status = userService.removeWishlist(productId);

        return switch (status) {
            case 1 -> ResponseEntity.status(200).body(new ApiResponse("remove successfully"));
            case 2 -> ResponseEntity.status(403).body(new ApiResponse("productId not found"));
            default -> ResponseEntity.status(403).body(new ApiResponse("Wishlist not removed successfully"));
        };

    }
}