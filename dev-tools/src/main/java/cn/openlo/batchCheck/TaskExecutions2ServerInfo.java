
package cn.openlo.batchCheck;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 推送给Server的执行信息，代表一次执行
 * <p>
 * Title: TaskExecutions2ServerInfo
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: openlo.cn Copyright (C) 2016
 * </p>
 *
 * @author SONGQQ
 * @since 2016年6月15日
 */
public class TaskExecutions2ServerInfo {

    private String subPath;// TaskExecutions的路径
    private TaskExecutionsStatus status; // 暂时未使用

    /**
     * 用于标识Divide任务是否分发给jobStepItemQueue。
     */
    private Map<String, Boolean> divideTaskMap;

    public Map<String, Boolean> getDivideTaskMap() {
        return divideTaskMap;
    }

    public void setDivideTaskMap(Map<String, Boolean> divideTaskMap) {
        this.divideTaskMap = divideTaskMap;
    }

    public TaskExecutions2ServerInfo() {
    }

    public String getSubPath() {

        return subPath;
    }

    public void setSubPath(String subPath) {

        this.subPath = subPath;
    }

    public TaskExecutionsStatus getStatus() {
        return status;
    }

    public void setStatus(TaskExecutionsStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TaskExecutions2ServerInfo [subPath=" + subPath + ", status=" + status + ", divideTaskMap=" + divideTaskMap + "]";
    }

    /**
     *
     * <p>
     * Title: TaskExecutionsStatus
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
     * @since 2016年10月28日
     */
    public enum TaskExecutionsStatus {
        READY,
        DOING,
        DONE,
        ERROR
    }


    /*生成任务的执行路径，用于记录执行状态*/
    public String genExecutingPath(String rootPath) {
        String path = this.getSubPath();
        String[] strs = StringUtils.split(path, "/");
        return rootPath + "/" + strs[1] + "." + strs[3] + "." + strs[4] + strs[5];

    }

    /*
    * 生成任务的执行lock路径
    * */
    public String genExecutingLockPath(String rootPath) {
        String path = this.getSubPath();
        String[] strs = StringUtils.split(path, "/");
        return rootPath + "/" + "locks" + "/" + strs[1] + "." + strs[3] + "." + strs[4] + strs[5] + ".lock";

    }
}
