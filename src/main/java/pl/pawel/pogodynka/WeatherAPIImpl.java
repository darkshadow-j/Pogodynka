package pl.pawel.pogodynka;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.pawel.pogodynka.pojo.weather.ConsolidatedWeather;
import pl.pawel.pogodynka.pojo.weather.Pogoda;

@Service
public class WeatherAPIImpl {

    private RestTemplate restTemplate;
    private String imageURL;

    private final String LOCATION_ENDPOINT = "https://www.metaweather.com/api/location/search/";
    private final String LOCATION_SEARCH_PARAM = "query";
    private final String WEATHER_ENDPOINT = "https://www.metaweather.com/api/location/";
    private final String ICON_ENDPOINT = "https://www.metaweather.com/static/img/weather/ico/";


    public WeatherAPIImpl() {

        restTemplate = new RestTemplate();

    }


    public String getIconURL(String type) {
        return ICON_ENDPOINT + type + ".ico";
    }

    public ConsolidatedWeather getWeather(String location) {

        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(LOCATION_ENDPOINT).queryParam(LOCATION_SEARCH_PARAM, location.toLowerCase());
        JsonNode node= restTemplate.getForObject(uri.toUriString(), JsonNode.class).get(0).get("woeid");
        Pogoda results = restTemplate.getForObject(WEATHER_ENDPOINT + node.toString(), Pogoda.class);
        return results.getConsolidatedWeather().get(0);
    }


}