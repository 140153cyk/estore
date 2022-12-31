package com.store.dao.type;

import com.store.entity.ProductLabel;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;

public class ProductLabelHandler extends EnumOrdinalTypeHandler<ProductLabel> {
    public ProductLabelHandler() {
        super(ProductLabel.class);
    }
}
