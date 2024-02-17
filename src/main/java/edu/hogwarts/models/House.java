package edu.hogwarts.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String founder;
    @ElementCollection
    private List<String> colors;

    public House(String name, String founder, List<String> colors) {
        this.name = name;
        this.founder = founder;
        this.colors = colors;
    }

    public House() {

    }

    public int getId() {
        return id;
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

    public List<String> getColors() {
        return colors;
    }
}
