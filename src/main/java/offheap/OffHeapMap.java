package offheap;

import com.sun.jna.Pointer;

/**
 * Created by zhangping on 2017/10/12.
 */
public class OffHeapMap<K,V> {

	private static final  int DEFAULTSIZE = 16;
	private int size;
	private Pointer pointer;
	private Class keyType;
	private Class valueType;
	private boolean typeInit;
	private int offset = 0;

	public OffHeapMap() {
		pointer = CLibrary.INSTANCE.malloc(DEFAULTSIZE);
	}

	public OffHeapMap(int initCapaticySize) {
		pointer = CLibrary.INSTANCE.malloc(initCapaticySize);
	}

	public boolean put(K key, V value) {
		if (!typeInit) {
			keyType = key.getClass();
			valueType =key.getClass();
			typeInit = true;
		}
		int keySize = OffHeapUtil.getOffsetByKeyType(keyType, pointer, key, offset);
		int valueSize = OffHeapUtil.getOffsetByKeyType(valueType, pointer, value, offset + keySize);
		offset+=(keySize + valueSize);
		return true;
	}


}
