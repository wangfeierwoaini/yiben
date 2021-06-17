package com.yiben_video.utils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Objects;

/**
 * @author 王飞
 * @create 2021/6/15 17:02
 * @emai 936886169@qq.com
 */
public class QrCodeEncodeUtil {
    /**
     * 生成二维码图片
     * @author zql
     * @createTime 2020-11-30 23:27:54
     *
     * @param content 二维码内容
     * @param logoPath logo路径
     * @param savePath 二维码保存路径
     * @param qrCodeSize 二维码图片大小
     * @param logoSize 二维码图片中的logo大小
     * @throws Exception
     */
    public static void encode(String content, String logoPath, String savePath, int qrCodeSize, int logoSize) throws Exception {
        BufferedImage image;
        if (Objects.nonNull(logoPath)) {
            File logoFile = new File(logoPath);
            if (logoFile.exists()) {
                image = QrCodeEncodeUtil.createImage(content, qrCodeSize, logoFile, logoSize);
            } else {
                image = QrCodeEncodeUtil.createImage(content, qrCodeSize);
            }
        } else {
            image = QrCodeEncodeUtil.createImage(content, qrCodeSize);
        }
        QrCodeEncodeUtil.mkdirs(savePath);
        ImageIO.write(image, "JPG", new File(savePath));
    }

    /**
     * 生成二维码图像缓存对象
     * @author zql
     * @createTime 2020-11-30 23:29:16
     *
     * @param content 二维码内容
     * @param logoPath logo路径
     * @param qrCodeSize 二维码图片大小
     * @param logoSize 二维码图片中的logo大小
     * @return BufferedImage
     * @throws Exception
     */
    public static BufferedImage encode(String content, String logoPath, int qrCodeSize, int logoSize) throws Exception {
        BufferedImage image;
        if (Objects.nonNull(logoPath)) {
            File logoFile = new File(logoPath);
            if (logoFile.exists()) {
                image = QrCodeEncodeUtil.createImage(content, qrCodeSize, logoFile, logoSize);
            } else {
                image = QrCodeEncodeUtil.createImage(content, qrCodeSize);
            }
        } else {
            image = QrCodeEncodeUtil.createImage(content, qrCodeSize);
        }
        return image;
    }

    /**
     * 生成二维码图片
     * @author zql
     * @createTime 2020-11-30 23:30:06
     *
     * @param content 二维码内容
     * @param savePath 二维码保存路径
     * @param qrCodeSize 二维码图片大小
     * @throws Exception
     */
    public static void encode(String content, String savePath, int qrCodeSize) throws Exception {
        QrCodeEncodeUtil.encode(content, null, savePath, qrCodeSize, 0);
    }

    /**
     * 生成二维码图像缓存对象
     * @author zql
     * @createTime 2020-11-30 23:30:27
     *
     * @param content 二维码内容
     * @param qrCodeSize 二维码图片大小
     * @return BufferedImage
     * @throws Exception
     */
    public static BufferedImage encode(String content, int qrCodeSize) throws Exception {
        return QrCodeEncodeUtil.encode(content, null, qrCodeSize, 0);
    }

