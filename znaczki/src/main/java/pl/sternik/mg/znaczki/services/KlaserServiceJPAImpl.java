package pl.sternik.mg.znaczki.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import pl.sternik.mg.znaczki.entities.Znaczek;
import pl.sternik.mg.znaczki.repositories.springdata.ZnaczekRepository;

@Service
@Qualifier("spring-data")
public class KlaserServiceJPAImpl implements KlaserService {

    @Autowired
    private ZnaczekRepository bazaDanych;

    @Override
    public List<Znaczek> findAll() {
        List<Znaczek> l = new ArrayList<>();
        for (Znaczek item : bazaDanych.findAll()) {
            l.add(item);
        }
        return l;
    }

    @Override
    public List<Znaczek> findAllToSell() {
        List<Znaczek> l = new ArrayList<>();
        for (Znaczek item : bazaDanych.findAll()) {
            l.add(item);
        }
        return l;
    }

    @Override
    public Optional<Znaczek> findById(Long id) {
        return Optional.ofNullable(bazaDanych.findByNumerKatalogowy(id));
    }

    @Override
    public Optional<Znaczek> create(Znaczek Znaczek) {
        return Optional.of(bazaDanych.save(Znaczek));
    }

    @Override
    public Optional<Znaczek> edit(Znaczek Znaczek) {
        return Optional.of(bazaDanych.save(Znaczek));
    }

    @Override
    public Optional<Boolean> deleteById(Long id) {
        bazaDanych.delete(id.intValue());
        return Optional.of(Boolean.TRUE);
    }

    @Override
    public List<Znaczek> findLatest3() {
        return Collections.emptyList();
    }

}