package offheap;

import com.sun.jna.Pointer;

import java.util.List;

public class People {

	private Pointer pointer;
	private String home;
	private int age;
	private String name;
	private List<People> fieneds;
	private People person;

	//需要自动生成
	public int getBytes() {
		return 4 + name.length();
	}


	public int getAge() {
		return age;
	}

	public void setAge(int age) {

	}

	public String getName() {
		return name;
	}

//	public String getHome() {
//		return home;
//	}
//
//	public void setHome(String home) {
//		this.home = home;
//	}
//
//	public List<Person> getFieneds() {
//		return fieneds;
//	}
//
//	public void setFieneds(List<Person> fieneds) {
//		this.fieneds = fieneds;
//	}

	public void setName(String name) {
			this.name = name;

		}

	}