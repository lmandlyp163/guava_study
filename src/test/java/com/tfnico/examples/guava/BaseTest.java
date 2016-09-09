package com.tfnico.examples.guava;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Defaults;
import com.google.common.base.Equivalences;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.base.Supplier;
import com.google.common.base.Throwables;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;

public class BaseTest {
	//使用和避免null---------前置条件-----------常见Object方法---------排序: Guava强大的”流畅风格比较器---------Throwables：简化了异常和错误的传播与检查>>>>>>>>>>>>>>>>>>>>>>>>>>
	@Test
	public void optional(){
		System.out.println(1);
		Optional<Integer> possible = Optional.of(5);//创建指定引用的Optional实例，若引用为null则快速失败
		Optional<Integer> possible2 = Optional.fromNullable(null);//创建指定引用的Optional实例，若引用为null则表示缺失
		possible.isPresent(); // returns true如果Optional包含非null的引用（引用存在），返回true
		possible.get(); // returns 5
		System.out.println(possible.get()+"--"+possible.isPresent());
		System.out.println(possible2.or(2)+"---");//	返回Optional所包含的引用，若引用缺失，返回指定的值
//		System.out.println(possible2.get()+"---"+possible2.isPresent());
	}
	@Test
	public void checkArgument(){// 前置条件 判断
		Preconditions.checkArgument(1 >= 0, "Argument was %s but expected nonnegative", 1);
		Preconditions.checkArgument(1 < 2, "Expected i < j, but %s > %s", 1, 2);
//		Preconditions.checkArgument(2 < 1, "Expected i < j, but %s > %s", 2, 1);
	}
	@Test
	public void object(){
//		System.out.println(Objects.toStringHelper("BaseTest").add("x", 1).toString());
//		System.out.println(Objects.toStringHelper("BaseTest").add("x", 1).add("y", 2).toString());//BaseTest{x=1, y=2}
		System.out.println(ComparisonChain.start().compare(1, 2).compare(4, 3).result());//先根据前面的规则来判断，相同再往下判断
		
	}

	@Test
	public void Ordering1(){
        List<String> list = Lists.newArrayList();
        list.add("peida");
        list.add("jerry");
        list.add("harry");
        list.add("eva");
        list.add("jhon");
        list.add("neron");
        
        System.out.println("list:"+ list);

        Ordering<String> naturalOrdering = Ordering.natural();     //使用Comparable类型的自然顺序， 例如：整数从小到大，字符串是按字典顺序;   
        Ordering<Object> usingToStringOrdering = Ordering.usingToString();//使用toString()返回的字符串按字典顺序进行排序；
        Ordering<Object> arbitraryOrdering = Ordering.arbitrary();//返回一个所有对象的任意顺序
        
        System.out.println("naturalOrdering:"+ naturalOrdering.sortedCopy(list));     
        System.out.println("usingToStringOrdering:"+ usingToStringOrdering.sortedCopy(list));        
        System.out.println("arbitraryOrdering:"+ arbitraryOrdering.sortedCopy(list));
	}

