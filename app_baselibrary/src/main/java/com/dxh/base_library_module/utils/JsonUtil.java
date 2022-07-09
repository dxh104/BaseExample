package com.dxh.base_library_module.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

	/**
	 * 对单个JavaBean对象的解析
	 * @param jsonString 表示JSON数据的字符串
	 * @param cls JavaBean 类型
	 * @return
	 */
	public static <T> T parseObject(String jsonString, Class<T> clazz){
		T t = null;
		try {
			t = JSON.parseObject(jsonString, clazz);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 对单个JavaBean对象的解析
	 * @param jsonString 表示JSON数据的字符串
	 * @param cls JavaBean 类型
	 * @return
	 */
	public static <T> T parseObject(String jsonString, Type type){
		T t = null;
		try {
			t = JSON.parseObject(jsonString, type);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 对单个JavaBean对象的解析
	 * @param jsonString 表示JSON数据的字符串
	 * @param cls JavaBean 类型
	 * @return
	 */
	public static <T> T parseObject(String jsonString, TypeReference<T> typeReference){
		T t = null;
		try {
			t = JSON.parseObject(jsonString, typeReference);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return t;
	}

	/**
	 * 对单个JavaBean对象的解析
	 * @param jsonString 表示JSON数据的字符串
	 * @param cls JavaBean 类型
	 * @return
	 */
	public static <T> List<T> parseList(String jsonString, Class<T> clazz){
		List<T> tList = new ArrayList<T>();
		try {
			tList = JSON.parseArray(jsonString, clazz);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return tList;
	}

	/**
	 * 从表示集合元素的JSON字符串中获取指定类型的第一个元素对象
	 * @param jsonString 集合元素的JSON字符串
	 * @param clazz 指定类型
	 * @return
	 */
	public static <T> T parseFirstFromJsonList(String jsonString, Class<T> clazz){
		T t = null;
		try {
			List<T> tempList = JsonUtil.parseList(jsonString, clazz);
			if(null != tempList && tempList.size() > 0){
				t = tempList.get(0);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return t;
	}

	/**
	 * 将对象转换为JSON字符串
	 * @param object 待转换对象
	 * @return
	 */
	public static String toJSONString(Object object){
		String jsonString = "";
		try {
			jsonString = JSON.toJSONString(object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return jsonString;
	}

	/**
	 * 将对象转换为JSONObject对象
	 * @param object 待转换对象
	 * @return
	 */
	public static JSONObject toJSON(Object object){
		JSONObject jsonObj = null;
		try {
			jsonObj = (JSONObject) JSON.toJSON(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObj;
	}

	/**
	 * 将对象转换为JSONArray对象
	 * @param object 待转换对象
	 * @return
	 */
	public static JSONArray toJSONArray(Object object){
		JSONArray jsonArray = null;
		try {
			jsonArray = (JSONArray) JSON.toJSON(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArray;
	}

}
