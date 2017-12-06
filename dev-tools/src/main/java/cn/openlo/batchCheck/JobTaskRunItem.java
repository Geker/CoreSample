
package cn.openlo.batchCheck;

/**
 * <p>
 * Title: TaskRunItem
 * </p>
 * <p>
 * Description:任务划分后的执行对象
 * </p>
 * <p>
 * Copyright: openlo.cn Copyright (C) 2016
 * </p>
 *
 * @author SONGQQ
 *
 * @version
 * @since 2016年4月29日
 */
public class JobTaskRunItem {

    public static enum TaskRunStatus {
        READY,
        DOING,
        DONE,
        ERROR
    }

    /**
     * 真正的执行Bean
     */
    private String subTaskBean;

    /**
     * 自身的path
     */
    private String path;
    private String curServer = "NONE";

    /**
     * READY, ----初始化生成的状态
     * SCHEDULE, ---调度中，未调用bean的execute成功。
     * DOING, ---execute执行中
     * DONE, ---执行成功
     * ERROR ---失败
     */
    private TaskRunStatus status;
    private String parameter;
    private int index;
    private String context;

    /**
     * 作为传递上下文finishMsg的字段，不序列化
     */
    private transient String ctxStr;

    public String getCtxStr() {
        return ctxStr;
    }

    public void setCtxStr(String ctxStr) {
        this.ctxStr = ctxStr;
    }
    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    private String divideParam;

    public String getDivideParam() {
        return divideParam;
    }

    public void setDivideParam(String divideParam) {
        this.divideParam = divideParam;
    }

    /**
     * 调用次数 初始值为0
     */
    private volatile int times;

    public void setTimes(int times) {
        this.times = times;
    }

    /**
     * 增加调用次数
     *
     * @return 当前被调用次数
     */
    synchronized public int increaseSchduleTimes() {

        return times = times + 1;

    }

    /**
     * 设置部分失败原因等信息
     */
    private String info;

    public String getInfo() {

        return info;
    }

    public void setInfo(String info) {

        this.info = info;
    }

    public int getTimes() {
        return times;
    }

    public String getCurServer() {

        return curServer;
    }

    public void setCurServer(String curServer) {

        this.curServer = curServer;
    }

    public TaskRunStatus getStatus() {

        return status;
    }

    public void setStatus(TaskRunStatus status) {

        this.status = status;
    }

    public String getParameter() {

        return parameter;
    }

    public void setParameter(String parameter) {

        this.parameter = parameter;
    }

    public int getIndex() {

        return index;
    }

    public void setIndex(int index) {

        this.index = index;
    }

    public String getSubTaskBean() {
        return subTaskBean;
    }

    public void setSubTaskBean(String subTaskBean) {
        this.subTaskBean = subTaskBean;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "TaskRunItem [subTaskBean=" + subTaskBean + ", path=" + path + ", curServer=" + curServer + ", status=" + status
                + ", parameter=" + parameter + ", index=" + index + ", context=" + context + ", divideParam=" + divideParam + ", times="
                + times + ", info=" + info + "]";
    }

}
