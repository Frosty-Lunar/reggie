package com.lengyue.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lengyue.commons.Result;
import com.lengyue.entity.Orders;
import com.lengyue.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 订单
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 用户下单
     *
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public Result<String> submit(@RequestBody Orders orders) {
        log.info("订单数据：{}", orders);
        orderService.submit(orders);
        return Result.success("下单成功");
    }

    @GetMapping("/userPage")
    public Result<Page> listPage(@Param("page") int page, @Param("pageSize") int pageSize, @Param("number") Long number, @Param("beginTime") String beginTime, @Param("endTime") String endTime) {
        log.info("当前页：{}，页面大小：{}", page, pageSize);
        log.info("订单号：{}", number);
        log.info("开始时间：{}，结束时间：{}", beginTime, endTime);
        Page<Orders> orders = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> ordersLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ordersLambdaQueryWrapper.eq(number != null, Orders::getId, number);
        ordersLambdaQueryWrapper.between(beginTime != null && endTime != null, Orders::getOrderTime, beginTime, endTime);
        Page<Orders> ordersPage = orderService.page(orders, ordersLambdaQueryWrapper);
        List<Orders> records = ordersPage.getRecords();
        records.forEach(record -> log.info(record.toString()));
        return Result.success(ordersPage);
    }

    @PutMapping
    public Result<String> update(@RequestBody Orders orders) {
        log.info(orders.toString());
        orderService.updateById(orders);
        return Result.success("派送成功！");
    }
}