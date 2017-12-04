package offheap;

import com.sun.jna.Pointer;

/**
 * Created by zhangping on 2017/10/18.
 */
public class PersonWithName {

	private Pointer pointer;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
