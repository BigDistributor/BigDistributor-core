package com.bigdistributor.core.task.items;

import com.bigdistributor.core.task.JobID;

import java.io.File;


public class Job extends Object {

    public static final String TASK_CLUSTER_NAME = "task.jar";

    private String id;
    private DataAccessMode dataAccess;
    private ProcessMode processMode;
    private String tmpDir;
    private int totalBlocks;

    private static Job instance;

    public static Job get() {
        if (instance == null) {
            instance = job();
            return get();
        } else {
            return instance;
        }
    }

    public static Job create(ProcessMode processMode) {
        instance = job(processMode);
        return get();
    }

    private static Job job(ProcessMode processMode) {
        String id = new JobID().get();
        System.out.println("Job id: " + id);
        File tmpDir = createTempDir();
        DataAccessMode access = DataAccessMode.READY_IN_CLUSTER_INPUT;
        ProcessMode mode = processMode;
        return new Job(id, access, mode, tmpDir, 0);
    }

    public static Job create() {
        instance = job();
        return get();
    }

    private Job(String id, DataAccessMode dataAccess, ProcessMode processMode, File tmpDir, int totalBlocks) {
        super();
        this.id = id;
        this.dataAccess = dataAccess;
        this.processMode = processMode;
        this.tmpDir = tmpDir.getAbsolutePath();
        this.totalBlocks = totalBlocks;
    }

    private static Job job() {
        return job(ProcessMode.CLUSTER_PROCESSING);
    }

    private static File createTempDir() {
        return TmpDir.get();
    }

    public String getId() {
        return id;
    }

    public File getTmpDir() {
        return new File(tmpDir);
    }

    public File file(String string) {
        return new File(tmpDir, string);
    }

    public int getTotalBlocks() {
        return totalBlocks;
    }

    public void setTotalBlocks(int totalBlocks) {
        this.totalBlocks = totalBlocks;
    }

    public ProcessMode getProcessMode() {
        return processMode;
    }

    public DataAccessMode getDataAccess() {
        return dataAccess;
    }
}
