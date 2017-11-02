package com.pay.aile.bill.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 邮箱文件
 * </p>
 *
 * @author yaoqiang.sun
 * @since 2017-11-02
 */
@TableName("credit_file")
public class CreditFile extends Model<CreditFile> {

    private static final long serialVersionUID = 1L;

	private Long id;
    /**
     * 邮箱对应的内容文件名称
     */
	@TableField("filen_name")
	private String filenName;
    /**
     * 创建时间
     */
	@TableField("create_date")
	private Long createDate;
    /**
     * 邮件收到时间
     */
	@TableField("receive_date")
	private Long receiveDate;
    /**
     * 邮箱id
     */
	@TableField("email_id")
	private String emailId;
    /**
     * 0 未处理 1 成功 2 失败
     */
	@TableField("process_result")
	private Integer processResult;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFilenName() {
		return filenName;
	}

	public void setFilenName(String filenName) {
		this.filenName = filenName;
	}

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public Long getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Long receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Integer getProcessResult() {
		return processResult;
	}

	public void setProcessResult(Integer processResult) {
		this.processResult = processResult;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "CreditFile{" +
			"id=" + id +
			", filenName=" + filenName +
			", createDate=" + createDate +
			", receiveDate=" + receiveDate +
			", emailId=" + emailId +
			", processResult=" + processResult +
			"}";
	}
}
