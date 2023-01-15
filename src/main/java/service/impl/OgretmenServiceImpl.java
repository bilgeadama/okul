package service.impl;

import dto.DersDto;
import dto.OgretmenDto;
import entity.Ders;
import entity.Ogretmen;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import repository.OgretmenRepository;
import service.OgretmenService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class OgretmenServiceImpl implements OgretmenService {

    private final OgretmenRepository ogretmenRepository;
    private final DersServiceImpl dersService;

    public OgretmenServiceImpl(OgretmenRepository ogretmenRepository, DersServiceImpl dersService) {
        this.ogretmenRepository = ogretmenRepository;
        this.dersService = dersService;
    }

    @Override
    public List<OgretmenDto> getAll() {
        List<OgretmenDto> ogretmenDtoList = new ArrayList<>();
        ogretmenRepository.findAll().forEach(ogretmen -> {
            OgretmenDto ogretmenDto = new OgretmenDto();
            entityToDto(ogretmen, ogretmenDto);
            ogretmenDtoList.add(ogretmenDto);
        });
        return ogretmenDtoList;
    }

    @Override
    public Optional<OgretmenDto> getById(long id) {
        Optional<OgretmenDto> ogretmenDto = Optional.of(new OgretmenDto());
        Ogretmen ogretmen = ogretmenRepository.findById(id).orElseThrow(() -> new RuntimeException("Ogretmen Bulunamadı"));
        entityToDto(ogretmen, ogretmenDto.get());
        return ogretmenDto;
    }

    @Override
    public OgretmenDto add(OgretmenDto ogretmenDto) {
        Ogretmen ogretmen = new Ogretmen();
        dtoToEntity(ogretmenDto, ogretmen);
        ogretmen = ogretmenRepository.save(ogretmen);
        entityToDto(ogretmen, ogretmenDto);
        return ogretmenDto;
    }

    @Override
    public void delete(OgretmenDto ogretmenDto) {
        Ogretmen ogretmen = new Ogretmen();
        dtoToEntity(ogretmenDto, ogretmen);
        ogretmenRepository.delete(ogretmen);
    }

    @Override
    public OgretmenDto update(OgretmenDto ogretmenDto) {
        Optional<OgretmenDto> optionalogretmen = Optional.of(getById(ogretmenDto.getId()).orElseThrow());
        optionalogretmen.get().setAd(ogretmenDto.getAd());
        Ogretmen ogretmen = new Ogretmen();
        dtoToEntity(ogretmenDto, ogretmen);
        entityToDto(ogretmenRepository.save(ogretmen), ogretmenDto);
        return ogretmenDto;
    }

    private void dtoToEntity(OgretmenDto ogretmenDto, Ogretmen ogretmen) {

        ogretmen.setId(ogretmenDto.getId());
        ogretmen.setAd(ogretmenDto.getAd());
        ogretmen.setYas(ogretmenDto.getYas());
        ogretmen.setGicik(ogretmenDto.isGicik());

        if (!CollectionUtils.isEmpty(ogretmenDto.getDersler())) {
            ogretmenDto.getDersler().forEach(dersDto -> {
                Ders ders = new Ders();
                dersService.dtoToEntity(dersDto, ders);
                if (CollectionUtils.isEmpty(ogretmen.getDersler()))
                    ogretmen.setDersler(new HashSet<>());
                ders.setOgretmen(ogretmen);
                ogretmen.getDersler().add(ders);
            });
        }
    }

    private void entityToDto(Ogretmen ogretmen, OgretmenDto ogretmenDto) {

        ogretmenDto.setId(ogretmen.getId());
        ogretmenDto.setAd(ogretmen.getAd());
        ogretmenDto.setYas(ogretmen.getYas());
        ogretmenDto.setGicik(ogretmen.isGicik());


        if (!CollectionUtils.isEmpty(ogretmen.getDersler()))
            ogretmen.getDersler().forEach(ders -> {
                DersDto dersDto = new DersDto();
                dersService.entityToDto(ders, dersDto);
                if (CollectionUtils.isEmpty(ogretmenDto.getDersler()))
                    ogretmenDto.setDersler(new HashSet<>());
                ogretmenDto.getDersler().add(dersDto);
            });
    }
}
