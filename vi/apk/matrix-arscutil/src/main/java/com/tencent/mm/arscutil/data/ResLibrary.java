package com.tencent.mm.arscutil.data;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import pink.madis.apk.arsc.LibraryChunk;
import pink.madis.apk.arsc.PackageUtils;

public class ResLibrary extends ResChunk {
    /**
     * The number of resources of this type at creation time.
     */
    private  int entryCount;

    /**
     * The libraries used in this chunk (package id + name).
     */
    private byte[] entries;
    public void setEntryCount(int entryCount) {
        this.entryCount = entryCount;
    }
    public int getEntryCount() {
        return entryCount;
    }
    public byte[] getEntryTable() {
        return entries;
    }

    public void setEntryTable(byte[] entryTable) {
        this.entries = entryTable;
    }
    public byte[] toBytes() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(chunkSize);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.clear();
        byteBuffer.putShort(type);
        byteBuffer.putShort(headSize);
        byteBuffer.putInt(chunkSize);
        byteBuffer.putInt(entryCount);
        if (headPadding > 0) {
            byteBuffer.put(new byte[headPadding]);
        }
        byteBuffer.put(entries);
        if (chunkPadding > 0) {
            byteBuffer.put(new byte[chunkPadding]);
        }
        byteBuffer.flip();
        return byteBuffer.array();
    }
    public static class Entry {
        private final int packageId;
        private final String packageName;
        Entry(int packageId,String packageName){
            this.packageId  = packageId;
            this.packageName = packageName;
        }
        /**
         * Library entries only contain a package ID (4 bytes) and a package name.
         */
        public static final int SIZE = 4 + PackageUtils.PACKAGE_NAME_SIZE;
        public int packageId() {
            return packageId;
        }
        public String packageName() {
            return packageName;
        }

        public static Entry create(ByteBuffer buffer, int offset) {
            int packageId = buffer.getInt(offset);
            String packageName = PackageUtils.readPackageName(buffer, offset + 4);
            return new Entry(packageId, packageName);
        }

        public byte[] toByteArray() throws IOException {
            return toByteArray(false);
        }

        public byte[] toByteArray(boolean shrink) throws IOException {
            ByteBuffer buffer = ByteBuffer.allocate(SIZE).order(ByteOrder.LITTLE_ENDIAN);
            buffer.putInt(packageId());
            PackageUtils.writePackageName(buffer, packageName());
            return buffer.array();
        }
    }
}
