package pl.sternik.mg.znaczki.web.controlers;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.sternik.mg.znaczki.entities.Znaczek;
import pl.sternik.mg.znaczki.entities.Status;
import pl.sternik.mg.znaczki.services.KlaserService;
import pl.sternik.mg.znaczki.services.NotificationService;


@Controller
public class ZnaczkiController {

    @Autowired
     @Qualifier("spring-data")
    private KlaserService klaserService;

    @Autowired
    private NotificationService notifyService;

    @ModelAttribute("statusyAll")
    public List<Status> populateStatusy() {
        return Arrays.asList(Status.ALL);
    }
    
    @ModelAttribute("statusyToSell")
    public List<Status> StatusToSell() {
        return Arrays.asList(Status.DO_SPRZEDANIA);
    }

    @GetMapping(value = "/znaczki/{id}")
    public String view(@PathVariable("id") Long id, final ModelMap model) {
        Optional<Znaczek> result;
        result = klaserService.findById(id);
        if (result.isPresent()) {
            Znaczek znaczek = result.get();
            model.addAttribute("znaczek", znaczek);
            return "znaczek";
        } else {
            notifyService.addErrorMessage("Cannot find znaczek #" + id);
            model.clear();
            return "redirect:/znaczki";
        }
    }

    @RequestMapping(value = "/znaczki/{id}/json", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Znaczek> viewAsJson(@PathVariable("id") Long id, final ModelMap model) {
        Optional<Znaczek> result;
        result = klaserService.findById(id);
        if (result.isPresent()) {
            Znaczek znaczek = result.get();
            return new ResponseEntity<Znaczek>(znaczek, HttpStatus.OK);
        } else {
            notifyService.addErrorMessage("Cannot find znaczek #" + id);
            model.clear();
            return new ResponseEntity<Znaczek>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/znaczki", params = { "save" }, method = RequestMethod.POST)
    public String saveZnaczek(@Valid Znaczek znaczek, BindingResult bindingResult, ModelMap model) {

        if (bindingResult.hasErrors()) {
            notifyService.addErrorMessage("Please fill the form correctly!");
            return "znaczek";
        }
        Optional<Znaczek> result = klaserService.edit(znaczek);
        if (result.isPresent())
            notifyService.addInfoMessage("Zapis udany");
        else
            notifyService.addErrorMessage("Zapis NIE udany");
        model.clear();
        return "redirect:/znaczki";
    }

    @RequestMapping(value = "/znaczki", params = { "create" }, method = RequestMethod.POST)
    public String createZnaczek(Znaczek znaczek, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            notifyService.addErrorMessage("Please fill the form correctly!");
            return "znaczek";
        }
        klaserService.create(znaczek);
        model.clear();
        notifyService.addInfoMessage("Zapis nowej udany");
        return "redirect:/znaczki";
    }

    @RequestMapping(value = "/znaczki", params = { "remove" }, method = RequestMethod.POST)
    public String removeRow(final Znaczek znaczek, final BindingResult bindingResult, final HttpServletRequest req) {
        final Integer rowId = Integer.valueOf(req.getParameter("remove"));
        Optional<Boolean> result = klaserService.deleteById(rowId.longValue());
        return "redirect:/znaczki";
    }

    @RequestMapping(value = "/znaczki/create", method = RequestMethod.GET)
    public String showMainPages(final Znaczek znaczek) {
        // Ustawiamy date nowego znaczka, na dole strony do dodania
    	znaczek.setDataNabycia(Calendar.getInstance().getTime());
        return "znaczek";
    }
}