	@Test
	public void Ordering2(){
        List<String> list = Lists.newArrayList();
        list.add("peida");
        list.add("jerry");
        list.add("harry");
        list.add("eva");
        list.add("jhon");
        list.add("neron");
        
        System.out.println("list:"+ list);
        
        Ordering<String> naturalOrdering = Ordering.natural();
        System.out.println("naturalOrdering:"+ naturalOrdering.sortedCopy(list));    

        List<Integer> listReduce= Lists.newArrayList();
        for(int i=9;i>0;i--){
            listReduce.add(i);
        }
        
        List<Integer> listtest= Lists.newArrayList();
        listtest.add(1);
        listtest.add(1);
        listtest.add(1);
        listtest.add(2);
        
        
        Ordering<Integer> naturalIntReduceOrdering = Ordering.natural();
        
        System.out.println("listtest:"+ listtest);
        System.out.println(naturalIntReduceOrdering.isOrdered(listtest));//判断可迭代对象是否已按排序器排序：允许有排序值相等的元素。返回true
        System.out.println(naturalIntReduceOrdering.isStrictlyOrdered(listtest));//是否严格有序。请注意，Iterable不能少于两个元素。
        
        
        System.out.println("naturalIntReduceOrdering:"+ naturalIntReduceOrdering.sortedCopy(listReduce));
        System.out.println("listReduce:"+ listReduce);
        
        
        System.out.println(naturalIntReduceOrdering.isOrdered(naturalIntReduceOrdering.sortedCopy(listReduce)));
        System.out.println(naturalIntReduceOrdering.isStrictlyOrdered(naturalIntReduceOrdering.sortedCopy(listReduce)));
        

        Ordering<String> natural = Ordering.natural();
              
        List<String> abc = ImmutableList.of("a", "b", "c");
        System.out.println(natural.isOrdered(abc));
        System.out.println(natural.isStrictlyOrdered(abc));
        
        System.out.println("isOrdered reverse :"+ natural.reverse().isOrdered(abc));
 
        List<String> cba = ImmutableList.of("c", "b", "a");
        System.out.println(natural.isOrdered(cba));
        System.out.println(natural.isStrictlyOrdered(cba));
        System.out.println(cba = natural.sortedCopy(cba));
        
        System.out.println("max:"+natural.max(cba));
        System.out.println("min:"+natural.min(cba));
        
        System.out.println("leastOf:"+natural.leastOf(cba, 2));
        System.out.println("naturalOrdering:"+ naturalOrdering.sortedCopy(list));    
        System.out.println("leastOf list:"+naturalOrdering.leastOf(list, 3));
        System.out.println("greatestOf:"+naturalOrdering.greatestOf(list, 3));
        System.out.println("reverse list :"+ naturalOrdering.reverse().sortedCopy(list));    
        System.out.println("isOrdered list :"+ naturalOrdering.isOrdered(list));
        System.out.println("isOrdered list :"+ naturalOrdering.reverse().isOrdered(list));
        list.add(null);
        System.out.println(" add null list:"+list);
        System.out.println("nullsFirst list :"+ naturalOrdering.nullsFirst().sortedCopy(list));
        System.out.println("nullsLast list :"+ naturalOrdering.nullsLast().sortedCopy(list));
	}

	
	
    @Test
    public void Ordering3(){
 
        List<String> stringList = Lists.newArrayList("b","a","h","z","d","e","3","1","k","a","9","m","k");
        System.out.println(Ordering.natural().max(stringList)); // z
        System.out.println(Ordering.natural().min(stringList)); // 1
 
        System.out.println("====================================================");
 
        List<String> stringList1 = Lists.newArrayList("b","a","h","z","d","e","3","1",null,"k","a","9","m","k");
        System.out.println(stringList1);//[b, a, h, z, d, e, 3, 1, null, k, a, 9, m, k]
        List<String> stringList2 = Ordering.natural().nullsFirst().sortedCopy(stringList1);
        System.out.println("自然排序：" + stringList2);//自然排序：[null, 1, 3, 9, a, a, b, d, e, h, k, k, m, z]
 
        List<String> stringList3 = Ordering.usingToString().nullsFirst().sortedCopy(stringList1);
        System.out.println("字符串排序：" + stringList3);//字符串排序：[null, 1, 3, 9, a, a, b, d, e, h, k, k, m, z]
 
        System.out.println("====================================================");
 
        List<String> stringList4 = Lists.newArrayList("ba","addd","hdd","z","d2243","e5","3325235","1262",null,"k3","a1","9","m3","k333");
        System.out.println(stringList4);
        //常用集合的排序
        Ordering<String> ordering = new Ordering<String>() {
            @Override
            public int compare(String left, String right) {
                return Ints.compare(left.length(),right.length());
            }
        };
        List<String> stringList5 = ordering.nullsFirst().reverse().sortedCopy(stringList4);//reverse获取语义相反的排序器
        System.out.println("自定义排序：" + stringList5);//自定义排序：[3325235, d2243, addd, 1262, k333, hdd, ba, e5, k3, a1, m3, z, 9, null]
        System.out.println("是否是有序的？：" + ordering.nullsFirst().reverse().isOrdered(stringList5));
        // 取出最大和最小的的3个
        List<String> stringList6 = ordering.nullsFirst().reverse().greatestOf(stringList4, 3);
        List<String> stringList7 = ordering.nullsFirst().reverse().leastOf(stringList4, 3);
        System.out.println(stringList6);
        System.out.println(stringList7);
 
    }
	
