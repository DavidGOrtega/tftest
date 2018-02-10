package org.tensorflow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

final class a {
    private static final boolean a = (System.getProperty("org.tensorflow.NativeLibrary.DEBUG") != null);

    private a() {
    }

    private static long a(InputStream inputStream, File file) {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try {
            byte[] bArr = new byte[1048576];
            long j = 0;
            while (true) {
                int read = inputStream.read(bArr);
                if (read < 0) {
                    break;
                }
                fileOutputStream.write(bArr, 0, read);
                j += (long) read;
            }
            return j;
        } finally {
            fileOutputStream.close();
            inputStream.close();
        }
    }

    private static String a(InputStream inputStream) {
        String mapLibraryName = System.mapLibraryName("tensorflow_jni");
        int indexOf = mapLibraryName.indexOf(".");
        File createTempFile = File.createTempFile(indexOf < 0 ? mapLibraryName : mapLibraryName.substring(0, indexOf), indexOf < 0 ? null : mapLibraryName.substring(indexOf));
        String absolutePath = createTempFile.getAbsolutePath();
        createTempFile.deleteOnExit();
        a("extracting native library to: " + absolutePath);
        long a = a(inputStream, createTempFile);
        a(String.format("copied %d bytes to %s", new Object[]{Long.valueOf(a), absolutePath}));
        return absolutePath;
    }

    public static void a() {
        if (!c() && !b()) {
            String f = f();
            a("resourceName: " + f);
            InputStream resourceAsStream = a.class.getClassLoader().getResourceAsStream(f);
            if (resourceAsStream == null) {
                throw new UnsatisfiedLinkError(String.format("Cannot find TensorFlow native library for OS: %s, architecture: %s. See https://github.com/tensorflow/tensorflow/tree/master/tensorflow/java/README.md for possible solutions (such as building the library from source). Additional information on attempts to find the native library can be obtained by adding org.tensorflow.NativeLibrary.DEBUG=1 to the system properties of the JVM.", new Object[]{d(), e()}));
            }
            try {
                System.load(a(resourceAsStream));
            } catch (IOException e) {
                throw new UnsatisfiedLinkError(String.format("Unable to extract native library into a temporary file (%s)", new Object[]{e.toString()}));
            }
        }
    }

    private static void a(String str) {
        if (a) {
            System.err.println("org.tensorflow.NativeLibrary: " + str);
        }
    }

    private static boolean b() {
        try {
            System.loadLibrary("tensorflow_jni");
            return true;
        } catch (UnsatisfiedLinkError e) {
            a("tryLoadLibraryFailed: " + e.getMessage());
            return false;
        }
    }

    private static boolean c() {
        try {
            TensorFlow.version();
            a("isLoaded: true");
            return true;
        } catch (UnsatisfiedLinkError e) {
            return false;
        }
    }

    private static String d() {
        String toLowerCase = System.getProperty("os.name").toLowerCase();
        return toLowerCase.contains("linux") ? "linux" : (toLowerCase.contains("os x") || toLowerCase.contains("darwin")) ? "darwin" : toLowerCase.contains("windows") ? "windows" : toLowerCase.replaceAll("\\s", "");
    }

    private static String e() {
        String toLowerCase = System.getProperty("os.arch").toLowerCase();
        return toLowerCase.equals("amd64") ? "x86_64" : toLowerCase;
    }

    private static String f() {
        return "org/tensorflow/native/" + String.format("%s-%s/", new Object[]{d(), e()}) + System.mapLibraryName("tensorflow_jni");
    }
}
