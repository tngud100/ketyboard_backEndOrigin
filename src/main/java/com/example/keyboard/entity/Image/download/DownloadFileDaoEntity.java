package com.example.keyboard.entity.Image.download;

import lombok.Data;

@Data
public class DownloadFileDaoEntity {
    private long download_file_id;
    private long downloads_id;
    private String path;
    private String name;
}
