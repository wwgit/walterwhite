package handy.tools.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class ParseObjAnno {
	
	public static void parseTypesAnnotation(Object beanObj) {
		
		Class<?> clazz = beanObj.getClass();
		Annotation[] annotations = clazz.getAnnotations();
		AnnoTypeTest typeAnno = clazz.getAnnotation(AnnoTypeTest.class);
		System.out.println("get typeAnno by anno clazz: " + typeAnno.testString());
		System.out.println("get typeAnno by anno clazz: " + typeAnno.testInt());
		System.out.println("get typeAnno by anno clazz: " + typeAnno.testClazz());
		System.out.println("get typeAnno by anno clazz: " + typeAnno.testBoolean());
		System.out.println("get typeAnno by anno clazz: " + typeAnno.testEnum());
		
		for(Annotation annotation : annotations) {
			AnnoTypeTest annoTest = (AnnoTypeTest) annotation;
			System.out.println("type name = " + clazz.getName() + 
					"| testValue = " + annoTest.testString());
		}
		
	}
	
	public static void parseMethodAnnotation(Object beanObj, Class<? extends Annotation> AnnoClazz) {
		
		Class<?> clazz = beanObj.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		
		for(Method method : methods) {
			boolean hasAnnotation = method.isAnnotationPresent(AnnoClazz);
			if(hasAnnotation) {
				System.out.println("has annotation " +
						AnnoClazz.getName() + " for method " +
						method.getName());
				Object annotation = method.getAnnotation(AnnoClazz);
				System.out.println("annotation object: " + annotation.toString());
			} else {
				//System.out.println("annotation " + AnnoClazz.getName() +
				//		" NOT presented for method " + method.getName());
			}
		}
		
	}
	
	public static void parseFieldAnnotation(Object beanObj, 
									Class<? extends Annotation> AnnoClazz) {
		
		Class<?> clazz = beanObj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		
		for(Field field : fields) {
			boolean hasAnnotation = field.isAnnotationPresent(AnnoClazz);
			if(hasAnnotation) {
				System.out.println("has annotation " +
						AnnoClazz.getName() + " for field " +
						field.getName());
				Object annotation = field.getAnnotation(AnnoClazz);
				System.out.println("annotation object: " + annotation.toString());
			}

		} 	
		
	}

}
