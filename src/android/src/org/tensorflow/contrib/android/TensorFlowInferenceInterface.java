package org.tensorflow.contrib.android;

import android.content.res.AssetManager;
import android.os.Build.VERSION;
import android.os.Trace;
import android.text.TextUtils;
import android.util.Log;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import org.tensorflow.DataType;
import org.tensorflow.Graph;
import org.tensorflow.Operation;
import org.tensorflow.Session;
import org.tensorflow.Session.Run;
import org.tensorflow.Session.Runner;
import org.tensorflow.Tensor;
import org.tensorflow.TensorFlow;

public class TensorFlowInferenceInterface {
    private final String a;
    private final Graph b;
    private final Session c;
    private Runner d;
    private List<String> e = new ArrayList();
    private List<Tensor> f = new ArrayList();
    private List<String> g = new ArrayList();
    private List<Tensor> h = new ArrayList();
    private RunStats i;

    private static class a {
        String a;
        int b;

        private a() {
        }

        public static a a(String str) {
            a aVar = new a();
            int lastIndexOf = str.lastIndexOf(58);
            if (lastIndexOf < 0) {
                aVar.b = 0;
                aVar.a = str;
            } else {
                try {
                    aVar.b = Integer.parseInt(str.substring(lastIndexOf + 1));
                    aVar.a = str.substring(0, lastIndexOf);
                } catch (NumberFormatException e) {
                    aVar.b = 0;
                    aVar.a = str;
                }
            }
            return aVar;
        }
    }

    public TensorFlowInferenceInterface(AssetManager assetManager, String str) {
        String str2;
        InputStream fileInputStream;
        Log.i("TensorFlowInferenceInterface", "Checking to see if TensorFlow native methods are already loaded");
        try {
            RunStats runStats = new RunStats();
            Log.i("TensorFlowInferenceInterface", "TensorFlow native methods already loaded");
        } catch (UnsatisfiedLinkError e) {
            Log.i("TensorFlowInferenceInterface", "TensorFlow native methods not found, attempting to load via tensorflow_inference");
            try {
                System.loadLibrary("tensorflow_inference");
                Log.i("TensorFlowInferenceInterface", "Successfully loaded TensorFlow native methods (RunStats error may be ignored)");
            } catch (UnsatisfiedLinkError e2) {
                throw new RuntimeException("Native TF methods not found; check that the correct native libraries are present in the APK.");
            }
        }
        this.a = str;
        this.b = new Graph();
        this.c = new Session(this.b);
        this.d = this.c.runner();
        boolean startsWith = str.startsWith("file:///android_asset/");
        if (startsWith) {
            try {
                str2 = str.split("file:///android_asset/")[1];
            } catch (Throwable e3) {
                if (startsWith) {
                    throw new RuntimeException("Failed to load model from '" + str + "'", e3);
                }
                try {
                    fileInputStream = new FileInputStream(str);
                } catch (IOException e4) {
                    throw new RuntimeException("Failed to load model from '" + str + "'", e3);
                }
            }
        }
        str2 = str;
        fileInputStream = assetManager.open(str2);
        try {
            a(fileInputStream, this.b);
            fileInputStream.close();
            Log.i("TensorFlowInferenceInterface", "Successfully loaded model from '" + str + "'");
        } catch (Throwable e32) {
            throw new RuntimeException("Failed to load model from '" + str + "'", e32);
        }
    }

    private void a() {
        for (Tensor close : this.f) {
            close.close();
        }
        this.f.clear();
        this.e.clear();
    }

    private void a(InputStream inputStream, Graph graph) {
        long currentTimeMillis = System.currentTimeMillis();
        if (VERSION.SDK_INT >= 18) {
            Trace.beginSection("initializeTensorFlow");
            Trace.beginSection("readGraphDef");
        }
        byte[] bArr = new byte[inputStream.available()];
        int read = inputStream.read(bArr);
        if (read != bArr.length) {
            throw new IOException("read error: read only " + read + " of the graph, expected to read " + bArr.length);
        }
        if (VERSION.SDK_INT >= 18) {
            Trace.endSection();
            Trace.beginSection("importGraphDef");
        }
        try {
            graph.importGraphDef(bArr);
            if (VERSION.SDK_INT >= 18) {
                Trace.endSection();
                Trace.endSection();
            }
            Log.i("TensorFlowInferenceInterface", "Model load took " + (System.currentTimeMillis() - currentTimeMillis) + "ms, TensorFlow version: " + TensorFlow.version());
        } catch (IllegalArgumentException e) {
            throw new IOException("Not a valid TensorFlow Graph serialization: " + e.getMessage());
        }
    }

