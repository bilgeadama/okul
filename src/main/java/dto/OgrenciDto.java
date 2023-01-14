package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OgrenciDto {

    private long id;

    private String ad;

    private long numara;

    private long yas;

    private Set<DersOgrenciDto> dersOgrenciler;

}
