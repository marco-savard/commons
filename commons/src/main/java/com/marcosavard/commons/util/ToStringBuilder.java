package com.marcosavard.commons.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A generic implementation for the toString() method, based by introspection. 
 * 
 * @author Marco
 *
 */
public class ToStringBuilder {
	
	public static String build(Object object) {
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
			value = descriptor.getReadMethod().invoke(object);
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
