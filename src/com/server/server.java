package com.server;


import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class server extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField postTo = new JTextField("", 50);
	// 创建文字框
	JLabel LabelSendTo;
	private JTextArea body = new JTextArea();
	// 创建文字域
	private JScrollPane roll = new JScrollPane(body);
	// 在文本框上添加滚动条
	private Socket socket;
	// 定义一个socket
	private List<Socket> socketList = new ArrayList<Socket>();
	private Map<Integer, String> clientlist = new HashMap<>();
	private ServerSocket server;
	private int count = 0;
	private String jieshou;

	public void creatWindow() {
		JFrame window = new JFrame("服务器");
		Container container = window.getContentPane();
		// 获取一个容器
		container.setLayout(new GridLayout(4, 1, 50, 10));
		// 设置网格，参数依次为（行，列，长度，宽度）
		LabelSendTo = new JLabel("开放端口：");
		// 创建标签
		JButton start = new JButton("开放");
		// 创建按钮
		body.setLineWrap(true);
		// 设置自动换行
		roll.setBounds(0, 0, 50, 50);
		// 设置矩形大小，参数依次为（左上角x，左上角y，长度，宽度）
		roll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		// 设置让滚动条一直显示
		container.add(LabelSendTo);
		container.add(postTo);
		container.add(roll);
		container.add(start);
		// 按顺序加入容器
		window.setVisible(true);
		// 使窗体可见
		window.setSize(500, 350);
		// 设置窗体大小
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// 设置窗体关闭方式
		start.addActionListener(new getAction());
		// 为按钮设置动作响应

	}

	public class getAction implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if(postTo.getText().length()!=0){	
				body.append("端口"+postTo.getText()+"已开放"+"\n");
				LabelSendTo.setText(LabelSendTo.getText()+postTo.getText()+"、");
				Count c = new Count();
				Thread count1 = new Thread(c);
				count1.start();
			}
			
		}
	}

	public class Count implements Runnable {
		@Override
		public void run() {
			Serverserver t = new Serverserver(Integer.parseInt(postTo.getText()));
			String name = "";
			while (true) {
				try {
					socket = server.accept();
					count++;
					body.append("第" + count + "个客户已连接" + "\n");
					
					DataInputStream dis = new DataInputStream(socket.getInputStream());
					String str = dis.readUTF();
					body.append(str+"已上线\n");
					
					clientlist.put(socket.getPort(), str);
					
					body.append("在线用户:\n");
					for(Integer key : clientlist.keySet()) {
						body.append("port: " + key + " ");
						body.append("name: "+ clientlist.get(key) + "\n");
						name += clientlist.get(key) + " ";
					}
					
					socketList.add(socket);
					
					for (int i = 0; i < socketList.size(); i++) {
						Socket socket = socketList.get(i);
						DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
						dos.writeUTF(name);
						body.append("用户列表更新，发送给端口："+socket.getPort()+ "\n");
						dos.flush();
						}
					
					name = "";
				} catch (IOException e) {
					e.printStackTrace();
				}
				Thread read = new Thread(t);
				read.start();
			}
		}
	}

	

	public class Serverserver implements Runnable {
		public Serverserver(int port) {
			// 构造方法
			try {
				server = new ServerSocket(port);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
		public void up() throws IOException {
			
			//接收名字的流
			InputStream is1=socket.getInputStream();
			byte[]bys3=new byte[1024];
			int len3=is1.read(bys3);
			String client3=new String(bys3,0,len3);
			client3=client3+"副本";//防止本机文件传输冲突
			
			BufferedInputStream bis=new BufferedInputStream(socket.getInputStream());
			@SuppressWarnings("resource")
			BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(client3));
			byte []bys=new byte[1024];
			int len=0;
			while((len=bis.read(bys))!=-1) {
				bos.write(bys, 0, len);
				bos.flush();
			}
		
		}
		
		@Override	
		public void run() {
			// 重写run方法
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				while (true) {
					InputStream is=socket.getInputStream();
					byte[]bys2=new byte[1024];
					int len2=is.read(bys2);
					String client=new String(bys2, 0, len2);
					
					if(client.contains("0")) {
						jieshou = in.readLine();
						body.append(jieshou + "\n");
						for (int i = 0; i < socketList.size(); i++) {
							Socket socket = socketList.get(i);
							PrintWriter out = new PrintWriter(socket.getOutputStream());
							out.println(jieshou);
							out.flush();
							}
						}
					else if(client.contains("1")){
						up();
					}			
			} 				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		System.setProperty("java.net.preferIPv4Stack", "true");
		// 设置为ipv4
		new server().creatWindow();
	}
}
