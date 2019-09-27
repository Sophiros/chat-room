package com.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class connectAction{
	
	public void connect(){
		try {
			String serverIP = new clientelem().getServerIP();
			int port = new clientelem().getPort();
			Socket socket1 = new Socket(serverIP, port);
			// Socket socket1 = new Socket(serverIP, Integer.parseInt(new value().getPostTo().getText()));
			new clientelem().setsocket(socket1);
			new clientelem().getBody().append("欢迎来到魔方小镇" + "\n");
			
			DataOutputStream dos = new DataOutputStream(new clientelem().getsocket().getOutputStream());
			dos.writeUTF(new clientelem().getName());
			dos.flush();
			//dos.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		// read r = new read(new clientelem().getsocket());
		// Thread read = new Thread(r);
		//read.start();
		RecToServer t = new RecToServer(new clientelem().getsocket());
		Thread Rec = new Thread(t);
		Rec.start();
	}
}

class read implements Runnable {
	Socket socket = null;
	public read(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while (true) {
				
				new clientelem().getBody().append(in.readLine() + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class RecToServer implements Runnable {
	Socket socket = null;
	public RecToServer(Socket socket) {
		this.socket = socket;
	}
	@Override
	public void run() {
		try {
			while (true) {
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				String namestr = dis.readUTF();
				if(namestr != "") {
					new clientelem().getBody().append("更新用户列表！"+"\n");
					String[] name = namestr.split(" ");
					new clientelem().setTotalName(name);
					
					// new clientelem().getContents().clear();
					// Vector<String> nameList = new Vector<String>();
					for(int i = 0; i < name.length; i++) {
						new clientelem().getContents().add(i, name[i]);
						new clientelem().setUserNameList();
						new clientelem().getBody().append("用户：" + name[i]);
					}
					//new clientelem().setContents(nameList);
				}
			}
		} catch (SocketException e) {
			System.out.println("服务器已关闭");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
