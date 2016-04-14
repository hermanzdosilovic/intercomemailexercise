package me.hermanzdosilovic.intercom.email.exercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

/**
 * <code>Flattenizer</code> represents class for flattening collection of
 * objects. <br>
 * If element of collection to flatten contains another collection or array then
 * this inner collection or array is flattened as well.
 * 
 * Example:<br>
 * <code>
 * [[1, 2, 3], 2, 3] -> [1, 2, 3, 2, 3]<br>
 * [[1, 2, 3], [[2]], 3] -> [1, 2, 3, 2, 3]<br>
 * [] -> []
 * [1, null] -> [1, null]
 * </code>
 * 
 * @author Herman Zvonimir Došilović
 */
public class Flattenizer {

	/** List to flatten. */
	private Collection<Object> collection;

	/** Flattened list. */
	private List<Object> flattenedCollection;

	/**
	 * Constructs new <code>Flattenizer</code>.
	 * 
	 * @param list
	 *            collection to flatten.
	 */
	public Flattenizer(Collection<Object> collection) {
		this.collection = collection;
	}

	/**
	 * Constructs new <code>Flattenizer</code>.
	 * 
	 * @param array
	 *            array to flatten
	 */
	public Flattenizer(Object[] array) {
		this(Arrays.asList(array));
	}

	/**
	 * Flattens collection given to constructor.
	 * 
	 * @return flattened collection
	 */
	public Collection<Object> flatten() {
		if (flattenedCollection != null) {
			return flattenedCollection;
		}
		flattenedCollection = new ArrayList<>(Flattenizer.flatten(collection));
		return flattenedCollection;
	}

	/**
	 * Returns list to flatten.
	 * 
	 * @return list to flatten
	 */
	public Collection<Object> getList() {
		return collection;
	}

	/**
	 * Returns flattened collection.
	 * 
	 * @return flattened collection.
	 */
	public Collection<Object> getFlattenedCollection() {
		return flatten();
	}

	/**
	 * Flattens given collection.
	 * 
	 * @param list
	 *            collection to flatten
	 * @return flattened collection
	 */
	public static Collection<Object> flatten(Collection<Object> collection) {
		if (collection == null) {
			throw new IllegalArgumentException("list cannot be null");
		}

		List<Object> flattenedCollection = new ArrayList<>();

		Stack<Object> stack = new Stack<>();
		for (Object collectionElement : collection) {
			stack.push(collectionElement);

			while (!stack.empty()) {
				Object top = stack.pop();
				Object[] topArray;
				if (top instanceof Collection<?>) {
					topArray = ((Collection<?>) top).toArray();
				} else if (top instanceof Object[]) {
					topArray = (Object[]) top;
				} else {
					flattenedCollection.add(top);
					continue;
				}

				for (int i = topArray.length - 1; i >= 0; i--) {
					stack.push(topArray[i]);
				}
			}
		}

		return flattenedCollection;
	}
}
