package com.store.service;

import com.store.dao.OrderMapper;
import com.store.dao.ProductMapper;
import com.store.entity.Product;
import com.store.entity.ProductLabel;
import com.store.util.IdentityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private ProductMapper mapper;
    private OrderMapper orders;
    private static int pageSize = 8;

    public ProductService(@Autowired ProductMapper mapper,@Autowired OrderMapper orders){
        this.mapper=mapper;
        this.orders = orders;
    }

    public List<String> getProductLabels(){
        List<String> list = new ArrayList<>();
        list.add("All");
        for (ProductLabel value : ProductLabel.values()) {
            list.add(value.name());
        }
        return list;
    }

    public Product getProductById(String id){
        return mapper.getProductById(id);
    }

    public List<Product> getProductsByStore(String storeId){
        return mapper.getProductsByStoreId(storeId);
    }

    public List<Product> searchProduct(String segment,int page){
        return mapper.searchProduct(segment,(page-1)*pageSize,pageSize);
    }

    public List<Product> getProductsByLabelAndPage(String label,int page) {
        ProductLabel l = ProductLabel.valueOf(label);
        return mapper.getProductByLabelAndLimit(l,(page-1)*pageSize,pageSize);
    }

    public List<Product> getProductsByPage(int page){
        return mapper.getProductByLimit((page-1)*pageSize,pageSize);
    }

    public int countByLabel(String label){
        ProductLabel l = ProductLabel.valueOf(label);
        return (int) Math.ceil(mapper.countByLabel(l)*1.0/pageSize);
    }

    public int countBySegment(String segment){
        return (int) Math.ceil(mapper.countBySegment(segment)*1.0/pageSize);
    }

    public int countAll(){
        return (int) Math.ceil(mapper.countAll()*1.0/pageSize);
    }

    public int addProduct(Product product,String store){
        try{
            product.setId(IdentityUtil.generateSequenceNo());
            mapper.addProduct(product,store);
            List<ProductLabel> labels = product.getLabels();
            String id = product.getId();
            for (ProductLabel label : labels) {
                mapper.addLabels(IdentityUtil.generateSequenceNo(),id,label);
            }
            return 0;
        }catch (Exception e){
            return -1;
        }
    }

    public int deleteProduct(String id){
        try{
            mapper.deleteLabelsByProduct(id);
            mapper.deleteById(id);
            orders.removeOrderByProduct(id);
            return 0;
        }catch (Exception e){
            return -1;
        }
    }

    public int updateProduct(Product product){
        try{
            mapper.updateProduct(product);
            return 0;
        }catch (Exception e){
            return -1;
        }
    }

}
