package com.ecomproj.firstecom.service;
import com.ecomproj.firstecom.dto.ProductResponseDTO;
import com.ecomproj.firstecom.exception.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.ecomproj.firstecom.model.Product;
import com.ecomproj.firstecom.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepo prod;

    public Page<Product> getAllproducts(Pageable pageable){
        return prod.findAll(pageable);
    }

    public Product getProductById(int id){

        return prod.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product not found with id : " + id
                        ));
    }

    public ProductResponseDTO getProductDTOById(int id){

        Product product = getProductById(id);

        ProductResponseDTO dto = new ProductResponseDTO();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setBrand(product.getBrand());
        dto.setPrice(product.getPrice());
        dto.setReleaseDate(product.getReleaseDate());
        dto.setCategory(product.getCategory());
        dto.setProductAvailable(product.isProductAvailable());
        dto.setStockQuantity(product.getStockQuantity());

        return dto;
    }
    public Product save(Product product) {
        return prod.save(product);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException{
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return prod.save(product);
    }

    public Product updateProduct(
            int id,
            Product product,
            MultipartFile imageFile)
            throws IOException {

        Product existingProduct =
                prod.findById(id)
                        .orElseThrow(() ->
                                new ProductNotFoundException(
                                        "Product not found"
                                ));

        product.setId(id);

        product.setImageData(
                imageFile.getBytes()
        );

        product.setImageType(
                imageFile.getContentType()
        );

        product.setImageName(
                imageFile.getOriginalFilename()
        );
        System.out.println(
                "NEW STOCK = "
                        + product.getStockQuantity()
        );
        if(product.getStockQuantity() > 0) {
            product.setProductAvailable(product.getStockQuantity() > 0);
        }

        return prod.save(product);
    }

    public void deleteProduct(int id) {
        prod.deleteById(id);
    }

    public List<Product> searchProducts(String keyword) {
        return prod.searchProducts(keyword);
    }
    private ProductResponseDTO mapToDTO(Product product){

        ProductResponseDTO dto =
                new ProductResponseDTO();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setBrand(product.getBrand());
        dto.setPrice(product.getPrice());
        dto.setCategory(product.getCategory());
        dto.setProductAvailable(
                product.isProductAvailable()
        );
        dto.setStockQuantity(
                product.getStockQuantity()
        );

        return dto;
    }
}
