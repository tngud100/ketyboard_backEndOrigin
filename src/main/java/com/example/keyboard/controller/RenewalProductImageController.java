package com.example.keyboard.controller;

import com.example.keyboard.entity.Image.renewalProduct.BestProductDaoEntity;
import com.example.keyboard.entity.Image.renewalProduct.RenewalProductDaoEntity;
import com.example.keyboard.entity.renewalProduct.PictorialProductEntity;
import com.example.keyboard.repository.ImageDao;
import com.example.keyboard.repository.RenewalProductDao;
import com.example.keyboard.repository.RenewalProductImageDao;
import com.example.keyboard.service.ImgUploadService;
import com.example.keyboard.service.S3Upload;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class RenewalProductImageController {
    private final RenewalProductImageDao renewalProductImageDao;
    private final RenewalProductDao renewalproductDao;
    private final S3Upload s3Upload;

    public RenewalProductImageController(RenewalProductImageDao renewalProductImageDao, RenewalProductDao renewalproductDao, S3Upload s3Upload) {
        this.renewalProductImageDao = renewalProductImageDao;
        this.renewalproductDao = renewalproductDao;
        this.s3Upload = s3Upload;
    }

    public void enrollImageProduct(MultipartFile image, Long product_id, int enrollType) throws Exception{
        String dirName = "";
        if(enrollType == 0){
            dirName = "product";
        }else if(enrollType == 1){
            dirName = "main/pictorial";
        }else if(enrollType == 2){
            dirName = "main/best";
        }

        String fileUrl = s3Upload.upload(image, dirName);
        String fileName = image.getOriginalFilename();

        if(enrollType == 0){
            renewalProductImageDao.saveRenewalProductImg(product_id, fileUrl, fileName);
        }else if(enrollType == 1){
            renewalProductImageDao.savePictorialProductImg(product_id, fileUrl, fileName);
        }else if(enrollType == 2){ // product_id = bestProductId
            renewalProductImageDao.saveBestProductImg(product_id, fileUrl, fileName);
        }
    }

    public void deleteImageProduct(Long product_id, int enrollType) throws Exception{
        String originalName = "";
        if(enrollType == 0){
            RenewalProductDaoEntity productList =  renewalProductImageDao.selectRenewalProductImgByProductId(product_id);
            originalName = productList.getPath();
        }else if(enrollType == 1){
            PictorialProductEntity productList = renewalproductDao.selectMainPictorialProductByPictorialProductId(product_id);
            originalName = productList.getPicture_path();
        }else if(enrollType == 2){
            BestProductDaoEntity productList =  renewalProductImageDao.selectBestProductImgByProductId(product_id);
            originalName = productList.getPath();
        }

        String decodedOriginalName = URLDecoder.decode(originalName, StandardCharsets.UTF_8);
        String imageNameInS3 = decodedOriginalName.replace("https://joseonkeyboard-image-bucket.s3.ap-northeast-2.amazonaws.com/", "");
        s3Upload.deleteFile(imageNameInS3);

        if(enrollType == 0){
            renewalProductImageDao.deleteRenewalProductImg(product_id);
        }else if(enrollType == 1){ // productId === pictorialProductId
            renewalProductImageDao.deletePictorialProductImg(product_id);
        }else if(enrollType == 2){ // procutId === bestProductId
            renewalProductImageDao.deleteBestProductImg(product_id);
        }
    }
}

