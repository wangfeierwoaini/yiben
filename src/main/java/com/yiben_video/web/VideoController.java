package com.yiben_video.web;
import com.yiben_video.common.Result;
import com.yiben_video.common.ResultEnum;
import com.yiben_video.pojo.VideoEntity;
import com.yiben_video.utils.QrCodeEncodeUtil;
import com.yiben_video.utils.Time;
import com.yiben_video.utils.UUIDUtil;
import com.yiben_video.utils.xgjUtils;
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
    @Value("${video.address}")
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
     * @return
     */
    @PostMapping("/saveVideo")
    public Result saveVideo(
            @RequestParam("file") MultipartFile file
    ) throws Exception {
        VideoEntity videoEntity1 = new VideoEntity();
        File addres = new File(address);
        //判断上级目录是否是目录
        if (addres.getParentFile().isDirectory()){
            if (!addres.exists()){
                addres.mkdirs();//创建文件目录
                System.out.println("创建目录");
            }
        }
        if (file.isEmpty()) {
            return result.error(ResultEnum.ERROR_SAVEVIDEO);
        }else {
            String uuid = UUIDUtil.getUUID();
            //上传视频
            String filePath = address;
            File dest = new File(filePath + uuid+".mp4");
            //生产url
            String videoUUIDName = uuid;
            String url1 = url+videoUUIDName+".mp4";
            log.info("视频url "+url1);
            //url转图片
            String savePath = address+videoUUIDName+".jpg";
            QrCodeEncodeUtil.encode(url1, savePath,300);
            //图片转url
            String ewm = url+videoUUIDName+".jpg";
            log.info("二维码url"+savePath);
            file.transferTo(dest);
            log.info("上传成功");
            String videoName = file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf(".mp4"));
            videoEntity1.setVideoName(videoName);
            videoEntity1.setVideoUrl(url1);
            videoEntity1.setVideoEwm(ewm);
            videoEntity1.setUploadTime(Time.sqlData());
            videoService.saveVideo(videoEntity1);
            return  result.success(ResultEnum.SUCCESS);
        }
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
        VideoEntity videoEntity = videoService.FindOneVideo(integer);
        System.out.println(videoEntity.toString());
        //String MP4 = (address+(videoEntity.getVideoUrl().substring(32,videoEntity.getVideoUrl().length())));

        String MP4 = (address+(xgjUtils.zfc(videoEntity.getVideoUrl())));
        System.out.println("   MP4路径"+MP4);
        File file = new File(MP4);
        file.delete();
        /*String ewmname = videoEntity.getVideoEwm();
        String e = ewmname.substring(32,ewmname.length());*/
        String ewm = (address+(xgjUtils.zfc(videoEntity.getVideoEwm())));
        log.warn("字符串："+ewm);
        //String ewm = address+e;
        File ewmfile = new File(ewm);
        ewmfile.delete();
        Result ok = videoService.deleteVideoId(integer);
        return ok;
    }

}
