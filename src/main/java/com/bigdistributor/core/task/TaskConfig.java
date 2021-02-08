package com.bigdistributor.core.task;

import com.bigdistributor.core.config.RemoteFile;
import com.bigdistributor.io.GsonIO;
import java.io.File;

public class TaskConfig {
    String metadataPath;
    String taskParamsPath;
    RemoteFile input;
    RemoteFile output;
    String awsCredentialPath;
    String jobId;

    public TaskConfig(String metadataPath, String taskParamsPath, RemoteFile input, RemoteFile output, String awsCredentialPath, String jobId) {
        this.metadataPath = metadataPath;
        this.taskParamsPath = taskParamsPath;
        this.input = input;
        this.output = output;
        this.awsCredentialPath = awsCredentialPath;
        this.jobId = jobId;
    }

    @Override
    public String toString() {
        return "TaskConfig{" +
                "metadataPath='" + metadataPath + '\'' +
                ", taskParamsPath='" + taskParamsPath + '\'' +
                ", input=" + input +
                ", output=" + output +
                ", awsCredentialPath='" + awsCredentialPath + '\'' +
                ", jobId='" + jobId + '\'' +
                '}';
    }

    public static TaskConfig fromJson(String configPath) throws Exception {
        return  GsonIO.fromJson(configPath,TaskConfig.class);
    }

    public void save(File f) {
        GsonIO.toJson(this, f);
    }

    public String getMetadataPath() {
        return metadataPath;
    }

    public String getTaskParamsPath() {
        return taskParamsPath;
    }

    public RemoteFile getInput() {
        return input;
    }

    public RemoteFile getOutput() {
        return output;
    }

    public String getAwsCredentialPath() {
        return awsCredentialPath;
    }

    public String getJobId() {
        return jobId;
    }
}

