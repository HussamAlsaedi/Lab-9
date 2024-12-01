package com.example.capstone1.Service;


import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.User;
import com.example.capstone1.Repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantService {

  private final MerchantRepository merchantRepository;

    public List<Merchant> getAllMerchants() {
        return merchantRepository.findAll();
    }

    public void addMerchants(Merchant merchant) {
        merchantRepository.save(merchant);
    }

    public boolean updateMerchant(Integer merchantId , Merchant merchant) {
        Merchant oldMerchant = merchantRepository.getById(merchantId);

        if(oldMerchant != null) {
            oldMerchant.setName(merchant.getName());
            merchantRepository.save(oldMerchant);
            return true;
        }
        return false;
    }

    public boolean deleteMerchant(int merchantId) {
        Merchant m1 = merchantRepository.getById(merchantId);

        if(m1 != null) {
            merchantRepository.delete(m1);
            return true;
        }
        return false;
    }

    public int checkMerchantId(int merchantId) {
       for (Merchant merchant : merchantRepository.findAll()) {
            if (merchant.getId() == merchantId) {
                return 1;
            }
        }
        return 0;
    }

}