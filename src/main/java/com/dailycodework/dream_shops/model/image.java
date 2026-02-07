package com.dailycodework.dream_shops.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Getter //Easier method to make getters
@Setter //Easier method to make setters
@AllArgsConstructor //automatically generates a constructor with one parameter for every field in a class
@NoArgsConstructor // Automatically generates a public no-arg constructor
@Entity

public class image {
    @Id //set Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;

    @Lob //Specifies that a persistent field should be stored as a Large Object (LOB) in the database
    private Blob image; //Blob - a data type used for storing large amounts of binary data, such as images, audio files or documents
    private String downloadURL;

    @ManyToOne // establish relationships with other tables
    // Many images to one product
    @JoinColumn(name="product_id") //Name of column where they are gonna be joined
    private Product product;
}
