package com.example.keyboard.service;

import com.example.keyboard.controller.RenewalProductImageController;
import com.example.keyboard.entity.Image.renewalProduct.BestProductDaoEntity;
import com.example.keyboard.entity.Image.renewalProduct.RenewalProductDaoEntity;
import com.example.keyboard.entity.renewalProduct.*;
import com.example.keyboard.repository.RenewalProductDao;
import com.example.keyboard.repository.RenewalProductImageDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RenewalProductService {
    private final RenewalProductDao renewalProductDao;
    private final RenewalProductImageController renewalProductImageController;
    private final RenewalProductImageDao renewalProductImageDao;
    private BestProductResponseEntity bestProductResponseEntity;


    // 상품 등록
    public void enrollProduct(RenewalProductEntity renewalProductEntity) throws Exception {
        String productName = renewalProductEntity.getName();
        MultipartFile image = renewalProductEntity.getImage();

        RenewalProductEntity existingProduct = renewalProductDao.selectProductListByName(productName);
        if(existingProduct != null){
            throw new Exception("이미 등록된 상품명 입니다.");
        }

        renewalProductDao.insertProduct(renewalProductEntity);
        Long product_id = renewalProductEntity.getProduct_id();
        renewalProductImageController.enrollImageProduct(image, product_id, 0);
    }
    public void enrollPictorialMain(PictorialProductEntity pictorialProductEntity) throws Exception {
        Long productId = pictorialProductEntity.getProduct_id();
        int sequence = pictorialProductEntity.getSequence();
        MultipartFile pictorialImage = pictorialProductEntity.getImage();

        RenewalProductEntity existingRenewalProduct = renewalProductDao.selectProductById(productId);
        PictorialProductEntity existingSameIdProduct = renewalProductDao.selectMainPictorialProductByProductId(productId);
        PictorialProductEntity existingSameSequenceProduct = renewalProductDao.selectMainPictorialProductBySequence(sequence);

        if(existingRenewalProduct == null){
            throw new Exception("등록된 상품번호에 등록해 주세요.");
        }
        if(existingSameIdProduct != null){
            throw new Exception("이미 화보에 등록된 상품 번호입니다");
        }
        if( existingSameSequenceProduct != null){
            throw new Exception("이미 등록된 순서입니다");
        }

        renewalProductDao.insertPictorialMain(pictorialProductEntity);
        renewalProductImageController.enrollImageProduct( pictorialImage, productId, 1);
    }
    public void enrollBestMain(BestProductEntity bestProductEntity) throws Exception {
        Long productId = bestProductEntity.getProduct_id();
        MultipartFile bestImage = bestProductEntity.getImage();

        BestProductEntity existingProduct = renewalProductDao.selectBestMainByProductId(productId);
        RenewalProductEntity existingRenewalProduct = renewalProductDao.selectProductById(productId);

        if(existingRenewalProduct == null){
            throw new Exception("등록된 상품을 화보에 등록해 주세요.");
        }
        if(existingProduct != null){
            throw new Exception("이미 베스트 상품에 등록된 상품 번호 입니다");
        }
        renewalProductDao.insertBestMain(bestProductEntity);
        Long best_product_id = bestProductEntity.getBest_product_id();
        renewalProductImageController.enrollImageProduct( bestImage, best_product_id, 2);
    }

    // 상품 가져오기
    public RenewalProductResponseEntity selectAllProductList() throws Exception{
        List<RenewalProductEntity> productLists = renewalProductDao.selectAllProductList();
        List<RenewalProductDaoEntity> productImageLists = renewalProductImageDao.selectRenewalProductImg();
        return new RenewalProductResponseEntity(productLists, productImageLists);
    }
    public RenewalProductResponseEntity selectProductById(Long product_id) throws Exception {
        RenewalProductEntity productList = renewalProductDao.selectProductById(product_id);
        RenewalProductDaoEntity productImageList = renewalProductImageDao.selectRenewalProductImgByProductId(product_id);
        return new RenewalProductResponseEntity(productList, productImageList);
    }
    public List<PictorialProductEntity> selectMainPictorialProduct() throws Exception{
        return renewalProductDao.selectMainPictorialProduct();
    }
    public  BestProductResponseEntity selectMainBestProduct() throws Exception{
        List<BestProductEntity> productList = renewalProductDao.selectMainBestProduct();
        List<BestProductDaoEntity> productImageList = renewalProductImageDao.selectBestProductImg();
        return new BestProductResponseEntity(productList, productImageList);
    }

    // 상품 수정하기
    public void updateRenewalProduct(RenewalProductEntity renewalProductEntity) throws Exception{
        String newProductName = renewalProductEntity.getName();
        Long product_id = renewalProductEntity.getProduct_id();
        MultipartFile newImage = renewalProductEntity.getImage();

        RenewalProductEntity originProductEntity = renewalProductDao.selectProductById(product_id);
        String originProductName = originProductEntity.getName();

        // 상품명 중복 확인 로직
        if(!originProductName.equals(newProductName)){
            RenewalProductEntity existingProduct = renewalProductDao.selectProductListByName(newProductName);
            if(existingProduct != null){
                throw new Exception("이미 등록된 상품명 입니다.");
            }
        }

        // 새로운 이미지가 있을시에 기존 삭제 및 새로운 이미지 등록
        if(newImage != null){
            renewalProductImageController.deleteImageProduct(product_id, 0);
            renewalProductImageController.enrollImageProduct(newImage,product_id, 0);
        }

        renewalProductDao.updateRenewalProduct(renewalProductEntity);
    }
    public void updatePictorialProduct(PictorialProductEntity pictorialProductEntity) throws Exception{
        Long pictorialProductId = pictorialProductEntity.getPictorial_product_id();
        int newSequence = pictorialProductEntity.getSequence();
        Long newProductId = pictorialProductEntity.getProduct_id();
        MultipartFile newImage = pictorialProductEntity.getImage();

        PictorialProductEntity pictorialProductOrigin = renewalProductDao.selectMainPictorialProductByPictorialProductId(pictorialProductId);
        RenewalProductEntity existingRenewalProduct = renewalProductDao.selectProductById(newProductId);
        int originSequence = pictorialProductOrigin.getSequence();
        Long originProductId = pictorialProductOrigin.getProduct_id();

        // 상품 번호가 존재하는 지 조회
        if(existingRenewalProduct == null){
            throw new Exception("등록된 상품을 화보에 등록해 주세요.");
        }

        // 해당 화보 상품이 중복되었는지 조회
        if(newProductId != originProductId){
            PictorialProductEntity alreadyExistProductIdPictorialEntity = renewalProductDao.selectMainPictorialProductByProductId(newProductId);
            if(alreadyExistProductIdPictorialEntity != null){
                throw new Exception("이미 등록되어 있는 상품 입니다.");
            }
        }

        // 새롭게 설정하려는 순서의 상품과 순서를 교체
        if(newSequence != originSequence){
            PictorialProductEntity pictorialEntityHasNewSequence = renewalProductDao.selectMainPictorialProductBySequence(newSequence);

            if(pictorialEntityHasNewSequence != null){
                pictorialEntityHasNewSequence.setSequence(originSequence);
                renewalProductDao.updatePictorialProduct(pictorialEntityHasNewSequence);
            }
        }

        if(newImage != null){
            renewalProductImageController.deleteImageProduct(pictorialProductId, 1);
            renewalProductImageController.enrollImageProduct(newImage, newProductId, 1);
        }

        renewalProductDao.updatePictorialProduct(pictorialProductEntity);
    }
    public void updateBestProduct(BestProductEntity bestProductEntity) throws Exception{
        Long bestProductId = bestProductEntity.getBest_product_id();
        Long newProductId = bestProductEntity.getProduct_id();
        MultipartFile newImage = bestProductEntity.getImage();

        BestProductEntity bestProductOrigin = renewalProductDao.selectBestMainByBestProductId(bestProductId);
        RenewalProductEntity existingRenewalProduct = renewalProductDao.selectProductById(newProductId);
        Long originProductId = bestProductOrigin.getProduct_id();
        Long originBestProductId = bestProductOrigin.getBest_product_id();

        // 해당 상품 번호가 존재하는 지 조회
        if(existingRenewalProduct == null){
            throw new Exception("등록된 상품을 화보에 등록해 주세요.");
        }

        // 해당 베스트 상품이 중복되었는지 조회
        if(newProductId != originProductId){
            BestProductEntity alreadyExistProductIdBestEntity = renewalProductDao.selectBestMainByProductId(newProductId);
            if(alreadyExistProductIdBestEntity != null){
                throw new Exception("이미 등록되어 있는 상품 입니다.");
            }
        }

        // 이미지 수정
        if(newImage != null){
            renewalProductImageController.deleteImageProduct(originBestProductId, 2);
            renewalProductImageController.enrollImageProduct(newImage, bestProductId, 2);
        }

        renewalProductDao.updateBestProduct(bestProductEntity);
    }

    // 상품 삭제하기
    public void deleteRenewalProduct(Long product_id) throws Exception{
        BestProductEntity bestProductEntity = renewalProductDao.selectBestMainByProductId(product_id);
        PictorialProductEntity pictorialProductEntity = renewalProductDao.selectMainPictorialProductByProductId(product_id);
        Long best_product_id = bestProductEntity.getBest_product_id();
        Long pictorial_product_id = pictorialProductEntity.getPictorial_product_id();

        renewalProductImageController.deleteImageProduct(product_id,0);
        renewalProductImageController.deleteImageProduct(pictorial_product_id,1);
        renewalProductImageController.deleteImageProduct(best_product_id,2);

        renewalProductDao.deleteRenewalProduct(product_id);
        renewalProductDao.deletePictorialProductByProductId(product_id);
        renewalProductDao.deleteBestProductByProductId(product_id);
    }
    public void deletePictorialProduct(Long pictorial_product_id) throws Exception{
        renewalProductImageController.deleteImageProduct(pictorial_product_id,1);
        renewalProductDao.deletePictorialProduct(pictorial_product_id);
    }
    public void deleteBestProduct(Long best_product_id) throws Exception{
        renewalProductImageController.deleteImageProduct(best_product_id,2);
        renewalProductDao.deleteBestProduct(best_product_id);
    }
}
