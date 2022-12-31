package com.store.controller;

import com.store.entity.Product;
import com.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService service;
    public ProductController(@Autowired ProductService service){
        this.service=service;
    }

    @RequestMapping(path = "/{id}",method = RequestMethod.GET)
    public Product getProduct(@PathVariable String id){
        return service.getProductById(id);
    }
    @RequestMapping(path = "/getLabels",method = RequestMethod.GET)
    public List<String> getLabels(){
        return service.getProductLabels();
    }

    @RequestMapping(path = "/selectByStore/{id}",method = RequestMethod.GET)
    public List<Product> selectByStore(@PathVariable("id") String id){
        return service.getProductsByStore(id);
    }

    @RequestMapping(path = "/search",method = RequestMethod.GET)
    public List<Product> search(String segment,int page){
        return service.searchProduct( segment, page);
    }

    @RequestMapping(path = "/selectByLabelAndPage",method = RequestMethod.GET)
    public List<Product> selectByLabelAndPage(String label,int page){
        if(label.equals("All"))return service.getProductsByPage(page);
        return service.getProductsByLabelAndPage(label,page);
    }

    @RequestMapping(path = "/totalPage",method = RequestMethod.GET)
    public int getTotalPage(String label,String segment){
        if(label!=null&&!label.equals("All")){
            return service.countByLabel(label);
        }
        if(segment!=null)return service.countBySegment(segment);
        return service.countAll();

    }

    @RequestMapping(path = "/addProduct",method = RequestMethod.POST)
    public int addProduct(@RequestBody Product product,String store){
        return service.addProduct(product,store);
    }

    @RequestMapping(path = "/deleteProduct/{id}",method = RequestMethod.DELETE)
    public int deleteProduct(@PathVariable String id){
        return service.deleteProduct(id);
    }

    @RequestMapping(path = "/updateProduct",method = RequestMethod.PUT)
    public int updateProduct(@RequestBody Product product){
        return service.updateProduct(product);
    }
}
