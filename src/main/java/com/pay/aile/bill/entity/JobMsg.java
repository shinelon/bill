package com.pay.aile.bill.entity;

/***
 * JobMsg.java
 *
 * @author shinelon
 *
 * @date 2017年11月9日
 *
 */
public class JobMsg {
    private Long creditEmailId;
    private String email;
    private Long lastJobTimestamp;
    private Long executionTime;
    private String status;
    private String Remarks;

    public Long getCreditEmailId() {
        return creditEmailId;
    }

    public String getEmail() {
        return email;
    }

    public Long getExecutionTime() {
        return executionTime;
    }

    public Long getLastJobTimestamp() {
        return lastJobTimestamp;
    }

    public String getRemarks() {
        return Remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setCreditEmailId(Long creditEmailId) {
        this.creditEmailId = creditEmailId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setExecutionTime(Long executionTime) {
        this.executionTime = executionTime;
    }

    public void setLastJobTimestamp(Long lastJobTimestamp) {
        this.lastJobTimestamp = lastJobTimestamp;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
