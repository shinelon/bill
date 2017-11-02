package com.pay.aile.bill.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 邮箱
 * </p>
 *
 * @author yaoqiang.sun
 * @since 2017-11-02
 */
@TableName("credit_email")
public class CreditEmail extends Model<CreditEmail> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 商户编号
     */
	private String customerNo;
    /**
     * 邮箱
     */
	private String email;
    /**
     * 密码
     */
	private String password;
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

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
		return "CreditEmail{" +
			"id=" + id +
			", customerNo=" + customerNo +
			", email=" + email +
			", password=" + password +
			", status=" + status +
			", updateDate=" + updateDate +
			", createDate=" + createDate +
			"}";
	}
}
