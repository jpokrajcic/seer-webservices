package com.seer.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

@SuppressWarnings("unchecked")
public class TypedProperties extends Properties {

	private static final long serialVersionUID = 1; 
	
	public TypedProperties() { super (); }
	public TypedProperties(Properties props) { super (props); }

	
	public TypedProperties extractSubProperties (String keyFilter)
	{
		if (!keyFilter.endsWith(".")) keyFilter = keyFilter + "."; 
		
		TypedProperties p = new TypedProperties ();  
		for ( Enumeration e = this.propertyNames(); e.hasMoreElements(); )
		{
			String key = (String) e.nextElement();
			if (key.startsWith(keyFilter, 0))
			{

				String newKey = key.substring(keyFilter.length());
				String newValue = this.getProperty(key);
				p.put( newKey, newValue );
			}		
		}	
		
		return p; 
	}
	
	public void add (TypedProperties source, String prefix)
	{
		if (!prefix.endsWith(".")) prefix = prefix + "."; 
		
		for ( Enumeration e = source.propertyNames(); e.hasMoreElements(); )
		{
			String key = (String) e.nextElement();
			put( prefix + key , source.getProperty(key) );	
		}	
		
	}

	public int getInt (String propertyName, int defaultValue)
	{
		String value = super.getProperty (propertyName, new Integer (defaultValue).toString());
		try{
			return Integer.parseInt(value.trim());
		}catch (Exception ex){
			return defaultValue; 
		}

	}
	
	public int getInt (String propertyName)
	{
		return getInt(propertyName, 0);
	}
	
	public long getLong (String propertyName, long defaultValue)
	{
		String value = super.getProperty (propertyName, new Long (defaultValue).toString());
		try{
			return Long.parseLong(value.trim());
		}catch (Exception ex){
			return defaultValue; 
		}

	}
	
	public long getLong (String propertyName)
	{
		return getLong(propertyName, 0L);
	}
	
	public byte getByte (String propertyName, byte defaultValue)
	{
		String value = super.getProperty (propertyName, new Byte (defaultValue).toString());
		try{
			return Byte.parseByte(value.trim());
		}catch (Exception ex){
			return defaultValue; 
		}

	}
	
	public byte getByte (String propertyName)
	{
		return getByte(propertyName, (byte)0);
	}

	public double getDouble (String propertyName, double defaultValue)
	{
		String value = super.getProperty (propertyName, new Double (defaultValue).toString());
		try{
			return Double.parseDouble(value.trim());
		}catch (Exception ex){
			return defaultValue; 
		}

	}
	
	public double getDouble (String propertyName)
	{
		return getDouble(propertyName, 0.0);
	}
	
	public boolean getBoolean (String propertyName, boolean defaultValue)
	{
		String value = super.getProperty (propertyName, new Boolean (defaultValue).toString());
		try{
			return Boolean.parseBoolean(value.trim());
		}catch (Exception ex){
			return defaultValue; 
		}
		

	}
	
	public boolean getBoolean (String propertyName)
	{
		return getBoolean(propertyName, false);
	}
	
	public String getString (String propertyName, String defaultValue) {
		String value = super.getProperty (propertyName, defaultValue);
		return value.trim(); 
	}
	
	public String getString (String propertyName) {
		return getString (propertyName, ""); 
	}
	

	public void putInt (String key, int value)
	{
		put (key, new Integer (value).toString());
	}
	
	public void putLong (String key, long value)
	{
		put (key, new Long (value).toString());
	}
	
	public void putBoolean (String key, boolean value)
	{
		put (key, new Boolean (value).toString());
	}
	
	public void putByte (String key, byte value)
	{
		put (key, new Byte (value).toString());
	}
	
	public void putDouble (String key, double value)
	{
		put (key, new Double (value).toString());
	}
	
	
	public TypedProperties trimProperties () {
		TypedProperties dest = new TypedProperties(this);

		for ( Enumeration  e = this.propertyNames(); e.hasMoreElements(); ) {
			String key = (String) e.nextElement();
			Object value = dest.getString(key);
			if (value != null && value.getClass().equals(String.class)) {
				value = ((String)value).trim();
				dest.remove(key);
				dest.put(key, value);
			}			 				
		}	

		return dest; 		
	}

	public String printoutProperties () {
		StringWriter w = new StringWriter (); 
		PrintWriter pw = new PrintWriter (w);
						
		SortedSet<String> keyNames = new TreeSet<String>(); 
		for ( Enumeration  e = this.propertyNames(); e.hasMoreElements(); ) {
			String key = (String) e.nextElement();
			keyNames.add(key); 
		}	
		
		for ( Iterator<String> iterator = keyNames.iterator(); iterator.hasNext(); ) {
			String key = iterator.next(); 
			String value = getString(key);
			pw.println (key + ":" + value); 
		}
		
		return w.toString(); 
	}
	

}
