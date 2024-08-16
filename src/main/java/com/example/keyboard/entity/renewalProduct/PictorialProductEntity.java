package com.example.keyboard.entity.renewalProduct;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class PictorialProductEntity {
    private Long pictorial_product_id;      // 화보 상품 일련번호
    private Long product_id;                // 상품 일련번호
    private MultipartFile image;   // 이미지 파일
    private int sequence;                   // 등록 순서
    private String comment;                 // 화보 상품 문구
    private String picture_path;            // 화보 사진 경로
    private String picture_name;            // 화보 사진 이름
    private LocalDate regdate;              // 상품 등록 날짜
    private LocalDate modified_date;        // 상품 수정 날짜
    private int isdelete;                   // 상품 삭제 여부
}
