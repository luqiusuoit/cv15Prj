/*

����������������еĲ˵��

������һ���Ĵ���

*/

import java.awt.event.*;

public class CVMenuListener implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		String cmd=e.getActionCommand();
		System.out.println(cmd);
		
		if(cmd.equals("�˳�"))
		{
			System.exit(0);
		}
	}
}