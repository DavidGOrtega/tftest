package org.tensorflow;

public class SavedModelBundle implements AutoCloseable {
    private final Graph a;
    private final Session b;
    private final byte[] c;

    static {
        TensorFlow.a();
    }

    public static SavedModelBundle load(String str, String... strArr) {
        return load(str, strArr, null);
    }

    private static native SavedModelBundle load(String str, String[] strArr, byte[] bArr);

    public void close() {
        this.b.close();
        this.a.close();
    }

    public Graph graph() {
        return this.a;
    }

    public byte[] metaGraphDef() {
        return this.c;
    }

    public Session session() {
        return this.b;
    }
}
