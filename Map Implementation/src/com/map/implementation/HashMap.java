package com.map.implementation;

import java.util.*;

public class HashMap<K, V> {

	private class Node {
		K key;
		V value;

		Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	private int size;
	private LinkedList<Node>[] buckets;

	HashMap() {
		initialBuckets(16);
		size = 0;
	}

	private void initialBuckets(int bucketSize) {
		buckets = new LinkedList[bucketSize];
		for (int bucketIndex = 0; bucketIndex < buckets.length; bucketIndex++) {
			buckets[bucketIndex] = new LinkedList<>();
		}
	}

	public void set(K key, V value) throws Exception {
		int bucketIndex = hashFunction(key);
		int dropIndex = getIndexWithInBuckets(key, bucketIndex);
		if (dropIndex == -1) {
			Node node = new Node(key, value);
			buckets[bucketIndex].add(node);
			size++;
		} else {
			Node node = buckets[bucketIndex].get(dropIndex);
			node.value = value;
		}
		float loadFactor = 0.75f;
		double thresHold = (size * 1.0) / buckets.length;
		if (thresHold > loadFactor) {
			reHash();
		}
	}

	private int hashFunction(K key) {
		int hashCode = hashCode(key);
		return (hashCode & (buckets.length - 1));
	}

	private int hashCode(K key) {
		String key1 = String.valueOf(key);
		byte[] val = key1.getBytes();
		int i = 1, result = 0;
		for (byte b : val) {
			result += ((val.length - i) != 0) ? ((int) (b * (Math.pow(31, (val.length - i++))))) : (b);
		}
		return result;
	}

	private int getIndexWithInBuckets(K key, int bucketIndex) {
		int dropIndex = 0;
		for (Node node : buckets[bucketIndex]) {
			if (node.key.equals(key)) {
				return dropIndex;
			}
			dropIndex++;
		}
		return -1;
	}

	private void reHash() throws Exception {
		LinkedList<Node>[] oldBuckets = buckets;
		initialBuckets(oldBuckets.length * 2);
		size = 0;
		for (int bucketIndex = 0; bucketIndex < oldBuckets.length; bucketIndex++) {
			for (Node node : oldBuckets[bucketIndex]) {
				set(node.key, node.value);
			}
		}
	}

	public V get(K key) throws Exception {
		int bucketIndex = hashFunction(key);
		int dropIndex = getIndexWithInBuckets(key, bucketIndex);
		if (dropIndex != -1) {
			Node node = buckets[bucketIndex].get(dropIndex);
			return node.value;
		} else {
			return null;
		}
	}

	public V unset(K key) throws Exception {
		int bucketIndex = hashFunction(key);
		int dropIndex = getIndexWithInBuckets(key, bucketIndex);
		if (dropIndex != -1) {
			Node node = buckets[bucketIndex].remove(dropIndex);
			size--;
			return node.value;
		} else {
			return null;
		}
	}

	public Boolean update(K key, V value) throws Exception {
		boolean flag = false;
		if (get(key) != null) {
			set(key, value);
			flag = true;
		}
		return flag;
	}

	public int size() {
		return size;
	}

	public int countNumberOfVariables(V value) throws Exception {
		int count = 0;
		for (int bucketIndex = 0; bucketIndex < buckets.length; bucketIndex++) {
			for (Node node : buckets[bucketIndex]) {
				if (value.equals(get(node.key))) {
					count++;
				}
			}
		}
		return count;
	}

	public Boolean containskey(K key) {
		int bucketIndex = hashFunction(key);
		int dropIndex = getIndexWithInBuckets(key, bucketIndex);

		if (dropIndex != -1) {
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<K> keys() throws Exception {
		ArrayList<K> keys = new ArrayList<>();
		for (int bucketIndex = 0; bucketIndex < buckets.length; bucketIndex++) {
			for (Node node : buckets[bucketIndex]) {
				keys.add(node.key);
			}
		}
		return keys;
	}

	public ArrayList<V> values() throws Exception {
		ArrayList<V> values = new ArrayList<>();
		for (int bucketIndex = 0; bucketIndex < buckets.length; bucketIndex++) {
			for (Node node : buckets[bucketIndex]) {
				values.add(node.value);
			}
		}
		return values;
	}

	public void display() {
		for (int bucketIndex = 0; bucketIndex < buckets.length; bucketIndex++) {
			System.out.println("Bucket Index --> " + bucketIndex + ":\n");
			for (Node node : buckets[bucketIndex]) {
				System.out.println("\tKey --> " + node.key + " --> Value --> " + node.value);
			}
		}
	}

}