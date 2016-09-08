package com.tfnico.examples.guava;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Test;

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
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

public class BaseTest {
//	public static void main(String[] args) {
//		System.out.println(Objects.toStringHelper("BaseTest").add("x", 1).add("y", 2).toString());
//	}
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


	
	
	
	
	
	
	
	
	
	
	
	
	
	//================>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	
	
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

    @Test
    public void joinSomeStrings() {
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
        Iterable<String> gorbleParts = Splitter.on(":").omitEmptyStrings()
                .trimResults().split(gorbleString);
        String gorbleBackTogether = Joiner.on(":").join(gorbleParts);
        assertEquals(string, gorbleBackTogether); // A:B:C
    }

    @Test
    public void moreFunWithStrings() {
        assertNull(Strings.emptyToNull(""));
        assertEquals("", Strings.nullToEmpty(null));
        assertTrue(Strings.isNullOrEmpty("")); // About the only thing we ever
                                               // used in commons-lang? :)
        assertEquals("oioioi", Strings.repeat("oi", 3));

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

    @Test
    public void someFunctions() {
        assertEquals("Bob (id 1)", bob.toString());

        Function<Object, String> toStringFunction = Functions
                .toStringFunction();
        assertEquals("Bob (id 1)", toStringFunction.apply(bob));
    }

    @Test
    public void fancierFunctions() {
        Function<Customer, Boolean> isCustomerWithOddId = new Function<Customer, Boolean>() {
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
    public void somePredicates() {
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
