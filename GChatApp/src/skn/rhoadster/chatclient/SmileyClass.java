package skn.rhoadster.chatclient;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class SmileyClass 
{
	protected static ImageIcon SMILE_IMG=createSmilingEmoticon();
	protected static ImageIcon FROWN_IMG=createFrowningEmoticon();
	protected static ImageIcon LAUGH_IMG=createLaughingEmoticon();
	protected static ImageIcon TONGUE_IMG=createTongueEmoticon();
	protected static ImageIcon GOGGLES_IMG=createGogglesEmoticon();
	protected static ImageIcon BIGEYES_IMG = createBigEyedEmoticon();
	
	static ImageIcon createSmilingEmoticon() 
	{
	    BufferedImage res=new BufferedImage(17, 17, BufferedImage.TYPE_INT_ARGB);
	    Graphics g=res.getGraphics();
	    ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g.setColor(Color.yellow);
	    g.fillOval(0,0,16,16);
	
	    g.setColor(Color.black);
	    g.drawOval(0,0,16,16);
	
	    g.drawLine(4,5, 6,5);
	    g.drawLine(4,6, 6,6);
	
	    g.drawLine(11,5, 9,5);
	    g.drawLine(11,6, 9,6);
	
	    g.drawLine(4,10, 8,12);
	    g.drawLine(8,12, 12,10);
	    g.dispose();
	
	    return new ImageIcon(res);
	}
	
	static ImageIcon createFrowningEmoticon() 
	{
	    BufferedImage res=new BufferedImage(17, 17, BufferedImage.TYPE_INT_ARGB);
	    Graphics g=res.getGraphics();
	    ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g.setColor(Color.yellow);
	    g.fillOval(0,0,16,16);
	
	    g.setColor(Color.black);
	    g.drawOval(0,0,16,16);
	
	    g.drawLine(4,5, 6,5);
	    g.drawLine(4,6, 6,6);
	
	    g.drawLine(11,5, 9,5);
	    g.drawLine(11,6, 9,6);
	
	    g.drawLine(4,12, 8,10);
	    g.drawLine(8,10, 12,12);
	    g.dispose();
	
	    return new ImageIcon(res);
	}
	
	static ImageIcon createLaughingEmoticon()
	{
		 BufferedImage res=new BufferedImage(17, 17, BufferedImage.TYPE_INT_ARGB);
	     Graphics g=res.getGraphics();
	     ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	     g.setColor(Color.yellow);
	     g.fillOval(0,0,16,16);
	
	     g.setColor(Color.black);
	     g.drawOval(0,0,16,16);
	
	     g.drawLine(4,5, 6,5);
	     g.drawLine(4,6, 6,6);
	
	     g.drawLine(11,5, 9,5);
	     g.drawLine(11,6, 9,6);
	
	     g.drawLine(4,10, 8,14);
	     g.drawLine(8,14, 12,10);
	     g.drawLine(4, 10, 12, 10);
	    
	     g.dispose();
	
	     return new ImageIcon(res);
	}
	
	static ImageIcon createTongueEmoticon()
	{
		 BufferedImage res=new BufferedImage(17, 17, BufferedImage.TYPE_INT_ARGB);
	     Graphics g=res.getGraphics();
	     ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	     g.setColor(Color.yellow);
	     g.fillOval(0,0,16,16);
	
	     g.setColor(Color.black);
	     g.drawOval(0,0,16,16);
	
	     g.drawLine(4,5, 6,5);
	     g.drawLine(4,6, 6,6);
	
	     g.drawLine(11,5, 9,5);
	     g.drawLine(11,6, 9,6);
	
	     
	     g.setColor(Color.red);
	     g.fillRect(8, 10, 4, 4);
	    
	     g.setColor(Color.black);         
	     g.drawLine(4, 10, 12, 10);
	     g.drawLine(8, 10, 8, 12);
	     g.drawLine(8, 12, 10, 14);
	     g.drawLine(10, 14, 12, 12);         
	     g.drawLine(12, 10, 12, 12);
	     
	     g.dispose();
	
	     return new ImageIcon(res);
	}
	
	static ImageIcon createGogglesEmoticon() 
	{
	    BufferedImage res=new BufferedImage(17, 17, BufferedImage.TYPE_INT_ARGB);
	    Graphics g=res.getGraphics();
	    ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g.setColor(Color.yellow);
	    g.fillOval(0,0,16,16);
	
	    g.setColor(Color.black);
	    g.drawOval(0,0,16,16);
	    g.fillRect(1, 3, 13, 2);
	    g.fillOval(3, 3,5, 5);
	    g.fillOval(9, 3, 5, 5);
	    g.drawLine(4,10, 8,12);
	    g.drawLine(8,12, 12,10);
	    g.dispose();
	
	    return new ImageIcon(res);
	}
	
	static ImageIcon createBigEyedEmoticon() 
	{
	    BufferedImage res=new BufferedImage(17, 17, BufferedImage.TYPE_INT_ARGB);
	    Graphics g=res.getGraphics();
	    ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g.setColor(Color.yellow);
	    g.fillOval(0,0,16,16);
	
	    g.setColor(Color.white);
	    g.fillOval(3, 3,5, 5);
	    g.fillOval(9, 3, 5, 5);
	    
	    g.setColor(Color.black);
	    g.drawOval(0,0,16,16);        
	    g.drawOval(3, 3,5, 5);
	    g.drawOval(9, 3, 5, 5);
	    g.fillOval(5, 5, 2, 2);
	    g.fillOval(11, 5, 2, 2);
	    
	    g.drawLine(4,10, 8,12);
	    g.drawLine(8,12, 12,10);
	    g.dispose();
	
	    return new ImageIcon(res);
	}

}
