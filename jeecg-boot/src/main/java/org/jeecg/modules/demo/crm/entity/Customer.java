package org.jeecg.modules.demo.crm.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: customer
 * @Author: jeecg-boot
 * @Date:   2019-08-30
 * @Version: V1.0
 */
@Data
@TableName("customer")
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**id*/
	@TableId(type = IdType.UUID)
	private java.lang.String id;
	/**客户编号*/
	@Excel(name = "客户编号", width = 15)
	private java.lang.String customerNo;
	/**客户名称*/
	@Excel(name = "客户名称", width = 15)
	private java.lang.String name;
	/**类型*/
	@Excel(name = "类型", width = 15)
	private java.lang.String type1Id;
	/**类型2*/
	@Excel(name = "类型2", width = 15)
	private java.lang.String type2Id;
	/**类型3*/
	@Excel(name = "类型3", width = 15)
	private java.lang.String type3Id;
	/**状态*/
	@Excel(name = "状态", width = 15)
	private java.lang.String statusId;
	/**来源*/
	@Excel(name = "来源", width = 15)
	private java.lang.String sourceId;
	/**最后联系时间*/
	@Excel(name = "最后联系时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date lastDate;
	/**下次联系时间*/
	@Excel(name = "下次联系时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date nextDate;
	/**备注*/
	@Excel(name = "备注", width = 15)
	private java.lang.String remarks;
	/**负责人*/
	@Excel(name = "负责人", width = 15)
	private java.lang.String managerId;
	/**负责人所在部门*/
	@Excel(name = "负责人所在部门", width = 15)
	private java.lang.String managerOrgId;
	/**createBy*/
	@Excel(name = "createBy", width = 15)
	private java.lang.String createBy;
	/**createTime*/
	@Excel(name = "createTime", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date createTime;
	/**updateBy*/
	@Excel(name = "updateBy", width = 15)
	private java.lang.String updateBy;
	/**updateTime*/
	@Excel(name = "updateTime", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date updateTime;
	/**delFlag*/
	@Excel(name = "delFlag", width = 15)
	private java.lang.String delFlag;
}
