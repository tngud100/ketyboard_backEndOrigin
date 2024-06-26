package com.example.keyboard.repository;

import com.example.keyboard.entity.board.download.DownloadEntity;
import com.example.keyboard.entity.board.faq.FaqEntity;
import com.example.keyboard.entity.board.notice.NoticeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BoardDao {
    public List<NoticeEntity> selectNoticeAllBoard() throws Exception;
    public NoticeEntity getNoticeByNoticeId(long notices_id) throws Exception;
    public void enrollNoticeBoard(NoticeEntity noticeEntity) throws Exception;
    public void updateNoticeBoard(NoticeEntity noticeEntity) throws Exception;
    public void deleteNoticeBoard(long notices_id) throws Exception;

    public List<FaqEntity> selectFaqAllBoard() throws Exception;
    public FaqEntity getFaqByFaqId(long faqs_id) throws Exception;
    public void enrollFaqBoard(FaqEntity faqEntity) throws Exception;
    public void updateFaqBoard(FaqEntity faqEntity) throws Exception;
    public void deleteFaqBoard(long faqs_id) throws Exception;


    public List<DownloadEntity> selectDownloadAllBoard() throws Exception;
    public DownloadEntity getDownloadByDownloadId(long downloads_id) throws Exception;
    public void enrollDownloadBoard(DownloadEntity DownloadEntity) throws Exception;
    public void updateDownloadBoard(DownloadEntity DownloadEntity) throws Exception;
    public void deleteDownloadBoard(long downloads_id) throws Exception;
}
