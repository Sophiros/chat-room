package com.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class uploadAction implements ActionListener {
	// 重写send按钮的动作响应
	public void actionPerformed(ActionEvent arg0) {
		upload t = new upload();
		Thread print = new Thread(t);
		print.start();
	}
}

class upload implements Runnable {
	// private Socket socket;
	public void go() throws IOException
	{

		BufferedInputStream bis=new BufferedInputStream(new FileInputStream(new clientelem().getFiletext().getText()));
		BufferedOutputStream bos=new BufferedOutputStream(new clientelem().getsocket().getOutputStream());
		//这是传名字的流
		OutputStream os=new clientelem().getsocket().getOutputStream();
		os.write(new clientelem().getFiletext().getText().getBytes());
		
		byte []bys=new byte[1024];
		int len=0;
		while((len=bis.read(bys))!=-1) {
			bos.write(bys, 0, len);
			bos.flush();
		}
		new clientelem().getsocket().shutdownOutput();
		bis.close();
		System.out.println("jieshu");
	}

	@Override
	public void run() {
		try {
			OutputStream os3 = new clientelem().getsocket().getOutputStream();
			os3.write("1".getBytes());
			go();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
