package com.pay.aile.bill.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 帐户类型
 * </p>
 *
 * @author yaoqiang.sun
 * @since 2017-11-02
 */
@TableName("credit_account_type")
public class CreditAccountType extends Model<CreditAccountType> {

    private static final long serialVersionUID = 1L;

	private Long id;
    /**
     * 名称
     */
	private String name;
    /**
     * 标识符
     */
	private String Identifier;


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

	public String getIdentifier() {
		return Identifier;
	}

	public void setIdentifier(String Identifier) {
		this.Identifier = Identifier;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "CreditAccountType{" +
			"id=" + id +
			", name=" + name +
			", Identifier=" + Identifier +
			"}";
	}
}
