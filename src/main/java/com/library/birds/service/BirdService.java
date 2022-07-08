package com.library.birds.service;

import com.library.birds.model.Birds;
import com.library.birds.repository.BirdsRepository;
import com.library.birds.response.Response;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class BirdService {


    @Autowired
    private BirdsRepository birdsRepository;


    public Response saveBird(MultipartFile file, String commonName, String scientificName, String spottedDate, String spottedLocation, String status, String family) throws IOException {

        Response<String, Birds> generics= new Response<String, Birds>();

        Birds birdExists =birdsRepository.findByCommonName(commonName);

        if(birdExists != null ){
            generics.setMessage("Couldnt Save. Bird already exists with common name "+commonName);
            return generics;
        }
        else{
            Birds birds = new Birds();
            birds.setCommonName(commonName);
            birds.setScientificName(scientificName);
            birds.setSpottedLocation(spottedLocation);
            birds.setStatus(status);
            birds.setFamily(family);

            Date spotDate = new Date(spottedDate);
            birds.setSpottedDate(spotDate.toString());


            if(file == null) {
                byte[] data = new byte[] {};
                birds.setImage(new Binary(BsonBinarySubType.BINARY, data));
            }
            else {
                birds.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
            }

            birdsRepository.save(birds);
            generics.setObject(birds);
            generics.setMessage("Bird saved Successfully");
            return generics;
        }


    }

    public Response getBirds() {

        Response<String, List<Birds>> generics= new Response<String, List<Birds>>();
        List<Birds> birds = birdsRepository.findAll();

        generics.setMessage("Displaying All Birds");
        generics.setObject(birds);

        return generics;
    }

    public Response updateBird(Birds existingBird,MultipartFile file, String commonName, String scientificName, String spottedDate,
                               String spottedLocation, String status, String family) throws IOException{


        existingBird.setCommonName(commonName);
        existingBird.setScientificName(scientificName);
        existingBird.setSpottedLocation(spottedLocation);
        existingBird.setStatus(status);
        existingBird.setFamily(family);

        Date spotDate = new Date(spottedDate);
        existingBird.setSpottedDate(spotDate.toString());

        if(file == null) {
            byte[] existingImage = existingBird.getImage().getData();
            existingBird.setImage(new Binary(BsonBinarySubType.BINARY, existingImage));
        }
        else {
            existingBird.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        }
        birdsRepository.save(existingBird);

        Response<String, Birds> generics= new Response<String, Birds>();
        generics.setObject(existingBird);
        generics.setMessage("Bird updated successfully");
        return generics;
    }

    public Response deleteBird(String id) {
        birdsRepository.deleteById(id);

        Response<String, Birds> generics= new Response<String, Birds>();
        generics.setMessage("Bird Deleted successfully");
        return generics;

    }
}
