package offheap;

import com.sun.jna.Memory;
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
	private Memory pointer;
	private Class type;
	private boolean typeInit;

	private void ensureCapacityInternal(int minCapacity) {

	}

	public OffHeapList() {
		pointer = new Memory(DEFAULTSIZE);

	}

	public OffHeapList(int capacity){

		if (capacity > 0) {
			pointer = new Memory(capacity);
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
		OffHeapUtil.addElement(type, pointer, e, size);
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
		int elementOffSet = index * CommonUtils.getLengthByType(type);

		return (E) OffHeapUtil.getValueByOffset(type, elementOffSet, pointer);


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
		//boolean类型测试
		OffHeapList<Boolean> booleanList = new OffHeapList<>(5);
		booleanList.add(true);
		booleanList.add(false);
		booleanList.add(true);
		booleanList.add(false);
		booleanList.add(false);
		for(int i =0;i<booleanList.size;i++) {
			System.out.println(booleanList.get(i));
		}

		/*int类型测试*/
		OffHeapList<Integer> list = new OffHeapList<>(12);
		list.add(99999);
		list.add(88888);
		list.add(77777);
		for(int i =0; i < list.size;++i) {
			System.out.println(list.get(i)+"  ");
		}
	   //double类型测试
		OffHeapList<Double> dList = new OffHeapList<>(100);
		dList.add(33.0d);
		dList.add(66.0d);
		dList.add(99.0d);
		for(int i =0; i < dList.size;++i) {
			System.out.println(dList.get(i)+"  ");
		}
		//String类型测试S
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
//			System.out.println(t.getAge()+","+t.getName());
		}

	}

}
