package org.tensorflow;

public final class Operation {
    private final long a;
    private final Graph b;

    Operation(Graph graph, long j) {
        this.b = graph;
        this.a = j;
    }

    private static native int dtype(long j, long j2, int i);

    private static native int inputListLength(long j, String str);

    private static native String name(long j);

    private static native int numOutputs(long j);

    private static native int outputListLength(long j, String str);

    private static native long[] shape(long j, long j2, int i);

    private static native String type(long j);

    long a() {
        return this.a;
    }

    long[] a(int i) {
        a a = this.b.a();
        try {
            long[] shape = shape(a.a(), this.a, i);
            return shape;
        } finally {
            a.close();
        }
    }

    DataType b(int i) {
        a a = this.b.a();
        try {
            DataType a2 = DataType.a(dtype(a.a(), this.a, i));
            return a2;
        } finally {
            a.close();
        }
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Operation)) {
            return false;
        }
        Operation operation = (Operation) obj;
        if (this.b != operation.b) {
            return false;
        }
        a a = this.b.a();
        try {
            if (this.a != operation.a) {
                z = false;
            }
            a.close();
            return z;
        } catch (Throwable th) {
            a.close();
        }
    }

    public int hashCode() {
        return Long.valueOf(this.a).hashCode();
    }

    public int inputListLength(String str) {
        a a = this.b.a();
        try {
            int inputListLength = inputListLength(this.a, str);
            return inputListLength;
        } finally {
            a.close();
        }
    }

    public String name() {
        a a = this.b.a();
        try {
            String name = name(this.a);
            return name;
        } finally {
            a.close();
        }
    }

    public int numOutputs() {
        a a = this.b.a();
        try {
            int numOutputs = numOutputs(this.a);
            return numOutputs;
        } finally {
            a.close();
        }
    }

    public Output output(int i) {
        return new Output(this, i);
    }

    public Output[] outputList(int i, int i2) {
        Output[] outputArr = new Output[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            outputArr[i3] = output(i + i3);
        }
        return outputArr;
    }

    public int outputListLength(String str) {
        a a = this.b.a();
        try {
            int outputListLength = outputListLength(this.a, str);
            return outputListLength;
        } finally {
            a.close();
        }
    }

    public String toString() {
        return String.format("<%s '%s'>", new Object[]{type(), name()});
    }

    public String type() {
        a a = this.b.a();
        try {
            String type = type(this.a);
            return type;
        } finally {
            a.close();
        }
    }
}
