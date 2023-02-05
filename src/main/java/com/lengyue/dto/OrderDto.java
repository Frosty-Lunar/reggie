package com.lengyue.dto;

import com.lengyue.entity.OrderDetail;
import com.lengyue.entity.Orders;
import lombok.Data;

import java.util.List;
@Data
public class OrderDto extends Orders {
    private List<OrderDetail> orderDetails;
}
