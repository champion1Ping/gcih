package offheap;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;

/**
 * Created by zhangping on 2017/10/13.
 */
public interface CLibrary extends Library {

	CLibrary INSTANCE = (CLibrary)Native.loadLibrary((Platform.isWindows() ? "msvcrt" : "c"),CLibrary.class);
	Pointer malloc(int size);
	void free(Pointer point);

}
