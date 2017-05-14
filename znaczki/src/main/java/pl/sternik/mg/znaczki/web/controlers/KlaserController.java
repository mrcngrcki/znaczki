package pl.sternik.mg.znaczki.web.controlers;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.sternik.mg.znaczki.entities.Znaczek;
import pl.sternik.mg.znaczki.services.KlaserService;
import pl.sternik.mg.znaczki.services.NotificationService;


@Controller
public class KlaserController {

    @Autowired
     @Qualifier("spring-data")
    private KlaserService klaserService;

    @Autowired
    private NotificationService notificationService;

//    @ModelAttribute("statusyAll")
//    public List<Status> populateStatusy() {
//        return Arrays.asList(Status.ALL);
//    }

    @ModelAttribute("stampsAll")
    public List<Znaczek> populateStamps() {
        return this.klaserService.findAll();
    }

    @ModelAttribute("stampsToSell")
    public List<Znaczek> populateStampsToSell() {
        return this.klaserService.findAllToSell();
    }

//    @ModelAttribute("stampsLast3")
//    public List<Moneta> populateLast3Stamps() {
//        return this.klaserService.findLatest3();
//    }

    @RequestMapping({ "/", "/index" })
    public String index(Model model) {
        return "index";
    }

    @RequestMapping(value = "/znaczki", method = RequestMethod.GET)
    public String showMainPage(Model model) {
        model.addAttribute("MyMessages",  notificationService.getNotificationMessages());
        return "klaser";
    }

    @RequestMapping("/tosell")
    public String showToSellPage() {
        return "tosell";
    }

}
