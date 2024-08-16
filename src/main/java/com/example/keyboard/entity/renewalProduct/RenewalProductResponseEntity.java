package com.example.keyboard.entity.renewalProduct;

import com.example.keyboard.entity.Image.renewalProduct.BestProductDaoEntity;
import com.example.keyboard.entity.Image.renewalProduct.RenewalProductDaoEntity;
import lombok.Data;

import java.util.List;

@Data
public class RenewalProductResponseEntity {
    private List<RenewalProductEntity> productLists;
    private List<RenewalProductDaoEntity> productImageLists;
    private RenewalProductEntity productList;
    private RenewalProductDaoEntity productImageList;

    public RenewalProductResponseEntity(List<RenewalProductEntity> productLists, List<RenewalProductDaoEntity> productImageLists) {
        this.productLists = productLists;
        this.productImageLists = productImageLists;
    }

    public RenewalProductResponseEntity(RenewalProductEntity productList, RenewalProductDaoEntity productImageList) {
        this.productList = productList;
        this.productImageList = productImageList;
    }
}
