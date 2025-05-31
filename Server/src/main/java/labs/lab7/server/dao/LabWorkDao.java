package labs.lab7.server.dao;

import jakarta.persistence.*;
import labs.lab7.common.models.Difficulty;

import java.time.ZonedDateTime;

@Entity
@Table(name = "lab_works")
public class LabWorkDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Column(name = "coord_x")
    private Integer coordX;

    @Column(name = "coord_y")
    private int coordY;

    @Column(name = "creation_date", columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private ZonedDateTime creationDate;

    @Column(name = "minimal_point")
    private Double minimalPoint;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Column(name = "discipline_name")
    private String disciplineName;

    @Column(name = "discipline_lecture_hours")
    private Long disciplineLectureHours;

    @Column(name = "discipline_practice_hours")
    private Long disciplinePracticeHours;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    // === Геттеры и сеттеры ===

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCoordX() {
        return coordX;
    }

    public void setCoordX(Integer coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Double getMinimalPoint() {
        return minimalPoint;
    }

    public void setMinimalPoint(Double minimalPoint) {
        this.minimalPoint = minimalPoint;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getDisciplineName() {
        return disciplineName;
    }

    public void setDisciplineName(String disciplineName) {
        this.disciplineName = disciplineName;
    }

    public Long getDisciplineLectureHours() {
        return disciplineLectureHours;
    }

    public void setDisciplineLectureHours(Long disciplineLectureHours) {
        this.disciplineLectureHours = disciplineLectureHours;
    }

    public Long getDisciplinePracticeHours() {
        return disciplinePracticeHours;
    }

    public void setDisciplinePracticeHours(Long disciplinePracticeHours) {
        this.disciplinePracticeHours = disciplinePracticeHours;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Object getAveragePoint() {
        throw new UnsupportedOperationException("Unimplemented method 'getAveragePoint'");
    }
}
