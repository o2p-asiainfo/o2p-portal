package com.ailk.o2p.portal.utils;

 

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.SecureRandom;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asiainfo.foundation.log.Logger;

public class AuthImg extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private Font mFont = new Font("Arial Black", Font.PLAIN, 16);
    Color getRandColor(int fc,int bc)
    {
    	SecureRandom random = new SecureRandom();
        if(fc>255) fc=255;
        if(bc>255) bc=255;
        int r=fc+random.nextInt(bc-fc);
        int g=fc+random.nextInt(bc-fc);
        int b=fc+random.nextInt(bc-fc);
        return new Color(r,g,b);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        
        int width=75, height=18;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        Graphics g = image.getGraphics();
        SecureRandom random = new SecureRandom();
        g.setColor(getRandColor(200,250));
        g.fillRect(1, 1, width-1, height-1);
        g.setColor(new Color(102,102,102));
        g.drawRect(0, 0, width-1, height-1);
        g.setFont(mFont);

        g.setColor(getRandColor(160,200));
        for (int i=0;i<155;i++)
        {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g.drawLine(x,y,x + xl,y + yl);
        }
        for (int i = 0;i < 70;i++)
        {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int xl = random.nextInt(12) + 1;
            int yl = random.nextInt(6) + 1;
            g.drawLine(x,y,x - xl,y - yl);
        }

        StringBuilder sRand=new StringBuilder();
        String tmp =null;
        for (int i=0;i<4;i++)
        {
			tmp = getRandomChar();
            sRand.append(tmp);
            g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
	        g.drawString(tmp,15*i+10,15);
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("rand",sRand.toString());
        g.dispose();
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }
    private String getRandomChar()
    {
    	SecureRandom random = new SecureRandom();
//		int rand = (int)Math.round(random.nextDouble() * 2);
		long itmp = 0;
//		char ctmp = '\u0000';
				itmp = Math.round(random.nextDouble() * 9);
				return String.valueOf(itmp);
//		}
    }
}
