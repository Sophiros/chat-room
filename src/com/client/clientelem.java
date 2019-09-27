package com.client;

import java.net.Socket;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class clientelem {
	private static String name;
	private static String[] totalName;
	private static Socket socket;
	private static int serverPort = 8888;
	private static String serverIP = "localhost";
	private static JTextField postTo = new JTextField(null);
	private static JTextField message = new JTextField(null);
	private static JTextField Filetext = new JTextField(null);
	private static JTextArea body = new JTextArea();
	private static JList<String> usernamelist = new JList<String>();
	
	public String getName() {
		return name;
	}
	public void setName(String obj) {
		name = obj;
	}
	public String[] getTotalName() {
		return totalName;
	}
	public void setTotalName(String[] obj) {
		totalName = obj;
	}
	public int getPort() {
		return serverPort;
	}
	public void setPort(int obj) {
		serverPort = obj;
	}
	
	public Socket getsocket() {
		return socket;
	}
	public void setsocket(Socket obj) {
		socket = obj;
	}
	public String getServerIP() {
		return serverIP;
	}
	public void setServerIP(String obj) {
		serverIP = obj;
	}
	public JTextField getPostTo() {
		return postTo;
	}
	public void setPostTo(JTextField obj) {
		postTo = obj;
	}
	public JTextField getMessage() {
		return message;
	}
	public void setmessage(JTextField obj) {
		message = obj;
	}
	public JTextField getFiletext() {
		return Filetext;
	}
	public void setFiletext(JTextField obj) {
		Filetext = obj;
	}
	public JTextArea getBody() {
		return body;
	}
	public void setBody(JTextArea obj) {
		body = obj;
	}
	public JList<String> getUserNameList(){
		return usernamelist;
	}
	public void setUserNameList(JList<String> obj) {
		usernamelist = obj;
	}
	public void setUserNameList() {
		usernamelist.setListData(totalName);
	}
}
