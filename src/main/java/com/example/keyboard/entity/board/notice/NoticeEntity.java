package com.example.keyboard.entity.board.notice;

import lombok.Data;

@Data
public class NoticeEntity {
    private Long notices_id;
    private String title;
    private String content;
    private String modified_date;
    private String regdate;
    private int isdelete;
}
