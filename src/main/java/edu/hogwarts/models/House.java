package edu.hogwarts.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class House {
    @Id
    private String name;
    private String founder;
    @JsonIgnore
    private String color1;
    @JsonIgnore
    private String color2;


    public House(String name, String founder, String color1, String color2) {
        this.name = name;
        this.founder = founder;
        this.color1 = color1;
        this.color2 = color2;

    }

    public House() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public String[] getColors() {
        return new String[]{color1, color2};
    }

    public void setColors(String[] colors) {
        this.color1 = colors[0];
        this.color2 = colors[1];
    }
}
