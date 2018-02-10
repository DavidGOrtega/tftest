package org.tensorflow;

import java.util.Arrays;

public final class Shape {
    private long[] a;

    Shape(long[] jArr) {
        this.a = jArr;
    }

    public static Shape make(long j, long... jArr) {
        Object obj = new long[(jArr.length + 1)];
        obj[0] = j;
        System.arraycopy(jArr, 0, obj, 1, jArr.length);
        return new Shape(obj);
    }

    public static Shape scalar() {
        return new Shape(new long[0]);
    }

    public static Shape unknown() {
        return new Shape(null);
    }

    long[] a() {
        return this.a;
    }

    public int numDimensions() {
        return this.a == null ? -1 : this.a.length;
    }

    public long size(int i) {
        return this.a[i];
    }

    public String toString() {
        return this.a == null ? "<unknown>" : Arrays.toString(this.a).replace("-1", "?");
    }
}
