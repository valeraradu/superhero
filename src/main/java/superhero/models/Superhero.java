package superhero.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "superhero")
public class Superhero {

    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    Collection<String> skills;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    @NotNull
    @Size(min = 2, max = 80)
    private String name;
    @Column(unique = true)
    @NotNull
    @Size(min = 3, max = 80)
    private String pseudonym;
    @Size(min = 3, max = 80)
    private String publisher;
    @ElementCollection
    private List<String> allies;

    @NotNull
    @Column(name = "first_appearance", columnDefinition = "DATETIME")
    @Temporal(TemporalType.DATE)
    private Date firstAppearance;

    public Superhero() {

    }

    public Superhero(String name, String pseudonym, String publisher, Collection<String> skills, List<String> allies, Date firstAppearance) {
        this.name = name;
        this.pseudonym = pseudonym;
        this.publisher = publisher;
        this.skills = skills;
        this.allies = allies;
        this.firstAppearance = firstAppearance;
    }

    public long getId() {
        return id;

    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPseudonym() {
        return pseudonym;
    }

    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<String> getSkills() {
        return skills;
    }

    public void setSkills(Collection<String> skills) {
        this.skills = skills;
    }

    public List<String> getAllies() {
        return allies;
    }

    public void setAllies(List<String> allies) {
        this.allies = allies;
    }

    public Date getFirstAppearance() {
        return firstAppearance;
    }

    public void setFirstAppearance(Date firstAppearance) {
        this.firstAppearance = firstAppearance;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
