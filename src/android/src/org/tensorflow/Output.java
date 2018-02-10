package org.tensorflow;

import java.util.Objects;

public final class Output implements Operand {
    private final Operation a;
    private final int b;

    public Output(Operation operation, int i) {
        this.a = operation;
        this.b = i;
    }

    public Output asOutput() {
        return this;
    }

    public DataType dataType() {
        return this.a.b(this.b);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Output)) {
            return false;
        }
        Output output = (Output) obj;
        return this.b == output.b && this.a.equals(output.a);
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.a, Integer.valueOf(this.b)});
    }

    public int index() {
        return this.b;
    }

    public Operation op() {
        return this.a;
    }

    public Shape shape() {
        return new Shape(this.a.a(this.b));
    }

    public String toString() {
        return String.format("<%s '%s:%d' shape=%s dtype=%s>", new Object[]{this.a.type(), this.a.name(), Integer.valueOf(this.b), shape().toString(), dataType()});
    }
}
