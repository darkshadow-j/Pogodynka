package pl.pawel.pogodynka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pawel.pogodynka.WeatherAPIImpl;
import pl.pawel.pogodynka.pojo.weather.ConsolidatedWeather;

@Controller
@RequestMapping("/")
public class WeatherController {


    private WeatherAPIImpl weatherAPI;
    private String location="london";

    @Autowired
    public WeatherController(WeatherAPIImpl weatherAPI) {
        this.weatherAPI = weatherAPI;
    }

    @GetMapping
    public String HomePage(Model model){
        ConsolidatedWeather consolidatedWeather = weatherAPI.getWeather(location);
        model.addAttribute("temp", consolidatedWeather);
        model.addAttribute("location", new LocationName());
        model.addAttribute("icon", weatherAPI.getIconURL(consolidatedWeather.getWeatherStateAbbr()));
        return "home";
    }
    @PostMapping
    public String SearchByLocation(@ModelAttribute LocationName location){
        this.location=location.getLocation();
        return "redirect:/";
    }


}
