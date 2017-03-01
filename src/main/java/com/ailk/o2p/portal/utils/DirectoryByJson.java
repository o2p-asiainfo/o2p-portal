package com.ailk.o2p.portal.utils;

import java.util.List;

import com.ailk.eaap.op2.bo.Directory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DirectoryByJson {
	public static JSONArray createTreeJson(List<Directory> list) {
		JSONArray rootArray = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			Directory dir = list.get(i);
			//判定是否父节点
			if (dir.getFaDirId()==null||dir.getFaDirId() == 0) {
				JSONObject rootObj = createBranch(list, dir);
				rootArray.add(rootObj);
			}
		}

		return rootArray;

	}

	/**
	 * 
	 * 递归创建分支节点Json对象
	 * 
	 * @param list
	 *            创建树的原始数据
	 * 
	 * @param currentNode
	 *            当前节点
	 * 
	 * @return 当前节点与该节点的子节点json对象
	 * 
	 */

	private static JSONObject createBranch(List<Directory> list, Directory currentNode) {

		/*
		 * 
		 * 将javabean对象解析成为JSON对象
		 * 
		 */

		JSONObject currentObj = JSONObject.fromObject(currentNode);

		JSONArray childArray = new JSONArray();

		/*
		 * 
		 * 循环遍历原始数据列表，判断列表中某对象的父id值是否等于当前节点的id值，
		 * 
		 * 如果相等，进入递归创建新节点的子节点，直至无子节点时，返回节点，并将该
		 * 
		 * 节点放入当前节点的子节点列表中
		 * 
		 */

		for (int i = 0; i < list.size(); i++) {
			Directory newNode = list.get(i);
			if (newNode.getFaDirId() != null && newNode.getFaDirId().compareTo(currentNode.getDirId()) == 0) {
				JSONObject childObj = createBranch(list, newNode);
				childArray.add(childObj);
			}
		}

		/*
		 * 
		 * 判断当前子节点数组是否为空，不为空将子节点数组加入children字段中
		 * 
		 */

		if (!childArray.isEmpty()) {
			currentObj.put("children", childArray);
		}

		return currentObj;

	}
}
