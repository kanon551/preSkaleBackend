package com.library.birds.controller;

import com.library.birds.model.Birds;
import com.library.birds.repository.BirdsRepository;
import com.library.birds.response.Response;
import com.library.birds.service.BirdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@CrossOrigin
@RequestMapping("/api/preSkale")
@RestController
public class BirdController {

    @Autowired
    private BirdService birdService;

    @Autowired
    private BirdsRepository birdsRepository;

    private static final String AUTH_MECHANISM = "bearerAuth";

    @PostMapping("/saveBird")
    @Operation(security = @SecurityRequirement(name = AUTH_MECHANISM),summary = "Insert Bird", description = "Add Bird", tags = { "Bird Library" })
    public Response createBird(@Valid @RequestBody MultipartFile file, @RequestParam String commonName,
                               @RequestParam String scientificName,@RequestParam String spottedDate,
                               @RequestParam String spottedLocation,@RequestParam String status,
                               @RequestParam String family) throws IOException {

        return birdService.saveBird(file,commonName,scientificName,spottedDate,spottedLocation,status,family);
    }

    @GetMapping("/getBirds")
    @Operation(security = @SecurityRequirement(name = AUTH_MECHANISM),summary = "Get Birds", description = "Fetch Members", tags = { "Bird Library" })
    public Response fetchBirds(){
        return birdService.getBirds();
    }

    @PutMapping("/updateBird")
    @Operation(security = @SecurityRequirement(name = AUTH_MECHANISM),summary = "Update Bird", description = "Edit Bird", tags = { "Bird Library" })
    public Response editBird(@Valid @RequestBody MultipartFile file, @RequestParam String commonName,
                               @RequestParam String scientificName,@RequestParam String spottedDate,
                               @RequestParam String spottedLocation,@RequestParam String status,
                               @RequestParam String family,  @RequestParam String id) throws IOException {

        Birds existingBird = birdsRepository.findById(id).orElse(null);
        return birdService.updateBird(existingBird,file,commonName,scientificName,spottedDate,spottedLocation,status,family);
    }


    @DeleteMapping("/deleteBird/{id}")
    @Operation(security = @SecurityRequirement(name = AUTH_MECHANISM),summary = "Delete Bird", description = "Delete Bird By ID", tags = { "Bird Library" })
    public Response removeProduct(@PathVariable String id ) {
        return birdService.deleteBird(id);
    }
}
