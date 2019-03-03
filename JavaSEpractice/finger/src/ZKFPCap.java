import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlock;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;

public class ZKFPCap {

	private static final String ZKFPCAPLIB = "ZKFPCap.dll";

	/**
	 * initialization
	 * 
	 * @return
	 * @throws NativeException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("deprecation")
	public static int sensorInit() throws NativeException,
			IllegalAccessException {

		JNative n = null;
		int ret = -1;

		try {
			n = new JNative(ZKFPCAPLIB, "sensorInit");
			n.setRetVal(Type.INT);

			n.invoke();
			ret = n.getRetValAsInt();
		} catch (NativeException e) {
			e.printStackTrace();
		} finally {
			if (null != n) {
				n.dispose();
			}
		}
		return ret;
	}

	/**
	 * free
	 * 
	 * @return
	 * @throws NativeException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("deprecation")
	public static int sensorFree() throws NativeException,
			IllegalAccessException {

		JNative n = null;
		int ret = -1;

		try {
			n = new JNative(ZKFPCAPLIB, "sensorFree");
			n.setRetVal(Type.INT);

			n.invoke();
			ret = n.getRetValAsInt();
		} catch (NativeException e) {
			e.printStackTrace();
		} finally {
			if (null != n) {
				n.dispose();
			}
		}
		return ret;
	}

	/**
	 * open sensor
	 * 
	 * @param index
	 *            : begin with 0
	 * @return
	 * @throws NativeException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("deprecation")
	public static int sensorOpen(int index) throws NativeException,
			IllegalAccessException {

		JNative n = null;
		int ret = -1;

		try {
			n = new JNative(ZKFPCAPLIB, "sensorOpen");
			n.setRetVal(Type.INT);
			n.setParameter(0, Type.INT, String.valueOf(index));

			n.invoke();
			ret = n.getRetValAsInt();
		} catch (NativeException e) {
			e.printStackTrace();
		} finally {
			if (null != n) {
				n.dispose();
			}
		}
		return ret;
	}

	@SuppressWarnings("deprecation")
	public static int sensorClose(int handle) throws NativeException,
			IllegalAccessException {

		JNative n = null;
		int ret = -1;

		try {
			n = new JNative(ZKFPCAPLIB, "sensorClose");
			n.setRetVal(Type.INT);
			n.setParameter(0, handle);

			n.invoke();
			ret = n.getRetValAsInt();
		} catch (NativeException e) {
			e.printStackTrace();
		} finally {
			if (null != n) {
				n.dispose();
			}
		}
		return ret;
	}

	/**
	 * Get the number of sensor
	 * 
	 * @return
	 * @throws NativeException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("deprecation")
	public static int sensorGetCount() throws NativeException,
			IllegalAccessException {

		JNative n = null;
		int ret = -1;

		try {
			n = new JNative(ZKFPCAPLIB, "sensorGetCount");
			n.setRetVal(Type.INT);

			n.invoke();
			ret = n.getRetValAsInt();
		} catch (NativeException e) {
			e.printStackTrace();
		} finally {
			if (null != n) {
				n.dispose();
			}
		}
		return ret;
	}

	/**
	 * Get SDK version
	 * 
	 * @return
	 * @throws NativeException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("deprecation")
	public static String sensorGetVersion() throws NativeException,
			IllegalAccessException {

		JNative n = null;
		Pointer version = null;
		int ret = -1;
		String str = null;

		try {
			version = new Pointer(MemoryBlockFactory.createMemoryBlock(16));
			n = new JNative(ZKFPCAPLIB, "sensorGetVersion");
			n.setRetVal(Type.INT);
			n.setParameter(0, version);
			n.setParameter(1, 16);

			n.invoke();
			ret = n.getRetValAsInt();
			if (0 == ret) {
				str = version.getAsString();
			}
		} catch (NativeException e) {
			e.printStackTrace();
		} finally {
			if (null != version) {
				version.dispose();
			}
			if (null != n) {
				n.dispose();
			}
		}
		return str;
	}

	/**
	 * 
	 * @param handle
	 *            return by sensorOpen
	 * @param paramCode
	 * @param paramValue
	 * @param paramLen
	 * @return
	 * @throws NativeException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("deprecation")
	public static int sensorGetParameter(int handle, int paramCode,
			byte[] paramValue, int[] paramLen) throws NativeException,
			IllegalAccessException {

		JNative n = null;
		Pointer pPparam = null;
		Pointer pLen = null;
		int ret = -1;

		try {
			pPparam = new Pointer(
					MemoryBlockFactory.createMemoryBlock(paramValue.length));
			pLen = new Pointer(
					MemoryBlockFactory.createMemoryBlock(4 * paramLen.length));
			pLen.setIntAt(0, paramLen[0]);

			n = new JNative(ZKFPCAPLIB, "sensorGetParameterEx");
			n.setRetVal(Type.INT);
			n.setParameter(0, Type.INT, String.valueOf(handle));
			n.setParameter(1, Type.INT, String.valueOf(paramCode));
			n.setParameter(2, pPparam);
			n.setParameter(3, pLen);

			n.invoke();
			ret = n.getRetValAsInt();
			if (0 == ret) {
				System.arraycopy(pPparam.getMemory(), 0, paramValue, 0,
						pLen.getAsInt(0));
				paramLen[0] = pLen.getAsInt(0);
			}
		} catch (NativeException e) {
			e.printStackTrace();
		} finally {
			if (null != pPparam) {
				pPparam.dispose();
			}
			if (null != pLen) {
				pLen.dispose();
			}
			if (null != n) {
				n.dispose();
			}
		}
		return ret;
	}

	/**
	 * 
	 * @param handle
	 *            return by sensorOpen
	 * @param paramCode
	 *            101: background light 102: green light 103�� red light 104��beep
	 * @param paramValue
	 *            at least 4 bytes, 1: open 0: close
	 * @param paramLen
	 * @return
	 * @throws NativeException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("deprecation")
	public static int sensorSetParameter(int handle, int paramCode,
			byte[] paramValue, int paramLen) throws NativeException,
			IllegalAccessException {

		JNative n = null;
		Pointer pParam = null;
		int ret = -1;

		try {
			pParam = new Pointer(MemoryBlockFactory.createMemoryBlock(paramLen));
			pParam.setMemory(paramValue);

			n = new JNative(ZKFPCAPLIB, "sensorSetParameterEx");
			n.setRetVal(Type.INT);
			n.setParameter(0, Type.INT, String.valueOf(handle));
			n.setParameter(1, Type.INT, String.valueOf(paramCode));
			n.setParameter(2, pParam);
			n.setParameter(3, Type.INT, String.valueOf(paramLen));

			n.invoke();
			ret = n.getRetValAsInt();
		} catch (NativeException e) {
			e.printStackTrace();
		} finally {
			if (null != pParam) {
				pParam.dispose();
			}
			if (null != n) {
				n.dispose();
			}
		}
		return ret;
	}

	/**
	 * 
	 * @param handle
	 *            return by sensorOpen
	 * @param imageBuffer
	 * @param imageBufferSize
	 * @return
	 * @throws NativeException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("deprecation")
	public static int sensorCapture(int handle, byte[] imageBuffer,
			int imageBufferSize) throws NativeException, IllegalAccessException {

		JNative n = null;
		Pointer pBuffer = null;
		int ret = -1;

		try {
			pBuffer = new Pointer(MemoryBlockFactory.createMemoryBlock(imageBufferSize));

			n = new JNative(ZKFPCAPLIB, "sensorCapture");
			n.setRetVal(Type.INT);
			n.setParameter(0, Type.INT, String.valueOf(handle));
			n.setParameter(1, pBuffer);
			n.setParameter(2, Type.INT, String.valueOf(imageBufferSize));

			n.invoke();
			ret = n.getRetValAsInt();
			if (ret > 0) {
				System.arraycopy(pBuffer.getMemory(), 0, imageBuffer, 0, ret);
			}
		} catch (NativeException e) {
			e.printStackTrace();
		} finally {
			if (null != pBuffer) {
				pBuffer.dispose();
			}
			if (null != n) {
				n.dispose();
			}
		}
		return ret;
	}
}
