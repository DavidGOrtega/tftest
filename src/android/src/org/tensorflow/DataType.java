package org.tensorflow;

public enum DataType {
    FLOAT(1),
    DOUBLE(2),
    INT32(3),
    UINT8(4),
    STRING(7),
    INT64(9),
    BOOL(10);
    
    private final int a;

    private DataType(int i) {
        this.a = i;
    }

    static DataType a(int i) {
        for (DataType dataType : values()) {
            if (dataType.a() == i) {
                return dataType;
            }
        }
        throw new IllegalArgumentException("DataType " + i + " is not recognized in Java (version " + TensorFlow.version() + ")");
    }

    int a() {
        return this.a;
    }
}
