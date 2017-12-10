package offheap;

/**
 * Created by zhangping on 2017/12/7.
 */
public class CommonUtils {

	//64位系统使用
	public static final int BOOLEAN_LENGTH = 1;
	public static final int BYTE_LENGTH = 1;
	public static final int SHORT_LENGTH = 2;
	public static final int CHAR_LENGTH = 2;
	public static final int INTEGER_LENGTH = 4;
	public static final int LONG_LENGTH = 8;
	public static final int FLOAT_LENGTH = 4;
	public static final int DOUBLE_LENGTH = 8;
	public static final int POINTER_LENGTH = 8;

	public static int getLengthByType(Class type) {
		int length = 0;
		switch (type.getName()) {
			case "java.lang.Boolean":
				length = BOOLEAN_LENGTH;
				break;
			case "java.lang.Integer":
				length = INTEGER_LENGTH;
				break;
			case "java.lang.Long":
				length = LONG_LENGTH;
				break;
			case "java.lang.Char":
				length = CHAR_LENGTH;
				break;
			case "java.lang.Float":
				length = FLOAT_LENGTH;
				break;
			case "java.lang.Double":
				length = DOUBLE_LENGTH;
				break;
			case "java.lang.String":
				length = POINTER_LENGTH;
			default:

		}
		return length;
	}


}
