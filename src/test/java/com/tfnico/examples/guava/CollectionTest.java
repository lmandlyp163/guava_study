package com.tfnico.examples.guava;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.Constraints;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

public class CollectionTest {
	//======>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>不可变的集合
	@Test
	public void Immutable(){//不可变的集合   Immutable=不变
		//不可变集合可以用如下多种方式创建：
		//Builder工具
		final ImmutableSet<String> ImmutableSetString =
				        ImmutableSet.<String>builder()
				            .add("a")
				            .add("b")
				            .add("c")
				            .build();
		//所有不可变集合都有一个asList()方法提供ImmutableList视图，
		System.out.println(ImmutableSetString.asList().get(0));
		//of方法，如ImmutableSet.of(“a”, “b”, “c”)或 ImmutableMap.of(“a”, 1, “b”, 2);
		final ImmutableMap<String, Integer> immutableMap=ImmutableMap.of("a", 1, "b", 2); 
		System.out.println(immutableMap.get("a"));
		//copyOf方法，如ImmutableSet.copyOf(set);
		
	}
	
	
	
	//======>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>新集合类型
	@Test
	public void multiset(){//新集合类型  Multiset  SortedMultiset
		//集合[set]概念的延伸，它的元素可以重复出现
		/*当把Multiset看成普通的Collection时，它表现得就像无序的ArrayList：
		  add(E)添加单个给定元素
	      iterator()返回一个迭代器，包含Multiset的所有元素（包括重复的元素）
		  size()返回所有元素的总个数（包括重复的元素）*/
		Multiset<String> multiset=HashMultiset.create();
		multiset.add("a");
		multiset.add("a");
		multiset.add("b");
		multiset.add("c");
		multiset.add("d");
		System.out.println(multiset.size()+"------"+multiset.iterator());//5
		
		
		/*当把Multiset看作Map<E, Integer>时，它也提供了符合性能期望的查询操作：
		count(Object)返回给定元素的计数。HashMultiset.count的复杂度为O(1)，TreeMultiset.count的复杂度为O(log n)。
		entrySet()返回Set<Multiset.Entry<E>>，和Map的entrySet类似。
		elementSet()返回所有不重复元素的Set<E>，和Map的keySet()类似。
		所有Multiset实现的内存消耗随着不重复元素的个数线性增长。*/
		System.out.println(multiset.count("a")+"------");//2-返回给定元素的计数
		System.out.println(multiset.entrySet()+"------");//[a x 2, b, c, d]
		System.out.println(multiset.remove("a", 2)+"------");//减少给定元素在Multiset中的计数
		System.out.println(multiset.entrySet()+"------");//[b, c, d]
		System.out.println(multiset.elementSet().size()+"------");//4-返回不重复元素的集合，类型为Set<E>
	}
	
	//http://ifeve.com/google-guava-newcollectiontypes/
	@Test
	public void multimap(){//新集合类型  Multimap  Map<K, List<V>>或Map<K, Set<V>>
		//一般来说，Multimap接口应该用第一种方式看待，但asMap()视图返回Map<K, Collection<V>>，让你可以按另一种方式看待Multimap。
		//重要的是，不会有任何键映射到空集合：一个键要么至少到一个值，要么根本就不在Multimap中
		//很少会直接使用Multimap接口，更多时候你会用ListMultimap或SetMultimap接口，它们分别把键映射到List或Set。
		
		//Multimap.get(key)以集合形式返回键所对应的值视图，即使没有任何对应的值，也会返回空集合。ListMultimap.get(key)返回List，SetMultimap.get(key)返回Set。
		Multimap<String,Object> multimap=HashMultimap.create();//键行为类似-HashMap 值行为类似-HashSet
		Multimap<String,Object> multimap2=ArrayListMultimap.create();//键行为类似-HashMap 值行为类似-ArrayList
		multimap.put("1", "a");
		multimap.put("2", "b");
		multimap.put("3", "c1");
		multimap.put("3", "c1");
		multimap.put("3", "c2");
		multimap.put("4", new String[]{"d1","d2"});
		//[a]--->[b]--->[c1, c2]--->[[Ljava.lang.String;@5ef04b5]--->    会去查
		System.out.println(multimap.asMap().get("1")+"--->"+multimap.asMap().get("2")+"--->"+multimap.asMap().get("3")+"--->"+multimap.asMap().get("4")+"--->");
		
		multimap2.put("1", "a");
		multimap2.put("2", "b");
		multimap2.put("3", "c1");
		multimap2.put("3", "c1");
		multimap2.put("3", "c2");
		multimap2.put("4", new String[]{"d1","d2"});
		//[a]--->[b]--->[c1, c1, c2]--->[[Ljava.lang.String;@14514713]--->
		System.out.println(multimap2.asMap().get("1")+"--->"+multimap2.asMap().get("2")+"--->"+multimap2.asMap().get("3")+"--->"+multimap2.asMap().get("4")+"--->");
	}
	
	@Test
	public void biMap(){//新集合类型  BiMap  实现键值对的双向映射
		BiMap<String, Integer> biMap = HashBiMap.create();//(a,1)
		biMap.put("a", 1);
		BiMap< Integer,String> biMap2=biMap.inverse();//(i,a)
		System.out.println(biMap2.get(1)+"==="+biMap.get("a"));
	}
	
	@Test
	public void table(){//Table，它有两个支持所有类型的键：”行”和”列”  类似Map<FirstName, Map<LastName, Person>>
		Table<Integer, Integer, Integer> weightedGraph = HashBasedTable.create();
		weightedGraph.put(1, 1, 11);
		weightedGraph.put(1, 2, 12);
		weightedGraph.put(1, 3, 13);
		weightedGraph.put(2, 1, 21);
		weightedGraph.put(2, 2, 22);
		weightedGraph.put(2, 3, 23);
		System.out.println(weightedGraph.row(1));; // 用Map<C, V>返回给定”行”的所有列
		System.out.println(weightedGraph.row(2));; // 用Map<C, V>返回给定”行”的所有列
//		weightedGraph.column(v3); // returns a Map mapping v1 to 20, v2 to 5

	}
	
	
	
	
	
	
	
	//--------------------------------------------------------------------------------------------------------------------------
    // Some customers
    Customer bob = new Customer(1, "Bob");
    Customer lisa = new Customer(2, "Lisa");
    Customer stephen = new Customer(3, "Stephen");
    Customer ken = new Customer(null, "Ken");

    @Test
    public void someSets() {
        ImmutableSet<Customer> customers1 = ImmutableSet.of(bob, lisa, stephen);
        ImmutableSet<Customer> customers2 = ImmutableSet.of(stephen, ken);

        assertEquals(4, Sets.union(customers1, customers2).size());

        assertEquals(ImmutableSet.of(stephen),
                Sets.intersection(customers1, customers2));
    }

    @Test(expected = NullPointerException.class)
    public void someConstraints() {
        HashSet<Customer> customers = Sets.newHashSet(); // instead of new
                                                         // HashSet<Customer>()
        customers.add(null); // this works. But should it?

        Set<Customer> noMoreNulls = Constraints.constrainedSet(customers,
                Constraints.notNull());
        noMoreNulls.add(null); // boom!
    }

    @Test
    public void some() {
        // TODO: Add more collections demos
    }

}
