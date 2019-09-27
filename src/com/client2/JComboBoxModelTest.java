package com.client2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.client.*;

/*聊天室类，负责保存当前登陆的每一个用户，并且当某一个用户给服务器发信息时，他需要立刻把这条信息转发给其他客户机*/
@SuppressWarnings("serial")
public class JComboBoxModelTest extends JFrame {
    private JScrollPane Txtchat;
    private JTextField Txtinput,Txtip,Txtport;
    private static String picfilepath;
    private JComboBox<String> userlist;
    private JScrollPane Txtuser;
    
    JComboBoxModelTest(){
        super("魔方小镇v1.1");
        setBounds(100,100,1150,750);
        Container c = getContentPane();
        c.setLayout(null);

        JButton jbtxt = new JButton("文字发送");jbtxt.setBounds(1000,490,110,60);c.add(jbtxt);
        JButton jbpic = new JButton("图片发送");jbpic.setBounds(1000,560,110,60);c.add(jbpic);

        userlist = new JComboBox<>(new MyComboBox());
        
        Txtinput = new clientelem().getMessage();//打字文本框
        Txtip = new JTextField(null);//服务器IP
        Txtport = new JTextField(null);//端口号
       
        //JTextArea ta = new JTextArea(400,200);
        JTextArea ta = new clientelem().getBody();
        ta.setLineWrap(true);//设置自动换行
        Txtchat = new JScrollPane(ta);//聊天文本框
        
        /*添加文本框*/
       
        Txtinput.setBounds(310,490,690,130);      
        Txtip.setBounds(90,20,200,40);
        Txtchat.setBounds(310,100,800,360);        
        Txtport.setBounds(390,20,200,40);
        userlist.setBounds(700,20,200,40);
        c.add(Txtinput);        
        c.add(Txtip);
        c.add(Txtchat);       
        c.add(Txtport); 
        c.add(userlist);
        Txtchat.setFocusable(false);

        /*添加标签*/
        JLabel j1 = new JLabel("服务器IP:");
        JLabel j2 = new JLabel("端口号:");
        JLabel j3 = new JLabel("请选择联系人:");
        JLabel j4 = new JLabel("当前在线用户:");

        j1.setBounds(30,20,60,40);
        j2.setBounds(330,20,60,40);
        j3.setBounds(610,20,100,40);
        j4.setBounds(30,80,100,40);
        
        c.add(j1);
        c.add(j2);
        c.add(j3);
        c.add(j4);
        
        JTextArea ta1 = new JTextArea();//登录状态显示框
        ta1.setLineWrap(true);//设置自动换行
        Txtuser = new JScrollPane(ta1);
        Txtuser.setBounds(30,120,250,500);
        c.add(Txtuser);
        

        c.setBackground(Color.WHITE);
        this.setResizable(false); 
        setVisible(true);
        //设置窗口大小不可改变（setResizable（boolean value）：value=true，窗口大小可以改变，value=false时用户不可改变）
        //如果不设置，在放大窗口或者拉小窗口四时，键盘会乱
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        Txtip.setText(new clientelem().getServerIP());
        //Txtport.setText(new clientelem().getPort());
        jbtxt.addActionListener(new sendAction());
        //设置按键监听，打开文件选择窗口
        jbpic.addActionListener(new ActionListener() {
            @Override
            public  void actionPerformed(ActionEvent e) {
            	picturefilepath pic = new picturefilepath();
            	
            	pic.getOpenFile();
            	picfilepath = picturefilepath.getFilepath();
            	if(picfilepath != null){
            		System.out.println("图片路径是："+picfilepath);
            	}           	
            }
        });
    }

     
    public static void main(String[] args){
        new JComboBoxModelTest();
    }

}


