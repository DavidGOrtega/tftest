package org.tensorflow.contrib.android;

public class RunStats implements AutoCloseable {
    private static byte[] b = new byte[]{(byte) 8, (byte) 3};
    private long a = allocate();

    private static native void add(long j, byte[] bArr);

    private static native long allocate();

    private static native void delete(long j);

    public static byte[] runOptions() {
        return b;
    }

    private static native String summary(long j);

    public synchronized void add(byte[] bArr) {
        add(this.a, bArr);
    }

    public void close() {
        if (this.a != 0) {
            delete(this.a);
        }
        this.a = 0;
    }

    public synchronized String summary() {
        return summary(this.a);
    }
}
