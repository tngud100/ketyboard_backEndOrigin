package com.example.keyboard.repository;

import com.example.keyboard.entity.board.notice.NoticeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BoardDao {
    public void enrollNoticeBoard(NoticeEntity noticeEntity) throws Exception;
    public List<NoticeEntity> selectNoticeAllBoard() throws Exception;
    public NoticeEntity getNoticeByNoticeId(long notices_id) throws Exception;
}
