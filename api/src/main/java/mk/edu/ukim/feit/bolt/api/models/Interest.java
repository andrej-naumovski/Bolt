package mk.edu.ukim.feit.bolt.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by gjorgjim on 8/7/17.
 */
@Entity
@Table(name = "interest")
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private Interest parentInterest;

    @OneToMany(mappedBy = "parentInterest", cascade = CascadeType.ALL)
    private List<Interest> childInterests;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public Interest getParentInterest() {
        return parentInterest;
    }

    @JsonIgnore
    public void setParentInterest(Interest parentInterest) {
        this.parentInterest = parentInterest;
    }

    public List<Interest> getChildInterests() {
        return childInterests;
    }

    public void setChildInterests(List<Interest> childInterests) {
        this.childInterests = childInterests;
    }
}
