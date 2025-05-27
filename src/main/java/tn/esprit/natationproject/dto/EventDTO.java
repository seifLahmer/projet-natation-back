package tn.esprit.natationproject.dto;

import java.util.List;

public class EventDTO {
    private Integer id;
    private String name;
    private List<CategoryDTO> categories;

    public EventDTO() {}

    public EventDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters et Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<CategoryDTO> getCategories() { return categories; }
    public void setCategories(List<CategoryDTO> categories) { this.categories = categories; }
}

