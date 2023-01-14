package service.impl;

import dto.DersOgrenciDto;
import entity.DersOgrenci;
import org.springframework.stereotype.Service;
import repository.DersOgrenciRepository;
import service.DersOgrenciService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DersOgrenciServiceImpl implements DersOgrenciService {

    private final DersOgrenciRepository dersOgrenciRepository;

    public DersOgrenciServiceImpl(DersOgrenciRepository DersOgrenciRepository) {
        this.dersOgrenciRepository = DersOgrenciRepository;
    }

    @Override
    public List<DersOgrenciDto> getAll() {
        List<DersOgrenciDto> dersOgrenciDtoList = new ArrayList<>();
        dersOgrenciRepository.findAll().forEach(DersOgrenci -> {
            DersOgrenciDto DersOgrenciDto = new DersOgrenciDto();
            entityToDto(DersOgrenci, DersOgrenciDto);
            dersOgrenciDtoList.add(DersOgrenciDto);
        });

        return dersOgrenciDtoList;
    }

    @Override
    public Optional<DersOgrenciDto> getById(long id) {

        Optional<DersOgrenciDto> DersOgrenciDto = Optional.of(new DersOgrenciDto());
        Optional<DersOgrenci> DersOgrenci = dersOgrenciRepository.findById(id);
        entityToDto(DersOgrenci.orElseGet(DersOgrenci::new), DersOgrenciDto.get());
        return DersOgrenciDto;
    }

    @Override
    public DersOgrenciDto add(DersOgrenciDto DersOgrenciDto) {

        DersOgrenci dersOgrenci = new DersOgrenci();
        dtoToEntity(DersOgrenciDto, dersOgrenci);
        dersOgrenci = dersOgrenciRepository.save(dersOgrenci);
        entityToDto(dersOgrenci, DersOgrenciDto);
        return DersOgrenciDto;
    }

    @Override
    public void delete(DersOgrenciDto DersOgrenciDto) {

        DersOgrenci DersOgrenci = new DersOgrenci();
        dtoToEntity(DersOgrenciDto, DersOgrenci);
        dersOgrenciRepository.delete(DersOgrenci);
    }

    @Override
    public DersOgrenciDto update(DersOgrenciDto dersOgrenciDto) {

        Optional<DersOgrenciDto> optionalDersOgrenci = Optional.of(getById(dersOgrenciDto.getId()).orElseThrow());
        DersOgrenci dersOgrenci = new DersOgrenci();
        dtoToEntity(optionalDersOgrenci.get(), dersOgrenci);
        entityToDto(dersOgrenciRepository.save(dersOgrenci), dersOgrenciDto);
        return dersOgrenciDto;
    }

    protected void dtoToEntity(DersOgrenciDto dersOgrenciDto, DersOgrenci dersOgrenci) {

        dersOgrenci.setId(dersOgrenciDto.getId());
        dersOgrenci.setDevamsizlik(dersOgrenciDto.getDevamsizlik());
        dersOgrenci.setNote(dersOgrenciDto.getNote());

    }

    protected void entityToDto(DersOgrenci DersOgrenci, DersOgrenciDto DersOgrenciDto) {

        DersOgrenciDto.setId(DersOgrenci.getId());
        DersOgrenciDto.setDevamsizlik(DersOgrenci.getDevamsizlik());
        DersOgrenciDto.setNote(DersOgrenci.getNote());

    }
}
