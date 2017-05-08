package pl.sternik.mg.znaczki.repositories;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import pl.sternik.mg.znaczki.entities.Znaczek;
import pl.sternik.mg.znaczki.entities.Status;


@Service
@Qualifier("lista")
public class ZnaczkiRepositoryJ8Impl implements ZnaczkiRepository {

    private List<Znaczek> znaczki = new ArrayList<Znaczek>() {
        {
            add(Znaczek.produceZnaczek(1L, "Polska", 1L, "zł", "Jan Paweł II", new Date(), new BigDecimal("1.2"),
                    Status.NOWY));
            add(Znaczek.produceZnaczek(2L, "Polska", 1L, "zł", "Mikołaj Kopernik", new Date(), new BigDecimal("1.2"),
                    Status.DO_SPRZEDANIA));
            add(Znaczek.produceZnaczek(3L, "Polska", 1L, "zł", "Poczta Polska", new Date(), new BigDecimal("1.2"), Status.DUBLET));
            add(Znaczek.produceZnaczek(4L, "Niemcy", 1L, "eur", "Benedikt XVI", new Date(), new BigDecimal("1.2"),
                    Status.DO_SPRZEDANIA));
            add(Znaczek.produceZnaczek(5L, "Polska", 1L, "zł", "Sternik", new Date(), new BigDecimal("1.2"), Status.NOWY));
            add(Znaczek.produceZnaczek(6L, "Polska", 1L, "zł", "Jacht", new Date(), new BigDecimal("1.2"), Status.NOWY));
        }
    };

    @Override
    public List<Znaczek> findAll() {
        return this.znaczki;
    }

    @Override
    public Znaczek readById(Long id) throws NoSuchZnaczekException {
        return this.znaczki.stream().filter(p -> Objects.equals(p.getNumerKatalogowy(), id)).findFirst()
                .orElseThrow(NoSuchZnaczekException::new);
    }

    @Override
    public Znaczek create(Znaczek znaczek) {
        if (!znaczki.isEmpty()) {
            znaczek.setNumerKatalogowy(
                    this.znaczki.stream().mapToLong(p -> p.getNumerKatalogowy()).max().getAsLong() + 1);
        } else {
            znaczek.setNumerKatalogowy(1L);
        }
        this.znaczki.add(znaczek);
        return znaczek;
    }

    @Override
    public Znaczek update(Znaczek znaczek) throws NoSuchZnaczekException {
        for (int i = 0; i < this.znaczki.size(); i++) {
            if (Objects.equals(this.znaczki.get(i).getNumerKatalogowy(), znaczek.getNumerKatalogowy())) {
                this.znaczki.set(i, znaczek);
                return znaczek;
            }
        }
        throw new NoSuchZnaczekException("Nie ma takiej znaczki: " + znaczek.getNumerKatalogowy());
    }

    @Override
    public void deleteById(Long id) throws NoSuchZnaczekException {
        for (int i = 0; i < this.znaczki.size(); i++) {
            if (Objects.equals(this.znaczki.get(i).getNumerKatalogowy(), id)) {
                this.znaczki.remove(i);
            }
        }
        throw new NoSuchZnaczekException("Nie ma takiego znaczka: " + id);
    }

}
