package com.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class GetAction implements ActionListener {
	serverelem serverValue = new serverelem();
    public void actionPerformed(ActionEvent arg0) {
    	serverValue.getBody().append("端口8888已开放"+"\n");
		//serverValue.getLabelSendTo().setText(serverValue.getLabelSendTo().getText()+"8888");
        Count c = new Count();
        Thread count1 = new Thread(c);
        count1.start();
    }
}

class Count implements Runnable {
	serverelem serverValue = new serverelem();
	private int count = 0;
    @Override
    public void run() {
        Serverserver t = new Serverserver(8888);
        while (true) {
            try {
            	serverValue.setSocket(serverValue.getServerSocket().accept());
            	serverValue.getSocketList().add(serverValue.getSocket());
            	serverValue.getClient()[count] = String.valueOf(serverValue.getSocket().getPort());
            	serverValue.getBody().append(serverValue.getClient()[count] + "已上线" + "\n");
                byte[] bytes = new byte[1024];
                for (int i = 0; i < serverValue.getSocketList().size(); i++) {
                    Socket socket = serverValue.getSocketList().get(i);
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    serverValue.setSendName("SEVR".getBytes("Gbk"));
                    serverValue.setStart("########".getBytes("Gbk"));
                    serverValue.setEnd("********".getBytes("Gbk"));
                    serverValue.setMessageOrFile("MESM".getBytes("Gbk"));
                    serverValue.setToName("ALLA".getBytes("Gbk"));
                    String s;
                    s = serverValue.getClient()[count] + "已上线";
                    byte[] message = s.getBytes("Gbk");
                    bytes = serverValue.Package(serverValue.getStart(), serverValue.getSendName(), 
                    		serverValue.getMessageOrFile(), serverValue.getToName(), message, serverValue.getEnd());
                    out.write(bytes);
                    out.flush();
                }
                Socket socket = serverValue.getSocketList().get(count);
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                for (int i = 0; i < serverValue.getSocketList().size() - 1; i++) {
                    serverValue.setSendName("SEVR".getBytes("Gbk"));
                    serverValue.setStart("########".getBytes("Gbk"));
                    serverValue.setEnd("********".getBytes("Gbk"));
                    serverValue.setMessageOrFile("MESM".getBytes("Gbk"));
                    serverValue.setToName("ALLA".getBytes("Gbk"));
                    String s;
                    s = serverValue.getClient()[i] + "已上线";
                   byte[] message = s.getBytes("Gbk");
                    bytes = serverValue.Package(serverValue.getStart(), serverValue.getSendName(), 
                    		serverValue.getMessageOrFile(), serverValue.getToName(), message, serverValue.getEnd());
                    out.write(bytes);
                    out.flush();
                }
                count++;
            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread read = new Thread(t);
            read.start();
        }
    }
}
