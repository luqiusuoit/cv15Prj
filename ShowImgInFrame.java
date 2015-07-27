/*
	ShowImgFrame.java
	
	在窗口内显示图片
*/

import java.awt.image.*;  
import java.io.*;  
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;

public class ShowImgInFrame extends JFrame
{
	static int width;
	static int height;
	static int imgHeight;
	static int imgWidth;
	
	static BufferedImage bufferedImage=null;
	
	public ShowImgInFrame() throws IOException
	{
		width=700;
		height=700;
		setBounds(20,20,width,height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		File img=new File("t.jpg");
		bufferedImage=ImageIO.read(img);
		imgHeight=bufferedImage.getHeight();
		imgWidth=bufferedImage.getWidth();
		
		setTitle("显示图片");
	}

	public void paint(Graphics g) 
	{
		g.drawImage(bufferedImage,0,0,imgWidth,imgHeight,this);
		
	}
	
	
	public static void main(String [] args) throws IOException
	{
		final ShowImgInFrame frame=new ShowImgInFrame();
		File img=new File("t.jpg");
		System.out.println("imgHeight="+imgHeight+" imgWidth="+imgWidth);
		JButton button=new JButton("增大");
		button.addActionListener(new ActionListener()
		{
			 public void actionPerformed(ActionEvent e)
			 {
				imgHeight=imgHeight+imgHeight;
				imgWidth=imgWidth+imgWidth;
				frame.update(frame.getGraphics());
			 }
		});
		
		frame.add(button,BorderLayout.NORTH);
		frame.setVisible(true);
	}
}