package org.tensorflow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class Session implements AutoCloseable {
    private final Graph a;
    private final a b;
    private final Object c;
    private long d;
    private int e;

    public static final class Run {
        public byte[] metadata;
        public List<Tensor> outputs;
    }

    public final class Runner {
        final /* synthetic */ Session a;
        private ArrayList<Output> b = new ArrayList();
        private ArrayList<Tensor> c = new ArrayList();
        private ArrayList<Output> d = new ArrayList();
        private ArrayList<Operation> e = new ArrayList();
        private byte[] f = null;

        private class a implements AutoCloseable {
            final /* synthetic */ Runner a;

            public a(Runner runner) {
                this.a = runner;
                synchronized (runner.a.c) {
                    if (runner.a.d == 0) {
                        throw new IllegalStateException("run() cannot be called on the Session after close()");
                    }
                    Session.c(runner.a);
                }
            }

            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void close() {
                /*
                r6 = this;
                r0 = r6.a;
                r0 = r0.a;
                r1 = r0.c;
                monitor-enter(r1);
                r0 = r6.a;	 Catch:{ all -> 0x0030 }
                r0 = r0.a;	 Catch:{ all -> 0x0030 }
                r2 = r0.d;	 Catch:{ all -> 0x0030 }
                r4 = 0;
                r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
                if (r0 != 0) goto L_0x0019;
            L_0x0017:
                monitor-exit(r1);	 Catch:{ all -> 0x0030 }
            L_0x0018:
                return;
            L_0x0019:
                r0 = r6.a;	 Catch:{ all -> 0x0030 }
                r0 = r0.a;	 Catch:{ all -> 0x0030 }
                r0 = org.tensorflow.Session.d(r0);	 Catch:{ all -> 0x0030 }
                if (r0 != 0) goto L_0x002e;
            L_0x0023:
                r0 = r6.a;	 Catch:{ all -> 0x0030 }
                r0 = r0.a;	 Catch:{ all -> 0x0030 }
                r0 = r0.c;	 Catch:{ all -> 0x0030 }
                r0.notifyAll();	 Catch:{ all -> 0x0030 }
            L_0x002e:
                monitor-exit(r1);	 Catch:{ all -> 0x0030 }
                goto L_0x0018;
            L_0x0030:
                r0 = move-exception;
                monitor-exit(r1);	 Catch:{ all -> 0x0030 }
                throw r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: org.tensorflow.Session.Runner.a.close():void");
            }
        }

        public Runner(Session session) {
            this.a = session;
        }

        private Operation a(String str) {
            Operation operation = this.a.a.operation(str);
            if (operation != null) {
                return operation;
            }
            throw new IllegalArgumentException("No Operation named [" + str + "] in the Graph");
        }

        private Run a(boolean z) {
            int i = 0;
            long[] jArr = new long[this.c.size()];
            long[] jArr2 = new long[this.b.size()];
            int[] iArr = new int[this.b.size()];
            long[] jArr3 = new long[this.d.size()];
            int[] iArr2 = new int[this.d.size()];
            long[] jArr4 = new long[this.e.size()];
            long[] jArr5 = new long[this.d.size()];
            Iterator it = this.c.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                int i3 = i2 + 1;
                jArr[i2] = ((Tensor) it.next()).a();
                i2 = i3;
            }
            Iterator it2 = this.b.iterator();
            i2 = 0;
            while (it2.hasNext()) {
                Output output = (Output) it2.next();
                jArr2[i2] = output.op().a();
                iArr[i2] = output.index();
                i2++;
            }
            it2 = this.d.iterator();
            i2 = 0;
            while (it2.hasNext()) {
                output = (Output) it2.next();
                jArr3[i2] = output.op().a();
                iArr2[i2] = output.index();
                i2++;
            }
            it = this.e.iterator();
            i2 = 0;
            while (it.hasNext()) {
                i3 = i2 + 1;
                jArr4[i2] = ((Operation) it.next()).a();
                i2 = i3;
            }
            a aVar = new a(this);
            try {
                byte[] a = Session.run(this.a.d, this.f, jArr, jArr2, iArr, jArr3, iArr2, jArr4, z, jArr5);
                List<Tensor> arrayList = new ArrayList();
                i2 = jArr5.length;
                while (i < i2) {
                    try {
                        arrayList.add(Tensor.a(jArr5[i]));
                        i++;
                    } catch (Exception e) {
                        Exception exception = e;
                        for (Tensor close : arrayList) {
                            close.close();
                        }
                        arrayList.clear();
                        throw exception;
                    }
                }
                Run run = new Run();
                run.outputs = arrayList;
                run.metadata = a;
                return run;
            } finally {
                aVar.close();
            }
        }

        private Output b(String str) {
            int lastIndexOf = str.lastIndexOf(58);
            if (lastIndexOf == -1 || lastIndexOf == str.length() - 1) {
                return new Output(a(str), 0);
            }
            try {
                String substring = str.substring(0, lastIndexOf);
                return new Output(a(substring), Integer.parseInt(str.substring(lastIndexOf + 1)));
            } catch (NumberFormatException e) {
                return new Output(a(str), 0);
            }
        }

        public Runner addTarget(String str) {
            Operation a = a(str);
            if (a != null) {
                this.e.add(a);
            }
            return this;
        }

        public Runner addTarget(Operation operation) {
            this.e.add(operation);
            return this;
        }

        public Runner feed(String str, int i, Tensor tensor) {
            Operation a = a(str);
            if (a != null) {
                this.b.add(a.output(i));
                this.c.add(tensor);
            }
            return this;
        }

        public Runner feed(String str, Tensor tensor) {
            return feed(b(str), tensor);
        }

        public Runner feed(Output output, Tensor tensor) {
            this.b.add(output);
            this.c.add(tensor);
            return this;
        }

        public Runner fetch(String str) {
            return fetch(b(str));
        }

        public Runner fetch(String str, int i) {
            Operation a = a(str);
            if (a != null) {
                this.d.add(a.output(i));
            }
            return this;
        }

        public Runner fetch(Output output) {
            this.d.add(output);
            return this;
        }

        public List<Tensor> run() {
            return a(false).outputs;
        }

        public Run runAndFetchMetadata() {
            return a(true);
        }

        public Runner setOptions(byte[] bArr) {
            this.f = bArr;
            return this;
        }
    }

    public Session(Graph graph) {
        this(graph, null);
    }

    public Session(Graph graph, byte[] bArr) {
        long allocate;
        this.c = new Object();
        this.a = graph;
        a a = graph.a();
        if (bArr == null) {
            try {
                allocate = allocate(a.a());
            } catch (Throwable th) {
                a.close();
            }
        } else {
            allocate = allocate2(a.a(), null, bArr);
        }
        this.d = allocate;
        this.b = graph.a();
        a.close();
    }

    private static native long allocate(long j);

    private static native long allocate2(long j, String str, byte[] bArr);

    static /* synthetic */ int c(Session session) {
        int i = session.e + 1;
        session.e = i;
        return i;
    }

    static /* synthetic */ int d(Session session) {
        int i = session.e - 1;
        session.e = i;
        return i;
    }

    private static native void delete(long j);

    private static native byte[] run(long j, byte[] bArr, long[] jArr, long[] jArr2, int[] iArr, long[] jArr3, int[] iArr2, long[] jArr4, boolean z, long[] jArr5);

    public void close() {
        this.b.close();
        synchronized (this.c) {
            if (this.d == 0) {
                return;
            }
            while (this.e > 0) {
                try {
                    this.c.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            delete(this.d);
            this.d = 0;
        }
    }

    public Runner runner() {
        return new Runner(this);
    }
}
