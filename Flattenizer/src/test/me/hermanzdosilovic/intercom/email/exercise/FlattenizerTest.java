package me.hermanzdosilovic.intercom.email.exercise;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Test suite for <code>Flattenizer</code> class.
 * 
 * @author Herman Zvonimir Došilović
 */
public class FlattenizerTest {

	@Test
	public void testConstructorWithEmptyList() {
		Flattenizer flattenizer = new Flattenizer(Arrays.asList());
		assertEquals(Arrays.asList(), flattenizer.getList());
	}

	@Test
	public void testConstructorWithEmptyArray() {
		Flattenizer flattenizer = new Flattenizer(new Object[] {});
		assertEquals(Arrays.asList(), flattenizer.getList());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullList() {
		Flattenizer.flatten(null);
	}

	@Test
	public void testFlattenMemberMethod() {
		Flattenizer flattenizer = new Flattenizer(new Object[] {});
		assertEquals(Arrays.asList(), flattenizer.flatten());
		assertEquals(Arrays.asList(), flattenizer.flatten());
	}

	/**
	 * Tests empty list. Depth of this list is 0.<br>
	 * [] -> []
	 */
	@Test
	public void testEmptyList() {
		List<Object> testList = new ArrayList<>();
		assertEquals(new ArrayList<Object>(), Flattenizer.flatten(testList));
	}

	/**
	 * Tests list with <code>null</code> element.<br>
	 * [1, null] -> [1, null]
	 */
	@Test
	public void testListWithNullElement() {
		assertEquals(Arrays.asList(1, null), Flattenizer.flatten(Arrays.asList(1, null)));
	}

	/**
	 * Tests list with only one <code>Integer</code> element. Depth of this list
	 * is 0.<br>
	 * [1] -> [1]
	 */
	@Test
	public void testListWithOneInteger() {
		List<Object> testList = Arrays.asList(1);
		assertEquals(testList, Flattenizer.flatten(testList));
	}

	/**
	 * Tests list with more <code>Integer</code> elements. Depth of tested list
	 * is 0.<br>
	 * [1, 2, 3, 4] -> [1, 2, 3, 4]
	 */
	@Test
	public void testListWithMoreIntegers() {
		List<Object> testList = Arrays.asList(1, 2, 3, 4);
		assertEquals(testList, Flattenizer.flatten(testList));
	}

	/**
	 * Tests list with only one <code>Collection</code> element. Depth of tested
	 * list is 1.<br>
	 * [[1, 2, 3]] -> [1, 2, 3]
	 */
	@Test
	public void testListWithOneCollection() {
		List<Object> testList = new ArrayList<>();
		testList.add(Arrays.asList(1, 2, 3));

		List<Object> expectedList = Arrays.asList(1, 2, 3);
		assertEquals(expectedList, Flattenizer.flatten(testList));
	}

	/**
	 * Tests list with more <code>Collection</code> elements. Depth of tested
	 * list is 1.<br>
	 * [[1, 2, 3], [4, 5], [6]] -> [1, 2, 3, 4, 5, 6]
	 */
	@Test
	public void testListWithMoreCollections() {
		List<Object> testList = new ArrayList<>();
		testList.add(Arrays.asList(1, 2, 3));
		testList.add(Arrays.asList(4, 5));
		testList.add(Arrays.asList(6));

		List<Object> expectedList = Arrays.asList(1, 2, 3, 4, 5, 6);
		assertEquals(expectedList, Flattenizer.flatten(testList));
	}

	/**
	 * Tests list with mixed objects but with <code>Integer</code> as first
	 * element. Depth of tested list is 1.<br>
	 * [1, [1, 2, 3], 3] -> [1, 1, 2, 3, 3]
	 */
	@Test
	public void testMixedObjectsWithIntegerFirst() {
		List<Object> testList = new ArrayList<>();
		testList.add(1);
		testList.add(Arrays.asList(1, 2, 3));
		testList.add(3);

		List<Object> expectedList = Arrays.asList(1, 1, 2, 3, 3);
		assertEquals(expectedList, Flattenizer.flatten(testList));
	}

	/**
	 * Tests list with mixed objects but with <code>Collection</code> as first
	 * element. Depth of tested list is 1.<br>
	 * [[1, 2, 3], 2, 3] -> [1, 2, 3, 2, 3]
	 */
	@Test
	public void testMixedObjectsWithCollectionFirst() {
		List<Object> testList = new ArrayList<>();
		testList.add(Arrays.asList(1, 2, 3));
		testList.add(2);
		testList.add(3);

		List<Object> expectedList = Arrays.asList(1, 2, 3, 2, 3);
		assertEquals(expectedList, Flattenizer.flatten(testList));
	}

	/**
	 * Tests list with only one <code>Collection</code> element with depth 5.
	 * <br>
	 * [[[[[[1, 2, 3, 4]]]]]] -> [1, 2, 3, 4]
	 */
	@Test
	public void testListWithOneCollectionOfDepth5() {
		List<Object> testList = new ArrayList<>();
		testList.add(Arrays.asList(1, 2, 3, 4));
		for (int i = 0; i < 4; i++) {
			List<Object> helperList = new ArrayList<>();
			helperList.add(testList);
			testList = helperList;
		}

		List<Object> expectedList = Arrays.asList(1, 2, 3, 4);
		assertEquals(expectedList, Flattenizer.flatten(testList));
	}

	/**
	 * Tests list with more <code>Collection</code> elements with depth 5.<br>
	 * [[[[[[[1, 2, 3, 4]]]]]], [[[[[[1, 2, 3, 4]]]]]]] -> [1, 2, 3, 4, 1, 2, 3,
	 * 4]
	 */
	@Test
	public void testListMoreCollectionsOfDepth5() {
		List<Object> testList = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			List<Object> testElement = new ArrayList<>();
			testElement.add(Arrays.asList(1, 2, 3, 4));
			for (int j = 0; j < 4; j++) {
				List<Object> helperList = new ArrayList<>();
				helperList.add(testElement);
				testElement = helperList;
			}
			testList.add(testElement);
		}

		List<Object> expectedList = Arrays.asList(1, 2, 3, 4, 1, 2, 3, 4);
		assertEquals(expectedList, Flattenizer.flatten(testList));
	}

	/**
	 * Test list with more empty <code>Collection</code> elements.<br>
	 * [[], []] -> []
	 */
	@Test
	public void testListWithMoreEmptyCollections() {
		List<Object> testList = new ArrayList<>();
		testList.add(Arrays.asList());
		testList.add(Arrays.asList());

		assertEquals(Arrays.asList(), Flattenizer.flatten(testList));
	}

	/**
	 * Test list with mixed elements.<br>
	 * [0, [1, 2, 3], [4], [5, [6, 7], 8], [], [[], [9, 10, 11], [12]]] -> [1,
	 * 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
	 */
	@Test
	public void testListWithMixedElementsOfMixedDepth() {
		Object[] testArray = { 0, new Object[] { 1, 2, 3 }, 4, new Object[] { 5, new Object[] { 6, 7 }, 8 },
				new Object[] {}, new Object[] { new Object[] { 9, 10, 11 }, new Object[] { 12 } } };

		List<Object> expectedList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
		assertEquals(expectedList, Flattenizer.flatten(Arrays.asList(testArray)));
	}
}
