package org.tensorflow;

import java.lang.reflect.Array;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.Arrays;

public final class Tensor implements AutoCloseable {
    private long a;
    private DataType b;
    private long[] c = null;

    static {
        TensorFlow.a();
    }

    private Tensor() {
    }

    private static int a(DataType dataType) {
        switch (dataType) {
            case UINT8:
            case BOOL:
                return 1;
            case FLOAT:
            case INT32:
                return 4;
            case DOUBLE:
            case INT64:
                return 8;
            case STRING:
                throw new IllegalArgumentException("STRING tensors do not have a fixed element size");
            default:
                throw new IllegalArgumentException("DataType " + dataType + " is not supported yet");
        }
    }

    private static int a(long[] jArr) {
        int i = 1;
        for (long j : jArr) {
            i *= (int) j;
        }
        return i;
    }

    private static IllegalArgumentException a(int i, long[] jArr) {
        return new IllegalArgumentException(String.format("buffer with %d elements is not compatible with a Tensor with shape %s", new Object[]{Integer.valueOf(i), Arrays.toString(jArr)}));
    }

    private static IllegalArgumentException a(Buffer buffer, DataType dataType) {
        return new IllegalArgumentException(String.format("cannot use %s with Tensor of type %s", new Object[]{buffer.getClass().getName(), dataType}));
    }

    private static DataType a(Object obj) {
        if (obj.getClass().isArray()) {
            if (Array.getLength(obj) == 0) {
                throw new IllegalArgumentException("cannot create Tensors with a 0 dimension");
            }
            Object obj2 = Array.get(obj, 0);
            return (Byte.class.isInstance(obj2) || Byte.TYPE.isInstance(obj2)) ? DataType.STRING : a(obj2);
        } else if (Float.class.isInstance(obj) || Float.TYPE.isInstance(obj)) {
            return DataType.FLOAT;
        } else {
            if (Double.class.isInstance(obj) || Double.TYPE.isInstance(obj)) {
                return DataType.DOUBLE;
            }
            if (Integer.class.isInstance(obj) || Integer.TYPE.isInstance(obj)) {
                return DataType.INT32;
            }
            if (Long.class.isInstance(obj) || Long.TYPE.isInstance(obj)) {
                return DataType.INT64;
            }
            if (Boolean.class.isInstance(obj) || Boolean.TYPE.isInstance(obj)) {
                return DataType.BOOL;
            }
            throw new IllegalArgumentException("cannot create Tensors of " + obj.getClass().getName());
        }
    }

    static Tensor a(long j) {
        Tensor tensor = new Tensor();
        tensor.b = DataType.a(dtype(j));
        tensor.c = shape(j);
        tensor.a = j;
        return tensor;
    }

    private static Tensor a(DataType dataType, long[] jArr, int i) {
        int a = a(jArr);
        if (dataType != DataType.STRING) {
            if (i != a) {
                throw a(i, jArr);
            }
            i = a * a(dataType);
        }
        Tensor tensor = new Tensor();
        tensor.b = dataType;
        tensor.c = Arrays.copyOf(jArr, jArr.length);
        tensor.a = allocate(tensor.b.a(), tensor.c, (long) i);
        return tensor;
    }

    private static void a(Object obj, int i, long[] jArr) {
        int i2 = 0;
        if (jArr != null && i != jArr.length) {
            int length = Array.getLength(obj);
            if (jArr[i] == 0) {
                jArr[i] = (long) length;
            } else if (jArr[i] != ((long) length)) {
                throw new IllegalArgumentException(String.format("mismatched lengths (%d and %d) in dimension %d", new Object[]{Long.valueOf(jArr[i]), Integer.valueOf(length), Integer.valueOf(i)}));
            }
            while (i2 < length) {
                a(Array.get(obj, i2), i + 1, jArr);
                i2++;
            }
        }
    }

    private static native long allocate(int i, long[] jArr, long j);

    private static native long allocateScalarBytes(byte[] bArr);

    private static int b(Object obj) {
        if (!obj.getClass().isArray()) {
            return 0;
        }
        Object obj2 = Array.get(obj, 0);
        return (Byte.class.isInstance(obj2) || Byte.TYPE.isInstance(obj2)) ? 0 : b(obj2) + 1;
    }

    private ByteBuffer b() {
        return buffer(this.a).order(ByteOrder.nativeOrder());
    }

    private static native ByteBuffer buffer(long j);

    private void c(Object obj) {
        if (b(obj) != numDimensions()) {
            throw new IllegalArgumentException(String.format("cannot copy Tensor with %d dimensions into an object with %d", new Object[]{Integer.valueOf(numDimensions()), Integer.valueOf(b(obj))}));
        } else if (a(obj) != this.b) {
            throw new IllegalArgumentException(String.format("cannot copy Tensor with DataType %s into an object of type %s", new Object[]{this.b.toString(), obj.getClass().getName()}));
        } else {
            long[] jArr = new long[numDimensions()];
            a(obj, 0, jArr);
            for (int i = 0; i < jArr.length; i++) {
                if (jArr[i] != shape()[i]) {
                    throw new IllegalArgumentException(String.format("cannot copy Tensor with shape %s into object with shape %s", new Object[]{Arrays.toString(shape()), Arrays.toString(jArr)}));
                }
            }
        }
    }

