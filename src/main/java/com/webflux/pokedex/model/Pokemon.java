package com.webflux.pokedex.model;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Pokemon {

    @Id
    private String id;
    private String name;
    private String category;
    private String hability;
    private BigDecimal weight;

    public Pokemon() {
        super();
    }

    public Pokemon(String id, String name, String category, String hability, BigDecimal weight) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.hability = hability;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHability() {
        return hability;
    }

    public void setHability(String hability) {
        this.hability = hability;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, hability, weight);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Pokemon))
            return false;

        Pokemon other = (Pokemon) obj;
        return Objects.equals(id, other.getId()) &&
                Objects.equals(name, other.getName()) &&
                Objects.equals(category, other.getCategory()) &&
                Objects.equals(hability, other.getHability()) &&
                Objects.equals(weight, other.getWeight());
    }

    @Override
    public String toString() {
        return "Pokemon [id=" + id + ", name=" + name + ", category=" + category + ", hability=" + hability
                + ", weight=" + weight + "]";
    }

}