package com.yiben_video.web;
import com.yiben_video.common.Result;
import com.yiben_video.common.ResultEnum;
import com.yiben_video.pojo.VideoEntity;
import com.yiben_video.utils.QrCodeEncodeUtil;
import com.yiben_video.utils.Time;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


/**
 * @author 王飞
 * @create 2021/5/31 13:52
 * @emai 936886169@qq.com
 */
@Slf4j
@RestController
@Api(tags = "视频管理")
@RequestMapping("/video")
public class VideoController extends BaseController{
    @Value("${file.address}")
    private String address;
    @Value("${url}")
    private String url;
    Result result = new Result();
    /**
     * 根据id查询视频
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getFindVideo(
            @PathVariable("id") Integer id
    ){
        Result idVideo = videoService.getFindOneVideo(id);
        return idVideo;
    }


    /**
     * 新增视频
     * 现在还没做上传功能，都是对象直接存到数据库，在所有功能做完后在做上传
     * @param videoEntity
     * @return
     */
    @PostMapping("/saveVideo")
    public Result saveVideo(
            VideoEntity videoEntity,
            @RequestParam("file") MultipartFile file
    ) throws Exception {
        VideoEntity videoEntity1 = new VideoEntity();
        if (file.isEmpty()) {
            return result.error(ResultEnum.ERROR_SAVEVIDEO);
        }
        //上传视频
        String fileName = file.getOriginalFilename();
        String filePath = address;
        File dest = new File(filePath + fileName);
        //生产url
        String url1 = url+fileName;
        log.info("视频url "+url1);
        //url转图片
        String savePath = address+fileName+".jpg";
        QrCodeEncodeUtil.encode(url1, savePath,300);
        //图片转url
        String ewm = url+fileName+".jpg";
        log.info("二维码url"+savePath);
        file.transferTo(dest);
        log.info("上传成功");
        videoEntity1.setVideoName(file.getOriginalFilename());
        videoEntity1.setVideoUrl(url1);
        videoEntity1.setVideoEwm(ewm);
        videoEntity1.setUploadTime(Time.sqlData());
        videoService.saveVideo(videoEntity1);
        return  result.success(ResultEnum.SUCCESS);

    }

    /**
     * 分页查询所有的视频
     * @param pn
     * @param size
     * @return
     */
    @GetMapping("/allVideo")
    public Result allVideo(@RequestParam(defaultValue = "20") Integer pn,
                           @RequestParam() Integer size){
        System.out.println(pn +"   " + size);
        if (pn<=0 || size ==0){
            return result.error(ResultEnum.ERROR_INQUIRE);
        }else {
            result = videoService.getAllVideo(pn,size);
            System.out.println(result);
            return result;
        }
    }
    @DeleteMapping("/deleteIdVideo")
    public Result deleteVideoId(@RequestParam("id") String id){
        System.out.println(id);
        Integer integer = Integer.valueOf(id);
        Result ok = videoService.deleteVideoId(integer);
        return ok;
    }

}
