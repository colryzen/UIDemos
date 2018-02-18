package com.example.hash;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class HashMapTest  {
	public static void main(String[] args) {
		  
		 LinkedHashSet<String> set =new LinkedHashSet<String>(5);
		 
		 set.add("1");
		 set.add("2");
		 set.add("3");
		 set.add("4");
		 set.add("5");
		 
		 System.out.println("========="+set.toString());
		 
		 
		 set.add("6");
		 set.add("7");
		 set.add("8");
		 
		 System.out.println("========="+set.toString());
		 
	}
	
	
	
	public void hashSetTest(){
		final int cacheSize = 5;
		
		LinkedHashMap<Integer, String> lru = new LinkedHashMap<Integer, String>() {
		    @Override
		    protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
		    return size() > cacheSize;
		    }
		};
		
	}
	
}
