package com.example.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.HashMap;

public class FragmentTest extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		FragmentManager fm=getFragmentManager();
		
		

		 HashMap<String, String> params= new HashMap<String, String>();
		 
		 params.put("msg", "msg");
		 params.put("c", "c");
		 params.put("b", "b");
		 params.put("a", "a");
		
		 
		 String [] test1 = new String[10];
		 
		 String[] keys = params.keySet().toArray(test1);
		 Arrays.sort(keys);
		 
		 
		 for(String s: keys){
			 System.out.println( s);
		 }
		
		
		return super.onCreateView(inflater, container, savedInstanceState);
		
		
	}
	
	
	
}
