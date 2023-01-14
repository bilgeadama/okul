package service.impl;

import dto.DersDto;
import dto.DersOgrenciDto;
import dto.OgrenciDto;
import entity.DersOgrenci;
import entity.Ogrenci;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import repository.OgrenciRepository;
import service.OgrenciService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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

        ogrenciRepository.findAll().forEach(ogrenci -> {
            OgrenciDto ogrenciDto = new OgrenciDto();
            BeanUtils.copyProperties(ogrenci, ogrenciDto);
            ogrenciDtoList.add(ogrenciDto);
        });

        return ogrenciDtoList;
    }

    @Override
    public Optional<OgrenciDto> getById(long id) {
        Optional<OgrenciDto> ogrenciDto = Optional.of(new OgrenciDto());
        BeanUtils.copyProperties(ogrenciRepository.findById(id), ogrenciDto);
        return ogrenciDto;
    }

    @Override
    public OgrenciDto add(OgrenciDto ogrenciDto) {
        Ogrenci ogrenci = new Ogrenci();
//        BeanUtils.copyProperties(ogrenciDto, ogrenci);
        dtoToEntity(ogrenciDto,ogrenci);
        ogrenci = ogrenciRepository.save(ogrenci);
        entityToDto(ogrenci,ogrenciDto);
        return ogrenciDto;
    }

    @Override
    public void delete(OgrenciDto ogrenciDto) {
        Ogrenci ogrenci = new Ogrenci();
        BeanUtils.copyProperties(ogrenciDto, ogrenci);
        ogrenciRepository.delete(ogrenci);
    }

    @Override
    public OgrenciDto update(OgrenciDto ogrenciDto) {
        Optional<OgrenciDto> optionalogrenci = Optional.of(getById(ogrenciDto.getId()).orElseThrow());
        optionalogrenci.get().setAd(ogrenciDto.getAd());
        Ogrenci ogrenci = new Ogrenci();
        BeanUtils.copyProperties(ogrenciDto, ogrenci);
        entityToDto(ogrenciRepository.save(ogrenci),ogrenciDto);
        return ogrenciDto;
    }

    private void dtoToEntity(OgrenciDto ogrenciDto, Ogrenci ogrenci) {

        ogrenci.setId(ogrenciDto.getId());
        ogrenci.setAd(ogrenciDto.getAd());
        ogrenci.setNumara(ogrenciDto.getNumara());
        ogrenci.setYas(ogrenciDto.getYas());


        if(!CollectionUtils.isEmpty(ogrenciDto.getDersOgrenciler())){
            ogrenciDto.getDersOgrenciler().forEach(dersOgrenciDto -> {
                DersOgrenci dersOgrenci = new DersOgrenci();
                dersOgrenciService.dtoToEntity(dersOgrenciDto,dersOgrenci);

                if(CollectionUtils.isEmpty(ogrenci.getDersOgrenciler()))
                    ogrenci.setDersOgrenciler(new HashSet<>());
                dersOgrenci.setOgrenci(ogrenci);
                ogrenci.getDersOgrenciler().add(dersOgrenci);
            });
        }

    }

    private void entityToDto(Ogrenci ogrenci, OgrenciDto ogrenciDto) {

        ogrenciDto.setId(ogrenciDto.getId());
        ogrenciDto.setAd(ogrenciDto.getAd());
        ogrenciDto.setNumara(ogrenciDto.getNumara());
        ogrenciDto.setYas(ogrenciDto.getYas());

        if(!CollectionUtils.isEmpty(ogrenci.getDersOgrenciler()))
            ogrenci.getDersOgrenciler().forEach(dersOgrenci -> {
                DersOgrenciDto dersOgrenciDto = new DersOgrenciDto();
                dersOgrenciService.entityToDto(dersOgrenci,dersOgrenciDto);
                if(CollectionUtils.isEmpty(ogrenciDto.getDersOgrenciler()))
                    ogrenciDto.setDersOgrenciler(new HashSet<>());
                ogrenciDto.getDersOgrenciler().add(dersOgrenciDto);
            });
    }
}
