package com.pay.aile.bill.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 卡分类
 * </p>
 *
 * @author yaoqiang.sun
 * @since 2017-11-02
 */
@TableName("credit_cardtype")
public class CreditCardtype extends Model<CreditCardtype> {

    private static final long serialVersionUID = 1L;

	private Long id;
    /**
     * 银行id
     */
	@TableField("bank_id")
	private Long bankId;
    /**
     * 名称
     */
	private String name;
    /**
     * 编码
     */
	private String code;
    /**
     * 有效标志1有效0无效
     */
	private Integer status;
    /**
     * 修改时间
     */
	@TableField("update_date")
	private Date updateDate;
    /**
     * 创建时间
     */
	@TableField("create_date")
	private Date createDate;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "CreditCardtype{" +
			"id=" + id +
			", bankId=" + bankId +
			", name=" + name +
			", code=" + code +
			", status=" + status +
			", updateDate=" + updateDate +
			", createDate=" + createDate +
			"}";
	}
}
