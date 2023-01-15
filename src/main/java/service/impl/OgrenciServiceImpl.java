package service.impl;

import dto.DersOgrenciDto;
import dto.OgrenciDto;
import entity.DersOgrenci;
import entity.Ogrenci;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import repository.OgrenciRepository;
import service.OgrenciService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OgrenciServiceImpl implements OgrenciService {

    private final OgrenciRepository ogrenciRepository;
    private final DersOgrenciServiceImpl dersOgrenciService;

    public OgrenciServiceImpl(OgrenciRepository ogrenciRepository, DersOgrenciServiceImpl dersOgrenciService) {
        this.ogrenciRepository = ogrenciRepository;
        this.dersOgrenciService = dersOgrenciService;
    }

    @Override
    public List<OgrenciDto> getAll() {

        List<OgrenciDto> ogrenciDtoList = new ArrayList<>();
        List<OgrenciDto> finalOgrenciDtoList = ogrenciDtoList;
        ogrenciRepository.findAll().forEach(ogrenci -> {
            OgrenciDto ogrenciDto = new OgrenciDto();
            entityToDto(ogrenci, ogrenciDto);
            finalOgrenciDtoList.add(ogrenciDto);
        });

        //-----1. Örnek------
        /*
         * listemizdeki numrası 1  olan ogrencilerden birinci sıradaki kayıdı getir ve
         * eğer bulamazsa run time exception fırlat
         * */
        ogrenciDtoList.stream()
                .filter(dto -> dto.getNumara() == 1).findFirst().orElseThrow(() -> new RuntimeException("OGRENCI BULUNAMADI"));
//                .filter(dto -> dto.getNumara()==1 ).findFirst().orElse(new OgrenciDto());

        //-----2. Örnek------
        /**
         * ögrenci listesininde adı ali olan kayıtlara göre filitleme yapıyoruz ve
         * dönen ali leri tekrar filtreleme alilerin içindeki ders ogrenci listesinde devamsızlığı büyük 0 olan
         * kayıtları tekrar listelemiş oldum.(bu koşulla eşleşen büyün kayıtları listeye collect ile dönüştürdüm.
         */
        ogrenciDtoList = finalOgrenciDtoList.stream()
                .filter(ogrenciDto -> ogrenciDto.getAd().equalsIgnoreCase("ali"))
                .filter(
//                        ogrenciDto -> ogrenciDto.getYas()==25  -- burada adi ali olan 25 yasındaki vatandaslar filtreleniyor.
                        ogrenciDto -> ogrenciDto.getDersOgrenciler().stream()
                        .filter(dersOgrenciDto -> dersOgrenciDto.getDevamsizlik() > 0)
                        .allMatch(dersOgrenciDto -> dersOgrenciDto.getDevamsizlik() > 0)
                        ).collect(Collectors.toList());
        return ogrenciDtoList;
    }

    @Override
    public Optional<OgrenciDto> getById(long id) {
        OgrenciDto ogrenciDto = new OgrenciDto();
        entityToDto(ogrenciRepository.findById(id).orElseThrow(() -> new RuntimeException("OGRENCI BULUNAMADI")), ogrenciDto);
        return Optional.of(ogrenciDto);
    }

    @Override
    public OgrenciDto add(OgrenciDto ogrenciDto) {
        Ogrenci ogrenci = new Ogrenci();
//        BeanUtils.copyProperties(ogrenciDto, ogrenci);
        dtoToEntity(ogrenciDto, ogrenci);
        ogrenci = ogrenciRepository.save(ogrenci);
        entityToDto(ogrenci, ogrenciDto);
        return ogrenciDto;
    }

    @Override
    public void delete(OgrenciDto ogrenciDto) {
        Ogrenci ogrenci = new Ogrenci();
        dtoToEntity(ogrenciDto, ogrenci);
        ogrenciRepository.delete(ogrenci);
    }

    @Override
    public OgrenciDto update(OgrenciDto ogrenciDto) {
        Optional<OgrenciDto> ogrenciDb = Optional.of(getById(ogrenciDto.getId()).orElseThrow());
        ogrenciDto.setId(ogrenciDb.get().getId());
        Ogrenci ogrenci = new Ogrenci();
        entityToDto(ogrenciRepository.save(ogrenci), ogrenciDto);
        return ogrenciDto;
    }

    private void dtoToEntity(OgrenciDto ogrenciDto, Ogrenci ogrenci) {

        ogrenci.setId(ogrenciDto.getId());
        ogrenci.setAd(ogrenciDto.getAd());
        ogrenci.setNumara(ogrenciDto.getNumara());
        ogrenci.setYas(ogrenciDto.getYas());


        if (!CollectionUtils.isEmpty(ogrenciDto.getDersOgrenciler())) {
            ogrenciDto.getDersOgrenciler().forEach(dersOgrenciDto -> {
                DersOgrenci dersOgrenci = new DersOgrenci();
                dersOgrenciService.dtoToEntity(dersOgrenciDto, dersOgrenci);

                if (CollectionUtils.isEmpty(ogrenci.getDersOgrenciler()))
                    ogrenci.setDersOgrenciler(new HashSet<>());
                dersOgrenci.setOgrenci(ogrenci);
                ogrenci.getDersOgrenciler().add(dersOgrenci);
            });
        }
    }

    private void entityToDto(Ogrenci ogrenci, OgrenciDto ogrenciDto) {

        ogrenciDto.setId(ogrenci.getId());
        ogrenciDto.setAd(ogrenci.getAd());
        ogrenciDto.setNumara(ogrenci.getNumara());
        ogrenciDto.setYas(ogrenci.getYas());
        if (!CollectionUtils.isEmpty(ogrenci.getDersOgrenciler()))
            ogrenci.getDersOgrenciler().forEach(dersOgrenci -> {
                DersOgrenciDto dersOgrenciDto = new DersOgrenciDto();
                dersOgrenciService.entityToDto(dersOgrenci, dersOgrenciDto);
                if (CollectionUtils.isEmpty(ogrenciDto.getDersOgrenciler()))
                    ogrenciDto.setDersOgrenciler(new HashSet<>());
                ogrenciDto.getDersOgrenciler().add(dersOgrenciDto);
            });
    }
}
