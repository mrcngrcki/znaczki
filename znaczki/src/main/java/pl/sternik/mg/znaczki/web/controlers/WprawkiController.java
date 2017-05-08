package pl.sternik.mg.znaczki.web.controlers;

import java.util.Date;

import javax.ws.rs.HeaderParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.sternik.mg.znaczki.entities.Znaczek;
import pl.sternik.mg.znaczki.entities.Status;
import pl.sternik.mg.znaczki.repositories.ZnaczekAlreadyExistsException;
import pl.sternik.mg.znaczki.repositories.ZnaczkiRepository;
import pl.sternik.mg.znaczki.repositories.NoSuchZnaczekException;



@Controller
public class WprawkiController {

    @Autowired
    @Qualifier("tablica")
    ZnaczkiRepository baza;
    
    @RequestMapping(path = "/wprawki", method = RequestMethod.GET)
    public String wprawki(ModelMap model) {
        model.put("msg", "Wartosc z modelu");
        model.addAttribute("data", new Date());
        return "wprawki";
    }

    @GetMapping("/wprawki/{cos}")
    public String wprawki(@PathVariable String cos, ModelMap model) {
        model.addAttribute("cos", cos);
        model.put("msg", "Wartosc z modelu");
        model.addAttribute("data", new Date());
        return "wprawki";
    }

    @GetMapping("/wprawki2")
    @ResponseBody
    public String wprawkiParam(@RequestParam("cos") String cosParam, ModelMap model) {
        return "Wprawki z param cos=" + cosParam;
    }
    
    @GetMapping("/wprawki3")
    @ResponseBody
    public String wprawkiHeader(@RequestHeader("User-Agent") String cosParam, ModelMap model) {
        return "Uzywasz przegladarki:=" + cosParam;
    }
    
    @GetMapping(value = "/wprawki/znaczki/{id}/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Znaczek> viewAsJson(@PathVariable("id") Long id, final ModelMap model) {
        Znaczek z;
        try {
            z = baza.readById(id);
            return new ResponseEntity<Znaczek>(z, HttpStatus.OK);
            
        } catch (NoSuchZnaczekException e) {
            System.out.println(e.getClass().getName());
            z = new Znaczek();
            z.setNumerKatalogowy(id);
            z.setKrajPochodzenia("Polska");
            z.setStatus(Status.NOWY);
            z.setNominal(10L);
            try {
                baza.create(z);
            } catch (ZnaczekAlreadyExistsException e1) {
                System.out.println(e1.getClass().getName());
            }
            return new ResponseEntity<Znaczek>(z, HttpStatus.CREATED);
        }
    }

}
