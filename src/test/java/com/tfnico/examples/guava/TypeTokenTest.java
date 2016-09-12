/**  
 * Project Name:guava_study  
 * File Name:TypeTokenTest.java  
 * Package Name:com.tfnico.examples.guava  
 * Date:2016年9月12日上午11:26:16  
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
 * Date:     2016年9月12日 上午11:26:16 <br/>  
 * @author   hzlimao  
 * @version    
 * @since    JDK 1.8 
 * @see        
 */
public class TypeTokenTest {
	@Test
	public void test1(){
		//获取一个基本的、原始类的TypeToken非常简单：
		TypeToken<String> stringTok = TypeToken.of(String.class);
		TypeToken<Integer> intTok = TypeToken.of(Integer.class);
		//为获得一个含有泛型的类型的TypeToken —— 当你知道在编译时的泛型参数类型 —— 你使用一个空的匿名内部类：
		TypeToken<List<String>> stringListTok = new TypeToken<List<String>>() {};
		//或者你想故意指向一个通配符类型：
		TypeToken<Map<?, ?>> wildMapTok = new TypeToken<Map<?, ?>>() {};
	}
	
	static <K, V> TypeToken<Map<K, V>> mapToken(TypeToken<K> keyToken, TypeToken<V> valueToken) {
	    return new TypeToken<Map<K, V>>() {}
	        .where(new TypeParameter<K>() {}, keyToken)
	        .where(new TypeParameter<V>() {}, valueToken);
	}
}
  
