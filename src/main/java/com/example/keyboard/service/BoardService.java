package com.example.keyboard.service;

import com.example.keyboard.controller.ImageController;
import com.example.keyboard.entity.Image.download.DownloadFileDaoEntity;
import com.example.keyboard.entity.Image.notice.NoticeDaoEntity;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {
    public final BoardDao boardDao;
    public final ImageDao imageDao;
    public final ImageController imageController;

    public List<NoticeEntity> selectNoticeAllBoard() throws Exception{
        return boardDao.selectNoticeAllBoard();
    }
    public NoticeEntity getNoticeByNoticeId(long notices_id) throws Exception{
        return boardDao.getNoticeByNoticeId(notices_id);
    }

    public void enrollNoticeBoard(NoticeEntity noticeEntity) throws Exception{
        boardDao.enrollNoticeBoard(noticeEntity);

        Long notices_id = noticeEntity.getNotices_id();
        List<String> imageUrls = noticeEntity.getImageUrls();
        if(!imageUrls.isEmpty()){
            imageController.enrollEditorPictures(imageUrls, notices_id, 1);
        }
    }

    public void updateNoticeBoard(NoticeEntity noticeEntity) throws Exception{
        boardDao.updateNoticeBoard(noticeEntity);
        
        Long notices_id = noticeEntity.getNotices_id();
        List<String> imageUrls = noticeEntity.getImageUrls();
        List<String> deletedImageUrls = noticeEntity.getDeleteImageUrls();

        imageController.updateEditorPicture(imageUrls, deletedImageUrls, notices_id, 1);
    }

    public void deleteNoticeBoard(long notices_id) throws Exception{
        boardDao.deleteNoticeBoard(notices_id);
        imageController.deleteEditorPictures(notices_id, 1);
    }



    public List<FaqEntity> selectFaqAllBoard() throws Exception{
        return boardDao.selectFaqAllBoard();
    }
    public FaqEntity getFaqByFaqId(long faqs_id) throws Exception{
        return boardDao.getFaqByFaqId(faqs_id);
    }

    public void enrollFaqBoard(FaqEntity faqEntity) throws Exception{
        boardDao.enrollFaqBoard(faqEntity);

        Long faqs_id = faqEntity.getFaqs_id();
        List<String> imageUrls = faqEntity.getImageUrls();
        if(!imageUrls.isEmpty()){
            imageController.enrollEditorPictures(imageUrls, faqs_id, 2);
        }
    }

    public void updateFaqBoard(FaqEntity faqEntity) throws Exception{
        boardDao.updateFaqBoard(faqEntity);

        Long faqs_id = faqEntity.getFaqs_id();
        List<String> imageUrls = faqEntity.getImageUrls();
        List<String> deletedImageUrls = faqEntity.getDeleteImageUrls();

        imageController.updateEditorPicture(imageUrls, deletedImageUrls, faqs_id, 2);
    }

    public void deleteFaqBoard(long faqs_id) throws Exception{
        boardDao.deleteFaqBoard(faqs_id);
        imageController.deleteEditorPictures(faqs_id, 2);
    }



    public List<DownloadEntity> selectDownloadAllBoard() throws Exception{
        return boardDao.selectDownloadAllBoard();
    }
    public DownloadEntity getDownloadByDownloadId(long downloads_id) throws Exception{
        return boardDao.getDownloadByDownloadId(downloads_id);
    }
    public List<Map<String,String>> getDownloadFilesNameByDownloadId(long downloads_id) throws Exception{
        List<DownloadFileDaoEntity> files = imageDao.getDownloadFilesByDownloadId(downloads_id);
        List<Map<String, String>> fileNameList = new ArrayList<>();
        for(var i = 0; i < files.size(); i++){
            String fileName = files.get(i).getName();
            String filePath = files.get(i).getPath();

            Map<String, String> fileMap = new HashMap<>();
            fileMap.put("fileName", fileName);
            fileMap.put("filePath", filePath);
            fileNameList.add(fileMap);
        }
        return fileNameList;
    }

    public Long enrollDownloadBoard(DownloadEntity downloadEntity) throws Exception{
        boardDao.enrollDownloadBoard(downloadEntity);

        Long downloads_id = downloadEntity.getDownloads_id();
        List<String> imageUrls = downloadEntity.getImageUrls();
        if(!imageUrls.isEmpty()){
            imageController.enrollEditorPictures(imageUrls, downloads_id, 3);
        }

        return downloadEntity.getDownloads_id();
    }

    public void updateDownloadBoard(DownloadEntity downloadEntity) throws Exception {
        boardDao.updateDownloadBoard(downloadEntity);

        Long downloads_id = downloadEntity.getDownloads_id();
        List<String> imageUrls = downloadEntity.getImageUrls();
        List<String> deletedImageUrls = downloadEntity.getDeleteImageUrls();

        imageController.updateDownloadFiles(downloadEntity);
        imageController.updateEditorPicture(imageUrls, deletedImageUrls, downloads_id, 3);
    }

    public void deleteDownloadBoard(long downloads_id) throws Exception {
        boardDao.deleteDownloadBoard(downloads_id);
        imageController.deleteFilesByDownloadsId(downloads_id);
        imageController.deleteEditorPictures(downloads_id, 3);
    }
}
