package com.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.io.PrintWriter;

public class sendAction implements ActionListener {
	// 重写send按钮的动作响应
	public void actionPerformed(ActionEvent arg0) {
		send t = new send();
		Thread print = new Thread(t);
		print.start();
	}
}

class send implements Runnable {
	@Override
	public void run() {
		try {
			//传送i过去
			OutputStream dos = new clientelem().getsocket().getOutputStream();
			dos.write("0".getBytes());
			PrintWriter out = new PrintWriter(new clientelem().getsocket().getOutputStream());
			out.println(new clientelem().getName() + "说: " + new clientelem().getMessage().getText());
			out.flush();
			new clientelem().getMessage().setText("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
