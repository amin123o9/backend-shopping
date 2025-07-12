package com.example.ecom_prj.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private  String brand;
    private BigDecimal price;
    private String category;

    private Date releasedDate;
    private boolean available;
    private int quantity;
    private String Imagename;
    private String Imagetype;
    @Lob
    private byte[] Imagedata;

}