    @Test
	public void throwables(){
        try {
            throw new Exception();
        } catch (Throwable t) {
            String ss = Throwables.getStackTraceAsString(t);
            System.out.println("ss:"+ss);
            Throwables.propagate(t);
        }
        
//        try {
//            throw new IOException();
//        } catch (Throwable t) {
//            Throwables.propagateIfInstanceOf(t, IOException.class);
//            throw Throwables.propagate(t);
//        }

	}
	
	
	
	
	
	
	//================字符串处理：分割，连接，填充>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	
	
    @Test
    public void charSetsAndDefaults() {
        // Here's some charsets
        Charset utf8 = Charsets.UTF_8;
        assertTrue(utf8.canEncode());

        // Primitive defaults:
        Integer defaultValue = Defaults.defaultValue(int.class);
        assertEquals(0, defaultValue.intValue());
    }

    @Test
    public void equalityAndIdentity() {
        // These could be useful for building equals methods
        assertFalse(Equivalences.equals().equivalent("you", null));
        assertTrue(Equivalences.identity().equivalent("hey", "hey"));
    }

    //连接器[Joiner]
    @Test
    public void joinSomeStrings() {
    	Joiner joiner = Joiner.on("; ").skipNulls();//skipNulls()方法是直接忽略null
    	System.out.println(joiner.join("Harry", null, "Ron", "Hermione"));//Harry; Ron; Hermione
    	Joiner joiner2 = Joiner.on("; ").useForNull("空");//useForNull(String)方法可以给定某个字符串来替换null
    	List<String> list=Lists.newArrayList();
    	list.add("1");
    	list.add(null);
    	list.add("2");
    	System.out.println(joiner2.join("Harry", null, "Ron", "Hermione"));//Harry; 空; Ron; Hermione
    	System.out.println(joiner2.join(list));//1; 空; 2
    	System.out.println(Joiner.on(";").useForNull("null").join(list));//1; null; 2
    	
    	Joiner.on(",").join(Arrays.asList(1, 5, 7)); // returns "1,5,7"
        ImmutableSet<String> strings = ImmutableSet.of("A", "B", "C");
        String joined = Joiner.on(":").join(strings);
        assertEquals("A:B:C", joined);
    }

    @Test
    public void splitSomeStrings() {
        String string = "A:B:C";

        String[] parts = string.split(":"); // the old way
        String backTogether = Joiner.on(":").join(parts);
        assertEquals(string, backTogether);

        String gorbleString = ": A::: B : C :::";
        Iterable<String> gorbleParts = Splitter.on(":").omitEmptyStrings()//omitEmptyStrings 从结果中自动忽略空字符串
                .trimResults().split(gorbleString);//trimResults移除结果字符串的前导空白和尾部空白
        System.out.println(gorbleParts);//[A, B, C]
        String gorbleBackTogether = Joiner.on(":").join(gorbleParts);
        System.out.println(gorbleBackTogether);
        assertEquals(string, gorbleBackTogether); // A:B:C
        
        //字符匹配器[CharMatcher]
        Iterable<String> gorbleParts2 = Splitter.on(CharMatcher.BREAKING_WHITESPACE).split("");
        
    }

    @Test
    public void moreFunWithStrings() {
        assertNull(Strings.emptyToNull(""));
        assertEquals("", Strings.nullToEmpty(null));
        assertTrue(Strings.isNullOrEmpty("")); // About the only thing we ever
                                               // used in commons-lang? :)
        assertEquals("oioioi", Strings.repeat("oi", 3));//重复3次 

        String a = "Too short      ";
        String b = a + " ";
        assertEquals("Too short      ",
                Strings.padEnd("Too short", a.length(), ' '));
        assertFalse("Too short      "
                .equals(Strings.padEnd(b, a.length(), ' ')));

        assertEquals(a, Strings.commonPrefix(a, b));
        assertEquals("      ", Strings.commonSuffix(a, b));
    }

