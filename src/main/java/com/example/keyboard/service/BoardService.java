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
        List<DownloadFileDaoEntity> files = imageDao.getDownloadFilesByDownloadId(downloads_id);
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
        boardDao.updateDownloadBoard(downloadEntity);
        imageController.updateDownloadFiles(downloadEntity);
    }

    public void deleteDownloadBoard(long downloads_id) throws Exception {
        boardDao.deleteDownloadBoard(downloads_id);
        imageController.deleteFilesByDownloadsId(downloads_id);
    }
}
