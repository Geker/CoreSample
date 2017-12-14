package cn.openlo.batchCheck;

import java.util.List;

/**
 * <p>
 * Title: ExecutionInfo 当前执行的Task的信息
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: ufo Copyright (C) 2016
 * </p>
 *
 * @author SONGQQ
 * @version
 * @since 2016年8月8日
 */
public class ExecutionTaskInfo {
    private ExecutionInfoStatus status;
    private String msg;
    private String servers;



    private volatile int cnt;
    private List<String> relations;

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public int decrementAndGet() {
        return --cnt;
    }

    public ExecutionInfoStatus getStatus() {
        return status;
    }

    public void setStatus(ExecutionInfoStatus status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getRelations() {
        return relations;
    }

    public void setRelations(List<String> relations) {
        this.relations = relations;
    }

    public String getServers() {
        return servers;
    }

    public void setServers(String servers) {
        this.servers = servers;
    }

    @Override
    public String toString() {
        return "ExecutionTaskInfo [status=" + status + ", msg=" + msg + ", servers=" + servers + ", cnt=" + cnt + ", relations=" + relations
                + "]";
    }

    /**
     * <p>
     * Title: ExecutionInfoStatus
     * </p>
     * <p>
     * Description:
     * </p>
     * <p>
     * Copyright: ufo Copyright (C) 2016
     * </p>
     *
     * @author SONGQQ
     * @version
     * @since 2016年8月10日
     */
    public enum ExecutionInfoStatus {
        READY, // 就绪状态，还未推送
        /**
         * 执行中 --暂时未使用
         */
        // DOING,
        ERROR, // 执行失败，目前未使用
        DONE, // 执行成功
        /**
         * 生成并推送成功状态---执行时一般都是此状态
         */
        GEN_SUCC,
        /**
         * 生成成功，但是未有服务器接收到执行的推送信息。
         */
        NO_SERVER,
        ERR_GEN // 生成错误
    }
}

