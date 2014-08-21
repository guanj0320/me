package pf.tools;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by guanj on 2014/5/4.
 */
public class ImageUtil {
    private String destFile;
    private int width;
    private int height;
    private Image img;
    private String suffixName;

    public ImageUtil(String srcFile, String destFile) throws IOException {
        File file = new File(srcFile); // 读入文件
        this.destFile = destFile;
        suffixName = getExtension(destFile);
        img = ImageIO.read(file); // 构造Image对象
        width = img.getWidth(null); // 得到源图宽
        height = img.getHeight(null); // 得到源图长
    }
    public ImageUtil(InputStream input, String destFile) throws IOException {
        img = ImageIO.read(input);
        this.destFile = destFile;
        suffixName = getExtension(destFile);
        width = img.getWidth(null); // 得到源图宽
        height = img.getHeight(null); // 得到源图长
    }
    public ImageUtil(File file, String destFile) throws IOException {
        img = ImageIO.read(file);
        this.destFile = destFile;
        suffixName = getExtension(destFile);
        width = img.getWidth(null); // 得到源图宽
        height = img.getHeight(null); // 得到源图长
    }

    /**
     * 按给定的宽度与高度生成图像
     * @param w int 新宽度
     * @param h int 新高度
     * @throws IOException
     */
    public void resize(int w, int h) throws IOException {
        FileOutputStream outputStream = null;
        try {
            BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

//            newImage.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图

            newImage.getGraphics().drawImage(img.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, Color.WHITE, null);

            outputStream = new FileOutputStream(destFile); // 输出到文件流
            /*
             * JPEGImageEncoder 将图像缓冲数据编码为 JPEG 数据流。该接口的用户应在 Raster 或
             * BufferedImage 中提供图像数据，在 JPEGEncodeParams 对象中设置必要的参数， 并成功地打开
             * OutputStream（编码 JPEG 流的目的流）。JPEGImageEncoder 接口可 将图像数据编码为互换的缩略
             * JPEG 数据流，该数据流将写入提供给编码器的 OutputStream 中。
             * 注意：com.sun.image.codec.jpeg 包中的类并不属于核心 Java API。它们属于 Sun 发布的 JDK
             * 和 JRE 产品的组成部分。虽然其它获得许可方可能选择发布这些类，但开发人员不能寄 希望于从非 Sun
             * 实现的软件中得到它们。我们期望相同的功能最终可以在核心 API 或标准扩 展中得到。
             */
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimageout);
//            encoder.encode(newImage); // 近JPEG编码

            ImageIO.write(newImage,suffixName,outputStream);

            newImage = null;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            outputStream.close();
        }
    }

    /**
     * 按照固定的比例缩放图片
     *
     * @param t
     *            double 比例
     * @throws IOException
     */
    public void resize(double t) throws IOException {
        int w = (int) (width * t);
        int h = (int) (height * t);
        resize(w, h);
    }

    /**
     * 以宽度为基准，等比例放缩图片
     *
     * @param w
     *            int 新宽度
     * @throws IOException
     */
    public void resizeByWidth(int w) throws IOException {
        int h = (int) (height * w / width);
        resize(w, h);
    }

    /**
     * 以高度为基准，等比例缩放图片
     *
     * @param h
     *            int 新高度
     * @throws IOException
     */
    public void resizeByHeight(int h) throws IOException {
        int w = (int) (width * h / height);
        resize(w, h);
    }

    /**
     * 按照最大高度限制，生成最大的等比例缩略图
     *
     * @param w
     *            int 最大宽度
     * @param h
     *            int 最大高度
     * @throws IOException
     */
    public void resizeFix(int w, int h) throws IOException {
        if (width / height > w / h) {
            resizeByWidth(w);
        } else {
            resizeByHeight(h);
        }
    }

    /**
     * 返回文件的文件后缀名
     *
     * @param fileName
     * @return
     */
    public String getExtension(String fileName) {
        try {
            return fileName.split("\\.")[fileName.split("\\.").length - 1];
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 设置目标文件名 setDestFile
     *
     * @param fileName
     *            String 文件名字符串
     */
    public void setDestFile(String fileName) throws Exception {
//        if (!fileName.endsWith(".jpg")) {
//            throw new Exception("Dest File Must end with .jpg");
//        }
        destFile = fileName;
    }

    /**
     * 获取目标文件名 getDestFile
     */
    public String getDestFile() {
        return destFile;
    }

    /**
     * 获取图片原始宽度 getSrcWidth
     */
    public int getSrcWidth() {
        return width;
    }

    /**
     * 获取图片原始高度 getSrcHeight
     */
    public int getSrcHeight() {
        return height;
    }

    /**
     * 获取目标文件后缀名
     * @return
     */
    public String getSuffixName() {
        return suffixName;
    }

    public void setSuffixName(String suffixName) {
        this.suffixName = suffixName;
    }

    /**
     * 将图片裁剪为特定尺寸并上传
     * @param w 宽度
     * @param h 高度
     * @throws IOException
     */
    public void cutImage(int w, int h) throws IOException {
        int newWidth = 0, newHeight = 0;
        int x = 0, y = 0;
        double scale_w = (double) w / width;
        double scale_h = (double) h / height;
        System.out.println("scale_w=" + scale_w + ",scale_h=" + scale_h);

        // 按原比例缩放图片
        if (scale_w < scale_h) {
            newHeight = height;
            newWidth = (int) (width * scale_h);
            x = (newWidth - width) / 2;
        } else {
            newHeight = (int) (height * scale_w);
            newWidth = w;
            y = (newHeight - h) / 2;
        }

        // BufferedImage.TYPE_INT_ARGB这个是将背景设为透明
        BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        newImage.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight,Image.SCALE_SMOOTH), 0, 0, Color.WHITE, null);

        FileOutputStream outputStream = new FileOutputStream(destFile); // 输出到文件流

        ImageIO.write(newImage.getSubimage(x, y, w, h),suffixName,outputStream);

        outputStream.close();
        newImage = null;
    }

    private static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        image = new ImageIcon(image).getImage();
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.OPAQUE;
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(image.getWidth(null),
                    image.getHeight(null), transparency);
        } catch (HeadlessException e) {
        }
        if (bimage == null) {
            int type = BufferedImage.TYPE_INT_ARGB;
            bimage = new BufferedImage(image.getWidth(null),
                    image.getHeight(null), type);
        }
        Graphics g = bimage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;
    }
}
