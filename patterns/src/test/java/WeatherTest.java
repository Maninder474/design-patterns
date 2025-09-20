import com.opentext.patterns.observer.weather.impl.CurrentConditionsDisplay;
import com.opentext.patterns.observer.weather.impl.ForcastDisplay;
import com.opentext.patterns.observer.weather.impl.WeatherData;
import org.junit.jupiter.api.Test;

public class WeatherTest {

    @Test
    public void weatherTest() {
        WeatherData weatherData = new WeatherData();
        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(weatherData);
        ForcastDisplay forcastDisplay = new ForcastDisplay(weatherData);

        weatherData.setMeasurements(80, 65, 30.4f);
        weatherData.setMeasurements(91, 95, 95.4f);
        weatherData.setMeasurements(20, 10, 10.4f);

        weatherData.removeObserver(currentDisplay);
        weatherData.setMeasurements(30, 30, 60.4f);
    }
}
