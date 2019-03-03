import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;

public class ZKFinger10 {

    private static final String ZKFINGERLIB = "ZKFinger10.dll";

    /**
     *
     * @param width
     * @param height
     * @return
     * @throws NativeException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("deprecation")
    // HANDLE BIOKEY_INIT(int License, WORD *isize, BYTE *Params, BYTE *Buffer,
    // int ImageFlag)
    public static int BIOKEY_INIT(int width, int height)
            throws NativeException, IllegalAccessException {

        JNative n = null;
        Pointer pSize = null;
        Pointer pParam = null;
        Pointer pBuffer = null;
        int ret = -1;

        try {
            pSize = new Pointer(MemoryBlockFactory.createMemoryBlock(44));
            pSize.setShortAt(0, (short) width);
            pSize.setShortAt(2, (short) height);
            pSize.setShortAt(40, (short) width);
            pSize.setShortAt(42, (short) height);

            n = new JNative(ZKFINGERLIB, "BIOKEY_INIT");
            n.setParameter(0, 0);
            n.setParameter(1, pSize);
            n.setParameter(2, pParam);
            n.setParameter(3, pBuffer);
            n.setParameter(4, 0);
            n.setRetVal(Type.INT);

            n.invoke();
            ret = n.getRetValAsInt();
        } catch (NativeException e) {
            e.printStackTrace();
        } finally {
            if (null != pSize) {
                pSize.dispose();
            }
            if (null != pParam) {
                pParam.dispose();
            }
            if (null != pBuffer) {
                pBuffer.dispose();
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
     *            return by BIOKEY_INIT
     * @return
     * @throws NativeException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("deprecation")
    // int BIOKEY_CLOSE(HANDLE Handle)
    public static int BIOKEY_CLOSE(int handle) throws NativeException,
            IllegalAccessException {

        JNative n = null;
        int ret = -1;

        try {
            n = new JNative(ZKFINGERLIB, "BIOKEY_CLOSE");
            n.setParameter(0, handle);
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
     *
     * @param handle
     *            return by BIOKEY_INIT
     * @param paramCode
     * @param paramValue
     * @return
     * @throws NativeException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("deprecation")
    // int BIOKEY_SET_PARAMETER(HANDLE Handle, int paramCode, int paramValue)
    public static int BIOKEY_SET_PARAMETER(int handle, int paramCode,
                                           int paramValue) throws NativeException, IllegalAccessException {

        JNative n = null;
        int ret = -1;

        try {
            n = new JNative(ZKFINGERLIB, "BIOKEY_SET_PARAMETER");
            n.setParameter(0, handle);
            n.setParameter(1, paramCode);
            n.setParameter(2, paramValue);
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
     *
     * @param handle
     *            return by BIOKEY_INIT
     * @param speed
     * @param threshold
     * @return
     * @throws NativeException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("deprecation")
    // int BIOKEY_MATCHINGPARAM(HANDLE Handle, int speed, int threshold)
    public static int BIOKEY_MATCHINGPARAM(int handle, int speed, int threshold)
            throws NativeException, IllegalAccessException {

        JNative n = null;
        int ret = -1;

        try {
            n = new JNative(ZKFINGERLIB, "BIOKEY_MATCHINGPARAM");
            n.setParameter(0, handle);
            n.setParameter(1, speed);
            n.setParameter(2, threshold);
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
     *
     * @param handle
     *            return by BIOKEY_INIT
     * @return
     * @throws NativeException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("deprecation")
    // int BIOKEY_GETLASTQUALITY(HANDLE Handle)
    public static int BIOKEY_GETLASTQUALITY(int handle) throws NativeException,
            IllegalAccessException {

        JNative n = null;
        int ret = -1;

        try {
            n = new JNative(ZKFINGERLIB, "BIOKEY_GETLASTQUALITY");
            n.setParameter(0, handle);
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
     *
     * @param handle
     *            return by BIOKEY_INIT
     * @return
     * @throws NativeException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("deprecation")
    // int BIOKEY_GETLASTERROR(HANDLE Handle)
    public static int BIOKEY_GETLASTERROR(int handle) throws NativeException,
            IllegalAccessException {

        JNative n = null;
        int ret = -1;

        try {
            n = new JNative(ZKFINGERLIB, "BIOKEY_GETLASTERROR");
            n.setParameter(0, handle);
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
     *
     * @param handle
     *            return by BIOKEY_INIT
     * @param PixelsBuffer
     * @param Template
     * @param PurposeMode
     * @return
     * @throws NativeException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("deprecation")
    // int BIOKEY_EXTRACT(HANDLE Handle, BYTE* PixelsBuffer, BYTE *Template, int
    // PurposeMode)
    public static int BIOKEY_EXTRACT(int handle, byte[] PixelsBuffer,
                                     byte[] Template, int PurposeMode) throws NativeException,
            IllegalAccessException {

        JNative n = null;
        Pointer pBuffer = null;
        Pointer pTemplate = null;
        int ret = -1;

        try {
            pBuffer = new Pointer(
                    MemoryBlockFactory.createMemoryBlock(PixelsBuffer.length));
            pBuffer.setMemory(PixelsBuffer);
            pTemplate = new Pointer(
                    MemoryBlockFactory.createMemoryBlock(Template.length));

            n = new JNative(ZKFINGERLIB, "BIOKEY_EXTRACT");
            n.setParameter(0, handle);
            n.setParameter(1, pBuffer);
            n.setParameter(2, pTemplate);
            n.setParameter(3, PurposeMode);

            n.setRetVal(Type.INT);

            n.invoke();
            ret = n.getRetValAsInt();
            if (ret > 0) {
                System.arraycopy(pTemplate.getMemory(), 0, Template, 0, ret);
            }
        } catch (NativeException e) {
            e.printStackTrace();
        } finally {
            if (null != pBuffer) {
                pBuffer.dispose();
            }
            if (null != pTemplate) {
                pTemplate.dispose();
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
     *            return by BIOKEY_INIT
     * @param Template1
     * @param Template2
     * @param Template3
     * @param TmpCount
     * @param GTemplate
     * @return
     * @throws NativeException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("deprecation")
    // int BIOKEY_GENTEMPLATE_SP(HANDLE Handle, BYTE *Template1, BYTE
    // *Template2, BYTE *Template3, int TmpCount, BYTE *GTemplate)
    public static int BIOKEY_GENTEMPLATE_SP(int handle, byte[] Template1,
                                            byte[] Template2, byte[] Template3, int TmpCount, byte[] GTemplate)
            throws NativeException, IllegalAccessException {

        JNative n = null;
        Pointer pTemplate1 = null;
        Pointer pTemplate2 = null;
        Pointer pTemplate3 = null;
        Pointer pTemplate = null;
        int ret = -1;

        try {
            pTemplate1 = new Pointer(
                    MemoryBlockFactory.createMemoryBlock(Template1.length));
            pTemplate1.setMemory(Template1);
            pTemplate2 = new Pointer(
                    MemoryBlockFactory.createMemoryBlock(Template2.length));
            pTemplate2.setMemory(Template2);
            pTemplate3 = new Pointer(
                    MemoryBlockFactory.createMemoryBlock(Template3.length));
            pTemplate3.setMemory(Template3);
            pTemplate = new Pointer(
                    MemoryBlockFactory.createMemoryBlock(GTemplate.length));

            n = new JNative(ZKFINGERLIB, "BIOKEY_GENTEMPLATE_SP");
            n.setParameter(0, handle);
            n.setParameter(1, pTemplate1);
            n.setParameter(2, pTemplate2);
            n.setParameter(3, pTemplate3);
            n.setParameter(4, TmpCount);
            n.setParameter(5, pTemplate);

            n.setRetVal(Type.INT);

            n.invoke();
            ret = n.getRetValAsInt();
            if (ret > 0) {
                System.arraycopy(pTemplate.getMemory(), 0, GTemplate, 0, ret);
            }
        } catch (NativeException e) {
            e.printStackTrace();
        } finally {
            if (null != pTemplate1) {
                pTemplate1.dispose();
            }
            if (null != pTemplate2) {
                pTemplate2.dispose();
            }
            if (null != pTemplate3) {
                pTemplate3.dispose();
            }
            if (null != pTemplate) {
                pTemplate.dispose();
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
     *            return by BIOKEY_INIT
     * @param TID
     * @param TempLength
     * @param Template
     * @return
     * @throws NativeException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("deprecation")
    // int BIOKEY_DB_ADD(HANDLE Handle, int TID, int TempLength, BYTE *Template)
    public static int BIOKEY_DB_ADD(int handle, int TID, int TempLength,
                                    byte[] Template) throws NativeException, IllegalAccessException {

        JNative n = null;
        Pointer pTemplate = null;
        int ret = -1;

        try {
            pTemplate = new Pointer(
                    MemoryBlockFactory.createMemoryBlock(Template.length));
            pTemplate.setMemory(Template);

            n = new JNative(ZKFINGERLIB, "BIOKEY_DB_ADD");
            n.setParameter(0, handle);
            n.setParameter(1, TID);
            n.setParameter(2, TempLength);
            n.setParameter(3, pTemplate);

            n.setRetVal(Type.INT);

            n.invoke();
            ret = n.getRetValAsInt();
        } catch (NativeException e) {
            e.printStackTrace();
        } finally {
            if (null != pTemplate) {
                pTemplate.dispose();
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
     *            return by BIOKEY_INIT
     * @param TID
     * @return
     * @throws NativeException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("deprecation")
    // int BIOKEY_DB_DEL(HANDLE Handle, int TID)
    public static int BIOKEY_DB_DEL(int handle, int TID)
            throws NativeException, IllegalAccessException {

        JNative n = null;
        int ret = -1;

        try {
            n = new JNative(ZKFINGERLIB, "BIOKEY_DB_DEL");
            n.setParameter(0, handle);
            n.setParameter(1, TID);
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
     *
     * @param handle
     *            return by BIOKEY_INIT
     * @return
     * @throws NativeException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("deprecation")
    // int BIOKEY_DB_COUNT(HANDLE Handle)
    public static int BIOKEY_DB_COUNT(int handle) throws NativeException,
            IllegalAccessException {

        JNative n = null;
        int ret = -1;

        try {
            n = new JNative(ZKFINGERLIB, "BIOKEY_DB_COUNT");
            n.setParameter(0, handle);
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
     *
     * @param handle
     *            return by BIOKEY_INIT
     * @return
     * @throws NativeException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("deprecation")
    // int BIOKEY_DB_CLEAR(HANDLE Handle)
    public static int BIOKEY_DB_CLEAR(int handle) throws NativeException,
            IllegalAccessException {

        JNative n = null;
        int ret = -1;

        try {
            n = new JNative(ZKFINGERLIB, "BIOKEY_DB_CLEAR");
            n.setParameter(0, handle);
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
     *
     * @param handle
     *            return by BIOKEY_INIT
     * @param Template
     * @param TID
     * @param Score
     * @return
     * @throws NativeException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("deprecation")
    // int BIOKEY_IDENTIFYTEMP(HANDLE Handle, BYTE *Template, int *TID, int
    // *Score)
    public static int BIOKEY_IDENTIFYTEMP(int handle, byte[] Template,
                                          int[] TID, int[] Score) throws NativeException,
            IllegalAccessException {

        JNative n = null;
        Pointer pTemplate = null;
        Pointer pTID = null;
        Pointer pScore = null;
        int ret = -1;

        try {
            pTemplate = new Pointer(
                    MemoryBlockFactory.createMemoryBlock(Template.length));
            pTemplate.setMemory(Template);
            pTID = new Pointer(
                    MemoryBlockFactory.createMemoryBlock(4 * TID.length));
            pScore = new Pointer(
                    MemoryBlockFactory.createMemoryBlock(4 * Score.length));
            n = new JNative(ZKFINGERLIB, "BIOKEY_IDENTIFYTEMP");
            n.setParameter(0, handle);
            n.setParameter(1, pTemplate);
            n.setParameter(2, pTID);
            n.setParameter(3, pScore);
            n.setRetVal(Type.INT);

            n.invoke();
            ret = n.getRetValAsInt();
            if (0 != ret) {
                TID[0] = pTID.getAsInt(0);
                Score[0] = pScore.getAsInt(0);
            }
        } catch (NativeException e) {
            e.printStackTrace();
        } finally {
            if (null != pTemplate) {
                pTemplate.dispose();
            }
            if (null != pTID) {
                pTID.dispose();
            }
            if (null != pScore) {
                pScore.dispose();
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
     *            return by BIOKEY_INIT
     * @param ImageBuffer
     * @param TID
     * @param Score
     * @return
     * @throws NativeException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("deprecation")
    // int BIOKEY_IDENTIFY(HANDLE Handle, BYTE *ImageBuffer, int *TID, int
    // *Score)
    public static int BIOKEY_IDENTIFY(int handle, byte[] ImageBuffer,
                                      int[] TID, int[] Score) throws NativeException,
            IllegalAccessException {

        JNative n = null;
        Pointer pBuffer = null;
        Pointer pTID = null;
        Pointer pScore = null;
        int ret = -1;

        try {
            pBuffer = new Pointer(
                    MemoryBlockFactory.createMemoryBlock(ImageBuffer.length));
            pBuffer.setMemory(ImageBuffer);
            pTID = new Pointer(
                    MemoryBlockFactory.createMemoryBlock(4 * TID.length));
            pScore = new Pointer(
                    MemoryBlockFactory.createMemoryBlock(4 * Score.length));
            n = new JNative(ZKFINGERLIB, "BIOKEY_IDENTIFY");
            n.setParameter(0, handle);
            n.setParameter(1, pBuffer);
            n.setParameter(2, pTID);
            n.setParameter(3, pScore);
            n.setRetVal(Type.INT);

            n.invoke();
            ret = n.getRetValAsInt();
            if (0 != ret) {
                TID[0] = pTID.getAsInt(0);
                Score[0] = pScore.getAsInt(0);
            }
        } catch (NativeException e) {
            e.printStackTrace();
        } finally {
            if (null != pBuffer) {
                pBuffer.dispose();
            }
            if (null != pTID) {
                pTID.dispose();
            }
            if (null != pScore) {
                pScore.dispose();
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
     *            return by BIOKEY_INIT
     * @param Template1
     * @param Template2
     * @return
     * @throws NativeException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("deprecation")
    // int BIOKEY_VERIFY(HANDLE handle, BYTE *Template1, BYTE *Template2)
    public static int BIOKEY_VERIFY(int handle, byte[] Template1,
                                    byte[] Template2) throws NativeException, IllegalAccessException {

        JNative n = null;
        Pointer pTemplate1 = null;
        Pointer pTemplate2 = null;
        int ret = -1;

        try {
            pTemplate1 = new Pointer(
                    MemoryBlockFactory.createMemoryBlock(Template1.length));
            pTemplate1.setMemory(Template1);
            pTemplate2 = new Pointer(
                    MemoryBlockFactory.createMemoryBlock(Template2.length));
            pTemplate2.setMemory(Template2);
            n = new JNative(ZKFINGERLIB, "BIOKEY_VERIFY");
            n.setParameter(0, handle);
            n.setParameter(1, pTemplate1);
            n.setParameter(2, pTemplate2);
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
}
