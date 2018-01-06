import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JOptionPane;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;

public class TalkClient {
	public static void main(String arg[]) {
		new InfTalkFrame().Launch();
	}
}

class InfTalkFrame extends Frame {
	public void Launch() {
		this.setName("Launcher");
		setBounds(300, 300, 600, 350);
		setTitle("Launcher");
		setVisible(true);
		setLayout(null);
		Panel p1 = new Panel();
		p1.setBounds(10, 50, 190, 200);
		p1.setLayout(new GridLayout(2, 1));
		add(p1);
		Panel p2 = new Panel();
		Button b = new Button("OK");
		b.setSize(450, 100);
		b.setLocation(75, 0);
		b.setBackground(new Color(124, 205, 124));
		p2.setBounds(0, 250, 600, 100);
		p2.setLayout(null);
		p2.add(b);
		Panel tp = new Panel();
		tp.setBounds(200, 50, 400, 200);
		tp.setLayout(new GridLayout(2, 1));
		TextField tf_usename = new TextField();
		TextField tf_id = new TextField();
		Font f = new Font(Font.DIALOG, Font.PLAIN, 18);
		tf_usename.setFont(new Font("宋体", 33, 88));
		tf_usename.setForeground(new Color(255, 48, 48));
		tf_id.setForeground(new Color(255, 48, 48));
		tf_id.setFont(new Font("宋体", 33, 88));
		tp.add(tf_usename);
		tp.add(tf_id);
		add(p2);
		add(tp);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				System.exit(0);
				;
			}
		});
		Label l1 = new Label("Input UseName:");
		Label l2 = new Label("Input Socket ID:");
		l1.setFont(f);
		l2.setFont(f);
		l1.setBounds(0, 0, 200, 100);
		l2.setBounds(0, 100, 200, 100);
		l1.setBackground(new Color(193, 255, 193));
		l2.setBackground(new Color(193, 255, 193));
		p1.add(l1);
		p1.add(l2);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String idstr = tf_id.getText();
				int id = 0;
				try {
					id = Integer.parseInt(idstr);
				} catch (Exception ae) {
					JOptionPane.showMessageDialog(null, "Id ERROR!   Input Number!", "Error!",
							JOptionPane.ERROR_MESSAGE);
				}
				String usenamestr = tf_usename.getText();
				if (id > 0 && id < 65535) {
					setVisible(false);
					// 成功关闭
					new ClientLaunch(usenamestr, id);
					// System.out.println("!");
					// System.exit(0);
				} else {
					JOptionPane.showMessageDialog(null, "Id ERROR!", "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		// JOptionPane.showMessageDialog(null, "Please input!", "Error!",
		// JOptionPane.ERROR_MESSAGE);
	}
}

class ClientLaunch {
	String usename = "";

	ClientLaunch(String usename, int id) {
		this.usename = usename;
		Socket s = null;
		DataOutputStream dos = null;
		DataInputStream dis = null;
		// DataOutputStream name_dos = null;
		// DataInputStream name_dis = null;
		// Socket sname = null;
		try {
			s = new Socket("127.1.2.201", id);
			// dos = new DataOutputStream(s.getOutputStream());
			// dis = new DataInputStream(s.getInputStream());
			// sname = new Socket("127.2.201",6578);
			// name_dos = new DataOutputStream(sname.getOutputStream());
			// name_dis = new DataInputStream(sname.getInputStream());
			// name_dos.writeUTF(usename);
			// String Oname = name_dis.readUTF();
			// System.out.println(Oname);
			// name_dos.flush();
			// name_dos.close();
			// name_dis.close();
			// sname.close();
			new TStart(s, usename);
		} catch (IOException ae) {
			JOptionPane.showMessageDialog(null, "Connected failed!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
		// Frame talkwin = new Frame();
		// talkwin.setBounds(300,300,300,500);

	}

	class TStart {
		boolean isconnect = false;
		Socket s = null;
		DataOutputStream dos = null;
		DataInputStream dis = null;
		TextArea tf_win = null;
		TextField tf_in = new TextField();
		String usename = "";
		String word = "";

		TStart() {
			/* DoNothing */}

		TStart(Socket s, String usename) {
			this.s = s;
			try {
				dos = new DataOutputStream(s.getOutputStream());
				dis = new DataInputStream(s.getInputStream());
				isconnect = true;
			} catch (IOException ae) {
				tf_win.setText("Can't set Stream.");
			}
			this.usename = usename;
			talkStart();
		}

		public void talkStart() {
			Frame talkf = new Frame(usename + " Client");
			talkf.setBounds(300, 300, 300, 500);
			talkf.setLayout(null);
			talkf.setVisible(true);
			tf_win = new TextArea();
			tf_win.setBounds(8, 50, 292, 350);
			tf_win.setEditable(false);
			tf_win.setBackground(Color.WHITE);
			tf_win.setFont(new Font("宋体", 48, 32));
			tf_in.setEditable(true);
			tf_in.setFont(new Font("宋体", 48, 32));
			tf_in.setBounds(8, 400, 162, 90);
			tf_in.addActionListener(new SendInf());
			Button send = new Button("Send");
			// 已经执行。
			talkf.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					talkf.setVisible(false);
					System.exit(0);
				}
			});
			send.addActionListener(new SendInf());
			send.setBounds(170, 400, 130, 90);
			talkf.add(tf_win);
			talkf.add(tf_in);
			talkf.add(send);
			new Thread(new ClientInf(s)).start();
			// 线程开始
			// try {
			// GetInf();
			// }catch(IOException ae) {
			// JOptionPane.showMessageDialog(null, "Connected failed!", "Error!",
			// JOptionPane.ERROR_MESSAGE);
			// }
		}

		// public void SendInf() throws IOException {
		// while(true) {
		// String nowword = tf_win.getText();
		// String getword = dis.readUTF();
		// String aword = nowword + getword;
		// tf_win.setText(aword);
		// }
		// }

		class SendInf implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (isconnect) {
					String sendword = tf_in.getText();
					if (sendword.equals("")) {
						// Do Nothing
					} else {
						tf_in.setText("");
						String nametime = usename + " " + getTime() + '\n';

						try {
							dos.writeUTF(nametime + sendword);
							dos.flush();
						} catch (IOException ae) {
							tf_win.setText("Can't send text.");
						}
					}
				}
			}

			public String getTime() {
				Date date = new Date();
				long times = date.getTime();// 时间戳
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateString = formatter.format(date);
				return dateString;
			}
		}

		class ClientInf implements Runnable {
			Socket s = null;
			DataOutputStream dos = null;
			DataInputStream dis = null;

			ClientInf(Socket s) {
				this.s = s;
				try {
					dos = new DataOutputStream(s.getOutputStream());
					dis = new DataInputStream(s.getInputStream());
					isconnect = true;
				} catch (IOException ae) {
					tf_win.setText("Can't set Stream.");
				}
			}

			public void run() {
				// String word = talkf.ge
				while (isconnect) {
					try {
						String inword = dis.readUTF();
						// String inword = usename + " " + new
						// Date().toString()+'\n'+dis.readUTF()+'\n';
						tf_win.setText(tf_win.getText() + inword + '\n');
						// String nowword = tf_win.getText();
						// String aword = nowword + inword;
						// tf_win.setText(aword);
					} catch (IOException ae1) {
						System.out.println("dis error");
					}
				}
			}
		}
	}
}
