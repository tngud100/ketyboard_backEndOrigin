package com.example.keyboard.repository;

import com.example.keyboard.entity.renewalProduct.BestProductEntity;
import com.example.keyboard.entity.renewalProduct.PictorialProductEntity;
import com.example.keyboard.entity.renewalProduct.RenewalProductEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RenewalProductDao {
    public void insertProduct(RenewalProductEntity renewalProductEntity) throws Exception;
    public void insertPictorialMain(PictorialProductEntity pictorialProductEntity) throws Exception;
    public void insertBestMain(BestProductEntity bestProductEntity) throws Exception;

    public List<RenewalProductEntity> selectAllProductList() throws Exception;
    public RenewalProductEntity selectProductById(@Param("product_id") Long product_id) throws Exception;
    public List<PictorialProductEntity> selectMainPictorialProduct() throws Exception;
    public List<BestProductEntity> selectMainBestProduct() throws Exception;

    public RenewalProductEntity selectProductListByName(@Param("name") String name) throws Exception;
    public PictorialProductEntity selectMainPictorialProductByProductId(@Param("product_id") Long product_id) throws Exception;
    public PictorialProductEntity selectMainPictorialProductByPictorialProductId(@Param("pictorial_product_id") Long pictorial_product_id) throws Exception;
    public PictorialProductEntity selectMainPictorialProductBySequence(@Param("sequence") int sequence) throws Exception;
    public BestProductEntity selectBestMainByProductId(@Param("product_id") Long product_id) throws Exception;
    public BestProductEntity selectBestMainByBestProductId(@Param("best_product_id") Long best_product_id) throws Exception;


    public void updateRenewalProduct(RenewalProductEntity renewalProduct) throws Exception;
    public void updatePictorialProduct(PictorialProductEntity pictorialProductEntity) throws Exception;
    public void updateBestProduct(BestProductEntity bestProductEntity) throws Exception;

    public void deleteRenewalProduct(@Param("product_id") Long product_id) throws Exception;
    public void deletePictorialProduct(@Param("pictorial_product_id") Long pictorial_product_id) throws Exception;
    public void deletePictorialProductByProductId(@Param("product_id") Long product_id) throws Exception;
    public void deleteBestProduct(@Param("best_product_id") Long best_product_id) throws Exception;
    public void deleteBestProductByProductId(@Param("product_id") Long product_id) throws Exception;

}
