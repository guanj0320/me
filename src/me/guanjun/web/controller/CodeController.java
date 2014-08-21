package me.guanjun.web.controller;

import me.guanjun.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by guanj0320 on 14-5-9.
 */
@Controller
public class CodeController extends BaseController {

    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public void code(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        int width = 80, height=24;
        // create the text for the image
        List<String> capText = createText();
        // store the text in the session
        if(this.getSession().getAttribute("code")!=null) this.getSession().removeAttribute("code");
        this.getSession().setAttribute("code",capText.get(1));
        this.getSession().setMaxInactiveInterval(900);

        // 禁止图像缓存。
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/jpeg");

        // create the image with the text
        BufferedImage bi = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics gd = bi.getGraphics();
        // 创建一个随机数生成器类
        Random random = new Random();
        // 将图像填充为白色
        gd.setColor(Color.WHITE);
        gd.fillRect(0, 0, width, height);

        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("Fixedsys", Font.BOLD, 19);
        // 设置字体。
        gd.setFont(font);

        // 画边框。
        gd.setColor(Color.BLACK);
        gd.drawRect(0, 0, width - 1, height - 1);

        // 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
//        gd.setColor(Color.BLACK);
//        for (int i = 0; i < 40; i++) {
//            int x = random.nextInt(width);
//            int y = random.nextInt(height);
//            int xl = random.nextInt(12);
//            int yl = random.nextInt(12);
//            gd.drawLine(x, y, x + xl, y + yl);
//        }

        gd.drawString(capText.get(0),15,16);

        ServletOutputStream out = resp.getOutputStream();

        // write the data out
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    @RequestMapping(value = "/admin/code", method = RequestMethod.GET)
    public void adminCode(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        int width = 80, height=24;
        // create the text for the image
        List<String> capText = createText();
        // store the text in the session
        if(this.getSession().getAttribute("adminCode")!=null) this.getSession().removeAttribute("adminCode");
        this.getSession().setAttribute("adminCode",capText.get(1));
        this.getSession().setMaxInactiveInterval(900);

        // 禁止图像缓存。
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/jpeg");

        // create the image with the text
        BufferedImage bi = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics gd = bi.getGraphics();
        // 创建一个随机数生成器类
        Random random = new Random();
        // 将图像填充为白色
        gd.setColor(Color.WHITE);
        gd.fillRect(0, 0, width, height);

        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("Fixedsys", Font.BOLD, 19);
        // 设置字体。
        gd.setFont(font);

        // 画边框。
        gd.setColor(Color.BLACK);
        gd.drawRect(0, 0, width - 1, height - 1);

        // 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
//        gd.setColor(Color.BLACK);
//        for (int i = 0; i < 40; i++) {
//            int x = random.nextInt(width);
//            int y = random.nextInt(height);
//            int xl = random.nextInt(12);
//            int yl = random.nextInt(12);
//            gd.drawLine(x, y, x + xl, y + yl);
//        }

        gd.drawString(capText.get(0),15,16);

        ServletOutputStream out = resp.getOutputStream();

        // write the data out
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }
    private List<String> createText() {
        int intFirst, intSec, intTemp, validCodeResult;
        String validCode = null;
        Random rand = new Random();
        intFirst = (int) (Math.random() * 10);
        intSec = (int) (Math.random() * 10);
        switch (rand.nextInt(3)) {
            case 0:
                if (intFirst < intSec) {
                    intTemp = intFirst;
                    intFirst = intSec;
                    intSec = intTemp;
                }
                validCode = intFirst + "-" + intSec + "=";
                validCodeResult = intFirst - intSec;
                break;
            case 1:
                validCode = intFirst + "+" + intSec + "=";
                validCodeResult = intFirst + intSec;
                break;
            default:
                validCode = intFirst + "*" + intSec + "=";
                validCodeResult = intFirst * intSec;
                break;
        }
        List<String> list = new ArrayList<String>();
        list.add(validCode);
        list.add(String.valueOf(validCodeResult));
        return list;
    }
}
