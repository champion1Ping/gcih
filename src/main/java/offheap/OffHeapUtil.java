package offheap;

import com.sun.jna.Pointer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by zhangping on 2017/10/13.
 */
public class OffHeapUtil {

	public static int getOffsetByKeyType(Class type, Pointer pointer, Object value, int offset) {
		int nextoffset = 0;
		switch (type.getName()){
			case "java.lang.Integer":
				pointer.setInt(offset, (int)value);
				nextoffset = 4;
				break;
			case "java.lang.Long":
				pointer.setLong(offset, (long)value);
				nextoffset = 4;
				break;
			case "java.lang.Char":
				pointer.setChar(offset, (char)value);
				nextoffset = 1;
				break;
			case "java.lang.Float":
				pointer.setFloat(offset, (float)value);
				nextoffset = 4;
				break;
			case "java.lang.Double":
				pointer.setDouble(offset, (double)value);
				nextoffset = 8;
				break;
			default:
				break;

		}
		return nextoffset;
	}

	public static int getOffsetByBasicType(Class type, Pointer pointer, Object value, int offset) {
		int nextoffset = 0;
		switch (type.getName()){
			case "int":
				pointer.setInt(offset, (int)value);
				nextoffset = 4;
				break;
			case "long":
				pointer.setLong(offset, (long)value);
				nextoffset = 8;
				break;
			case "char":
				pointer.setChar(offset, (char)value);
				nextoffset = 1;
				break;
			case "float":
				pointer.setFloat(offset, (float)value);
				nextoffset = 4;
				break;
			case "double":
				pointer.setDouble(offset, (double)value);
				nextoffset = 8;
				break;
			case "java.lang.String":
				pointer.setString(offset, (String)value);
				nextoffset = ((String)value).length();
				break;
			default:
				//复杂类型的处理
				try {
					Field[]  fields = type.getDeclaredFields();
					int size = offset;
					for(Field f: fields) {
						Class fType = f.getType();
						String fName = f.getName();
						Method fMethod = type.getMethod("get" + fName.substring(0,1).toUpperCase()+fName.substring(1), null);
						Object fValue = fMethod.invoke(value, null);
						int off = getOffsetByType(fType, pointer, fValue,size);
						size +=off;
					}

					Method method = type.getMethod("getBytes",null);
					nextoffset =(int) method.invoke(value, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				;

		}
		return nextoffset;
	}

	public static int getOffsetByType(Class type, Pointer pointer, Object value, int offset) {
		int nextoffset = 0;
		switch (type.getName()){
			case "java.lang.Integer":
				pointer.setInt(offset, (int)value);
				nextoffset = 4;
				break;
			case "java.lang.Long":
				pointer.setLong(offset, (long)value);
				nextoffset = 8;
				break;
			case "java.lang.Char":
				pointer.setChar(offset, (char)value);
				nextoffset = 1;
				break;
			case "java.lang.Float":
				pointer.setFloat(offset, (float)value);
				nextoffset = 4;
				break;
			case "java.lang.Double":
				pointer.setDouble(offset, (double)value);
				nextoffset = 8;
				break;
			case "java.lang.String":
				//（1）当前分配的内存直接存值
				pointer.setString(offset, (String)value);
				nextoffset = ((String)value).length();
				//（2）另外开辟空间 存放String的值，当前内存只存放引用
				break;
			default:
				//复杂类型的处理
				try {
				Field[]  fields = type.getDeclaredFields();
				int size = offset;
				for(Field f: fields) {
					Class fType = f.getType();
					String fName = f.getName();
					Method fMethod = type.getMethod("get" + fName.substring(0,1).toUpperCase()+fName.substring(1), null);
					Object fValue = fMethod.invoke(value, null);
					int off = getOffsetByBasicType(fType, pointer, fValue,size);
					size +=off;
				}

					Method method = type.getMethod("getBytes",null);
					nextoffset =(int) method.invoke(value, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				;

		}
		return nextoffset;
	}

	public static  Object getBasicValueByOffset(Class type, int offset, Pointer pointer, int nextoffset) {
		Object value = null;
		switch (type.getName()){
			case "int":
				value = pointer.getInt(offset);
				break;
			case "long":
				value = pointer.getLong(offset);
				break;
			case "char":
				value = pointer.getChar(offset);
				break;
			case "float":
				value = pointer.getFloat(offset);
				break;
			case "double":
				value = pointer.getDouble(offset);
				break;
			case "java.lang.String":
				value = nextoffset != 0 ? pointer.getString(offset).substring(0,nextoffset-offset):
						pointer.getString(offset);
				break;
			default:


		}
		return value;
	}


	public static  Object getValueByOffset(Class type, int offset, Pointer pointer, int nextoffset, int index) {
		Object value = null;
		switch (type.getName()){
			case "java.lang.Integer":
				value = pointer.getInt(offset);
				break;
			case "java.lang.Long":
				value = pointer.getLong(offset);
				break;
			case "java.lang.Char":
				value = pointer.getChar(offset);
				break;
			case "java.lang.Float":
				value = pointer.getFloat(offset);
				break;
			case "java.lang.Double":
				value = pointer.getDouble(offset);
				break;
			case "java.lang.String":
				value = nextoffset != 0 ? pointer.getString(offset).substring(0,nextoffset-offset):
						pointer.getString(offset);
				break;
			default:
				//如何取出一个复杂对象呢？
				try {
					Object obj = type.newInstance();
					Field [] fields = type.getDeclaredFields();
					int size = offset;
					for(Field f:fields) {
						Class fType = f.getType();
						String fName = f.getName();
						Method method = type.getMethod("set"+fName.substring(0,1).toUpperCase()+fName.substring(1),fType);
						Object fValue = getBasicValueByOffset(fType,size, pointer,0);
						size += 4;
						method.invoke(obj, fValue);
					}
					value = obj;
				} catch (Exception e) {
					e.printStackTrace();
				}
				/* OffHeapList.A a = new OffHeapList.A();
				a.setAge(pointer.getInt(offset));
				a.setName(pointer.getString(offset + 4));
				value = a; */
				;

		}
		return value;
	}
}
