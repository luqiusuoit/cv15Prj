/*

	15Prj
	
	
	MulDocVideoFrame.java
	使用闪屏、多窗口
	使用表格
	使用JTextArea
	
	没有加上Robot截图对象
	没有使用JMF
*/

import java.awt.image.*;  
import java.io.*;  
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.beans.*;
import javax.swing.table.*;

public class MulDocVideoFrame extends JFrame  
{
	private static JDesktopPane desktop;
	private static JInternalFrame inVideoFrame;
	private static JInternalFrame inBlackFrame;
	private static JInternalFrame inInfoFrame;
	
	Dimension screenSize=null;
	private static SplashScreen splash=null;
	CVMenuListener cvMenuListener=null;
	
    private Component com;  
    private Panel panel;  

	String [] columnNames={"名单编号","记录时间","图像名称"};
	String [][] cells=new String [30][3];
	JTable table=null;	
	
	
	public MulDocVideoFrame()
	{
		//显示UI部分
		setTitle("基于FPGA的高动态范围监控系统");
		setIconImage(new ImageIcon("icon.jpg").getImage());
		
		desktop=new JDesktopPane();
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		add(desktop,BorderLayout.CENTER);
		//图像窗口
		inVideoFrame=new JInternalFrame("监控图像",true,true,true,true);
		inVideoFrame.reshape(5,3,700,675);
	//	inVideoFrame.setVisible(true);
	//	inVideoFrame.setFrameIcon(new ImageIcon("icon.jpg"));
		desktop.add(inVideoFrame);
		//黑名单窗口
		inBlackFrame=new JInternalFrame("黑名单",true,true,true,true);
	//	inBlackFrame.add(new Button("按钮二"),BorderLayout.CENTER);
		inBlackFrame.reshape(710,3,645,335);
	//	inBlackFrame.setVisible(true);
		table=new JTable(cells,columnNames);
		table.setRowHeight(25);
		
		DefaultTableCellRenderer   r   =   new   DefaultTableCellRenderer();   
		r.setHorizontalAlignment(JLabel.CENTER);   
		table.setDefaultRenderer(Object.class,r);
		
		JScrollPane blackScrollPane=new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
		blackScrollPane.setViewportView(table);
		inBlackFrame.add(blackScrollPane);
		desktop.add(inBlackFrame);
		//提示信息窗口
		inInfoFrame=new JInternalFrame("提示信息",true,true,true,true);
		inInfoFrame.add(new Button("按钮三"),BorderLayout.CENTER);
		inInfoFrame.reshape(710,343,645,335);
	//	inInfoFrame.setVisible(true);
		JScrollPane infoScrollPane=new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JTextArea inforTextArea=new JTextArea();//
		inforTextArea.setFont(new Font("微软雅黑",Font.BOLD,15));
		inforTextArea.setLineWrap(true);
		infoScrollPane.setViewportView(inforTextArea);
		inInfoFrame.add(infoScrollPane);
		desktop.add(inInfoFrame);
		
		screenSize =Toolkit.getDefaultToolkit().getScreenSize(); //1366,768
		setBounds(0,0,screenSize.width,screenSize.height-30);
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		CVMenuListener cvMenuListener=new CVMenuListener();
		JMenuBar mb=new JMenuBar();
		JMenu file=new JMenu("文件");
		JMenu edit=new JMenu("编辑");
		JMenu look=new JMenu("查看");//
		JMenu huifang=new JMenu("回放");//
		JMenu tools=new JMenu("工具");
		JMenu help=new JMenu("帮助");
		
		
		//文件
		JMenuItem startItem=new JMenuItem("开始");
		JMenuItem pauseItem=new JMenuItem("暂停");
		JMenuItem exitItem=new JMenuItem("退出");
		file.add(startItem);
		file.add(pauseItem);
		file.add(exitItem);
		startItem.addActionListener(cvMenuListener);
		pauseItem.addActionListener(cvMenuListener);
		exitItem.addActionListener(cvMenuListener);
		
		//编辑
		JMenuItem addToBlack=new JMenuItem("加入黑名单");
		JMenuItem deleteFromBlack=new JMenuItem("从黑名单中删除");
		edit.add(addToBlack);
		edit.add(deleteFromBlack);
		addToBlack.addActionListener(cvMenuListener);
		deleteFromBlack.addActionListener(cvMenuListener);
		
		//查看
		JMenuItem shiPinWin=new JMenuItem("视频窗口");
		JMenuItem heiDanWin=new JMenuItem("黑名单窗口");
		JMenuItem xinXiWin=new JMenuItem("提示信息窗口");
		look.add(shiPinWin);
		look.add(heiDanWin);
		look.add(xinXiWin);
		shiPinWin.addActionListener(cvMenuListener);
		heiDanWin.addActionListener(cvMenuListener);
		xinXiWin.addActionListener(cvMenuListener);
		
		//回放
		JMenuItem qian1min=new JMenuItem("前1min");
		JMenuItem setTime=new JMenuItem("设置回放时间");
		huifang.add(qian1min);
		huifang.add(setTime);
		qian1min.addActionListener(cvMenuListener);
		setTime.addActionListener(cvMenuListener);
		
		//工具
		JMenuItem jietuItem=new JMenuItem("截图");
		tools.add(jietuItem);
		jietuItem.addActionListener(cvMenuListener);
		
		
		//帮助
		JMenuItem helpItem=new JMenuItem("关于本软件");
		help.add(helpItem);
		helpItem.addActionListener(cvMenuListener);
		
		mb.add(file);
		mb.add(edit);
		mb.add(look);
		mb.add(huifang);
		mb.add(tools);
		mb.add(help);
		
		setJMenuBar(mb);
		
		inVideoFrame.setVisible(true);
		inBlackFrame.setVisible(true);
		inInfoFrame.setVisible(true);
		
	}
		
