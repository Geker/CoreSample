package cn.openlo.batchCheck;
public class ServerInfo {
    private String transdate;
    private String taskname;
    private String runkey;
    private String status;
    private String subpath;
    private String server;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubpath() {
        return subpath;
    }

    public void setSubpath(String subpath) {
        this.subpath = subpath;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

}
