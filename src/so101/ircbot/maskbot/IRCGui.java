package so101.ircbot.maskbot;

import java.awt.Frame;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import java.awt.Dimension;

public class IRCGui 
{
	JTextPane a;
	Window w;
	private JTextField textField;
	
	/**WIP Class. So bot console isn't totally invisible. Currently does nothing.*/
	public IRCGui()
	{
		JFrame frame = new JFrame(IRCBot.getNick() + ": Espernet IRC Bot");
		Window window = new Window(frame);
		frame.getContentPane().add(window, BorderLayout.NORTH);
		window.setSize(600, 400);
		
		w = window;
		
		JTextPane field = new JTextPane();
		field.setSize(window.getSize());
		JScrollPane areaScrollPane = new JScrollPane(field);
		frame.getContentPane().add(areaScrollPane, BorderLayout.NORTH);
		a = field;
		
		JSplitPane splitPane = new JSplitPane();
		frame.getContentPane().add(splitPane, BorderLayout.SOUTH);
		
		textField = new JTextField();
		splitPane.setLeftComponent(textField);
		textField.setColumns(10);
		
		JButton btnExecute = new JButton("Execute");
		btnExecute.setMinimumSize(new Dimension(36, 23));
		btnExecute.setMaximumSize(new Dimension(36, 23));
		splitPane.setRightComponent(btnExecute);
		w.setVisible(true);
		
	}
	
	public void updateConsole()
	{
		a.setText(System.out.toString());
	}
	
	
}
