package tn.esprit.natationproject.dto;

public class ResultDTO {
    private Integer id;
    private String place;
    private String namePrenom;
    private String nation;
    private String club;
    private String temps;
    private String points;
    private String tempsPassage;
    private Integer categoryId;
    private String categoryName;
    private Integer eventId;
    private String eventName;

    public ResultDTO() {}

    // Getters et Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getPlace() { return place; }
    public void setPlace(String place) { this.place = place; }

    public String getNamePrenom() { return namePrenom; }
    public void setNamePrenom(String namePrenom) { this.namePrenom = namePrenom; }

    public String getNation() { return nation; }
    public void setNation(String nation) { this.nation = nation; }

    public String getClub() { return club; }
    public void setClub(String club) { this.club = club; }

    public String getTemps() { return temps; }
    public void setTemps(String temps) { this.temps = temps; }

    public String getPoints() { return points; }
    public void setPoints(String points) { this.points = points; }

    public String getTempsPassage() { return tempsPassage; }
    public void setTempsPassage(String tempsPassage) { this.tempsPassage = tempsPassage; }

    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public Integer getEventId() { return eventId; }
    public void setEventId(Integer eventId) { this.eventId = eventId; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }
}
