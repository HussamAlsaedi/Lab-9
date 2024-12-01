package com.example.capstone1.Service;


import com.example.capstone1.Model.Product;
import com.example.capstone1.Repository.CategoryRepository;
import com.example.capstone1.Repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;



    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public int addProduct(Product product)
    {
        for (int i = 0; i < categoryRepository.findAll().size(); i++) {
            if (categoryRepository.findAll().get(i).getId().equals(product.getCategoryId())) {
                productRepository.save(product);
                return 1;
            }
        }


        return 0;

    }

    public  boolean updateProduct(int productId, Product product){
        Product oldProduct = productRepository.getById(productId);
        if (oldProduct != null) {
            oldProduct.setName(product.getName());
            oldProduct.setPrice(product.getPrice());
            oldProduct.setCategoryId(product.getCategoryId());
            productRepository.save(oldProduct);
            return true;
        }
        return false;
    }

    public boolean deleteProduct(int productId){
        Product DeleteProduct = productRepository.getById(productId);
        if (DeleteProduct != null) {
            productRepository.delete(DeleteProduct);
            return true;
        }

        return false;
    }


    public int checkProductId(int id){
        for (Product product : getAllProducts()) {
            if (product.getId() == id) {
                return 1;
            }
        }
        return 0;
    }

    public double getProductPrice(int index){
        for (Product product : getAllProducts()) {
            if (product.getId() == index) {
                return product.getPrice();
            }
        }
        return 0;
    }


    public ArrayList<Product> getProductById(int index) {

        ArrayList<Product> matchingProducts = new ArrayList<>();

        for(Product product : getAllProducts()) {
            if (product.getId() == index) {
                matchingProducts.add(product);
            }
        }
        return matchingProducts;
    }

    public ArrayList<Product> getProductByCategory(int index) {

        ArrayList<Product> matchingCategory = new ArrayList<>();

        for(Product product : getAllProducts()) {
            if (product.getCategoryId() == index) {
                matchingCategory.add(product);
            }
        }
        return matchingCategory;
    }

    public ArrayList<Integer> getProductCount(int index) {

        ArrayList<Integer> countProducts = new ArrayList<>();

        countProducts.add(index);

        return countProducts;
    }



    public double checkProductBalance(int index) {
        for (Product product : getAllProducts()) {
            if (product.getId() == index) {
                return product.getPrice();
            }
        }
        return 0;
    }

}