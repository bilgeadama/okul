package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// note 100 'den büyük olamaz
@Check(constraints = "NOTE < 101")
public class DersOgrenci {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int devamsizlik;

    private int note;

    @ManyToOne
    // foreign key 'e isim vermek için
    @JoinColumn(foreignKey = @ForeignKey(name = "OGRENCI_FK"))
    private Ogrenci ogrenci;

    @ManyToOne
    // foreign key 'e isim vermek için
    @JoinColumn(foreignKey = @ForeignKey(name = "DERS_FK"))
    private Ders ders;
}
