package com.example.keyboard.service;

import com.example.keyboard.entity.board.faq.FaqEntity;
import com.example.keyboard.entity.board.notice.NoticeEntity;
import com.example.keyboard.repository.BoardDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    public final BoardDao boardDao;
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


}
