package dto;

import lombok.*;

import java.util.Set;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OgretmenDto {

    private long id;

    private String ad;

    private int yas;

    private boolean gicik;

    private Set<DersDto> dersler;

}