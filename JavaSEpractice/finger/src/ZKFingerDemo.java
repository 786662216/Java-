import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextArea;

import org.xvolks.jnative.exceptions.NativeException;

@SuppressWarnings("serial")
public class ZKFingerDemo extends JFrame {

	private JLabel jlab;
	private JTextField jtf;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextArea textArea;
	private JButton button_1;
	private int width;
	private int height;
	private int handle;
	private int fpHandle;
	private JButton btnN;
	private JButton button_3;
	private JButton button_2;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox_1;
	byte[] imageBuffer = null;
	int bufferSize;
	private JButton btnNewButton;

	byte[] paramValue = new byte[64];
	byte[] template = new byte[2048];
	int[] paramLen = { paramValue.length };

	boolean regFinger = false;
	boolean verifyFinger = false;
	boolean IdentifyFinger = false;
	

	/**
	 * Launch the application.
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		ZKFingerDemo frame = new ZKFingerDemo();
		frame.setVisible(true);

	}

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ZKFingerDemo() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screensize.getWidth();
		int screenHeight = (int) screensize.getHeight();
		int frameWidth = 800;
		int frameHeight = 500;
		setBounds((screenWidth - frameWidth) / 2,
				(screenHeight - frameHeight) / 2, frameWidth, frameHeight);
		getContentPane().setLayout(null);
		this.setResizable(false);
		jlab = new JLabel("检测到的指纹个数");
		jlab.setBounds(20, 22, 110, 20);

		jtf = new JTextField();
		jtf.setBounds(126, 24, 66, 20);
		jtf.setEditable(false);

		getContentPane().add(jtf);
		getContentPane().add(jlab);

		JLabel lblNewLabel = new JLabel(
				"\u5F53\u524D\u4F7F\u7528\u7684\u91C7\u96C6\u5668\u5E8F\u53F7");
		lblNewLabel.setBounds(210, 24, 130, 20);
		getContentPane().add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(343, 24, 66, 21);
		getContentPane().add(textField);
		textField.setColumns(10);
		textField.setEditable(false);

		JLabel label = new JLabel("\u56FE\u7247\u5BBD\u5EA6");
		label.setBounds(64, 65, 54, 15);
		getContentPane().add(label);

		textField_1 = new JTextField();
		textField_1.setBounds(126, 62, 66, 21);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		textField_1.setEditable(false);

		JLabel label_1 = new JLabel("\u56FE\u7247\u9AD8\u5EA6");
		label_1.setBounds(280, 65, 55, 15);
		getContentPane().add(label_1);

		textField_2 = new JTextField();
		textField_2.setBounds(343, 62, 66, 21);
		getContentPane().add(textField_2);
		textField_2.setColumns(10);
		textField_2.setEditable(false);

		JLabel label_2 = new JLabel("\u91C7\u96C6\u5668\u5E8F\u5217\u53F7");
		label_2.setBounds(44, 93, 78, 15);
		getContentPane().add(label_2);

		textField_3 = new JTextField();
		textField_3.setBounds(126, 90, 283, 21);
		getContentPane().add(textField_3);
		textField_3.setColumns(10);
		textField_3.setEditable(false);

		JLabel label_3 = new JLabel("\u767B\u8BB0\u6B21\u6570");
		label_3.setBounds(195, 294, 54, 15);
		getContentPane().add(label_3);

		JLabel label_4 = new JLabel("\u6307\u7EB9\u7B97\u6CD5\u7248\u672C");
		label_4.setBounds(260, 129, 80, 15);
		getContentPane().add(label_4);

		textField_5 = new JTextField();
		textField_5.setBounds(343, 136, -3, 8);
		getContentPane().add(textField_5);
		textField_5.setColumns(10);

		textField_6 = new JTextField();
		textField_6.setBounds(343, 126, 66, 21);
		getContentPane().add(textField_6);
		textField_6.setColumns(10);
		textField_6.setText("10");
		textField_6.setEditable(false);

		final JButton button = new JButton("连接采集器");
		button.setBounds(99, 157, 100, 23);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int ret = ZKFPCap.sensorInit();
					if (ret == 0) {
						button.setEnabled(false);
						int sensorCount = ZKFPCap.sensorGetCount();// 指纹个数
						if (sensorCount > 0) {
							setDesc("连接指纹采集器成功");
							int sensorIndex = 0;
							handle = ZKFPCap.sensorOpen(0);// 连接指纹采集器

							ZKFPCap.sensorGetParameter(handle, 1, paramValue,
									paramLen);// 图像宽度
							width = byte2Int(paramValue);

							ZKFPCap.sensorGetParameter(handle, 2, paramValue,
									paramLen);// 图像高度
							height = byte2Int(paramValue);

							// width 和 height 一定要对
							fpHandle = ZKFinger10.BIOKEY_INIT(width, height);
							
							//指纹偏移180度测试
							ZKFinger10.BIOKEY_SET_PARAMETER(fpHandle, 4, 180);

							paramLen[0] = paramValue.length;
							ret = ZKFPCap.sensorGetParameter(handle, 106,
									paramValue, paramLen);
							bufferSize = byte2Int(paramValue);
							imageBuffer = new byte[bufferSize];

							paramLen[0] = paramValue.length;
							ZKFPCap.sensorGetParameter(handle, 1103,
									paramValue, paramLen);// 序列号
							String sn = new String(paramValue, 0, paramLen[0]);

							jtf.setText(sensorCount + "");
							textField.setText(sensorIndex + "");
							textField_1.setText(width + "");
							textField_2.setText(height + "");
							textField_3.setText(sn + "");

							button_1.setEnabled(true);
							button_2.setEnabled(true);
							button_3.setEnabled(true);
							
							btnN.setEnabled(true);

						} else {
							setDesc("未检测到采集器");
						}
					} else {
						textArea.setText("连接指纹采集器失败");
					}
				} catch (Exception e1) {
				}

			}
		});
		getContentPane().add(button);

		button_1 = new JButton("退出");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button_3.setEnabled(false);
				btnN.setEnabled(false);
				button_2.setEnabled(false);
				button.setEnabled(true);
				button_1.setEnabled(false);
				try {
					ZKFPCap.sensorClose(handle);
					ZKFPCap.sensorFree();
					ZKFinger10.BIOKEY_CLOSE(fpHandle);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				setDesc("退出成功");
			}
		});
		button_1.setBounds(275, 157, 93, 23);
		button_1.setEnabled(false);
		getContentPane().add(button_1);

		JLabel lblid = new JLabel("指纹ID");
		lblid.setBounds(44, 294, 50, 15);
		getContentPane().add(lblid);

		textField_7 = new JTextField();
		textField_7.setBounds(99, 291, 66, 21);
		getContentPane().add(textField_7);
		textField_7.setColumns(10);

		button_2 = new JButton("登记");
		button_2.setEnabled(false);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				boolean fingerIdOK = checkedFingerId();
				if (!fingerIdOK) {
					JOptionPane.showMessageDialog(null, "指纹ID错误,必须为整数.",
							"指纹ID验证", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (!regFinger) {
					button_2.setText("取消");
					regFinger = true;
					button_3.setEnabled(false);//@@1:1
					btnN.setEnabled(false);//@@1:N
					new RegFinger().start();
				} else {
					button_2.setText("登记");
					regFinger = false;
					button_3.setEnabled(true);//@@1:1
					btnN.setEnabled(true);//@@1:N
				}

			}
		});
		button_2.setBounds(318, 290, 70, 23);
		getContentPane().add(button_2);

		btnN = new JButton("1：N比对");

		btnN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!IdentifyFinger) {
					btnN.setText("取消");
					IdentifyFinger = true;
					button_3.setEnabled(false);//@@1:1
					button_2.setEnabled(false);//@@登记
					new Identify().start();
				} else {
					btnN.setText("1：N比对");
					IdentifyFinger = false;
					button_3.setEnabled(true);//@@1:1
					button_2.setEnabled(true);//@@登记
				}
				
				
			}
		});
		btnN.setBounds(222, 344, 93, 23);
		btnN.setEnabled(false);
		getContentPane().add(btnN);

		button_3 = new JButton("1：1比对");
		button_3.setEnabled(false);
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean fingerIdOK = checkedFingerId();
				if (!fingerIdOK) {
					JOptionPane.showMessageDialog(null, "指纹ID错误,必须为整数.", "1:1比对", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!verifyFinger) {
					button_3.setText("取消");
					btnN.setEnabled(false);
					button_2.setEnabled(false);
					verifyFinger = true;
					new Verify().start();
				} else {
					button_3.setText("1：1比对");//@@1:1
					verifyFinger = false;
					btnN.setEnabled(true);//@@1:N
					button_2.setEnabled(true);//@@登录
				}
			}
		});
		button_3.setBounds(64, 344, 93, 23);
		getContentPane().add(button_3);

		btnNewButton = new JButton();
		btnNewButton.setBounds(491, 24, 256, 300);
		btnNewButton.setDefaultCapable(false);

		getContentPane().add(btnNewButton);

		textArea = new JTextArea();
		textArea.setBounds(119, 207, 302, 50);
		getContentPane().add(textArea);

		JLabel label_7 = new JLabel("操作结果描述");
		label_7.setBounds(32, 222, 80, 15);
		getContentPane().add(label_7);

		comboBox_1 = new JComboBox();
		comboBox_1.setBounds(246, 291, 45, 21);
		comboBox_1.addItem("3");

		getContentPane().add(comboBox_1);

		Panel p = new Panel();
		p.setBounds(471, 344, 100, 100);
	}

	public void setDesc(String desc) {
		textArea.setText(desc);
	}

	public int byte2Int(byte[] b) {
		return (int) (b[0] & 0xFF) + (int) ((b[1] & 0xFF) << 8)
				+ (int) ((b[2] & 0xFF) << 16) + (int) ((b[3] & 0xFF) << 24);
	}

	//采集指纹
	class RegFinger extends Thread {
		Map<Integer, byte[]> map = new HashMap<Integer, byte[]>();
		int index = 0;
		int willAdd = Integer.parseInt(comboBox_1.getSelectedItem().toString());
		@Override
		public void run() 
		{
			setDesc("请按压手指");
			map.put(0, new byte[0]);
			map.put(1, new byte[0]);
			map.put(2, new byte[0]);
			while (regFinger && willAdd > index) 
			{
				try 
				{
					ZKFingerDemo.this.requestFocus();
					ZKFingerDemo.this.setFocusable(false);
					ZKFingerDemo.this.setFocusable(true);
					int ret = ZKFPCap.sensorCapture(handle, imageBuffer,bufferSize);
					if (ret > 0) 
					{
						//采集指纹
						ret = ZKFinger10.BIOKEY_EXTRACT(fpHandle, imageBuffer,	template, 1);
						if (ret > 0) 
						{
							setDesc("第 " + (index + 1) + " 枚手指采集成功");
							String imagePaht = textField_7.getText()+".bmp";
							//保存指纹图片
							writeBitmap(imageBuffer, width, height, imagePaht);
							//预览指纹采集
							btnNewButton.setIcon(new ImageIcon(ImageIO.read(new File(imagePaht))));
							map.put(index++, template);
							
						}
					}
					Thread.sleep(50);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
			if (willAdd == index) {
				try {
					byte[] gTemplate = new byte[bufferSize];
					// 合并指纹
					int ret = ZKFinger10.BIOKEY_GENTEMPLATE_SP(fpHandle,map.get(0), map.get(1), map.get(2), willAdd, gTemplate);
					if (ret > 0) {
						int fingerId = Integer.parseInt(textField_7.getText());
						//放入缓存，之后1：N才有数据做对比
						ret = ZKFinger10.BIOKEY_DB_ADD(fpHandle, fingerId, ret,	gTemplate);
						if(ret == 1)//缓存成功
						{
							DB.fingerInfo.put(fingerId, gTemplate);
							setDesc("指纹采集完成, 当前指纹总数：" + ZKFinger10.BIOKEY_DB_COUNT(fpHandle));
						}
					} 
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			button_2.setText("登记");
			button_3.setEnabled(true);//@@1:1
			btnN.setEnabled(true);//@@1:N
			regFinger = false;
		}
	}
	//1:1比对
	class Verify extends Thread
	{
		
		@Override
		public void run() {
			setDesc("请按压手指");
			while(verifyFinger)
			{
				try
				{
					ZKFingerDemo.this.requestFocus();
					ZKFingerDemo.this.setFocusable(false);
					ZKFingerDemo.this.setFocusable(true);
					int ret = ZKFPCap.sensorCapture(handle, imageBuffer,bufferSize);
					if (ret > 0) 
					{
						//采集指纹
						ret = ZKFinger10.BIOKEY_EXTRACT(fpHandle, imageBuffer,	template, 1);
						if(ret >0 )
						{
							int fingerId = Integer.parseInt(ZKFingerDemo.this.textField_7.getText());
							byte[] fingerTemp = DB.fingerInfo.get(fingerId);
							String imagePaht = textField_7.getText()+"_verify.bmp";
							//保存指纹图片
							writeBitmap(imageBuffer, width, height, imagePaht);
							//预览指纹采集
							btnNewButton.setIcon(new ImageIcon(ImageIO.read(new File(imagePaht))));
							ret = 0;
							if(fingerTemp != null)
							{
								ret = ZKFinger10.BIOKEY_VERIFY(fpHandle, fingerTemp, template);
							}
							
							if(ret>0)
							{
								setDesc("1:1比对分值：" + ret);
							}
							else
							{
								setDesc("1:1比对失败");
							}
//							verifyFinger = false;
//							button_3.setText("1：1比对");//@@1:1比对
//							btnN.setEnabled(true);//@@1:N
//							button_2.setEnabled(true);//@@登录
						}
					}
					Thread.sleep(50);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	//1:N比对
	class Identify extends Thread
	{
		@Override
		public void run() 
		{
			
			setDesc("请按压手指");
			while(IdentifyFinger)
			{
				try
				{
					ZKFingerDemo.this.requestFocus();
					ZKFingerDemo.this.setFocusable(false);
					ZKFingerDemo.this.setFocusable(true);
					int ret = ZKFPCap.sensorCapture(handle, imageBuffer,bufferSize);
					if (ret > 0) 
					{
						//采集指纹
						ret = ZKFinger10.BIOKEY_EXTRACT(fpHandle, imageBuffer,	template, 1);
						if(ret >0 )
						{
							String imagePaht = textField_7.getText()+"_verify.bmp";
							//保存指纹图片
							writeBitmap(imageBuffer, width, height, imagePaht);
							//预览指纹采集
							btnNewButton.setIcon(new ImageIcon(ImageIO.read(new File(imagePaht))));
							
							int[] fingId = new int[1];
							int[] score = new int[1];
							
							ret = ZKFinger10.BIOKEY_IDENTIFYTEMP(fpHandle, template, fingId, score);
							if(ret != 0)
							{
								setDesc(String.format("1:N比对结果：手指ID=%d, 分值=%d", fingId[0], score[0]));
							}
							else
							{
								setDesc("1:N比对失败");
							}
//							IdentifyFinger = false;
//							btnN.setText("1:N比对");
//							button_3.setEnabled(true);
//							button_2.setEnabled(true);
						}
					}
					Thread.sleep(50);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		
	}
	

	public boolean checkedFingerId() {
		try {
			String fingerId = textField_7.getText();
			Integer.parseInt(fingerId);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;

	}

	public static boolean isValidInt(String value) {
		try {
			Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static void writeBitmap(byte[] imageBuf, int nWidth, int nHeight,
			String path) throws IOException {
		java.io.FileOutputStream fos = new java.io.FileOutputStream(path);
		java.io.DataOutputStream dos = new java.io.DataOutputStream(fos);

		int bfType = 0x424d; // 位图文件类型（0—1字节）
		int bfSize = 54 + 1024 + nWidth * nHeight;// bmp文件的大小（2—5字节）
		int bfReserved1 = 0;// 位图文件保留字，必须为0（6-7字节）
		int bfReserved2 = 0;// 位图文件保留字，必须为0（8-9字节）
		int bfOffBits = 54 + 1024;// 文件头开始到位图实际数据之间的字节的偏移量（10-13字节）

		dos.writeShort(bfType); // 输入位图文件类型'BM'
		dos.write(changeByte(bfSize), 0, 4); // 输入位图文件大小
		dos.write(changeByte(bfReserved1), 0, 2);// 输入位图文件保留字
		dos.write(changeByte(bfReserved2), 0, 2);// 输入位图文件保留字
		dos.write(changeByte(bfOffBits), 0, 4);// 输入位图文件偏移量

		int biSize = 40;// 信息头所需的字节数（14-17字节）
		int biWidth = nWidth;// 位图的宽（18-21字节）
		int biHeight = nHeight;// 位图的高（22-25字节）
		int biPlanes = 1; // 目标设备的级别，必须是1（26-27字节）
		int biBitcount = 8;// 每个像素所需的位数（28-29字节），必须是1位（双色）、4位（16色）、8位（256色）或者24位（真彩色）之一。
		int biCompression = 0;// 位图压缩类型，必须是0（不压缩）（30-33字节）、1（BI_RLEB压缩类型）或2（BI_RLE4压缩类型）之一。
		int biSizeImage = nWidth * nHeight;// 实际位图图像的大小，即整个实际绘制的图像大小（34-37字节）
		int biXPelsPerMeter = 0;// 位图水平分辨率，每米像素数（38-41字节）这个数是系统默认值
		int biYPelsPerMeter = 0;// 位图垂直分辨率，每米像素数（42-45字节）这个数是系统默认值
		int biClrUsed = 0;// 位图实际使用的颜色表中的颜色数（46-49字节），如果为0的话，说明全部使用了
		int biClrImportant = 0;// 位图显示过程中重要的颜色数(50-53字节)，如果为0的话，说明全部重要

		dos.write(changeByte(biSize), 0, 4);// 输入信息头数据的总字节数
		dos.write(changeByte(biWidth), 0, 4);// 输入位图的宽
		dos.write(changeByte(biHeight), 0, 4);// 输入位图的高
		dos.write(changeByte(biPlanes), 0, 2);// 输入位图的目标设备级别
		dos.write(changeByte(biBitcount), 0, 2);// 输入每个像素占据的字节数
		dos.write(changeByte(biCompression), 0, 4);// 输入位图的压缩类型
		dos.write(changeByte(biSizeImage), 0, 4);// 输入位图的实际大小
		dos.write(changeByte(biXPelsPerMeter), 0, 4);// 输入位图的水平分辨率
		dos.write(changeByte(biYPelsPerMeter), 0, 4);// 输入位图的垂直分辨率
		dos.write(changeByte(biClrUsed), 0, 4);// 输入位图使用的总颜色数
		dos.write(changeByte(biClrImportant), 0, 4);// 输入位图使用过程中重要的颜色数

		for (int i = 0; i < 256; i++) {
			dos.writeByte(i);
			dos.writeByte(i);
			dos.writeByte(i);
			dos.writeByte(0);
		}

		dos.write(imageBuf, 0, nWidth * nHeight);
		dos.flush();
		dos.close();
		fos.close();
	}

	public static byte[] changeByte(int data) {
		byte b4 = (byte) ((data) >> 24);
		byte b3 = (byte) (((data) << 8) >> 24);
		byte b2 = (byte) (((data) << 16) >> 24);
		byte b1 = (byte) (((data) << 24) >> 24);
		byte[] bytes = { b1, b2, b3, b4 };
		return bytes;
	}
}
