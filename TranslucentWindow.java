/*
	创建透明窗口

*/
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

public class TranslucentWindow extends JFrame {
    public TranslucentWindow() {
        super("透明窗体");
        this.setLayout(new FlowLayout());
        this.add(new JButton("按钮"));
        this.add(new JCheckBox("复选按钮"));
        this.add(new JRadioButton("单选按钮"));
        this.add(new JProgressBar(20, 100));
        this.setSize(new Dimension(400, 300));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Window w = new TranslucentWindow();
                com.sun.awt.AWTUtilities.setWindowOpacity(w, 0.8f);
                w.setVisible(true);
            }
        });
    }
}