    private void a(String str, Tensor tensor) {
        a a = a.a(str);
        this.d.feed(a.a, a.b, tensor);
        this.e.add(str);
        this.f.add(tensor);
    }

    private void b() {
        for (Tensor close : this.h) {
            close.close();
        }
        this.h.clear();
        this.g.clear();
    }

    public void close() {
        a();
        b();
        this.c.close();
        this.b.close();
        if (this.i != null) {
            this.i.close();
        }
        this.i = null;
    }

    public void feed(String str, ByteBuffer byteBuffer, long... jArr) {
        a(str, Tensor.create(DataType.UINT8, jArr, byteBuffer));
    }

    public void feed(String str, DoubleBuffer doubleBuffer, long... jArr) {
        a(str, Tensor.create(jArr, doubleBuffer));
    }

    public void feed(String str, FloatBuffer floatBuffer, long... jArr) {
        a(str, Tensor.create(jArr, floatBuffer));
    }

    public void feed(String str, IntBuffer intBuffer, long... jArr) {
        a(str, Tensor.create(jArr, intBuffer));
    }

    public void feed(String str, byte[] bArr, long... jArr) {
        a(str, Tensor.create(DataType.UINT8, jArr, ByteBuffer.wrap(bArr)));
    }

    public void feed(String str, double[] dArr, long... jArr) {
        a(str, Tensor.create(jArr, DoubleBuffer.wrap(dArr)));
    }

    public void feed(String str, float[] fArr, long... jArr) {
        a(str, Tensor.create(jArr, FloatBuffer.wrap(fArr)));
    }

    public void feed(String str, int[] iArr, long... jArr) {
        a(str, Tensor.create(jArr, IntBuffer.wrap(iArr)));
    }

    public void fetch(String str, ByteBuffer byteBuffer) {
        getTensor(str).writeTo(byteBuffer);
    }

    public void fetch(String str, DoubleBuffer doubleBuffer) {
        getTensor(str).writeTo(doubleBuffer);
    }

    public void fetch(String str, FloatBuffer floatBuffer) {
        getTensor(str).writeTo(floatBuffer);
    }

    public void fetch(String str, IntBuffer intBuffer) {
        getTensor(str).writeTo(intBuffer);
    }

    public void fetch(String str, byte[] bArr) {
        fetch(str, ByteBuffer.wrap(bArr));
    }

    public void fetch(String str, double[] dArr) {
        fetch(str, DoubleBuffer.wrap(dArr));
    }

    public void fetch(String str, float[] fArr) {
        fetch(str, FloatBuffer.wrap(fArr));
    }

    public void fetch(String str, int[] iArr) {
        fetch(str, IntBuffer.wrap(iArr));
    }

    protected void finalize() {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    public String getStatString() {
        return this.i == null ? "" : this.i.summary();
    }

    public Tensor getTensor(String str) {
        int i = 0;
        for (String equals : this.g) {
            if (equals.equals(str)) {
                return (Tensor) this.h.get(i);
            }
            i++;
        }
        throw new RuntimeException("Node '" + str + "' was not provided to run(), so it cannot be read");
    }

    public Tensor getTensor2(String str) {
        int i = 0;
        for (String equals : this.g) {
            if (equals.equals(str)) {
                return (Tensor) this.h.get(i);
            }
            i++;
        }
        throw new RuntimeException("Node '" + str + "' was not provided to run(), so it cannot be read");
    }

    public Graph graph() {
        return this.b;
    }

    public Operation graphOperation(String str) {
        Operation operation = this.b.operation(str);
        if (operation != null) {
            return operation;
        }
        throw new RuntimeException("Node '" + str + "' does not exist in model '" + this.a + "'");
    }

    public void run(String[] strArr) {
        run(strArr, false);
    }

    public void run(String[] strArr, boolean z) {
        b();
        for (String str : strArr) {
            this.g.add(str);
            a a = a.a(str);
            this.d.fetch(a.a, a.b);
        }
        if (z) {
            try {
                Run runAndFetchMetadata = this.d.setOptions(RunStats.runOptions()).runAndFetchMetadata();
                this.h = runAndFetchMetadata.outputs;
                if (this.i == null) {
                    this.i = new RunStats();
                }
                this.i.add(runAndFetchMetadata.metadata);
            } catch (RuntimeException e) {
                Log.e("TensorFlowInferenceInterface", "Failed to run TensorFlow inference with inputs:[" + TextUtils.join(", ", this.e) + "], outputs:[" + TextUtils.join(", ", this.g) + "]");
                throw e;
            } catch (Throwable th) {
                a();
                this.d = this.c.runner();
            }
        } else {
            this.h = this.d.run();
        }
        a();
        this.d = this.c.runner();
    }
}
