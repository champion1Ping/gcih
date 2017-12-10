package offheap;


import com.sun.jna.Memory;
import com.sun.jna.Pointer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhangping on 2017/12/6.
 */
public class TestUtil {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		List<String> list = new ArrayList<>(3);
		list.add("ping");
		list.add("an");
		list.add("happy");
		list.add("sadness");
		list.retainAll(Arrays.asList("an","ping"));
		System.out.println(list.toString());
		int a = 3 > 2 ? 2: 1;
		System.out.println(a);
		int total = (int)(System.currentTimeMillis())/1000/60;
		System.out.println("total = " + total);
	}
}
