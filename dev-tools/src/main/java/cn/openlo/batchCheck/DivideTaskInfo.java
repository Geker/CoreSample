package cn.openlo.batchCheck;

/**
 * <p>
 * Title: DivideTaskInfo 当前执行的Task的信息
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
public class DivideTaskInfo {

    private DivideTaskInfoStatus status;

    // 作为划分的参数
    private String dividedParam;
    private String context;

    private String info;

    public String getDividedParam() {
        return dividedParam;
    }

    public void setDividedParam(String dividedParam) {
        this.dividedParam = dividedParam;
    }

    public DivideTaskInfoStatus getStatus() {
        return status;
    }

    public void setStatus(DivideTaskInfoStatus status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "DivideTaskInfo [status=" + status + ", dividedParam=" + dividedParam + ", context=" + context + ", info=" + info + "]";
    }

    public enum DivideTaskInfoStatus {
        READY,
        DOING,
        ERROR,
        DONE

    }
}


