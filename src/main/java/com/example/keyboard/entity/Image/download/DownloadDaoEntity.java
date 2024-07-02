package com.example.keyboard.entity.Image.download;

import lombok.Data;

@Data
public class DownloadDaoEntity {
    private long download_picture_id;
    private long downloads_id;
    private String picture_path;
    private String picture_name;
}
