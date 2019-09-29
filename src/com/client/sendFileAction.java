package com.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class sendFileAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent arg0) {
        sendFile t = new sendFile();
        Thread print = new Thread(t);
        print.start();
    }
}

class sendFile implements Runnable {
	clientelem clientValue = new clientelem();
    @SuppressWarnings("resource")
	@Override
    public void run() {
        try {
            DataOutputStream out = new DataOutputStream(clientValue.getSocket().getOutputStream());
            File file = new File(clientValue.getFiletext().getText());
            clientValue.setSendName(clientValue.intToByte4(clientValue.getPort()));
            clientValue.setStart("########".getBytes("Gbk"));
            clientValue.setEnd("********".getBytes("Gbk"));
            clientValue.setMessageOrFile("FILE".getBytes("Gbk"));
            clientValue.setToName("ALLA".getBytes("Gbk"));
            byte[] addr = clientValue.getFiletext().getText().getBytes("Gbk");
            byte[] temp = "@@@@@@@@".getBytes("Gbk");
            byte[] fil = new byte[946];
            byte[] message = new byte[996];
			FileInputStream in = new FileInputStream(file);
            while (in.read(fil) != -1) {
                System.arraycopy(addr, 0, message, 0, addr.length);
                System.arraycopy(temp, 0, message, addr.length, temp.length);
                System.arraycopy(fil, 0, message, addr.length+temp.length, clientValue.ActualLength(fil));
                byte[] bytes = clientValue.Package(clientValue.getStart(), clientValue.getSendName(), 
                		clientValue.getMessageOrFile(), clientValue.getToName(), message, clientValue.getEnd());
                out.write(bytes);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
