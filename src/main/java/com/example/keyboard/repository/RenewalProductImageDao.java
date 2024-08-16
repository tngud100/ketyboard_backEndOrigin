package com.example.keyboard.repository;

import com.example.keyboard.entity.Image.renewalProduct.BestProductDaoEntity;
import com.example.keyboard.entity.Image.renewalProduct.RenewalProductDaoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RenewalProductImageDao {
    public void saveRenewalProductImg(@Param("product_id") Long product_id, @Param("picture_path") String picture_path, @Param("picture_name") String picture_name) throws Exception;
    public void savePictorialProductImg(@Param("product_id") Long product_id, @Param("picture_path") String picture_path, @Param("picture_name") String picture_name) throws Exception;
    public void saveBestProductImg(@Param("best_product_id") Long best_product_id, @Param("picture_path") String picture_path, @Param("picture_name") String picture_name) throws Exception;

    public List<RenewalProductDaoEntity> selectRenewalProductImg() throws Exception;
    public RenewalProductDaoEntity selectRenewalProductImgByProductId(@Param("product_id") Long product_id) throws Exception;
    public List<BestProductDaoEntity> selectBestProductImg() throws Exception;
    public BestProductDaoEntity selectBestProductImgByProductId(@Param("best_product_id") Long best_product_id) throws Exception;

    public void deleteRenewalProductImg(@Param("product_id") Long product_id);
    public void deletePictorialProductImg(@Param("pictorial_product_id") Long pictorial_product_id) throws Exception;
    public void deleteBestProductImg(@Param("best_product_id") Long best_product_id) throws Exception;
}
