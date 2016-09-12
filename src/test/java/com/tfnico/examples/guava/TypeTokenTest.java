/**  
 * Project Name:guava_study  
 * File Name:TypeTokenTest.java  
 * Package Name:com.tfnico.examples.guava  
 * Date:2016��9��12������11:26:16  
 * Copyright (c) 2016,  All Rights Reserved.  
 *  
*/  
  
package com.tfnico.examples.guava;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;

/**  
 * ClassName:TypeTokenTest <br/>  
 * Date:     2016��9��12�� ����11:26:16 <br/>  
 * @author   hzlimao  
 * @version    
 * @since    JDK 1.8 
 * @see        
 */
public class TypeTokenTest {
	@Test
	public void test1(){
		//��ȡһ�������ġ�ԭʼ���TypeToken�ǳ��򵥣�
		TypeToken<String> stringTok = TypeToken.of(String.class);
		TypeToken<Integer> intTok = TypeToken.of(Integer.class);
		//Ϊ���һ�����з��͵����͵�TypeToken ���� ����֪���ڱ���ʱ�ķ��Ͳ������� ���� ��ʹ��һ���յ������ڲ��ࣺ
		TypeToken<List<String>> stringListTok = new TypeToken<List<String>>() {};
		//�����������ָ��һ��ͨ������ͣ�
		TypeToken<Map<?, ?>> wildMapTok = new TypeToken<Map<?, ?>>() {};
	}
	
	static <K, V> TypeToken<Map<K, V>> mapToken(TypeToken<K> keyToken, TypeToken<V> valueToken) {
	    return new TypeToken<Map<K, V>>() {}
	        .where(new TypeParameter<K>() {}, keyToken)
	        .where(new TypeParameter<V>() {}, valueToken);
	}
}
  