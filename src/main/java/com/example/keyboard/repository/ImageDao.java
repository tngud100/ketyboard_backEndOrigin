package com.example.keyboard.repository;

import com.example.keyboard.entity.Image.download.DownloadDaoEntity;
import com.example.keyboard.entity.Image.download.DownloadFileDaoEntity;
import com.example.keyboard.entity.Image.faq.FaqDaoEntity;
import com.example.keyboard.entity.Image.notice.NoticeDaoEntity;
import com.example.keyboard.entity.Image.product.ProductDaoEntity;
import com.example.keyboard.entity.Image.inquire.InquireDaoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ImageDao {
    public void saveProductImage(ProductDaoEntity imageVO) throws Exception;
    public List<ProductDaoEntity> selectPictureByProductId(@Param("product_id") Long product_id) throws Exception;
    public List<ProductDaoEntity> selectMainPictureByProductId(@Param("product_id") Long product_id) throws Exception;
    public void deletePictureByImgId(@Param("img_id") Long img_id) throws Exception;
    public void deletePictureByProductId(@Param("product_id") Long product_id) throws Exception;
    public void deleteMainPictureByProductId(@Param("product_id") Long product_id) throws Exception;

    public void saveInquireImage(InquireDaoEntity imageVO) throws Exception;
    public List<InquireDaoEntity> selectInquireImage(@Param("inquires_id") Long inquires_id) throws Exception;
    public InquireDaoEntity selectInquireImageByPictureName(@Param("picture_name") String picture_name, @Param("inquires_id") Long inquires_id) throws Exception;

    public void deleteInquirePicturesById(@Param("inquires_id") Long inquires_id) throws Exception;
    public void deleteInquirePicturesByPictureId(@Param("inquire_picture_id") Long inquire_picture_id) throws Exception;


    public void saveNoticePictures(@Param("notices_id") Long notices_id, @Param("picture_path") String picture_path, @Param("picture_name") String picture_name) throws Exception;
    public List<NoticeDaoEntity> selectNoticePicturesByNoticesId(@Param("notices_id") Long notices_id) throws Exception;
    public List<NoticeDaoEntity> selectNoticePicturesByPicturePath(@Param("picture_path") String picture_path) throws Exception;
    public void deleteNoticePicturesByNoticesId(@Param("notices_id") Long notices_id) throws Exception;
    public void deleteNoticePicturesByNoticePictureId(@Param("notice_picture_id") Long notice_picture_id) throws Exception;

    public void saveFaqPictures(@Param("faqs_id") Long faqs_id, @Param("picture_path") String picture_path, @Param("picture_name") String picture_name) throws Exception;
    public List<FaqDaoEntity> selectFaqPicturesByFaqsId(@Param("faqs_id") Long faqs_id) throws Exception;
    public List<FaqDaoEntity> selectFaqPicturesByPicturePath(@Param("picture_path") String picture_path) throws Exception;
    public void deleteFaqsByFaqsId(@Param("faqs_id") Long faqs_id) throws Exception;
    public void deleteFaqPicturesByFaqPictureId(@Param("faq_picture_id") Long faq_picture_id) throws Exception;

    public void saveDownloadPictures(@Param("downloads_id") Long downloads_id, @Param("picture_path") String picture_path, @Param("picture_name") String picture_name) throws Exception;
    public List<DownloadDaoEntity> selectDownloadPicturesByDownloadsId(@Param("downloads_id") Long downloads_id) throws Exception;
    public List<DownloadDaoEntity> selectDownloadPicturesByPicturePath(@Param("picture_path") String picture_path) throws Exception;
    public void deleteDownloadsByDownloadsId(@Param("downloads_id") Long downloads_id) throws Exception;
    public void deleteDownloadPicturesByDownloadPicturesId(@Param("download_picture_id") Long download_picture_id) throws Exception;


    public void saveDownloadFiles(DownloadFileDaoEntity fileVO) throws Exception;
    public DownloadFileDaoEntity getDownloadFilesByDownloadFileId(@Param("download_file_id") Long download_file_id) throws Exception;
    public List<DownloadFileDaoEntity> getDownloadFilesByDownloadId(@Param("downloads_id") Long downloads_id) throws Exception;
    public void deleteFilesByDownloadFilesId(@Param("download_file_id") Long download_file_id) throws Exception;
    public void deleteFilesByDownloadsId(@Param("downloads_id") Long downloads_id) throws Exception;
}
