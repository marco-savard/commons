package com.marcosavard.commons.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * A generic implementation for the toString() method, based by introspection. 
 * 
 * @author Marco
 *
 */
public class ToStringBuilder {
	
	public static String build(Object object) {
		boolean toStringPermitted = isToStringSafe(object) || isToStringDefined(object); 
		String str = toStringPermitted ? object.toString() : relectiveToString(object); 
		return str; 
	}
	
	private static boolean isToStringSafe(Object object) {
		boolean toStringSafe = (object instanceof Collection); 
		toStringSafe = toStringSafe || (object instanceof Map); 
		return toStringSafe;
	}

	private static boolean isToStringDefined(Object object) {
		Class claz = object.getClass(); 
		boolean toStringDefined; 
		
		try {
			Method method = claz.getDeclaredMethod("toString", null);
			toStringDefined = true; 
		} catch (NoSuchMethodException | SecurityException e) {
			toStringDefined = false;
		} 
		
		return toStringDefined;
	}

	private static String relectiveToString(Object object) {
		List<String> values = new ArrayList<>(); 
		List<PropertyDescriptor> descriptors = getDescriptors(object);
		
		for (PropertyDescriptor descriptor : descriptors) {
			Object value = getValue(object, descriptor); 
			
			if (! (value instanceof Class)) {
				String keyValue = MessageFormat.format("{0}={1}", descriptor.getName(), value);
				values.add(keyValue); 
			}
		}
		
		String joined = String.join(", ", values);
		String str = "{" + joined + "}"; 
		return str; 
	}

	private static Object getValue(Object object, PropertyDescriptor descriptor) {
		Object value; 
		
		try {
			Method reader = descriptor.getReadMethod(); 
			reader.setAccessible(true);
			value = reader.invoke(object);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			value = null;
		} 
		
		return value;
	}

	private static List<PropertyDescriptor>getDescriptors(Object object) {
		PropertyDescriptor[] descriptorArray; 
		
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
			descriptorArray = beanInfo.getPropertyDescriptors(); 
		} catch (IntrospectionException e) {
			descriptorArray = null;
		}
		
		List<PropertyDescriptor> descriptors = new ArrayList<>(Arrays.asList(descriptorArray)); 
		return descriptors;
	}
}
