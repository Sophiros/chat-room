package com.client2;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

class MyComboBox extends AbstractListModel<String> implements ComboBoxModel<String> {
	/**
	 * 设置下拉列表，为私聊准备；改变的数据，即显示数据：selecteditem
	 */
	private static final long serialVersionUID = 1L;
	String selecteditem = "全体";
	String[] username = { "全体", "秦赞", "李佳航", "张涵文","冯紫楠","孙小鱼"};
	
	public String getElementAt(int index) {
		return username[index];
	}
	
	public int getSize() {
		return username.length;
	}
	
	public void setSelectedItem(Object item) {
		selecteditem = (String) item;
		System.out.println("选择对象： "+selecteditem);
	}
	
	public Object getSelectedItem() {
		return selecteditem;
	}
	
	public int getIndex() {
		for (int i = 0; i < username.length; i++) {
			if (username[i].equals(getSelectedItem()))
				return i;
		}
		return 0;
	}
	//检查用户列表中是否已经有需要添加的用户，如果没有添加
	public void addusername(String name) {
		int j = 0;
		for(int i = 0;i < username.length;i++){
			if(username[i] == name)
				j = 1;
		}
		if(j != 1){
			username[username.length] = name;
		}
		
	}
}
