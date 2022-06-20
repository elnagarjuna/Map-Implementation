package com.map.implementation;

import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		HashMap<String, String> map = new HashMap();
		ArrayList<HashMap<String, String>> mapList = new ArrayList<>();

		System.out.println("The Command Lines are:-" + "\nSET         - To set the key and value."
				+ "\nGET         - To get the value of key." + "\nUNSET       - To removet the key and vaue."
				+ "\nUPDATE      - To update the key." + "\nCOUNT       - To count the values."
				+ "\nBEGIN       - To start a new Transaction."
				+ "\nCOMMIT      - To save new transaction to old transaction."
				+ "\nROLLBACK    - Back to the old Transaction."
				+ "\nCONTAINSKEY - It show the key is available or not."
				+ "\nSIZE        - It shows the size of the map." + "\nKEYS        - It shows the keys of the map."
				+ "\nVALUES      - It shows the values of the map." + "\nDISPLAY     - It displays the map."
				+ "\nQUIT        - Exit the Console" + "\n------------------------------------------------------\n");
		System.out.print("-> ");
		String str = br.readLine();

		while (str.equals("QUIT") == false) {
			try {
				if (str.startsWith("SET")) {
					String[] input = str.split(" ");
					String key = input[1];
					String value = input[2];
					map.set(key, value);
					System.out.println("The key and value pair is added.");
				} else if (str.startsWith("GET")) {
					String[] input = str.split(" ");
					String key = input[1];
					System.out.println(map.get(key));
				} else if (str.startsWith("UNSET")) {
					String[] input = str.split(" ");
					String key = input[1];
					System.out.println(map.unset(key));
				} else if (str.startsWith("UPDATE")) {
					String[] input = str.split(" ");
					String key = input[1];
					String value = input[2];
					if (map.update(key, value) == false) {
						System.out.println("No Variable Named ," + key + "'");
					}
				} else if (str.startsWith("SIZE")) {
					System.out.println(map.size());
				} else if (str.startsWith("COUNT")) {
					String[] input = str.split(" ");
					String value = input[1];
					int count = map.countNumberOfVariables(value);
					if (count != 0) {
						System.out.println(count);
					} else {
						System.out.println("null");
					}
				} else if (str.startsWith("BEGIN")) {
					mapList.add(map);
					map = new HashMap();
					int i = mapList.size() - 1;
					while (i >= 0) {
						HashMap<String, String> tempMap = mapList.get(i);
						for (int index = 0; index < tempMap.size(); index++) {
							map.set((String) tempMap.keys().toArray()[index],
									(String) tempMap.values().toArray()[index]);
						}
						break;
					}
				} else if (str.startsWith("ROLLBACK")) {
					int i = 0;
					while (i < map.size()) {
						map.unset((String) map.keys().toArray()[i]);
					}
					map = mapList.get(mapList.size() - 1);
					mapList.remove(mapList.size() - 1);
				} else if (str.startsWith("COMMIT")) {
					int i = mapList.size() - 1;
					while (i >= 0) {
						HashMap<String, String> tempMap = mapList.get(i);
						for (int index = 0; index < map.size(); index++) {
							tempMap.set((String) map.keys().toArray()[index], (String) map.values().toArray()[index]);
						}
						break;
					}
				} else if (str.startsWith("CONTAINSKEY")) {
					String[] input = str.split(" ");
					String key = input[1];
					System.out.println(map.containskey(key));
				} else if (str.startsWith("KEYS")) {
					System.out.println(map.keys());
				} else if (str.startsWith("VALUES")) {
					System.out.println(map.values());
				} else if (str.startsWith("DISPLAY")) {
					map.display();
				} else {
					System.out.println("Please give correct command.");
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			System.out.print("-> ");
			str = br.readLine();
		}
	}
}