package com.yiben_video.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 王飞
 * @create 2021/6/15 17:03
 * @emai 936886169@qq.com
 */
class QrCodeEncodeUtilTest {

    @Test
    public void encode() throws Exception {
        // 存放在二维码中的内容
        String text = "http://192.168.31.117:7879/file/01汽车灯光的检查.mp4";
        String savePath = "D:\\image\\test2.jpg";
        // 生成二维码
        QrCodeEncodeUtil.encode(text, savePath,300);
    }

    @Test
    void testEncode() {
    }

    @Test
    void testEncode1() {
    }

    @Test
    void testEncode2() {
    }

    @Test
    void bufferedImageToInputStream() {
    }
}