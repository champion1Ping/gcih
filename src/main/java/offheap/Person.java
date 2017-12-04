package offheap;

public  class Person {

	private int age;
	private String name;
//	private Person person;
//
//	public Person getPerson() {
//		return person;
//	}
//
//	public void setPerson(Person person) {
//		this.person = person;
//	}

	//需要自动生成
	public int getBytes() {
		return 4 + name.length();
	}


	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
			this.name = name;
		}

	}