	public static void init()
	{
		splash=SplashScreen.getSplashScreen();
		if(splash==null)
		{
			System.err.println("没有splash对象");
			System.exit(1);
		}
		try
		{
			for(int i=0;i<=101;++i)
			{
				drawOnSplash(i);
				Thread.sleep(50);
			}
		}
		catch(InterruptedException e)
		{
			System.err.println("InterruptedException");
		}
	}
	
	
	public static void drawOnSplash(int percent)
	{
		Rectangle bounds=splash.getBounds();
		Graphics2D g=splash.createGraphics();
		
		Font infoFont=new Font("宋体",Font.BOLD,25);
		
		int height=20;
		int x=0;
		int y=bounds.height-height;
		int width=bounds.width-4;
		Color brightGreen=new Color(95,243,0);
		g.setColor(brightGreen);
		g.fillRect(x,y,width*percent/100,height);
		g.setFont(infoFont);
		g.setColor(Color.WHITE);
		g.drawString("系统正在加载......",bounds.width-250,bounds.height-40);
		splash.update();
	}
	
	
	
	public static void main(String [] args) throws AWTException,IOException,InterruptedException
	{	
		int serialNum=0;
		//Robot screenShotRobot=new Robot();
		BufferedImage screenShort=null;
		
		init();
	
		MulDocVideoFrame mulDocVideoFrame=new MulDocVideoFrame();
		
		mulDocVideoFrame.inVideoFrame.setVisible(true);
		mulDocVideoFrame.inBlackFrame.setVisible(true);
		mulDocVideoFrame.inInfoFrame.setVisible(true);
		mulDocVideoFrame.setVisible(true); 
		/*
		for(int i=0;i<1000;++i)
		{
			screenShort=screenShotRobot.createScreenCapture(new Rectangle(5,3,700,675));
			ImageIO.write(screenShort,"jpeg",new File(String.valueOf(serialNum)+".jpeg"));
			serialNum++;
			System.out.println(serialNum);
			Thread.sleep(5);
		}
		*/
	}
}

