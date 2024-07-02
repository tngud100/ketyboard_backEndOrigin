package com.example.keyboard.entity.board.download;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class DownloadEntity {
    private Long downloads_id;
    private String category;
    private String title;
    private String content;
    private String regdate;
    private String modified_date;
    private List<MultipartFile> files;
    private List<String> existedFileNames;
    private List<String> imageUrls;
    private List<String> deleteImageUrls;
    private int isdelete;
}
