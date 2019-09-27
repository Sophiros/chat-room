package com.client3;

import java.io.*;
import javax.swing.*;

public class Filepathchooser extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String filepath;
	Boolean ensureswitch = true;
	
	public   Filepathchooser() {
		JFileChooser fileChooser = new JFileChooser();// 创建文件选择对话框
		// 显示文件选择对话框
		int i = fileChooser.showOpenDialog(getContentPane());
		// 判断用户单击的是否为“打开”按钮
		if (i == JFileChooser.APPROVE_OPTION) {
			// 获得选中的文件对象
			File selectedFile = fileChooser.getSelectedFile();
			// 显示选中文件的名称
			filepath = selectedFile.getAbsolutePath();
				
		}
		
	}

	public String getPath() {
		if(filepath != null){
			return filepath;
		}
		return filepath;
	}
}

