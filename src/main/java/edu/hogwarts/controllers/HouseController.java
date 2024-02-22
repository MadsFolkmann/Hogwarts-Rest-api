package edu.hogwarts.controllers;

import edu.hogwarts.models.House;
import edu.hogwarts.repositories.HouseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/houses")
public class HouseController {

    HouseRepository houseRepository;

    public HouseController(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @GetMapping()
    public List<House> getHouses() {
        return houseRepository.findAll();
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getHouse(@PathVariable String name) {
        List<House> house = houseRepository.findByName(name);
        if (house != null) {
            return new ResponseEntity<>(house, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("House not found", HttpStatus.NOT_FOUND);
        }
    }
}
