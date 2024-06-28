    package com.example.keyboard.controller;

    import com.example.keyboard.entity.Image.download.DownloadFileDaoEntity;
    import com.example.keyboard.entity.Image.inquire.InquireDaoEntity;
    import com.example.keyboard.entity.Image.inquire.InquireImageEntity;
    import com.example.keyboard.entity.Image.product.ProductDaoEntity;
    import com.example.keyboard.entity.Image.product.ProductImageEntity;
    import com.example.keyboard.entity.board.download.DownloadEntity;
    import com.example.keyboard.service.ImgUploadService;
    import io.swagger.v3.oas.annotations.Operation;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import jakarta.annotation.Resource;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.core.io.InputStreamResource;
    import org.springframework.http.HttpHeaders;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.MediaType;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Component;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.File;
    import java.io.IOException;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.UUID;

    @Tag(name = "이미지 API", description = "이미지 등록 API")
    @Component
    @RestController
    public class ImageController {

        private final ImgUploadService imgUploadService;

        public ImageController(ImgUploadService imgUploadService) {
            this.imgUploadService = imgUploadService;
        }
        @Value("${upload.path}") // application.properties에 설정된 이미지 업로드 경로를 가져옵니다.
        private String uploadPath;
        @Value("${fileUpload.path}")
        private String uploadFilePath;

        @Operation(summary = "이미지 업로드")
        public ResponseEntity<Object> uploadImage(ProductImageEntity productImageEntity) throws Exception {
            try{
                Long product_id = productImageEntity.getProduct_id();
                MultipartFile list_picture = productImageEntity.getList_picture();
                MultipartFile represent_picture = productImageEntity.getRepresent_picture();
                MultipartFile list_back_picture = productImageEntity.getList_back_picture();
                List<MultipartFile> desc_picture = productImageEntity.getDesc_picture();

                List<MultipartFile> picture = new ArrayList<>();
                picture.add(list_picture);
                picture.add(represent_picture);
                picture.add(list_back_picture);

                for(int i = 0; i < picture.size(); i ++){
                    ProductDaoEntity imgEntity = imgUploadService.uploadImg(picture.get(i), product_id);
                    imgUploadService.saveImgPath(imgEntity);
                }
                for( MultipartFile desc_pic : desc_picture){
                    ProductDaoEntity imgEntity = imgUploadService.uploadImg(desc_pic, product_id);
                    imgUploadService.saveImgPath(imgEntity);
                }

                return new ResponseEntity<>("이미지 업로드 완료", HttpStatus.OK);
            }catch (Exception e){
                System.out.println(e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        @Operation(summary = "이미지 업로드")
        public ResponseEntity<Object> uploadInquireImg(List<MultipartFile> multipartFileList, Long inquires_id) throws Exception {
            try{
                for(MultipartFile imgFile : multipartFileList){
                    InquireDaoEntity inquireDaoEntity = imgUploadService.uploadInquireImg(imgFile, inquires_id);
                    imgUploadService.saveInquireImgPath(inquireDaoEntity);
                }
                return new ResponseEntity<>("이미지 업로드 완료", HttpStatus.OK);
            }catch (Exception e){
                System.out.println(e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }

        public ResponseEntity<Object> uploadMainImg(MultipartFile mainImg, @RequestParam("product_id") Long product_id) throws Exception{
            try {
                ProductDaoEntity imgEntity = imgUploadService.uploadImg(mainImg, product_id);
                imgUploadService.saveImgPath(imgEntity);
                return new ResponseEntity<>("이미지 업로드 완료", HttpStatus.OK);
            }catch (Exception e){
                System.out.println(e.getMessage());
                return new ResponseEntity<>(e.getMessage(),  HttpStatus.BAD_REQUEST);
            }
        }
        @Operation(summary = "이미지 삭제")
        public ResponseEntity<String> deleteImg(@RequestParam("product_id") Long product_id) throws Exception {
            try{
                imgUploadService.deleteImg(product_id);
                return new ResponseEntity<>(HttpStatus.OK);
            }catch (Exception e){
                System.out.println(e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        @Operation(summary = "메인 이미지 화보 삭제")
        public ResponseEntity<String> deleteMainImg(@RequestParam("product_id") Long product_id) throws Exception {
            try{
                imgUploadService.deleteMainImg(product_id);
                return new ResponseEntity<>(HttpStatus.OK);
            }catch (Exception e){
                System.out.println(e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }


        public ResponseEntity<Object> modifyUploadImg(ProductImageEntity productImageEntity) throws Exception{
            try{
                imgUploadService.modifyUpload(productImageEntity);
                return new ResponseEntity<>("이미지 수정 완료", HttpStatus.OK);
            }catch(Exception e) {
                System.out.println(e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }

        public ResponseEntity<Object> modifyInquireImg(MultipartFile imgFile, String existedFileName, Long inquires_id) throws Exception{
            try{
                if(!existedFileName.equals("null")){
                    InquireDaoEntity deletedInquireDaoEntity = imgUploadService.deleteBeforeInquireImg(existedFileName, inquires_id);
                    imgUploadService.deleteInquireImg(deletedInquireDaoEntity);
                }
                if(imgFile != null){
                    InquireDaoEntity newInquireDaoEntity = imgUploadService.uploadInquireImg(imgFile, inquires_id);
                    imgUploadService.saveInquireImgPath(newInquireDaoEntity);
                }

                return new ResponseEntity<>("이미지 수정 완료", HttpStatus.OK);
            }catch(Exception e) {
                System.out.println(e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }

        public ResponseEntity<InputStreamResource> downloadFile(@RequestParam String fileName) {
            try {
                InputStreamResource fileResource = imgUploadService.loadFileAsResource(fileName);

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("application/octet-stream"))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                        .body(fileResource);

            } catch (IOException e) {
                return ResponseEntity.status(500).body(null);
            }
        }

        @PostMapping("api/editor/imgUpload")
        public ResponseEntity<?> uploadEditorImage(@RequestParam("upload") MultipartFile file) {
            try {
                String uniqueFilename = imgUploadService.uploadEditorImage(file);

                // 이미지 URL 반환
                String fileUrl = "http://localhost:8080/images/editor/" + uniqueFilename;
                return ResponseEntity.ok().body("{\"url\": \"" + fileUrl + "\"}");
            } catch (Exception e) {
                return ResponseEntity.status(500).body("{\"error\": \"" + e.getMessage() + "\"}");
            }
        }

        @DeleteMapping("api/editor/imgDelete/{imgName}")
        public ResponseEntity<?> deleteEditorImage(@PathVariable("imgName") String imgName) {
            try {
                imgUploadService.deleteEditorImage(imgName);
                return ResponseEntity.ok().body("");
            } catch (Exception e) {
                return ResponseEntity.status(500).body("{\"error\": \"" + e.getMessage() + "\"}");
            }
        }

        // 게시글 작성시에는 하루뒤 자동 삭제, 즉 editor폴더에서 머무른지 하루된 이미지는 자동 삭제
        // 게시글을 등록시에는 editor폴더에 있던 이미지를 images폴더로 이동 후 데이터 베이스 저장
        // 게시글 삭제시에는 images폴더에 있는 해당 게시글 이미지 삭제
        // 게시글 수정시에는 url확인 후 삭제

        // board_type 1일때 notice, 2일때 faq, 3일때 download
        public void enrollEditorPictures(List<String> imageUrls, Long board_id, int board_type) throws Exception{
            for(String imgUrl : imageUrls){
                String imgName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
                String[] originalName = imgName.split("_",2);
                String path = "/images"+ File.separator + imgName;

                imgUploadService.moveEditorImages(imgUrl);
                imgUploadService.enrollEditorImageToDatabase(originalName[1], path, board_id, board_type);
            }
        }


        public void enrollDownloadFiles(List<MultipartFile> files, Long downloads_id) throws Exception{
            if(files != null){
                imgUploadService.uploadDownloadFile(files, downloads_id);
            }
        }
        public void updateDownloadFiles(DownloadEntity downloadEntity) throws Exception{
            imgUploadService.updateDownloadFiles(downloadEntity);
        }

        public void deleteFilesByDownloadsId(Long downloads_id) throws Exception {
            imgUploadService.deleteFilesByDownloadsId(downloads_id);
        }

    }

