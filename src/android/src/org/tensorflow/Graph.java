package org.tensorflow;

public final class Graph implements AutoCloseable {
    private final Object a = new Object();
    private long b = allocate();
    private int c = 0;

    class a implements AutoCloseable {
        final /* synthetic */ Graph a;
        private boolean b;

        private a(Graph graph) {
            boolean z = true;
            this.a = graph;
            synchronized (graph.a) {
                if (graph.b == 0) {
                    z = false;
                }
                this.b = z;
                if (this.b) {
                    this.b = true;
                    graph.c = graph.c + 1;
                } else {
                    throw new IllegalStateException("close() has been called on the Graph");
                }
            }
        }

        public long a() {
            long b;
            synchronized (this.a.a) {
                b = this.b ? this.a.b : 0;
            }
            return b;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void close() {
            /*
            r2 = this;
            r0 = r2.a;
            r1 = r0.a;
            monitor-enter(r1);
            r0 = r2.b;	 Catch:{ all -> 0x0023 }
            if (r0 != 0) goto L_0x000d;
        L_0x000b:
            monitor-exit(r1);	 Catch:{ all -> 0x0023 }
        L_0x000c:
            return;
        L_0x000d:
            r0 = 0;
            r2.b = r0;	 Catch:{ all -> 0x0023 }
            r0 = r2.a;	 Catch:{ all -> 0x0023 }
            r0 = org.tensorflow.Graph.d(r0);	 Catch:{ all -> 0x0023 }
            if (r0 != 0) goto L_0x0021;
        L_0x0018:
            r0 = r2.a;	 Catch:{ all -> 0x0023 }
            r0 = r0.a;	 Catch:{ all -> 0x0023 }
            r0.notifyAll();	 Catch:{ all -> 0x0023 }
        L_0x0021:
            monitor-exit(r1);	 Catch:{ all -> 0x0023 }
            goto L_0x000c;
        L_0x0023:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0023 }
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: org.tensorflow.Graph.a.close():void");
        }
    }

    static {
        TensorFlow.a();
    }

    private static native long allocate();

    static /* synthetic */ int d(Graph graph) {
        int i = graph.c - 1;
        graph.c = i;
        return i;
    }

    private static native void delete(long j);

    private static native void importGraphDef(long j, byte[] bArr, String str);

    private static native long operation(long j, String str);

    private static native byte[] toGraphDef(long j);

    a a() {
        return new a();
    }

    public void close() {
        synchronized (this.a) {
            if (this.b == 0) {
                return;
            }
            while (this.c > 0) {
                try {
                    this.a.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            delete(this.b);
            this.b = 0;
        }
    }

    public void importGraphDef(byte[] bArr) {
        importGraphDef(bArr, "");
    }

    public void importGraphDef(byte[] bArr, String str) {
        if (bArr == null || str == null) {
            throw new IllegalArgumentException("graphDef and prefix cannot be null");
        }
        synchronized (this.a) {
            importGraphDef(this.b, bArr, str);
        }
    }

    public OperationBuilder opBuilder(String str, String str2) {
        return new OperationBuilder(this, str, str2);
    }

    public Operation operation(String str) {
        Operation operation;
        synchronized (this.a) {
            long operation2 = operation(this.b, str);
            if (operation2 == 0) {
                operation = null;
            } else {
                operation = new Operation(this, operation2);
            }
        }
        return operation;
    }

    public byte[] toGraphDef() {
        byte[] toGraphDef;
        synchronized (this.a) {
            toGraphDef = toGraphDef(this.b);
        }
        return toGraphDef;
    }
}
