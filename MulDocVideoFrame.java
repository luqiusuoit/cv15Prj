/*

	15Prj
	
	
	MulDocVideoFrame.java
	ʹ���������ര��
	ʹ�ñ��
	ʹ��JTextArea
	
	û�м���Robot��ͼ����
	û��ʹ��JMF
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

	String [] columnNames={"�������","��¼ʱ��","ͼ������"};
	String [][] cells=new String [30][3];
	JTable table=null;	
	
	
	public MulDocVideoFrame()
	{
		//��ʾUI����
		setTitle("����FPGA�ĸ߶�̬��Χ���ϵͳ");
		setIconImage(new ImageIcon("icon.jpg").getImage());
		
		desktop=new JDesktopPane();
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		add(desktop,BorderLayout.CENTER);
		//ͼ�񴰿�
		inVideoFrame=new JInternalFrame("���ͼ��",true,true,true,true);
		inVideoFrame.reshape(5,3,700,675);
	//	inVideoFrame.setVisible(true);
	//	inVideoFrame.setFrameIcon(new ImageIcon("icon.jpg"));
		desktop.add(inVideoFrame);
		//����������
		inBlackFrame=new JInternalFrame("������",true,true,true,true);
	//	inBlackFrame.add(new Button("��ť��"),BorderLayout.CENTER);
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
		//��ʾ��Ϣ����
		inInfoFrame=new JInternalFrame("��ʾ��Ϣ",true,true,true,true);
		inInfoFrame.add(new Button("��ť��"),BorderLayout.CENTER);
		inInfoFrame.reshape(710,343,645,335);
	//	inInfoFrame.setVisible(true);
		JScrollPane infoScrollPane=new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JTextArea inforTextArea=new JTextArea();//
		inforTextArea.setFont(new Font("΢���ź�",Font.BOLD,15));
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
		JMenu file=new JMenu("�ļ�");
		JMenu edit=new JMenu("�༭");
		JMenu look=new JMenu("�鿴");//
		JMenu huifang=new JMenu("�ط�");//
		JMenu tools=new JMenu("����");
		JMenu help=new JMenu("����");
		
		
		//�ļ�
		JMenuItem startItem=new JMenuItem("��ʼ");
		JMenuItem pauseItem=new JMenuItem("��ͣ");
		JMenuItem exitItem=new JMenuItem("�˳�");
		file.add(startItem);
		file.add(pauseItem);
		file.add(exitItem);
		startItem.addActionListener(cvMenuListener);
		pauseItem.addActionListener(cvMenuListener);
		exitItem.addActionListener(cvMenuListener);
		
		//�༭
		JMenuItem addToBlack=new JMenuItem("���������");
		JMenuItem deleteFromBlack=new JMenuItem("�Ӻ�������ɾ��");
		edit.add(addToBlack);
		edit.add(deleteFromBlack);
		addToBlack.addActionListener(cvMenuListener);
		deleteFromBlack.addActionListener(cvMenuListener);
		
		//�鿴
		JMenuItem shiPinWin=new JMenuItem("��Ƶ����");
		JMenuItem heiDanWin=new JMenuItem("����������");
		JMenuItem xinXiWin=new JMenuItem("��ʾ��Ϣ����");
		look.add(shiPinWin);
		look.add(heiDanWin);
		look.add(xinXiWin);
		shiPinWin.addActionListener(cvMenuListener);
		heiDanWin.addActionListener(cvMenuListener);
		xinXiWin.addActionListener(cvMenuListener);
		
		//�ط�
		JMenuItem qian1min=new JMenuItem("ǰ1min");
		JMenuItem setTime=new JMenuItem("���ûط�ʱ��");
		huifang.add(qian1min);
		huifang.add(setTime);
		qian1min.addActionListener(cvMenuListener);
		setTime.addActionListener(cvMenuListener);
		
		//����
		JMenuItem jietuItem=new JMenuItem("��ͼ");
		tools.add(jietuItem);
		jietuItem.addActionListener(cvMenuListener);
		
		
		//����
		JMenuItem helpItem=new JMenuItem("���ڱ����");
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
			System.err.println("û��splash����");
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
		
		Font infoFont=new Font("����",Font.BOLD,25);
		
		int height=20;
		int x=0;
		int y=bounds.height-height;
		int width=bounds.width-4;
		Color brightGreen=new Color(95,243,0);
		g.setColor(brightGreen);
		g.fillRect(x,y,width*percent/100,height);
		g.setFont(infoFont);
		g.setColor(Color.WHITE);
		g.drawString("ϵͳ���ڼ���......",bounds.width-250,bounds.height-40);
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

