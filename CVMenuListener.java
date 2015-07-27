/*

这个类用来监听所有的菜单项，

进行下一步的处理

*/

import java.awt.event.*;

public class CVMenuListener implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		String cmd=e.getActionCommand();
		System.out.println(cmd);
		
		if(cmd.equals("退出"))
		{
			System.exit(0);
		}
	}
}