package pl.sternik.mg.znaczki.repositories.springdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.sternik.mg.znaczki.entities.Znaczek;

@Repository
public interface ZnaczekRepository 
         extends JpaRepository<Znaczek, Integer>{
    public Znaczek findByNumerKatalogowy(Long id);
}