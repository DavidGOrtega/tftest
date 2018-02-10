package org.tensorflow;

public final class TensorFlow {
    static {
        a();
    }

    private TensorFlow() {
    }

    static void a() {
        a.a();
    }

    public static native byte[] registeredOpList();

    public static native String version();
}
