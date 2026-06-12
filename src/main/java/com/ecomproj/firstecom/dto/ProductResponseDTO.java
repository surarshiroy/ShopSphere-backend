package com.ecomproj.firstecom.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductResponseDTO {

    private int id;
    private String name;
    private String description;
    private String brand;
    private BigDecimal price;
    private String category;
    private Date releaseDate;
    private boolean productAvailable;
    private int stockQuantity;
}