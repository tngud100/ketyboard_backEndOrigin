package com.example.keyboard.entity.renewalProduct;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class RenewalProductEntity {
    private Long product_id;              // 상품 일련번호
    private String category;              // 상품 카테고리
    private MultipartFile image;          // 상품 이미지
    private String name;                  // 상품명
    private int amount;                   // 상품 금액
    private String shopping_link;         // 상품 링크
    private LocalDate regdate;            // 상품 등록 날짜
    private LocalDate modified_date;      // 상품 수정 날짜
    private int isdelete;                 // 상품 삭제 여부
}