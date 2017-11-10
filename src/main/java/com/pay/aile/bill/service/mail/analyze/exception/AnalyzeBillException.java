package com.pay.aile.bill.service.mail.analyze.exception;

/***
 * MailBillException.java
 *
 * @author shinelon
 *
 * @date 2017年10月30日
 *
 */
public class AnalyzeBillException extends Exception {

    private static final long serialVersionUID = 1980019507208526746L;

    private Integer errorCode;

    public AnalyzeBillException() {
        super();
    }

    public AnalyzeBillException(String errormsg) {
        super(errormsg);

    }

    public AnalyzeBillException(String errormsg, Integer errorCode) {
        super(errormsg);
        this.errorCode = errorCode;
    }

    public AnalyzeBillException(String prompt, Throwable cause) {
        super(prompt, cause);
    }

    /**
     * MailBillException
     *
     * @param prompt
     * @param cause
     *            抛出原因
     * @param errorCode
     *            错误类型码
     */
    public AnalyzeBillException(String prompt, Throwable cause, Integer errorCode) {
        super(prompt, cause);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getPrompt() {
        return getMessage();
    }

}
