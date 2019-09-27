package com.client2;

import java.awt.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class picturefilepath extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTextField filePath = null;
	private JButton openFile = null;
	private JButton ensureButton = null;
	private File selectedFile;
	private static String picturefilepath;
	private boolean Bensurevalue = true;
	private JTextField getJTextField() {
		if (filePath == null) {
			filePath = new JTextField();
			filePath.setPreferredSize(new Dimension(200, 30));
			filePath.setEditable(false);
		}
		return filePath;
	}
	
	// 获取选择文件按钮的方法
	public JButton getOpenFile() {
		if (openFile == null) {
			openFile = new JButton();
			openFile.setText("选择文件");
			openFile.addActionListener(new java.awt.event.ActionListener() {
				
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser
							.setFileFilter(new FileNameExtensionFilter(
									"支持的图像文件（*.bmp、*.jpeg、*.gif", "png",
									"jpg"));
					fileChooser.showOpenDialog(picturefilepath.this);
					selectedFile = fileChooser.getSelectedFile();
					filePath.setText(selectedFile.getAbsolutePath());
					setFilepath(selectedFile.getAbsolutePath());
					
					setFilepath(selectedFile.getAbsolutePath());
					System.out.println("duqulujing:"+picturefilepath);
				}
			});
		}
		return openFile;
	}
	
	// 获取确认按钮的方法
	private JButton getensureButton() {
		if (ensureButton == null) {
			ensureButton = new JButton();
			ensureButton.setText("确定");
			ensureButton
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(
								java.awt.event.ActionEvent e) {
							if(picturefilepath == null){
								popWindows("图片文件路径不能为空!","错误");
							}else{
								popWindows("读取图片的路径成功！","正确");
							}
							Bensurevalue = false;
							//System.out.println("duqulujing:"+picturefilepath);
						}
					});
		}
		return ensureButton;
	}
	
	//设置警告弹窗
	public void popWindows(String strWarning, String strTitle) {
		JOptionPane.showMessageDialog(this, strWarning, strTitle,
				JOptionPane.INFORMATION_MESSAGE);
	}
	public static void main(String[] args) {
		new picturefilepath();
		
	}
	
	public picturefilepath() {
		super();
		initialize();
	}
	
	// 初始化方法
	private void initialize() {
		this.setSize(408, 100);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(Bensurevalue);
	}
	
	// 初始化内容面板的方法
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new FlowLayout());
			jContentPane.add(getJTextField(), null);
			jContentPane.add(getOpenFile(), null);
			jContentPane.add(getensureButton(), null);
		}
		return jContentPane;
	}
	public static String getFilepath() {
		return picturefilepath;
	}

	public void setFilepath(String filepath) {
		picturefilepath = filepath;
	}
}