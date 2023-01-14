package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DersDto {

    private long id;

    private String dersAdi;

    private Set<KonuDto> konular;

    private Set<DersOgrenciDto> dersOgrenciler;
}