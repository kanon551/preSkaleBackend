package com.library.birds.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Data
@Component
@Document(collection = "birdsLibrary")
public class Birds {

    @Id
    @Schema(hidden = true)
    private String _id;

    private String commonName;
    private String scientificName;
    private String spottedDate;
    private String spottedLocation;
    private String status;
    private String family;

    private Binary image;





}
