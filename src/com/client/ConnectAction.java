package com.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

public class ConnectAction{
	ClientElem clientValue = new ClientElem();
	public void connect(){
		try {
			String serverIP = clientValue.getServerIP();
			int port = clientValue.getPort();
			Socket socket1 = new Socket(serverIP, port);
			clientValue.setSocket(socket1);
			clientValue.getBody().append("欢迎来到魔方小镇" + "\n");
			
			byte[] bytes = new byte[1024];
			DataOutputStream dos = new DataOutputStream(clientValue.getSocket().getOutputStream());
			clientValue.setSendName("SEVR".getBytes("Gbk"));
			clientValue.setStart("########".getBytes("Gbk"));
			clientValue.setEnd("********".getBytes("Gbk"));
			clientValue.setMessageOrFile("NAME".getBytes("Gbk"));
			clientValue.setToName("ALLA".getBytes("Gbk"));
            byte[] name = clientValue.getName().getBytes("Gbk");
            bytes = clientValue.Package(clientValue.getStart(), clientValue.getSendName(), 
            		clientValue.getMessageOrFile(), clientValue.getToName(), name, clientValue.getEnd());
            dos.write(bytes);
            dos.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		Read r = new Read();
		Thread read = new Thread(r);
		read.start();
	}
}

class Read implements Runnable {
	ClientElem clientValue = new ClientElem();
    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(clientValue.getSocket().getInputStream());
            while (true) {
                byte[] bytes = new byte[1024];
                in.read(bytes);
                int i = clientValue.getEnd(bytes,"########".getBytes("Gbk"),0);
                for (int j = 0; j < 4; j++) {
                	clientValue.getSendName()[j] = bytes[j + i];
                }
                String s1 = new String(clientValue.getSendName(),"Gbk");
                if (Objects.equals(s1, "SEVR")) {
                    s1 = "服务器";
                } else if (Objects.equals(s1, clientValue.getName())) {
                    s1 = "你";
                } else {
                    s1 = "<" + new String(clientValue.getSendName(),"Gbk") + ">";
                }
                for (int j = 0; j < 4; j++) {
                    clientValue.getToName()[j] = bytes[j + i + 4];
                }
                String s2 = new String(clientValue.getToName(),"Gbk");
                if (Objects.equals(s2, "ALLA")) {
                    s2 = "对大家说：";
                } else if (Objects.equals(s2,clientValue.getName())) {
                    s2 = "对你说：";
                } else {
                    s2 = "对<" + new String(clientValue.getToName(),"Gbk") + ">说：";
                }
                for (int j = 0; j < 4; j++) {
                    clientValue.getMessageOrFile()[j] = bytes[j + i + 8];
                }
                String s3 = new String(clientValue.getMessageOrFile(),"Gbk");
                if (Objects.equals(s3, "MESM")) {
                    int j=clientValue.getEnd(bytes,"********".getBytes("Gbk"),i+12)-8;
                    byte[] getMessage = new byte[j];
                    System.arraycopy(bytes, i+12, getMessage, 0, j);
                    String s4 = new String(getMessage,"Gbk");
                    clientValue.getBody().append(s1 + s2 + s4 + "\n");
                } else if (Objects.equals(s3, "LIST")) {
                	int j=clientValue.getEnd(bytes,"********".getBytes("Gbk"),i+12)-8;
                    byte[] getMessage = new byte[j];
                    System.arraycopy(bytes, i+12, getMessage, 0, j);
                    String namelist = new String(getMessage,"Gbk");
                    if(namelist != "") {
                    	clientValue.getBody().append("更新用户列表！"+"\n");
    					String[] name = namelist.split(" ");
    					clientValue.setTotalName(name);
    					clientValue.setUserNameList();
    					for(int n = 0; n < name.length; n++) {
    						clientValue.getBody().append("用户：" + name[n] + "\n");
    					}
    				}
                }else if (Objects.equals(s3, "FILE")) {
                	int index = clientValue.getEnd(bytes,"********".getBytes("Gbk"),8+12)-8;
                    byte[] getMessage = new byte[index];
                    System.arraycopy(bytes, 8+12, getMessage, 0, index);
                    i = clientValue.getEnd(getMessage, "@@@@@@@@".getBytes("Gbk"), 0)-8;
                    byte[] v = new byte[i];
                    System.arraycopy(getMessage, 0, v, 0, i);
                    String name = new String(v,"Gbk");
                    name = name + "副本";
                    byte[] f = new byte[(getMessage.length - 8 - i)];
                    System.arraycopy(getMessage, (i + 8), f, 0, (getMessage.length - 8 - i));
                    File file = new File(name);
                    if (!file.exists()) {
                         try {
                             file.createNewFile();
                         } catch (IOException e) {
                        	 e.printStackTrace();
                         }
                    }
                    FileOutputStream writer = new FileOutputStream(name, true);
                    writer.write(f);
                    writer.close();
                }
            }
        } catch (Exception e) {
        	System.out.println("客户端退出。");
//            e.printStackTrace();
        }
    }
}
