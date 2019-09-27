package com.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class serverelem {
	private static int count = 0;
	private static int index = 0;
	private static boolean flag = false;
	private static JLabel LabelSendTo;
	private static ServerSocket serverSocket;
	private static JTextArea body = new JTextArea();
	private static JTextField postTo = new JTextField(null);
	private static List<Socket> clientSocketList = new ArrayList<Socket>();
	private static List<String> clientNameList = new ArrayList<String>();
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int newIndex) {
		index = newIndex;
	}
	public boolean getFlag() {
		return flag;
	}
	public void setFlag(boolean newFlag) {
		flag = newFlag;
	}
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	public void setServerSocket(ServerSocket obj) {
		serverSocket = obj;
	}
	public List<Socket> getSocketList(){
		return clientSocketList;
	}
	public List<String> getNameList(){
		return clientNameList;
	}
}
