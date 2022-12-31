package com.store.dao;

import com.store.entity.Product;
import com.store.entity.ProductLabel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper {
    public Product getProductById(@Param("id") String id);
    public List<Product> getProductsByStoreId(@Param("id") String id);
    public List<ProductLabel> getProductLabels(@Param("id") String id);
    public List<Product> getProductByLabelAndLimit(@Param("label") ProductLabel label,@Param("skip") int skip,@Param("take") int take);
    public List<Product> getProductByLimit(@Param("skip") int skip,@Param("take") int take);
    public List<Product> searchProduct(@Param("segment")String segment,@Param("skip") int skip,@Param("take") int take);
    public int getQuantityById(@Param("id")String id);
    public int countByLabel(@Param("label") ProductLabel label);
    public int countBySegment(@Param("segment") String segment);
    public int countAll();

    public void addProduct(@Param("product") Product product,@Param("store") String store);
    public void addLabels(@Param("id")String id,@Param("productId") String productId,@Param("label")ProductLabel label);

    public void deleteById(@Param("id")String id);
    public void deleteLabelsByProduct(@Param("id")String id);

    public void updateProduct(@Param("product") Product product);
}
