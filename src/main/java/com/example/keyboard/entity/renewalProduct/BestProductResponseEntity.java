package com.example.keyboard.entity.renewalProduct;

import com.example.keyboard.entity.Image.renewalProduct.BestProductDaoEntity;
import lombok.Data;

import java.util.List;

@Data
public class BestProductResponseEntity {
    private List<BestProductEntity> productLists;
    private List<BestProductDaoEntity> productImageLists;
    private BestProductEntity productList;
    private BestProductDaoEntity productImageList;

    public BestProductResponseEntity(List<BestProductEntity> productLists, List<BestProductDaoEntity> productImageLists) {
        this.productLists = productLists;
        this.productImageLists = productImageLists;
    }
    public BestProductResponseEntity(BestProductEntity productList, BestProductDaoEntity productImageList) {
        this.productList = productList;
        this.productImageList = productImageList;
    }
}
