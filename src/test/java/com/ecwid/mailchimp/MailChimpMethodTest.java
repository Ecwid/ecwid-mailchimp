package com.ecwid.mailchimp;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import static org.testng.Assert.*;

/**
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class MailChimpMethodTest {

	class Method<R> extends MailChimpMethod<R> { }

	class IntegerMethod extends Method<Integer> { }
	class VectorMethod extends Method<Vector<Integer>> { }
	class ArrayMethod extends Method<String[]> { }
	class GenericArrayMethod extends Method<LinkedList<Integer>[]> { }

	@Test
	public void testGetResultType() throws Exception {
		assertEquals(new IntegerMethod().getResultType(), Integer.class);
		assertEquals(new VectorMethod().getResultType(), new TypeToken<Vector<Integer>>() {}.getType());
		assertEquals(new ArrayMethod().getResultType(), new TypeToken<String[]>() {}.getType());
		assertEquals(new GenericArrayMethod().getResultType(), new TypeToken<LinkedList<Integer>[]>() {}.getType());
	}

	@Test
	public void testDeserializeResult() {
		assertEquals(new Gson().fromJson("123678", new IntegerMethod().getResultType()), 123678);

		Vector<Integer> vector = new Gson().fromJson("[123, 678]", new VectorMethod().getResultType());
		assertEquals(vector.getClass(), Vector.class);
		assertEquals(vector, Arrays.asList(123, 678));

		String[] array = new Gson().fromJson("[1236, 78]", new ArrayMethod().getResultType());
		assertEquals(array.getClass(), String[].class);
		assertEquals(Arrays.asList(array), Arrays.asList("1236", "78"));

		LinkedList<Integer>[] genericArray = new Gson().fromJson("[[23,33], [34,66]]", new GenericArrayMethod().getResultType());
		assertEquals(genericArray.getClass(), LinkedList[].class);
		assertEquals(genericArray.length, 2);
		assertEquals(genericArray[0], Arrays.asList(23, 33));
		assertEquals(genericArray[1], Arrays.asList(34, 66));
	}
}
