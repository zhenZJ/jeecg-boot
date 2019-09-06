package org.jeecg.modules.demo.crm.service.impl;

import org.jeecg.modules.demo.crm.entity.Customer;
import org.jeecg.modules.demo.crm.mapper.CustomerMapper;
import org.jeecg.modules.demo.crm.service.ICustomerService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: customer
 * @Author: jeecg-boot
 * @Date:   2019-08-30
 * @Version: V1.0
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

}
