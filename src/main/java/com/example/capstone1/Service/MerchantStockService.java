package com.example.capstone1.Service;


import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Repository.MerchantRepository;
import com.example.capstone1.Repository.MerchantStockRepository;
import com.example.capstone1.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantStockService {



    private final MerchantStockRepository merchantStockRepository;
    private final ProductRepository productRepository;
    private final MerchantRepository merchantRepository;



     public List<MerchantStock> getAllMerchantStocks() {
        return merchantStockRepository.findAll();
    }

    public int addMerchantStocks(MerchantStock merchantStock) {

        for (int i = 0; i < productRepository.findAll().size(); i++) {
            if (productRepository.findAll().get(i).getId().equals(merchantStock.getProductId()))
            {

                for (int j = 0; j < merchantRepository.findAll().size(); j++) {
                    if (merchantRepository.findAll().get(j).getId().equals(merchantStock.getMerchantId())) {
                        merchantStockRepository.save(merchantStock);
                        return 0;
                    }
                }
                return 2;
            }
            return 1;
        }



        return 0;
    }

    public boolean updateMerchantStocks(int merchantStockId ,MerchantStock merchantStock) {
         MerchantStock oldMerchantStock = merchantStockRepository.getById(merchantStockId);

         if (oldMerchantStock != null) {
             oldMerchantStock.setMerchantId(merchantStock.getMerchantId());
             oldMerchantStock.setProductId(merchantStock.getProductId());
             oldMerchantStock.setStock(merchantStock.getStock());
             merchantStockRepository.save(oldMerchantStock);
             return true;
         }
        return false;
    }

    public boolean deleteMerchantStocks(int merchantStockId) {
        MerchantStock DeleteMerchantStock = merchantStockRepository.getById(merchantStockId);
        if (DeleteMerchantStock != null) {
            merchantStockRepository.delete(DeleteMerchantStock);
            return true;
        }
        return false;
    }

    public int checkProductId(int ProductId) {
        for (MerchantStock merchantStock : getAllMerchantStocks()) {
            if (merchantStock.getProductId() == ProductId) {
                return 1;
            }
        }
        return 0;
    }


    public int getStockQuantity(int merchantId) {
        for (MerchantStock merchantStock : getAllMerchantStocks()) {
            if (merchantStock.getMerchantId() == merchantId && merchantStock.getStock() >= 1) {
                return merchantStock.getStock();
            }
        }
        return 0;
    }

    public void reduceStock(int MerchantID){
        for (MerchantStock merchantStock : getAllMerchantStocks()) {
            if (merchantStock.getMerchantId() == MerchantID) {
                merchantStock.setStock(merchantStock.getStock() - 1);
            }
        }
    }


    public ArrayList<MerchantStock> getAllLowStock(int merchantID)
    {
        ArrayList<MerchantStock> LowStock = new ArrayList<>();
        for (MerchantStock merchantStock : getAllMerchantStocks()) {
            if (merchantStock.getMerchantId() == merchantID && merchantStock.getStock() <=10) {
                LowStock.add(merchantStock);
            }
        }
        return LowStock;
    }



}