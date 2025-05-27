package tn.esprit.natationproject.dto;

import java.util.List;

public class CategoryDTO {
    private Integer id;
    private String name;
    private Integer eventId;
    private String eventName;
    private List<ResultDTO> results;

    public CategoryDTO() {}

    public CategoryDTO(Integer id, String name, Long eventId, String eventName) {
        this.id = id;
        this.name = name;
        this.eventId = Math.toIntExact(eventId);
        this.eventName = eventName;
    }

    // Getters et Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getEventId() { return eventId; }
    public void setEventId(Integer eventId) { this.eventId = eventId; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public List<ResultDTO> getResults() { return results; }
    public void setResults(List<ResultDTO> results) { this.results = results; }
}
