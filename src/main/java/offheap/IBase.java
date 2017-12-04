package offheap;

/**
 * Created by zhangping on 2017/12/4.
 */
public interface IBase<T> {
	public T get();
	public void set(T t);
}
