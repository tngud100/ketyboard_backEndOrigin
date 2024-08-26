package com.example.keyboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3Upload {
        private final S3Client s3Client;

        @Value("${aws.s3.bucket}")
        private String bucket;

        // MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
        public String upload(MultipartFile multipartFile, String dirName) throws IOException {
            File uploadFile = convert(multipartFile)
                    .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
            return upload(uploadFile, dirName);
        }

        private String upload(File uploadFile, String dirName) {
            String fileName = dirName + "/" + UUID.randomUUID().toString() + "_" + uploadFile.getName();
            String uploadImageUrl = putS3(uploadFile, fileName);

            removeNewFile(uploadFile);  // convert()함수로 인해서 로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)

            return uploadImageUrl;      // 업로드된 파일의 S3 URL 주소 반환
        }

        private String putS3(File uploadFile, String fileName) {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .acl(ObjectCannedACL.PUBLIC_READ) // PublicRead 권한으로 업로드 됨
                    .build();

            s3Client.putObject(putObjectRequest, Paths.get(uploadFile.getPath()));

            return s3Client.utilities().getUrl(b -> b.bucket(bucket).key(fileName)).toExternalForm();
        }

        private void removeNewFile(File targetFile) {
            if(targetFile.delete()) {
                log.info("임시 파일이 삭제되었습니다.");
            } else {
                log.info("임시 파일이 삭제되지 못했습니다.");
            }
        }

    private Optional<File> convert(MultipartFile file) throws IOException {
        // 임시 파일 경로 출력
        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String tempFilePath = System.getProperty("java.io.tmpdir") + "/" + uniqueFileName;
        System.out.println("임시 파일 경로: " + tempFilePath);

        File convertFile = new File(tempFilePath);

        try {
            if (convertFile.createNewFile()) {
                System.out.println("파일이 성공적으로 생성되었습니다: " + convertFile.getAbsolutePath());
                try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                    fos.write(file.getBytes());
                }
                return Optional.of(convertFile);
            } else {
                System.out.println("파일 생성 실패: " + convertFile.getAbsolutePath());
            }
        } catch (IOException e) {
            System.out.println("파일 생성 중 IOException 발생: " + e.getMessage());
            e.printStackTrace();
        }

        return Optional.empty();
    }



    public boolean deleteImageFromS3IfExists(String picturePath) throws Exception{
            // 객체가 존재하는지 확인
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(bucket)
                    .key(picturePath)
                    .build();

            try {
                s3Client.headObject(headObjectRequest);
                // 객체가 존재하면 삭제
                DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                        .bucket(bucket)
                        .key(picturePath)
                        .build();

                s3Client.deleteObject(deleteObjectRequest);
                System.out.println("File deleted successfully from S3: " + picturePath);
                return true;  // 삭제 성공
            } catch (NoSuchKeyException e) {
                System.out.println("File does not exist in S3: " + picturePath);
                return false;  // 파일이 존재하지 않음
            } catch (S3Exception e) {
                System.out.println("Failed to delete file from S3: " + picturePath);
                e.printStackTrace();
                return false;  // 삭제 실패
            }
        }

        public String moveFile(String sourceFileName, String targetDirName) {
            String sourceKey = "editorImage/" + sourceFileName;
            String targetKey = targetDirName + "/" + sourceFileName;

            // Copy the file
            CopyObjectRequest copyObjectRequest = CopyObjectRequest.builder()
                    .sourceBucket(bucket)
                    .sourceKey(sourceKey)
                    .destinationBucket(bucket)
                    .destinationKey(targetKey)
                    .acl(ObjectCannedACL.PUBLIC_READ) // PublicRead 권한으로 업로드 됨
                    .build();

            s3Client.copyObject(copyObjectRequest);
            log.info("파일이 복사되었습니다. 원본 파일 이름: {}, 대상 파일 이름: {}", sourceKey, targetKey);

            // Delete the original file
            deleteFile(sourceKey);
            return targetKey;
        }

        public void deleteFile(String fileName) {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
            log.info("파일이 삭제되었습니다. 파일 이름: {}", fileName);
        }

}
