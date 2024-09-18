package com.reactiveweb.itglobers.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "product") // Specifies the MongoDB collection name
public class Product {
    
    @Id
    private String productId; // MongoDB uses String type for IDs

    private String name;
    private Integer status; // 0: Inactive, 1: Active
    private Integer stock;
    private String description;
    private Double price;

    // Additional transient fields (not stored in MongoDB)
    private transient String statusName; // Status name (retrieved from cache)
    private transient Double discount;   // Discount (retrieved from external service)
    private transient Double finalPrice; // Final calculated price
}
