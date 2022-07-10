package me.geza3d.toldi.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.geza3d.toldi.handlers.ConfigHandler;

/***
 * This is for generating a accesswidener for every fields/methods/inner classes of certain classes automatically when needed.
 */
public class AccessWidenerUtil {
	
	static File f = new File(ConfigHandler.DIR +  "\\accesswidenergen.txt");
	
	static class MethodWidener {
		String className;
		String methodName;
		String descriptor;
		
		public MethodWidener(Class<?> clazz, Method method) {
			this.className = clazz.getName().replace(".", "/");
			this.methodName = method.getName();

			String desc = "(";
			
			for(Parameter arg : method.getParameters()) {
				desc += arg.getType().descriptorString();
			}
			
			desc += ")" + method.getReturnType().descriptorString();
			
			this.descriptor = desc;
		}
		
		@Override
		public String toString() {
			return "accessible   method   " + className + "   " + methodName + "   " + descriptor;
		}
		
		
	}
	
	static class FieldWidener {
		String className;
		String fieldName;
		String descriptor;
		
		public FieldWidener(Class<?> clazz, Field field) {
			this.className = clazz.getName().replace(".", "/");
			this.fieldName = field.getName();
			this.descriptor = field.getType().descriptorString();
		}
		
		@Override
		public String toString() {
			return "accessible   field   " + className + "   " + fieldName + "   " + descriptor;
		}
		
		
	}
	
	static class ClassWidener {
		String className;
		
		public ClassWidener(Class<?> clazz) {
			this.className = clazz.getName().replace(".", "/");
		}
		
		@Override
		public String toString() {
			return "accessible   class   " + className;
		}
		
		
	}
	
	public static void generateFullAccessWidenerForClass(Class<?> clazz) {
		List<Object> toWiden = new ArrayList<>();
		toWiden.addAll(generateMethodWideners(clazz));
		toWiden.addAll(generateFieldWideners(clazz));
		toWiden.addAll(generateClassWideners(clazz));
		FileWriter writer;
		try {
			writer = new FileWriter(f);
			for(Object o : toWiden)
				writer.write(o.toString() + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static List<MethodWidener> generateMethodWideners(Class<?> clazz) {
		List<MethodWidener> methods = new ArrayList<>();
		for(Method method : clazz.getMethods()) {
			if(!Arrays.asList(Object.class.getMethods()).contains(method))
				methods.add(new MethodWidener(clazz, method));
		}
		return methods;
	}
	
	private static List<FieldWidener> generateFieldWideners(Class<?> clazz) {
		List<FieldWidener> fields = new ArrayList<>();
		for(Field field : clazz.getFields()) {
			fields.add(new FieldWidener(clazz, field));
		}
		return fields;
	}
	
	private static List<ClassWidener> generateClassWideners(Class<?> clazzIn) {
		List<ClassWidener> classes = new ArrayList<>();
		for(Class<?> clazz : clazzIn.getClasses()) {
			classes.add(new ClassWidener(clazz));
		}
		return classes;
	}
}
