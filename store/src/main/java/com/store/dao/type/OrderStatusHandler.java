package com.store.dao.type;

import com.store.entity.OrderStatus;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;

public class OrderStatusHandler extends EnumOrdinalTypeHandler<OrderStatus> {
    public OrderStatusHandler() {
        super(OrderStatus.class);
    }
}
