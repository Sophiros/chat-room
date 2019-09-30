package com.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class GetAction implements ActionListener {
	ServerElem serverValue = new ServerElem();
    public void actionPerformed(ActionEvent arg0) {
    	serverValue.getBody().append("端口8888已开放"+"\n");
        Count c = new Count();
        Thread count1 = new Thread(c);
        count1.start();
        Thread check = new Thread(new CheckConnection());
        check.start();
    }
}

class Count implements Runnable {
	ServerElem serverValue = new ServerElem();
	@Override
    public void run() {
        SendToEveryClient t = new SendToEveryClient(8888);
        while (true) {
            try {
            	serverValue.setSocket(serverValue.getServerSocket().accept());
            	serverValue.getSocketList().add(serverValue.getSocket());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread read = new Thread(t);
            read.start();
        }
    }
}

class CheckConnection implements Runnable {
	ServerElem serverValue = new ServerElem();
	@Override
	public void run() {
		while (true) {
			try {
				for(int i = 0; i < serverValue.getSocketList().size(); i++) {
					Socket socket = serverValue.getSocketList().get(i);
					if(socket.isClosed()) {
						serverValue.getSocketList().remove(i);
						serverValue.getClientList().remove(socket);
						byte[] bytes = new byte[1024];
						String namelist = ""; 
	                	int j=serverValue.getEnd(bytes,"********".getBytes("Gbk"),8+12)-8;
	                    byte[] getMessage = new byte[j];
	                    System.arraycopy(bytes, 8+12, getMessage, 0, j);
	                    String name = new String(getMessage,"Gbk");
	                	serverValue.getClientList().put(serverValue.getSocket(), name);
	                	serverValue.getBody().append("在线用户:\n");
						for(Socket key : serverValue.getClientList().keySet()) {
							serverValue.getBody().append("port: " + key.getPort() + " ");
							serverValue.getBody().append("name: "+ serverValue.getClientList().get(key) + "\n");
							namelist += serverValue.getClientList().get(key) + " ";
						}
						for (int n = 0; n < serverValue.getSocketList().size(); n++) {
							Socket socket1 = serverValue.getSocketList().get(n);
							DataOutputStream dos = new DataOutputStream(socket1.getOutputStream());
							serverValue.setSendName("SEVR".getBytes("Gbk"));
		                    serverValue.setStart("########".getBytes("Gbk"));
		                    serverValue.setEnd("********".getBytes("Gbk"));
		                    serverValue.setMessageOrFile("LIST".getBytes("Gbk"));
		                    serverValue.setToName("ALLA".getBytes("Gbk"));
		                    byte[] nameList = namelist.getBytes("Gbk");
		                    bytes = serverValue.Package(serverValue.getStart(), serverValue.getSendName(), 
		                    		serverValue.getMessageOrFile(), serverValue.getToName(), nameList, serverValue.getEnd());
		                    dos.write(bytes);
		                    dos.flush();
		                    serverValue.getBody().append("用户列表更新，发送给端口："+socket1.getPort()+ "\n");
						}
						namelist = "";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
