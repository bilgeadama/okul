package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ogretmen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50,nullable = false)
    private String ad;

    @Column(nullable = false)
    private int yas;

    private boolean gicik;

    @OneToMany(mappedBy = "ogretmen",cascade = {CascadeType.REMOVE,CascadeType.MERGE,CascadeType.PERSIST})
    private Set<Ders> dersler;

}