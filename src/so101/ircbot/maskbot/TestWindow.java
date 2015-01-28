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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JSplitPane;
import javax.swing.JButton;
import javax.swing.JTextField;

public class TestWindow {

	public JFrame frame;
	public JTextArea textArea = null;
	private JTextField textFieldCommand;

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
		frame.setBounds(100, 100, 720, 400);
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int i = JOptionPane.showConfirmDialog(frame,
						"Quit EsperNet IRCBot? IRC \nconnections will close.",
						"Warning!", JOptionPane.OK_CANCEL_OPTION);
				if (i == JOptionPane.OK_OPTION) {
					IRCBot.getInstance().closeConnections();
					System.exit(0);
				}
			}
		});

		textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
		textArea.setText("Starting IRC Bot...");
		textArea.setTabSize(4);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setColumns(10);
		textArea.setBorder(null);

		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setFont(new Font("Monospaced", Font.PLAIN, 13));
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setAutoscrolls(true);

		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerLocation(frame.getWidth() - 108);
		frame.getContentPane().add(splitPane, BorderLayout.SOUTH);

		JButton btnGo = new JButton("Go");
		splitPane.setRightComponent(btnGo);

		textFieldCommand = new JTextField();
		splitPane.setLeftComponent(textFieldCommand);
		textFieldCommand.setColumns(10);
		DefaultCaret caret1 = (DefaultCaret) textFieldCommand.getCaret();
		caret1.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		textFieldCommand.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					pressButton();
				}
			}

		});

		// frame.getContentPane().add(textArea, BorderLayout.SOUTH);

		frame.setVisible(true);
	}

	/** Clear text box and send command to server (To Maskbot, from Maskbot) */
	public void pressButton() {
		if (!this.textFieldCommand.getText().equals("")) {
			IRCBot.getInstance().sendToIRC(
					"PRIVMSG " + IRCBot.getNick() + " :CMD:"
							+ this.textFieldCommand.getText());
			this.textFieldCommand.setText("");
		}
	}
}
