package offheap;

import com.sun.jna.Pointer;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by zhangping on 2017/10/12.
 */
public class OffHeapList<E> implements List<E>{


	private static final  int DEFAULTSIZE = 16;
	private int size;
	private Pointer pointer;
	private Class type;
	private boolean typeInit;
	private int offset = 0;
	private int[] offsetArray;//集合中每个元素的偏移量
	 //存放集合中每个元素所含字段的偏移量，写入的时候可知道

	private void ensureCapacityInternal(int minCapacity) {

	}

	public OffHeapList() {
		pointer = CLibrary.INSTANCE.malloc(DEFAULTSIZE);

	}

	public OffHeapList(int capacity){

		if (capacity > 0) {
			pointer = CLibrary.INSTANCE.malloc(size);
			offsetArray = new int[capacity];
		} else {
			throw new IllegalArgumentException("Illegal Capacity:" + capacity);
		}

	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size ==0;
	}

	@Override
	public boolean contains(Object o) {
		return false;
	}

	@Override
	public Iterator<E> iterator() {
		return null;
	}

	@Override
	public Object[] toArray() {
		return new Object[0];
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return null;
	}


	public boolean add(E e) {
		if (!typeInit) {
			type = e.getClass();
			typeInit = true;
		}

		int nextOffSet = OffHeapUtil.getOffsetByType(type, pointer, e, offset);
		offsetArray[size] = offset;
		offset += nextOffSet;
		++size;
		return true;
	}

	@Override
	public boolean remove(Object o) {
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return false;
	}

	@Override
	public void clear() {

	}

	public E  get(int index) {
		int elementOffSet = offsetArray[index];

		int nextElementOffSet = index + 1 < size ? offsetArray[index + 1] : 0;
		return (E) OffHeapUtil.getValueByOffset(type, elementOffSet, pointer, nextElementOffSet, index);


	}

	@Override
	public E set(int index, E element) {
		return null;
	}

	@Override
	public void add(int index, E element) {

	}

	@Override
	public E remove(int index) {
		return null;
	}

	@Override
	public int indexOf(Object o) {
		return 0;
	}

	@Override
	public int lastIndexOf(Object o) {
		return 0;
	}

	@Override
	public ListIterator<E> listIterator() {
		return null;
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return null;
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return null;
	}


	public static void main(String[] args) {

		OffHeapList<Integer> list = new OffHeapList<>(100);
		list.add(33);
		list.add(44);
		for(int i =0; i < list.size;++i) {
			System.out.println(list.get(i)+"  ");
		}

		OffHeapList<Double> dList = new OffHeapList<>(100);
		dList.add(33.0d);
		dList.add(66.0d);
		dList.add(99.0d);
		for(int i =0; i < dList.size;++i) {
			System.out.println(dList.get(i)+"  ");
		}
		OffHeapList<String> sList = new OffHeapList<>(100);
		sList.add("ping");
		sList.add("an");
		sList.add("champion");
		for(int i = 0; i < sList.size; ++i) {
			System.out.println(sList.get(i));
		}

		OffHeapList<Person> aList = new OffHeapList<>(100);

		Person a1 = new Person();
		a1.setAge(26);
		a1.setName("zhangping");
		aList.add(a1);
		Person a2 = new Person();
		a2.setAge(28);
		a2.setName("zhangwei");
		aList.add(a2);
		for(int i =0;i<aList.size;++i) {
			Person t = aList.get(i);
			System.out.println(t.getAge()+","+t.getName());
		}

		//类中是有单个String变量类型
		OffHeapList<PersonWithName> stringList = new OffHeapList<>();
		PersonWithName personWithName = new PersonWithName();
		personWithName.setName("zhangping");
		stringList.add(personWithName);
		PersonWithName personWithName2 = new PersonWithName();
		personWithName.setName("zhangping2");
		stringList.add(personWithName2);
		for(int i = 0;i < stringList.size; ++i) {
			PersonWithName person = stringList.get(i);
			System.out.println(person.getName());
		}
	}

}
