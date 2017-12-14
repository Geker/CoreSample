package com.bn.dubbo.los.request;

/**
 * 用作LOS服务调用的请求类
 * <p>
 * Title: BatchInvokeRequest
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
 * @since 2016年8月12日
 */
public class BatchInvokeRequest {

    String productName;
    String taskName;
    String date;
    String runkey;
    String a;
    String context;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRunkey() {
        return runkey;
    }

    public void setRunkey(String runkey) {
        this.runkey = runkey;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public BatchInvokeRequest() {
    }

    public BatchInvokeRequest(String productName, String taskName, String date, String runkey, String context) {
        super();
        this.productName = productName;
        this.taskName = taskName;
        this.date = date;
        this.runkey = runkey;
        this.context = context;
    }

    @Override
    public String toString() {
        return "BatchRequest [productName=" + productName + ", taskName=" + taskName + ", date=" + date + ", runkey=" + runkey
                + ", context=" + context + "]";
    }

}
