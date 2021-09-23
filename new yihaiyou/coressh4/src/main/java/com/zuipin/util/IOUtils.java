package com.zuipin.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.codec.binary.Base64;

public class IOUtils {
    public static String text(InputStream inputStream) {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }
            return builder.toString();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String text(File file) {
        try {
            FileInputStream stream = new FileInputStream(file);
            String text = text(stream);
            stream.close();
            return text;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String base64(InputStream inputStream) {
        try {
            BufferedInputStream input = new BufferedInputStream(inputStream);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1000];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            byte[] bytes = output.toByteArray();
            return new String(Base64.encodeBase64(bytes));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String base64(File file) {
        try {
            FileInputStream stream = new FileInputStream(file);
            String string = base64(stream);
            stream.close();
            return string;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
