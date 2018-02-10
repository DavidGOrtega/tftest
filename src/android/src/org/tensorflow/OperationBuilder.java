package org.tensorflow;

import java.nio.charset.Charset;

public final class OperationBuilder {
    private long a;
    private Graph b;

    OperationBuilder(Graph graph, String str, String str2) {
        this.b = graph;
        a a = graph.a();
        try {
            this.a = allocate(a.a(), str, str2);
        } finally {
            a.close();
        }
    }

    private static native void addControlInput(long j, long j2);

    private static native void addInput(long j, long j2, int i);

    private static native void addInputList(long j, long[] jArr, int[] iArr);

    private static native long allocate(long j, String str, String str2);

    private static native long finish(long j);

    private static native void setAttrBool(long j, String str, boolean z);

    private static native void setAttrBoolList(long j, String str, boolean[] zArr);

    private static native void setAttrFloat(long j, String str, float f);

    private static native void setAttrFloatList(long j, String str, float[] fArr);

    private static native void setAttrInt(long j, String str, long j2);

    private static native void setAttrIntList(long j, String str, long[] jArr);

    private static native void setAttrShape(long j, String str, long[] jArr, int i);

    private static native void setAttrString(long j, String str, byte[] bArr);

    private static native void setAttrStringList(long j, String str, Object[] objArr);

    private static native void setAttrTensor(long j, String str, long j2);

    private static native void setAttrTensorList(long j, String str, long[] jArr);

    private static native void setAttrType(long j, String str, int i);

    private static native void setAttrTypeList(long j, String str, int[] iArr);

    private static native void setDevice(long j, String str);

    public OperationBuilder addControlInput(Operation operation) {
        a a = this.b.a();
        try {
            addControlInput(this.a, operation.a());
            return this;
        } finally {
            a.close();
        }
    }

    public OperationBuilder addInput(Output output) {
        a a = this.b.a();
        try {
            addInput(this.a, output.op().a(), output.index());
            return this;
        } finally {
            a.close();
        }
    }

    public OperationBuilder addInputList(Output[] outputArr) {
        a a = this.b.a();
        try {
            long[] jArr = new long[outputArr.length];
            int[] iArr = new int[outputArr.length];
            for (int i = 0; i < outputArr.length; i++) {
                jArr[i] = outputArr[i].op().a();
                iArr[i] = outputArr[i].index();
            }
            addInputList(this.a, jArr, iArr);
            return this;
        } finally {
            a.close();
        }
    }

    public Operation build() {
        a a = this.b.a();
        try {
            Operation operation = new Operation(this.b, finish(this.a));
            this.a = 0;
            return operation;
        } finally {
            a.close();
        }
    }

    public OperationBuilder setAttr(String str, float f) {
        a a = this.b.a();
        try {
            setAttrFloat(this.a, str, f);
            return this;
        } finally {
            a.close();
        }
    }

    public OperationBuilder setAttr(String str, long j) {
        a a = this.b.a();
        try {
            setAttrInt(this.a, str, j);
            return this;
        } finally {
            a.close();
        }
    }

    public OperationBuilder setAttr(String str, String str2) {
        setAttr(str, str2.getBytes(Charset.forName("UTF-8")));
        return this;
    }

    public OperationBuilder setAttr(String str, DataType dataType) {
        a a = this.b.a();
        try {
            setAttrType(this.a, str, dataType.a());
            return this;
        } finally {
            a.close();
        }
    }

    public OperationBuilder setAttr(String str, Shape shape) {
        a a = this.b.a();
        try {
            setAttrShape(this.a, str, shape.a(), shape.numDimensions());
            return this;
        } finally {
            a.close();
        }
    }

    public OperationBuilder setAttr(String str, Tensor tensor) {
        a a = this.b.a();
        try {
            setAttrTensor(this.a, str, tensor.a());
            return this;
        } finally {
            a.close();
        }
    }

    public OperationBuilder setAttr(String str, boolean z) {
        a a = this.b.a();
        try {
            setAttrBool(this.a, str, z);
            return this;
        } finally {
            a.close();
        }
    }

    public OperationBuilder setAttr(String str, byte[] bArr) {
        a a = this.b.a();
        try {
            setAttrString(this.a, str, bArr);
            return this;
        } finally {
            a.close();
        }
    }

    public OperationBuilder setAttr(String str, float[] fArr) {
        a a = this.b.a();
        try {
            setAttrFloatList(this.a, str, fArr);
            return this;
        } finally {
            a.close();
        }
    }

    public OperationBuilder setAttr(String str, long[] jArr) {
        a a = this.b.a();
        try {
            setAttrIntList(this.a, str, jArr);
            return this;
        } finally {
            a.close();
        }
    }

    public OperationBuilder setAttr(String str, String[] strArr) {
        Charset forName = Charset.forName("UTF-8");
        Object[] objArr = new Object[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            objArr[i] = strArr[i].getBytes(forName);
        }
        a a = this.b.a();
        try {
            setAttrStringList(this.a, str, objArr);
            return this;
        } finally {
            a.close();
        }
    }

    public OperationBuilder setAttr(String str, DataType[] dataTypeArr) {
        int[] iArr = new int[dataTypeArr.length];
        for (int i = 0; i < dataTypeArr.length; i++) {
            iArr[i] = dataTypeArr[i].a();
        }
        a a = this.b.a();
        try {
            setAttrTypeList(this.a, str, iArr);
            return this;
        } finally {
            a.close();
        }
    }

    public OperationBuilder setAttr(String str, Tensor[] tensorArr) {
        int i = 0;
        long[] jArr = new long[tensorArr.length];
        int length = tensorArr.length;
        int i2 = 0;
        while (i < length) {
            int i3 = i2 + 1;
            jArr[i2] = tensorArr[i].a();
            i++;
            i2 = i3;
        }
        a a = this.b.a();
        try {
            setAttrTensorList(this.a, str, jArr);
            return this;
        } finally {
            a.close();
        }
    }

    public OperationBuilder setAttr(String str, boolean[] zArr) {
        a a = this.b.a();
        try {
            setAttrBoolList(this.a, str, zArr);
            return this;
        } finally {
            a.close();
        }
    }

    public OperationBuilder setDevice(String str) {
        a a = this.b.a();
        try {
            setDevice(this.a, str);
            return this;
        } finally {
            a.close();
        }
    }
}
