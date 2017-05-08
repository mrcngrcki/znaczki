package pl.sternik.mg.znaczki.repositories;

import java.util.List;

import pl.sternik.mg.znaczki.entities.Znaczek;


public interface ZnaczkiRepository {
    Znaczek create(Znaczek znaczek) throws ZnaczekAlreadyExistsException;
    Znaczek readById(Long id) throws NoSuchZnaczekException;
    Znaczek update(Znaczek znaczek) throws NoSuchZnaczekException;
    void deleteById(Long id) throws NoSuchZnaczekException;
    List<Znaczek> findAll();
    List<Znaczek> findAllToSell();
}