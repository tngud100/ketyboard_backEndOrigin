package com.example.keyboard.entity.board.faq;

import lombok.Data;

@Data
public class FaqEntity {
    private Long faqs_id;
    private String category;
    private String title;
    private String ask;
    private String comment;
    private String regdate;
    private String modified_date;
    private int isdelete;
}