    public static Tensor create(Object obj) {
        Tensor tensor = new Tensor();
        tensor.b = a(obj);
        tensor.c = new long[b(obj)];
        a(obj, 0, tensor.c);
        if (tensor.b != DataType.STRING) {
            tensor.a = allocate(tensor.b.a(), tensor.c, (long) (a(tensor.b) * a(tensor.c)));
            setValue(tensor.a, obj);
        } else if (tensor.c.length != 0) {
            throw new UnsupportedOperationException(String.format("non-scalar DataType.STRING tensors are not supported yet (version %s). Please file a feature request at https://github.com/tensorflow/tensorflow/issues/new", new Object[]{TensorFlow.version()}));
        } else {
            tensor.a = allocateScalarBytes((byte[]) obj);
        }
        return tensor;
    }

    public static Tensor create(DataType dataType, long[] jArr, ByteBuffer byteBuffer) {
        int a;
        if (dataType != DataType.STRING) {
            a = a(dataType);
            if (byteBuffer.remaining() % a != 0) {
                throw new IllegalArgumentException(String.format("ByteBuffer with %d bytes is not compatible with a %s Tensor (%d bytes/element)", new Object[]{Integer.valueOf(byteBuffer.remaining()), dataType.toString(), Integer.valueOf(a)}));
            }
            a = byteBuffer.remaining() / a;
        } else {
            a = byteBuffer.remaining();
        }
        Tensor a2 = a(dataType, jArr, a);
        a2.b().put(byteBuffer);
        return a2;
    }

    public static Tensor create(long[] jArr, DoubleBuffer doubleBuffer) {
        Tensor a = a(DataType.DOUBLE, jArr, doubleBuffer.remaining());
        a.b().asDoubleBuffer().put(doubleBuffer);
        return a;
    }

    public static Tensor create(long[] jArr, FloatBuffer floatBuffer) {
        Tensor a = a(DataType.FLOAT, jArr, floatBuffer.remaining());
        a.b().asFloatBuffer().put(floatBuffer);
        return a;
    }

    public static Tensor create(long[] jArr, IntBuffer intBuffer) {
        Tensor a = a(DataType.INT32, jArr, intBuffer.remaining());
        a.b().asIntBuffer().put(intBuffer);
        return a;
    }

    public static Tensor create(long[] jArr, LongBuffer longBuffer) {
        Tensor a = a(DataType.INT64, jArr, longBuffer.remaining());
        a.b().asLongBuffer().put(longBuffer);
        return a;
    }

    private static native void delete(long j);

    private static native int dtype(long j);

    private static native void readNDArray(long j, Object obj);

    private static native boolean scalarBoolean(long j);

    private static native byte[] scalarBytes(long j);

    private static native double scalarDouble(long j);

    private static native float scalarFloat(long j);

    private static native int scalarInt(long j);

    private static native long scalarLong(long j);

    private static native void setValue(long j, Object obj);

    private static native long[] shape(long j);

    long a() {
        return this.a;
    }

    public boolean booleanValue() {
        return scalarBoolean(this.a);
    }

    public byte[] bytesValue() {
        return scalarBytes(this.a);
    }

    public void close() {
        if (this.a != 0) {
            delete(this.a);
            this.a = 0;
        }
    }

    public <T> T copyTo(T t) {
        c(t);
        readNDArray(this.a, t);
        return t;
    }

    public DataType dataType() {
        return this.b;
    }

    public double doubleValue() {
        return scalarDouble(this.a);
    }

    public float floatValue() {
        return scalarFloat(this.a);
    }

    public int intValue() {
        return scalarInt(this.a);
    }

    public long longValue() {
        return scalarLong(this.a);
    }

    public int numBytes() {
        return b().remaining();
    }

    public int numDimensions() {
        return this.c.length;
    }

    public int numElements() {
        return a(this.c);
    }

    public long[] shape() {
        return this.c;
    }

    public String toString() {
        return String.format("%s tensor with shape %s", new Object[]{this.b.toString(), Arrays.toString(shape())});
    }

    public void writeTo(ByteBuffer byteBuffer) {
        byteBuffer.put(b());
    }

    public void writeTo(DoubleBuffer doubleBuffer) {
        if (this.b != DataType.DOUBLE) {
            throw a((Buffer) doubleBuffer, this.b);
        }
        doubleBuffer.put(b().asDoubleBuffer());
    }

    public void writeTo(FloatBuffer floatBuffer) {
        if (this.b != DataType.FLOAT) {
            throw a((Buffer) floatBuffer, this.b);
        }
        floatBuffer.put(b().asFloatBuffer());
    }

    public void writeTo(IntBuffer intBuffer) {
        if (this.b != DataType.INT32) {
            throw a((Buffer) intBuffer, this.b);
        }
        intBuffer.put(b().asIntBuffer());
    }

    public void writeTo(LongBuffer longBuffer) {
        if (this.b != DataType.INT64) {
            throw a((Buffer) longBuffer, this.b);
        }
        longBuffer.put(b().asLongBuffer());
    }
}
