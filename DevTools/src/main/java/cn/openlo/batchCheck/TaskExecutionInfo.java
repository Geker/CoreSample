package cn.openlo.batchCheck;
public class TaskExecutionInfo {

    private String systemname;
    private String transdate;
    private String taskname;
    private String runkey;
    private String runkey_value;
    private String status;

    public String getSystemname() {
        return systemname;
    }

    public void setSystemname(String systemname) {
        this.systemname = systemname;
    }

    public String getTransdate() {
        return transdate;
    }

    public void setTransdate(String transdate) {
        this.transdate = transdate;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getRunkey() {
        return runkey;
    }

    public void setRunkey(String runkey) {
        this.runkey = runkey;
    }

    public String getRunkey_value() {
        return runkey_value;
    }

    public void setRunkey_value(String runkey_value) {
        this.runkey_value = runkey_value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
