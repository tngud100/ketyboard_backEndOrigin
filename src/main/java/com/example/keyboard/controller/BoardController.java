package com.example.keyboard.controller;

import com.example.keyboard.entity.board.inquire.InquireEntity;
import com.example.keyboard.entity.board.notice.NoticeEntity;
import com.example.keyboard.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "게시판 관련 API", description = "게시판, 다운로드, FAQ관련 게시판 CRUD")
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    public final BoardService boardService;

    @Operation(summary = "공지사항 전체리스트 조회", description = "공지사항 전체리스트 조회")
    @GetMapping("/notice/get/all")
    public ResponseEntity<Object> selectNoticeAllBoard(){
        try{
            List<NoticeEntity> noticeList =  boardService.selectNoticeAllBoard();
            return new ResponseEntity<>(noticeList, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "공지사항 특정 게시판 조회", description = "공지사항 특정 게시판 조회")
    @GetMapping("/notice/get/{noticesId}")
    public ResponseEntity<Object> getNoticeByNoticeId(@PathVariable("noticesId") Long notices_id){
        try{
            NoticeEntity noticeBoard =  boardService.getNoticeByNoticeId(notices_id);
            return new ResponseEntity<>(noticeBoard, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "공지사항 등록", description = "공지사항 등록하기")
    @PostMapping("/notice/enroll")
    public ResponseEntity<Object> enrollNoticeBoard(@RequestBody NoticeEntity noticeEntity){
        try{
            boardService.enrollNoticeBoard(noticeEntity);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
//    @Operation(summary = "공지사항 등록", description = "공지사항 등록하기")
//    @PostMapping("/faq/enroll")
//    public ResponseEntity<Object> enrollFaqBoard(NoticeEntity noticeEntity){
//        try{
//            boardService.enrollNoticeBoard(noticeEntity);
//            return new ResponseEntity<>("ok", HttpStatus.OK);
//        } catch (Exception e){
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//    @Operation(summary = "공지사항 등록", description = "공지사항 등록하기")
//    @PostMapping("/download/enroll")
//    public ResponseEntity<Object> enrollDownloadBoard(NoticeEntity noticeEntity){
//        try{
//            boardService.enrollNoticeBoard(noticeEntity);
//            return new ResponseEntity<>("ok", HttpStatus.OK);
//        } catch (Exception e){
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }


}
