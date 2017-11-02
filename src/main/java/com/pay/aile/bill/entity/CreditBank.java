package com.pay.aile.bill.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 银行
 * </p>
 *
 * @author yaoqiang.sun
 * @since 2017-11-02
 */
@TableName("credit_bank")
public class CreditBank extends Model<CreditBank> {

    private static final long serialVersionUID = 1L;

	private Long id;
    /**
     * 银行名称
     */
	private String name;
    /**
     * 扩展主题关键字
     */
	@TableField("ext_keyword")
	private String extKeyword;
    /**
     * 是否支持网银
     */
	private Integer online;
    /**
     * 是否支持邮件
     */
	private Integer email;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExtKeyword() {
		return extKeyword;
	}

	public void setExtKeyword(String extKeyword) {
		this.extKeyword = extKeyword;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	public Integer getEmail() {
		return email;
	}

	public void setEmail(Integer email) {
		this.email = email;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "CreditBank{" +
			"id=" + id +
			", name=" + name +
			", extKeyword=" + extKeyword +
			", online=" + online +
			", email=" + email +
			"}";
	}
}
