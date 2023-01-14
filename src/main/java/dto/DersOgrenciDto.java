package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// note 100 'den büyük olamaz
@Check(constraints = "NOTE < 101")
public class DersOgrenciDto {

    private long id;

    private int devamsizlik;

    private int note;

}
