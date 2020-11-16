package com.yimian.service.impl;

import com.github.pagehelper.PageHelper;
import com.yimian.dao.IOrdersDao;
import com.yimian.domain.Orders;
import com.yimian.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrdersServiceImpl implements IOrderService {
    @Autowired
    private IOrdersDao  ordersDao;
    @Override
    public List<Orders> findAll(int page,int size) throws Exception {
        PageHelper.startPage(page,size);
        return ordersDao.findAll();
    }

    @Override
    public Orders findById(String ordersId) throws Exception {
        return ordersDao.findById(ordersId);
    }
}
