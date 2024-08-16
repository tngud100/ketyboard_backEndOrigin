package com.example.keyboard.entity.renewalProduct;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class BestProductEntity {
    private Long best_product_id;         // 베스트 상품 일련번호
    private Long product_id;              // 상품 일련번호
    private MultipartFile image;          // 베스트 상품 이미지
    private String comment;               // 베스트 상품문구
    private LocalDate regdate;            // 상품 등록 날짜
    private LocalDate modified_date;      // 상품 수정 날짜
    private int isdelete;                 // 상품 삭제 여부
}
