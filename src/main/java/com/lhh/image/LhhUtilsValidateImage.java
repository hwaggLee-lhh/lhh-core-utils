package com.lhh.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

/**
 * 图片验证码
 * @author hwaggLee
 * @createDate 2016年11月30日
 */
public class LhhUtilsValidateImage {
    
    private static final String SESSION_NAME = "SESSION_NAME_ValidateImageUtils";

    private static final int width = 60;

    private static final int height = 20;
    
    // 在内存中创建图象
    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    
    // 随机数字
    private String randomNum = "";
    
    public void setRandInSession(HttpServletRequest req) {
        req.getSession().setAttribute(SESSION_NAME, getRandomNum());
    }
    
    public static String getRandFromSession(HttpServletRequest req) {
        return (String) req.getSession().getAttribute(SESSION_NAME);
    }
    
    public static LhhUtilsValidateImage getInstance() {
        LhhUtilsValidateImage validateImageUtils = new LhhUtilsValidateImage();
        validateImageUtils.buidSecurityChart();
        return validateImageUtils;
    }

    private void buidSecurityChart() {
        
        // 获取图形上下文
        Graphics graphics = image.getGraphics();
        // 设定背景色
        graphics.setColor(getRandColor(200, 250));
        graphics.fillRect(0, 0, width, height);
        // 设定字体
        graphics.setFont(new Font("Times New Roman", Font.PLAIN, 18));

        // 产生干扰线
        buildThread(graphics);
        
        // 生成图片
        randomNum = buildRamNum(graphics);
        
        // 图象生效
        graphics.dispose();
    }
    
    public String getRandomNum() {
        return randomNum;
    }
    
    public BufferedImage getImage() {
        return image;
    }

    /**
     * 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
     * @param graphics
     * @return
     */
    private static void buildThread(Graphics graphics) {
        Random random = new Random();
        graphics.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            graphics.drawLine(x, y, x + xl, y + yl);
        }
    }
    
    /**
     * 取随机产生的认证码(4位数字)
     * @param graphics
     * @return
     */
    private static String buildRamNum(Graphics graphics) {
        Random random = new Random();
        StringBuffer buf = new StringBuffer(16);
        for (int i = 0; i < 4; i++) {
        	int charOrNum = random.nextInt(2) % 2;
        	String rand = null;
        	switch (charOrNum) {
			case 0:
				rand = String.valueOf(random.nextInt(10));
				break;
			default:
				rand = (char)(random.nextInt(26)+65)+"";
				break;
			}
            //将认证码改为固定值：1234
//            rand = i+1+"";
            //去掉O和0
        	if("0".equals(rand)) rand = "3";
        	if("O".equals(rand)) rand = "7";
            buf.append(rand);
            // 将认证码显示到图象中
            graphics.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            //调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            graphics.drawString(rand, 13 * i + 6, 16);
        }
        return buf.toString();
    }

    /**
     * 给定范围获得随机颜色
     * @param fc
     * @param bc
     * @return
     */
    private static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
    public static void main(String[] args) {
    	for (int i = 0; i < 100; i++) {
    		LhhUtilsValidateImage.getInstance().getRandomNum();
		}
	}
}