    // Some customers
    Customer bob = new Customer(1, "Bob");
    Customer lisa = new Customer(2, "Lisa");
    Customer stephen = new Customer(3, "Stephen");
    Customer ken = new Customer(null, "Ken");

    @Test
    public void toStringsAndHashcodes() {
        Object[] bobAndLisa = new Object[] { bob, lisa };

        // Make some hashcode!
        int hashCode = Objects.hashCode(bob, lisa);

        assertEquals(Arrays.hashCode(bobAndLisa), hashCode);

        // Build toString method
        String string = Objects.toStringHelper(bob).add("name", bob.getName())
                .add("id", bob.getId()).toString();
        assertEquals("Customer{name=Bob, id=1}", string);
    }

    @Test(expected = NullPointerException.class)
    public void needAnIntegerWhichIsNeverNull() {
        Integer defaultId = 0;
        Integer kensId = ken.getId() != null ? ken.getId() : defaultId;
        assertEquals(kensId, defaultId);
        // this one does not throw!

        int kensId2 = Objects.firstNonNull(ken.getId(), null);
        assertEquals(0, kensId2);
        // But the above does! That means that at least one parameter of
        // Objects.firstNonNull should be nonNull
    }

    @Test(expected = IllegalArgumentException.class)
    public void somePreconditions() {
        // Pretend this is a constructor:
        Preconditions.checkNotNull(lisa.getId()); // Will not throw NPE
        assertEquals(Preconditions.checkNotNull(lisa.getId()),
                Integer.valueOf(2));

        Preconditions.checkState(!lisa.isSick()); // Will throw
                                                  // IllegalStateException
        Preconditions.checkArgument(lisa.getAddress() != null,
                "We couldn't find the description for customer with id %s",
                lisa.getId());
    }

    //=============================Function===>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void someFunctions() {
        assertEquals("Bob (id 1)", bob.toString());

        Function<Object, String> toStringFunction = Functions
                .toStringFunction();
        assertEquals("Bob (id 1)", toStringFunction.apply(bob));
    }

    @Test
    public void fancierFunctions() {//Functions[函数] TODO 还没有看
        Function<Customer, Boolean> isCustomerWithOddId = new Function<Customer, Boolean>() {//Customer入参 Boolean出参
            public Boolean apply(Customer customer) {
                return customer.getId().intValue() % 2 != 0;
            }
        };

        assertTrue(isCustomerWithOddId.apply(bob));
        assertFalse(isCustomerWithOddId.apply(lisa));

        // Functions are great for higher-order functions, like
        // project/transform, and fold
    }

    @Test
    public void somePredicates() {//Predicates[断言]  
        ImmutableSet<Customer> customers = ImmutableSet.of(bob, lisa, stephen);

        Predicate<Customer> itsBob = Predicates.equalTo(bob);
        Predicate<Customer> itsLisa = Predicates.equalTo(lisa);
        Predicate<Customer> bobOrLisa = Predicates.or(itsBob, itsLisa);

        // Predicates are great to pass in to higher-order functions like
        // filter/search
        Iterable<Customer> filtered = Iterables.filter(customers, bobOrLisa);
        assertEquals(2, ImmutableSet.copyOf(filtered).size());
        assertEquals(ImmutableSet.of(bob, lisa), ImmutableSet.copyOf(filtered));
    }

}

class Customer {

    private Integer id;
    private String name;

    public Customer(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Customer)) {
            return false;
        }

        Customer that = (Customer) obj;
        return Objects.equal(id, that.getId())
                && Objects.equal(name, that.getName());

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name);
    }

    @Override
    public String toString() {
        return name + " (id " + id + ")";
    }

    public Integer getId() {
        return id;
    }

    public boolean isSick() {
        return false;
    }

    public String getAddress() {
        return null;
    }

    public String getName() {
        return name;
    }
}

class Ingredients {

}

class Cake {
    Cake(Ingredients ingredients) {

    }
}

class IngredientsFactory implements Supplier<Ingredients> {

    private int counter;

    public Ingredients get() {
        counter++;
        return new Ingredients();
    }

    int getNumberOfIngredientsUsed() {
        return counter;
    }

}
