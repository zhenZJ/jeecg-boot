package org.jeecg.modules.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.crm.entity.Customer;
import org.jeecg.modules.crm.mapper.CustomerMapper;
import org.jeecg.modules.crm.service.ICustomerService;
import org.springframework.stereotype.Service;

/**
 * @Description: customer
 * @Author: jeecg-boot
 * @Date:   2019-08-29
 * @Version: V1.0
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

}
