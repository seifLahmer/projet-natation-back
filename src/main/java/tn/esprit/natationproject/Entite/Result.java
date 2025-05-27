package tn.esprit.natationproject.Entite;

import jakarta.persistence.*;

@Entity
@Table(name = "result")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "place", length = 50)
    private String place;

    @Column(name = "name_prenom", length = 255)
    private String namePrenom;

    @Column(name = "nation", length = 100)
    private String nation;

    @Column(name = "club", length = 255)
    private String club;

    @Column(name = "temps", length = 50)
    private String temps;

    @Column(name = "points", length = 50)
    private String points;

    @Column(name = "temps_passage", length = 50)
    private String tempsPassage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Constructeurs
    public Result() {}

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

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}