    /**
     * 将BufferedImage转换为InputStream
     * @author zql
     * @createTime 2021-01-02 11:39:57
     *
     * @param image
     * @param imgType 图片类型
     * @return InputStream
     * @throws IOException
     */
    public static InputStream bufferedImageToInputStream(BufferedImage image, String imgType) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, imgType, os);
            InputStream input = new ByteArrayInputStream(os.toByteArray());
            return input;
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 创建有logo的二维码图像缓存对象
     * @author zql
     * @createTime 2020-11-30 23:25:30
     *
     * @param content 二维码内容
     * @param qrCodeSize 二维码图片大小
     * @param logoFile logo文件对象
     * @param logoSize logo在二维码中的大小
     * @return BufferedImage
     * @throws Exception
     */
    private static BufferedImage createImage(String content, int qrCodeSize, File logoFile, int logoSize) throws Exception {
        // 二维码图片大小不能小于logo图片大小的3倍，否则生成二维码会出现异常，导致二维码无效
        if (qrCodeSize < logoSize * 3) {
            logoSize = qrCodeSize / 3;
        }
        Hashtable<EncodeHintType, Object> ht = new Hashtable<EncodeHintType, Object>();
        ht.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        ht.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 二维码的边界，0为无边界
        ht.put(EncodeHintType.MARGIN, 0);
        // UTF-8编码长度不能超过1273个字节，全是数字字符的话不超过3057
        // 因汉字占了3个字节，所以最多能有424个汉字
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, ht);
        BufferedImage image = new BufferedImage(qrCodeSize, qrCodeSize, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < qrCodeSize; x++) {
            // 如果想要的二维码不是正方形，这里的qrCodeSize请使用二维码图像的高度
            for (int y = 0; y < qrCodeSize; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        // 插入logo
        QrCodeEncodeUtil.insertImage(image, logoFile, logoSize);
        return image;
    }

    /**
     * 往二维码图片插入logo
     * @author zql
     * @createTime 2020-11-30 23:26:12
     *
     * @param source 二维码图像缓存对象
     * @param file logo的文件对象
     * @param logoSize logo的尺寸
     * @throws Exception
     */
    private static void insertImage(BufferedImage source, File file, int logoSize) throws Exception {
        Image src = ImageIO.read(file);
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        // 对logo图片进行压缩
        width = width > logoSize ? logoSize : width;
        height = height > logoSize ? logoSize : height;
        Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        // 绘制缩小后的图
        g.drawImage(image, 0, 0, null);
        g.dispose();
        src = image;
        // 因为得到的二维码是正方形，因此只需要获取宽度或高度即可
        int qrCodeSize = source.getWidth();
        // 中间插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (qrCodeSize - logoSize) / 2;
        int y = (qrCodeSize - logoSize) / 2;
        graph.drawImage(src, x, y, logoSize, logoSize, null);
        /*
         * @param x 新构造的圆矩形的x坐标
         * @param y 新构造的圆矩形的y坐标
         * @param w 新构造的圆矩形的宽度
         * @param h 新构造的圆矩形的高度
         * @param arcw 圆弧的宽度，用来四舍五入新建的圆矩形的角
         * @param arch 圆弧的高度，用来四舍五入新建的圆矩形的角
         * java.awt.geom.RoundRectangle2D.Float.Float(float x, float y, float w, float h, float arcw, float arch)
         */
        // 外边画个圆距形，让logo不显得那么生硬
        Shape shape = new RoundRectangle2D.Float(x, y, logoSize, logoSize, logoSize / 10, logoSize / 10);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 创建二维码图像缓存对象
     * @author zql
     * @createTime 2020-11-30 23:26:58
     *
     * @param content 二维码内容
     * @param qrCodeSize 二维码图片大小
     * @return
     * @throws Exception
     */
    private static BufferedImage createImage(String content, int qrCodeSize) throws Exception {
        Hashtable<EncodeHintType, Object> ht = new Hashtable<EncodeHintType, Object>();
        ht.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        ht.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        ht.put(EncodeHintType.MARGIN, 0);
        // UTF-8编码长度不能超过1273个字节，全是数字字符的话不超过3057
        // 因汉字占了3个字节，所以最多能有424个汉字
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, ht);
        BufferedImage image = new BufferedImage(qrCodeSize, qrCodeSize, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < qrCodeSize; x++) {
            for (int y = 0; y < qrCodeSize; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }

    /**
     * 保存二维码图片自动创建不存在的文件夹
     * @author zql
     * @createTime 2020-11-30 23:27:29
     *
     * @param savePath
     */
    private static void mkdirs(String savePath) {
        File file = new File(savePath);
        // 当文件夹不存在时，自动创建多层目录
        if (!file.exists() && !file.isDirectory()) {
            // mkdirs和mkdir的区别在于mkdir如果父目录不存在则会抛出异常
            file.mkdirs();
        }
    }
}
