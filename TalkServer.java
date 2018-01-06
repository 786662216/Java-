import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JOptionPane;
import java.io.*;
import java.net.*;

public class TalkServer {
	public static void main(String arg[]) {
		new InfFrame();
	}
}

class InfFrame extends Frame {
	TextArea tf = new TextArea();
	TextField tf_in = null;
	ServerSocket ss = null;
	Socket s = null;
	DataOutputStream dos = null;
	DataInputStream dis = null;
	boolean socketconnect = false;
	int addressmain;
	ArrayList<ServerStart> al = new ArrayList<ServerStart>();

	InfFrame() {
		setTitle("Server");
		setBounds(300, 300, 700, 300);
		setLayout(null);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				try {
					ss.close();
				} catch (NullPointerException aep) {
					System.out.println("Already closed!");
				} catch (IOException ae) {
					tf.setText("Can't Close the Socket!");
				}
				System.exit(0);
			}
		});
		Font f = new Font(Font.DIALOG, Font.PLAIN, 28);
		tf.setFont(f);
		tf.setEditable(false);
		tf.setText("Need input Address." + '\n' + '\n' + "No Connected.");
		tf.setBounds(5, 50, 525, 250);
		Panel p = new Panel();
		p.setBounds(530, 50, 170, 250);
		p.setLayout(null);
		tf_in = new TextField();
		tf_in.setBounds(0, 0, 100, 40);
		tf_in.setFont(new Font("宋体", 48, 32));
		tf_in.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String address = tf_in.getText();
				int add = 0;
				boolean flag = false;
				try {
					add = Integer.parseInt(address);
					flag = true;
				} catch (NumberFormatException ae4) {
					tf.setText(tf.getText() + '\n' + "Address Error!");
				}
				if (flag) {
					if (add < 10000 && add > 1024) {
						addressmain = add;
						tf.setText("Address:" + add + '\n' + '\n' + "No Connected.");
					} else {
						tf.setText(tf.getText() + '\n' + "Address Error!");
					}
				}
			}
		});
		Button b = new Button("Random");
		b.setBounds(100, 0, 60, 40);
		b.setBackground(new Color(193, 255, 193));
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int address = 0;
				do {
					address = (int) Math.rint(Math.random() * 10000);
				} while (address < 1024);
				tf_in.setText("" + address);
				addressmain = address;
				tf.setText("Address:" + address + '\n' + '\n' + "No Connected.");
			}
		});
		Button b_sure = new Button("Sure");
		b_sure.setBounds(30, 80, 110, 110);
		b_sure.addActionListener(new Monitor());
		p.add(tf_in);
		p.add(b);
		p.add(b_sure);
		add(tf);
		add(p);
		setVisible(true);
		// for(;;) {
		// System.out.println("");
		// int a = 0 ;
		// a++;
		// continue;
		// }
	}

	public void Started(int add) throws IOException {
		// System.out.println("!");
		// DataOutputStream dos = null;
		// DataInputStream dis = null;
		// System.out.println("!");
		boolean bconnect = false;
		{
			ss = new ServerSocket(add);
			socketconnect = true;
			// System.out.println(socketconnect);
			bconnect = true;
		}

		try {
			while (bconnect) {
				s = ss.accept();
				// 可以连接Socket
				// tf.setText("ID:"+add);
				ServerStart serverstart = new ServerStart(s);
				new Thread(serverstart).start();
				al.add(serverstart);
				tf.setText(tf.getText() + '\n' + "Socket connected Successful!");
			}
			// System.out.println("!");
		} catch (IOException ae) {
			ae.printStackTrace();
			tf.setText("ID:8678" + '\n' + "Failed to connected.");
		} finally {
			try {
				s.close();
			} catch (IOException ae) {
				System.out.println("Error!");
			}
		}
		// new Thread(new ServerStart(s)).start();
	}

	class ServerStart implements Runnable {
		Socket s = null;
		boolean connect = false;
		DataInputStream dis = null;
		DataOutputStream dos = null;

		// String word = "";
		ServerStart() {
			/* Do Notihing */
		}

		ServerStart(Socket s) {
			// this.dis = dis ;
			// this.dos = dos ;
			this.s = s;
			try {
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
				connect = true;
			} catch (NullPointerException aen) {
				System.out.println("NullPointerException.");
			} catch (IOException ae3) {
				System.out.println("DataInOutputStream error.Server Close.");
			}
		}

		public void send(String word) {
			try {
				dos.writeUTF(word);
				// System.out.println("send");
			} catch (IOException ae2) {
				try {
					dos.flush();
					dos.close();
					ss.close();
				} catch (IOException ae3) {
					ae3.printStackTrace();
				}
			}
		}

		public void run() {
			String word = "";
			while (connect) {
				try {
					word = dis.readUTF();
					for (int i = 0; i < al.size(); i++) {
						ServerStart sstart = al.get(i);
						sstart.send(word);
						// try {
						//
						// }catch(IOException ae2) {
						// ae2.printStackTrace();
						// }
					}
					System.out.println("read");
				}
				// catch(SocketException aesocket) {
				// System.out.println("!");
				// }
				catch (IOException ae) {
					try {
						dis.close();
						s.close();
						dos.close();
						break;
						// System.exit(0);
					} catch (IOException ae3) {
						System.out.println("Close Failed!");
					}
					ae.printStackTrace();
				}

			}
		}
	}

	class Monitor implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (addressmain > 1024 && addressmain < 10000) {
				try {
					System.out.println("Started!");
					System.out.println(addressmain);
					tf.setText(tf.getText() + '\n' + "Successful launch.");
					Started(addressmain);
				} catch (BindException aee) {
					tf.setText("This Address has already been used!" + '\n' + "Please close it!");
				} catch (IOException ae) {
					ae.printStackTrace();
					tf.setText("Failed to launch.");
				}
				// System.out.println(isconnect);
			} else {
				tf.setText(tf.getText() + '\n' + '\n' + "Address not in range.");
			}
		}
	}
}
