package so101.ircbot.maskbot;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.text.DefaultCaret;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



public class TestWindow {

	public JFrame frame;
	public JTextArea textArea = null;

	/**
	 * Create the application.
	 */
	public TestWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
		    	  int i = JOptionPane.showConfirmDialog(frame, "Quit EsperNet IRCBot? IRC \nconnections will close.", "Warning!", JOptionPane.OK_CANCEL_OPTION);
		    	  if (i == JOptionPane.OK_OPTION)
		    	  {
		    		  IRCBot.getInstance().closeConnections();
		    	  }
		      }
		    });
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
		textArea.setText("...");
		textArea.setTabSize(4);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setColumns(10);
		textArea.setBorder(null);
		
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setFont(new Font("Monospaced", Font.PLAIN, 13));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setAutoscrolls(true);
		
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		
		//frame.getContentPane().add(textArea, BorderLayout.SOUTH);
		
		frame.setVisible(true);
	}

}
