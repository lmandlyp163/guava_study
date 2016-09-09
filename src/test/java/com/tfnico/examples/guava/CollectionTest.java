package com.tfnico.examples.guava;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Constraints;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public class CollectionTest {

	@Test
	public void Immutable(){//不可变的集合
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
	
	public void multiset(){//新集合类型  Multiset
		
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
