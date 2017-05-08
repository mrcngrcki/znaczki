package pl.sternik.mg.znaczki.services;

import java.util.List;
import java.util.Optional;

import pl.sternik.mg.znaczki.entities.Znaczek;


public interface KlaserService {
    List<Znaczek> findAll();

    List<Znaczek> findAllToSell();

    Optional<Znaczek> findById(Long id);

    Optional<Znaczek> create(Znaczek znaczek);

    Optional<Znaczek> edit(Znaczek znaczek);

    Optional<Boolean> deleteById(Long id);

    List<Znaczek> findLatest3();
}