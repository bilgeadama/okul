package service.impl;

import dto.DersDto;
import dto.KonuDto;
import entity.Ders;
import entity.DersOgrenci;
import entity.Konu;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import repository.DersRepository;
import service.DersService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class DersServiceImpl implements DersService {

    private final DersRepository dersRepository;
    private final KonuServiceImpl konuService;
    private final DersOgrenciServiceImpl dersOgrenciService;

    public DersServiceImpl(DersRepository dersRepository, KonuServiceImpl konuService, DersOgrenciServiceImpl dersOgrenciService) {
        this.dersRepository = dersRepository;
        this.konuService = konuService;
        this.dersOgrenciService = dersOgrenciService;
    }

    @Override
    public List<DersDto> getAll() {

        List<DersDto> dersDtoList = new ArrayList<>();
        dersRepository.findAll().forEach(ders -> {
            DersDto dersDto = new DersDto();
            entityToDto(ders, dersDto);
            dersDtoList.add(dersDto);
        });

        return dersDtoList;
    }

    @Override
    public Optional<DersDto> getById(long id) {

        Optional<DersDto> dersDto = Optional.of(new DersDto());
        Optional<Ders> ders = dersRepository.findById(id);
        entityToDto(ders.orElseGet(Ders::new), dersDto.get());
        return dersDto;
    }

    @Override
    public DersDto add(DersDto dersDto) {

        Ders ders = new Ders();
        dtoToEntity(dersDto, ders);
        dersRepository.save(ders);
        entityToDto(ders, dersDto);
        return dersDto;
    }

    @Override
    public void delete(DersDto dersDto) {

        Ders ders = new Ders();
        dtoToEntity(dersDto, ders);
        dersRepository.delete(ders);
    }

    @Override
    public DersDto update(DersDto dersDto) {

        Optional<DersDto> optionalDers = Optional.of(getById(dersDto.getId()).orElseThrow());
        Ders ders = new Ders();
        dtoToEntity(optionalDers.get(), ders);
        entityToDto(dersRepository.save(ders), dersDto);
        return dersDto;
    }

    protected void dtoToEntity(DersDto dersDto, Ders ders) {

        ders.setId(dersDto.getId());
        ders.setDersAdi(dersDto.getDersAdi());

        if (!CollectionUtils.isEmpty(dersDto.getKonular())) {
            dersDto.getKonular().forEach(konuDto -> {
                Konu konu = new Konu();
                konuService.dtoToEntity(konuDto, konu);

//                if (CollectionUtils.isEmpty(ders.getKonular()))
//                    ders.setKonular(new HashSet<>());
                konu.setDers(ders);
                if (CollectionUtils.isEmpty(ders.getKonular()))
                    ders.setKonular(new HashSet<>());
                ders.getKonular().add(konu);
            });
        }

        if (!CollectionUtils.isEmpty(dersDto.getDersOgrenciler())) {
            dersDto.getDersOgrenciler().forEach(dersOgrenciDto -> {
                DersOgrenci dersOgrenci = new DersOgrenci();
                dersOgrenciService.dtoToEntity(dersOgrenciDto, dersOgrenci);

//                if (CollectionUtils.isEmpty(ders.getKonular()))
//                    ders.setKonular(new HashSet<>());
                dersOgrenci.setDers(ders);
                if (CollectionUtils.isEmpty(ders.getDersOgrenciler()))
                    ders.setDersOgrenciler(new HashSet<>());
                ders.getDersOgrenciler().add(dersOgrenci);
            });
        }
    }

    protected void entityToDto(Ders ders, DersDto dersDto) {

        dersDto.setId(ders.getId());
        dersDto.setDersAdi(ders.getDersAdi());

        if (!CollectionUtils.isEmpty(ders.getKonular())) {
            ders.getKonular().forEach(konu -> {
                KonuDto konuDto = new KonuDto();
                konuService.entityToDto(konu, konuDto);
                if (CollectionUtils.isEmpty(dersDto.getKonular()))
                    dersDto.setKonular(new HashSet<>());
                dersDto.getKonular().add(konuDto);
            });
        }
    }
}
