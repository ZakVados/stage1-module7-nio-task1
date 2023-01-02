package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class FileReader {

    public Profile getDataFromFile(File file) {
        Profile profile = new Profile();
        String word = "";

        try (RandomAccessFile aFile = new RandomAccessFile(file, "r");
             FileChannel inChannel = aFile.getChannel();) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            while (inChannel.read(buffer) > 0) {
                buffer.flip();
                for (int i = 0; i < buffer.limit(); i++) {
                    word += (char) buffer.get();
                }
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] arr = word.split("\n");
        String name = getValueByKey(arr[0], "Name");
        profile.setName(name);
        String email = getValueByKey(arr[2], "Email");
        profile.setEmail(email);

        try {
            String age = getValueByKey(arr[1], "Age");
            profile.setAge(Integer.parseInt(age));
            Long phone = (long) Integer.parseInt(getValueByKey(arr[3], "Phone"));
            profile.setPhone(phone);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return profile;
    }

    public String getValueByKey(String pair, String key) {
        String[] arr = pair.split(": ");
        if (arr[0].equals(key)) return arr[1].trim();
        return "";
    }

    public static void main(String[] args) {
        FileReader fr = new FileReader();
        File file = new File("D:\\EPAM_MJCSS\\stage1-module7-nio-task1\\src\\main\\resources\\Profile.txt");
        fr.getDataFromFile(file);
    }
}
