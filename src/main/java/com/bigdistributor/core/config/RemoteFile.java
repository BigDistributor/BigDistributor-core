package com.bigdistributor.core.config;

public class RemoteFile {
    String bucketName;
    String path;
    String FileName;

    public RemoteFile(String bucketName, String path, String fileName) {
        this.bucketName = bucketName;
        this.path = path;
        FileName = fileName;
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getPath() {
        return path;
    }

    public String getFileName() {
        return FileName;
    }
}
