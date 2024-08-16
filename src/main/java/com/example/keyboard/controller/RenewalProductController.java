package com.example.keyboard.controller;


import com.example.keyboard.entity.Image.renewalProduct.BestProductDaoEntity;
import com.example.keyboard.entity.Image.renewalProduct.RenewalProductDaoEntity;
import com.example.keyboard.entity.board.inquire.InquireEntity;
import com.example.keyboard.entity.renewalProduct.*;
import com.example.keyboard.repository.RenewalProductDao;
import com.example.keyboard.service.RenewalProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.Path;

import java.util.List;
import java.util.Map;

@Tag(name = "상품관련 API", description = "리뉴얼 상품 CRUD API")
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class RenewalProductController {
    private final RenewalProductService renewalProductService;

//    ----------------------- enroll -----------------------
    @Operation(summary = "상품 등록", description = "리뉴얼 상품 등록하기")
    @PostMapping("/product/enroll")
    public ResponseEntity<Object> enrollProduct(RenewalProductEntity renewalProductEntity){
        try{
            renewalProductService.enrollProduct(renewalProductEntity);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "메인 화보 상품 등록", description = "메인페이지 화보 상품 등록하기")
    @PostMapping("/main/pictorial/enroll")
    public ResponseEntity<Object> enrollPictorialMain(PictorialProductEntity pictorialProductEntity){
        try{
            renewalProductService.enrollPictorialMain(pictorialProductEntity);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "메인 베스트 상품 등록", description = "리뉴얼 상품 등록하기")
    @PostMapping("/main/best/enroll")
    public ResponseEntity<Object> enrollBestMain(BestProductEntity bestProductEntity){
        try{
            renewalProductService.enrollBestMain(bestProductEntity);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    //    ----------------------- select -----------------------
    @Operation(summary = "상품 리스트 전체 조회", description = "상품 리스트 전체 조회하기")
    @GetMapping("/product/get/all")
    public ResponseEntity<Object> selectProductAllList(){
        try{
            RenewalProductResponseEntity productAllList = renewalProductService.selectAllProductList();
            return new ResponseEntity<>(productAllList, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "해당 상품 리스트 조회", description = "해당 상품 아이디로 상품 리스트 조회")
    @GetMapping("/product/get/{product_id}")
    public ResponseEntity<Object> selectProductListById(@PathVariable("product_id") Long product_id){
        try{
            RenewalProductResponseEntity productList = renewalProductService.selectProductById(product_id);
            return new ResponseEntity<>(productList, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "메인 화보 상품 조회", description = "메인 화보 상품 조회하기")
    @GetMapping("/main/get/pictorialProduct")
    public ResponseEntity<Object> selectMainPictorialProduct(){
        try{
            List<PictorialProductEntity> mainPictorialProduct = renewalProductService.selectMainPictorialProduct();
            return new ResponseEntity<>(mainPictorialProduct, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "메인 베스트 상품 조회", description = "메인 베스트 상품 조회")
    @GetMapping("/main/get/bestProduct")
    public ResponseEntity<Object> selectMainBestProduct(){
        try{
            BestProductResponseEntity mainBestProductList = renewalProductService.selectMainBestProduct();
            return new ResponseEntity<>(mainBestProductList, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //    ----------------------- put -----------------------

    @Operation(summary = "해당 상품 수정", description = "해당 상품 수정")
    @PutMapping("/product/update")
    public ResponseEntity<Object> updateRenewalProduct(RenewalProductEntity renewalProductEntity){
        try{
            renewalProductService.updateRenewalProduct(renewalProductEntity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "메인 화보 상품 수정", description = "메인 화보 상품 수정")
    @PutMapping("/main/pictorial/update")
    public ResponseEntity<Object> updatePictorialProduct(PictorialProductEntity pictorialProductEntity){
        try{
            renewalProductService.updatePictorialProduct(pictorialProductEntity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "베스트 상품 수정", description = "메인페이지의 베스트 상품 수정")
    @PutMapping("/main/best/update")
    public ResponseEntity<Object> updateBestProduct(BestProductEntity bestProductEntity){
        try{
            renewalProductService.updateBestProduct(bestProductEntity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    //    ----------------------- delete -----------------------
    @Operation(summary = "해당 상품 삭제", description = "해당 상품 삭제")
    @PutMapping("/product/delete/{product_id}")
    public ResponseEntity<Object> deleteRenewalProduct(@PathVariable("product_id") Long product_id){
        try{
            renewalProductService.deleteRenewalProduct(product_id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "메인 화보 상품 삭제", description = "메인 화보 상품 삭제")
    @PutMapping("/main/pictorial/delete/{pictorial_product_id}")
    public ResponseEntity<Object> deletePictorialProduct(@PathVariable("pictorial_product_id") Long pictorial_product_id){
        try{
            renewalProductService.deletePictorialProduct(pictorial_product_id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "베스트 상품 삭제", description = "메인페이지의 베스트 상품 삭제")
    @PutMapping("/main/best/delete/{best_product_id}")
    public ResponseEntity<Object> deleteBestProduct(@PathVariable("best_product_id") Long best_product_id) throws Exception {
        try{
            renewalProductService.deleteBestProduct(best_product_id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
