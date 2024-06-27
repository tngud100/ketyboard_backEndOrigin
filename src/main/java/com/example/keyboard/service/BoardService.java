package com.example.keyboard.service;

import com.example.keyboard.controller.ImageController;
import com.example.keyboard.entity.Image.download.DownloadFileDaoEntity;
import com.example.keyboard.entity.board.download.DownloadEntity;
import com.example.keyboard.entity.board.faq.FaqEntity;
import com.example.keyboard.entity.board.notice.NoticeEntity;
import com.example.keyboard.repository.BoardDao;
import com.example.keyboard.repository.ImageDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    public final BoardDao boardDao;
    public final ImageDao imageDao;
    public final ImageController imageController;

    @Value("${fileUpload.path}")
    private String uploadFilePath;

    public List<NoticeEntity> selectNoticeAllBoard() throws Exception{
        return boardDao.selectNoticeAllBoard();
    }
    public NoticeEntity getNoticeByNoticeId(long notices_id) throws Exception{
        return boardDao.getNoticeByNoticeId(notices_id);
    }

    public void enrollNoticeBoard(NoticeEntity noticeEntity) throws Exception{
        boardDao.enrollNoticeBoard(noticeEntity);
    }

    public void updateNoticeBoard(NoticeEntity noticeEntity) throws Exception{
        boardDao.updateNoticeBoard(noticeEntity);
    }

    public void deleteNoticeBoard(long notices_id) throws Exception{
        boardDao.deleteNoticeBoard(notices_id);
    }



    public List<FaqEntity> selectFaqAllBoard() throws Exception{
        return boardDao.selectFaqAllBoard();
    }
    public FaqEntity getFaqByFaqId(long faqs_id) throws Exception{
        return boardDao.getFaqByFaqId(faqs_id);
    }

    public void enrollFaqBoard(FaqEntity faqEntity) throws Exception{
        boardDao.enrollFaqBoard(faqEntity);
    }

    public void updateFaqBoard(FaqEntity faqEntity) throws Exception{
        boardDao.updateFaqBoard(faqEntity);
    }

    public void deleteFaqBoard(long faqs_id) throws Exception{
        boardDao.deleteFaqBoard(faqs_id);
    }



    public List<DownloadEntity> selectDownloadAllBoard() throws Exception{
        return boardDao.selectDownloadAllBoard();
    }
    public DownloadEntity getDownloadByDownloadId(long downloads_id) throws Exception{
        return boardDao.getDownloadByDownloadId(downloads_id);
    }
    public List<String> getDownloadFilesNameByDownloadId(long downloads_id) throws Exception{
        List<DownloadFileDaoEntity> files = imageDao.getDownloadFilesNameByDownloadId(downloads_id);
        List<String> fileNameList = new ArrayList<>();
        for(var i = 0; i < files.size(); i++){
            String fileName = files.get(i).getFile_name();
            fileNameList.add(fileName);
        }
        return fileNameList;
    }

    public Long enrollDownloadBoard(DownloadEntity DownloadEntity) throws Exception{
        boardDao.enrollDownloadBoard(DownloadEntity);
        return DownloadEntity.getDownloads_id();
    }

    public void updateDownloadBoard(DownloadEntity downloadEntity) throws Exception {
        List<MultipartFile> files = downloadEntity.getFiles();
        List<String> existingFileNames = downloadEntity.getExistedFileNames();
        Long downloads_id = downloadEntity.getDownloads_id();

        String absolutePath = new File("").getAbsolutePath() + "\\" + uploadFilePath;

        if (existingFileNames == null) {
            existingFileNames = new ArrayList<>();
        }
        if (files == null) {
            files = new ArrayList<>();
        }

        List<DownloadFileDaoEntity> existedDownloadFiles = imageDao.getDownloadFilesNameByDownloadId(downloads_id);

        for (DownloadFileDaoEntity downloadFile : existedDownloadFiles) {
            if (!existingFileNames.contains(downloadFile.getFile_name())) {
                imageController.deleteFilesByDownloadFilesId(downloadFile.getDownload_file_id());
                deletePhysicalFile(absolutePath, downloadFile.getFile_path());
            }
        }
        if (!files.isEmpty()) {
            imageController.enrollDownloadFiles(files, downloads_id);
        }
        boardDao.updateDownloadBoard(downloadEntity);
    }

    public void deleteDownloadBoard(long downloads_id) throws Exception {
        boardDao.deleteDownloadBoard(downloads_id);
        imageController.deleteFilesByDownloadsId(downloads_id);

        List<DownloadFileDaoEntity> existedDownloadFiles = imageDao.getDownloadFilesNameByDownloadId(downloads_id);
        String absolutePath = new File("").getAbsolutePath() + "\\" + uploadFilePath;

        for (DownloadFileDaoEntity downloadFile : existedDownloadFiles) {
            deletePhysicalFile(absolutePath, downloadFile.getFile_path());
        }
    }

    private void deletePhysicalFile(String absolutePath, String filePath) {
        String lastImgPath = absolutePath + filePath.replace("/files", "");
        File file = new File(lastImgPath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("File delete successfully");
            } else {
                System.out.println("Failed to delete file");
            }
        } else {
            System.out.println("File not found");
        }
    }

}
