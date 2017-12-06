package cn.openlo.batchCheck;

import java.util.List;

/**
 * <p>
 * Title: StepStatus
 * </p>
 * <p>
 * Description:执行步骤的当前状态和计数
 * </p>
 * <p>
 * Copyright: ufo Copyright (C) 2016
 * </p>
 *
 * @author SONGQQ
 * @version
 * @since 2016年7月20日
 */
public class StepTaskInfo {
    // 作为划分的参数
    private String dividedParam;

    private String context;

    /**
     * step执行完毕之后的消息
     */
    private String finishMsg;

    public String getFinishMsg() {
        return finishMsg;
    }

    public void setFinishMsg(String finishMsg) {
        this.finishMsg = finishMsg;
    }

    public enum StepTaskInfoStatus {
        READY,
        DOING,
        DONE,
        ERROR
    }

    private StepTaskInfoStatus curStatus; // 当前状态
    private int cnt; // 子步骤未完成数量

    /**
     * 步骤
     */
    private List<String> relations;

    public List<String> getRelations() {
        return relations;
    }

    public void setRelations(List<String> relations) {
        this.relations = relations;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public StepTaskInfoStatus getCurStatus() {
        return curStatus;
    }

    public void setCurStatus(StepTaskInfoStatus curStatus) {
        this.curStatus = curStatus;
    }

    public int decrementAndGet() {
        return --cnt;
    }

    public StepTaskInfo(StepTaskInfoStatus curStatus, int cnt) {
        this.curStatus = curStatus;
        this.cnt = cnt;
    }

    public StepTaskInfo(StepTaskInfoStatus curStatus) {
        this.curStatus = curStatus;
    }

    public StepTaskInfo() {
    }

    public String getDividedParam() {
        return dividedParam;
    }

    public void setDividedParam(String dividedParam) {
        this.dividedParam = dividedParam;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "StepTaskInfo [dividedParam=" + dividedParam + ", context=" + context + ", curStatus=" + curStatus + ", cnt=" + cnt
                + ", relations=" + relations + "]";
    }

}
