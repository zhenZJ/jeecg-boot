package org.jeecg.modules.crm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: customer
 * @Author: jeecg-boot
 * @Date: 2019-08-30
 * @Version: V1.0
 */
@Data
@TableName("customer")
public class Customer implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * id
   */
  @TableId(type = IdType.UUID)
  private String id;
  /**
   * 客户编号
   */
  @Excel(name = "客户编号", width = 15)
  private String customerNo;
  /**
   * 客户名称
   */
  @Excel(name = "客户名称", width = 15)
  private String name;
  /**
   * 类型
   */
  @Excel(name = "类型", width = 15)
	@Dict(dicCode = "customer_type")
  private String type1Id;
  /**
   * 类型2
   */
  @Excel(name = "类型2", width = 15)
  private String type2Id;
  /**
   * 类型3
   */
  @Excel(name = "类型3", width = 15)
  private String type3Id;
  /**
   * 状态
   */
  @Excel(name = "状态", width = 15)
  private String statusId;
  /**
   * 来源
   */
  @Excel(name = "来源", width = 15)
	@Dict(dicCode = "customer_source")
  private String sourceId;
  /**
   * 最后联系时间
   */
  @Excel(name = "最后联系时间", width = 15, format = "yyyy-MM-dd")
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date lastDate;
  /**
   * 下次联系时间
   */
  @Excel(name = "下次联系时间", width = 15, format = "yyyy-MM-dd")
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date nextDate;
  /**
   * 备注
   */
  @Excel(name = "备注", width = 15)
  private String remarks;
  /**
   * 负责人
   */
  @Excel(name = "负责人", width = 15)
	@Dict(dicCode = "id",dictTable = "sys_user",dicText = "realname")
  private String managerId;
  /**
   * 负责人所在部门
   */
  @Excel(name = "负责人所在部门", width = 15)
  private String managerOrgId;
  /**
   * createBy
   */
  @Excel(name = "createBy", width = 15)
  private String createBy;
  /**
   * createTime
   */
  @Excel(name = "createTime", width = 15, format = "yyyy-MM-dd")
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date createTime;
  /**
   * updateBy
   */
  @Excel(name = "updateBy", width = 15)
  private String updateBy;
  /**
   * updateTime
   */
  @Excel(name = "updateTime", width = 15, format = "yyyy-MM-dd")
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date updateTime;
  /**
   * delFlag
   */
  @Excel(name = "delFlag", width = 15)
  private String delFlag;
}
