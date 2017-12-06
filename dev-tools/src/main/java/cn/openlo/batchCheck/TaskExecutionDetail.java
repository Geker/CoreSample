package cn.openlo.batchCheck;
public class TaskExecutionDetail {
    private String runkey;
    private String divide;
    private String divide_value;
    private String divide_status;
    private String step;
    private String step_value;
    private String step_status;
    private String step_finishMsg;
    private String job;
    private String job_value;
    private String job_status;

    public String getDivide_status() {
        return divide_status;
    }

    public void setDivide_status(String divide_status) {
        this.divide_status = divide_status;
    }

    public String getStep_status() {
        return step_status;
    }

    public void setStep_status(String step_status) {
        this.step_status = step_status;
    }

    public String getJob_status() {
        return job_status;
    }

    public void setJob_status(String job_status) {
        this.job_status = job_status;
    }

    public String getRunkey() {
        return runkey;
    }

    public void setRunkey(String runkey) {
        this.runkey = runkey;
    }

    public String getDivide() {
        return divide;
    }

    public void setDivide(String divide) {
        this.divide = divide;
    }

    public String getDivide_value() {
        return divide_value;
    }

    public void setDivide_value(String divide_value) {
        this.divide_value = divide_value;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getStep_value() {
        return step_value;
    }

    public void setStep_value(String step_value) {
        this.step_value = step_value;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getJob_value() {
        return job_value;
    }

    public void setJob_value(String job_value) {
        this.job_value = job_value;
    }

    public String getStep_finishMsg() {
        return step_finishMsg;
    }

    public void setStep_finishMsg(String step_finishMsg) {
        this.step_finishMsg = step_finishMsg;
    }
}
