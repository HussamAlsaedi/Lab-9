package com.example.capstone1.Service;

import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import com.example.capstone1.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MerchantStockService merchantStockService;
    private final ProductService productService;
    private final MerchantService merchantService;

     ArrayList<Product> wishlist = new ArrayList<>();

      public ArrayList<Product> getWishlist() {
        return wishlist;
    }

   public List<User> getAllUsers() {
           return userRepository.findAll();
   }

    public void addUser(User user)
    {
        userRepository.save(user);
    }

    public  boolean updateUser(Integer  userid, User user){
         User oldUser = userRepository.getById(userid);

        if(oldUser != null) {
            oldUser.setUsername(user.getUsername());
            oldUser.setBalance(user.getBalance());
            oldUser.setEmail(user.getEmail());
            oldUser.setRole(user.getRole());
            oldUser.setPassword(user.getPassword());
            userRepository.save(oldUser);
            return true;
        }
         return false;
    }

    public boolean deleteUser(int userid){
        User u1 = userRepository.getById(userid);

        if(u1 != null) {
            userRepository.delete(u1);
            return true;
        }
        return false;
    }

    public int checkUserid(int userID){
        for (User user : userRepository.findAll()) {
            if (user.getId() == userID) {
                return 1;
            }
        }
        return 0;
    }

    // this method receive one parameter than return balance of user
    public double getBalance(int userID){

        for (User user : userRepository.findAll()) {
            if (user.getId() == userID) {
                return user.getBalance();
            }
        }
        return 0 ;
    }



    // this method receive two parameters than  deducted  of user
    public void deductedBalance(int userID, double deducted){
        for (User user : userRepository.findAll()) {
            if (user.getId() == userID) {
                user.setBalance(user.getBalance() - deducted);
            }
        }
    }

    public int userBuy(int userID, int merchantID, int productID) {

        // this code check user by ID is it in List
        if (checkUserid(userID) == 0) {
            return 1;
        }
        // this code check product by ID is it in List
        if (productService.checkProductId(productID) == 0) {
            return 2;
        }
        // this code check merchant by ID is it in List
        if (merchantService.checkMerchantId(merchantID) == 0) {
            return 3;
        }

        // this code check stock of merchant
        if (merchantStockService.getStockQuantity(merchantID) < 1) {
            return 4;
        }

        // this code check user balance if it bigger then product price
        if (getBalance(userID) < productService.getProductPrice(productID)) {
            return 5;
        }

        // this code sent two parameters to method "deductedBalance" to deducted ProductPrice from user balance
        deductedBalance(userID, productService.getProductPrice(productID));

        // this code send one parameter "merchantID" to "reduceStock" to reduce Stock from merchantStock
        merchantStockService.reduceStock(merchantID);


        return 60;
    }




    public boolean updateUserBalance(int userId, double newBalance) {
        for (User user : userRepository.findAll()) {
            if (user.getId() == userId) {
                user.setBalance(user.getBalance() + newBalance);
                return true;
            }
        }
        return false;
    }

    public Double checkUserBalance(int userId) {
        for (User user : userRepository.findAll()) {
            if (user.getId() == userId) {
                return user.getBalance();
            }
        }
        return null;
    }


    public int transferBalance(int fromUserId, int toUserId, double amount) {

        User sender = null;
        User receiver = null;

        for (User user : userRepository.findAll()) {
            if (user.getId() == fromUserId) {
                sender = user;
            }
            // this code check is sender available
            if (user.getId() == toUserId) {
                receiver = user;
            }
        }

        // this code check is sender = null
        if (sender == null ) {
            return 1;
        }
        // this code check is receiver = null
        if (receiver == null ) {
            return 2;
        }

        // this code check sender's Balance
        if (sender.getBalance() < amount) {
            return 3;
        }

        // this code reduce from sender Balance
        sender.setBalance(sender.getBalance() - amount);

        // this code increase receiver Balance
        receiver.setBalance(receiver.getBalance() + amount);
        return 0;
    }

    // this code to  add a product to the wishlist
    public void addToWishlist(int productId) {
        // this code is loop to find productId in List of Products
        for(Product product : productService.getAllProducts()) {
            if (product.getId() == productId) {
                if (!wishlist.contains(product)) {
                    wishlist.add(product);
                }
            }
        }

    }

    public int addToWishlist(int userId, int productId) {

        User user1 = null;
        Product product1 = null;

        // this code is loop to find userId in List of users
        for (User user : userRepository.findAll()) {
            if (user.getId() == userId) {
                user1 = user;
            }
        }

        // this code is loop to find productId in List of Products
        for(Product product : productService.getAllProducts()) {
            if (product.getId() == productId) {
                product1 = product;
            }
        }

        // this code check is user1 = null
        if (user1 == null ) {
            return 1;
        }
        // this code check is product1 = null
        if (product1 == null ) {
            return 2;
        }
        // this code to send productId to addToWishlist
        addToWishlist(productId);


        return 0;
    }


    // this Endpoint Remove a product from the wishlist
    public int removeWishlist(int productId) {
        Product product1 = null;

        // this code is loop to find productId in List of Products
        for(Product product : productService.getAllProducts()) {
            if (product.getId() == productId) {
                product1 = product;
            }
        }
        // this code check is product1 = null
        if (product1 == null ) {
            return 2;
        }

        for (int i = 0; i < wishlist.size() ; i++) {
            if (wishlist.get(i).getId() == productId) {
                wishlist.remove(i);
                return 1;
            }
        }
        return 0 ;
    }




}