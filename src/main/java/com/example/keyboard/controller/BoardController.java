package com.example.keyboard.controller;

import com.example.keyboard.entity.Image.download.DownloadFileDaoEntity;
import com.example.keyboard.entity.board.download.DownloadEntity;
import com.example.keyboard.entity.board.faq.FaqEntity;
import com.example.keyboard.entity.board.inquire.InquireEntity;
import com.example.keyboard.entity.board.notice.NoticeEntity;
import com.example.keyboard.repository.ImageDao;
import com.example.keyboard.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "게시판 관련 API", description = "게시판, 다운로드, FAQ관련 게시판 CRUD")
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    public final BoardService boardService;
    public final ImageController imageController;
    public final ImageDao imageDao;

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
    @Operation(summary = "공지사항 삭제", description = "공지사항 삭제하기")
    @PutMapping("/notice/{noticesId}/delete")
    public ResponseEntity<Object> deleteNoticeBoard(@PathVariable("noticesId") Long notices_id){
        try{
            boardService.deleteNoticeBoard(notices_id);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "공지사항 수정", description = "공지사항 수정하기")
    @PutMapping("/notice/{noticesId}/update")
    public ResponseEntity<Object> updateNoticeBoard(@RequestBody NoticeEntity noticeEntity){
        try{
            boardService.updateNoticeBoard(noticeEntity);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



    @Operation(summary = "자주묻는 질문 전체리스트 조회", description = "자주묻는 질문 전체리스트 조회")
    @GetMapping("/faq/get/all")
    public ResponseEntity<Object> selectFaqAllBoard(){
        try{
            List<FaqEntity> faqList =  boardService.selectFaqAllBoard();
            return new ResponseEntity<>(faqList, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "자주묻는 질문 특정 게시판 조회", description = "자주묻는 질문 특정 게시판 조회")
    @GetMapping("/faq/get/{faqsId}")
    public ResponseEntity<Object> getFaqByFaqId(@PathVariable("faqsId") Long faqs_id){
        try{
            FaqEntity faqBoard =  boardService.getFaqByFaqId(faqs_id);
            return new ResponseEntity<>(faqBoard, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "자주묻는 질문 등록", description = "자주묻는 질문 등록하기")
    @PostMapping("/faq/enroll")
    public ResponseEntity<Object> enrollFaqBoard(@RequestBody FaqEntity faqEntity){
        try{
            boardService.enrollFaqBoard(faqEntity);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "자주묻는 질문 삭제", description = "자주묻는 질문 삭제하기")
    @PutMapping("/faq/{faqsId}/delete")
    public ResponseEntity<Object> deleteFaqBoard(@PathVariable("faqsId") Long faqs_id){
        try{
            boardService.deleteFaqBoard(faqs_id);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "자주묻는 질문 수정", description = "자주묻는 질문 수정하기")
    @PutMapping("/faq/{faqsId}/update")
    public ResponseEntity<Object> updateFaqBoard(@RequestBody FaqEntity faqEntity){
        try{
            boardService.updateFaqBoard(faqEntity);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }






    @Operation(summary = "다운로드 게시판 전체리스트 조회", description = "다운로드 게시판 전체리스트 조회")
    @GetMapping("/download/get/all")
    public ResponseEntity<Object> selectDownloadAllBoard(){
        try{
            List<DownloadEntity> downloadList =  boardService.selectDownloadAllBoard();
            return new ResponseEntity<>(downloadList, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "다운로드 게시판 특정 게시판 조회", description = "다운로드 게시판 특정 게시판 조회")
    @GetMapping("/download/get/{downloadsId}")
    public ResponseEntity<Object> getDownloadByDownloadId(@PathVariable("downloadsId") Long downloads_id){
        try{
            DownloadEntity downloadBoard = boardService.getDownloadByDownloadId(downloads_id);
            List<String> fileNameList = boardService.getDownloadFilesNameByDownloadId(downloads_id);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("boardList", downloadBoard);
            responseMap.put("fileNames", fileNameList);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "다운로드 게시판 등록", description = "다운로드 게시판 등록하기")
    @PostMapping("/download/enroll")
    public ResponseEntity<Object> enrollDownloadBoard(@ModelAttribute DownloadEntity downloadEntity){
        try{
            List<MultipartFile> files = downloadEntity.getFiles();
            Long downloads_id = boardService.enrollDownloadBoard(downloadEntity);
            imageController.enrollDownloadFiles(files, downloads_id);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "다운로드 게시판 삭제", description = "다운로드 게시판 삭제하기")
    @PutMapping("/download/{downloadsId}/delete")
    public ResponseEntity<Object> deleteDownloadBoard(@PathVariable("downloadsId") Long downloads_id){
        try{
            boardService.deleteDownloadBoard(downloads_id);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "다운로드 게시판 수정", description = "다운로드 게시판 수정하기")
    @PutMapping("/download/{downloadsId}/update")
    public ResponseEntity<Object> updateDownloadBoard(@ModelAttribute DownloadEntity downloadEntity){
        try{
            boardService.updateDownloadBoard(downloadEntity);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
