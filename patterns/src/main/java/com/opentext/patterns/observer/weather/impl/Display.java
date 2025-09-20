package com.opentext.patterns.observer.weather.impl;

import com.opentext.patterns.observer.weather.DisplayElement;
import com.opentext.patterns.observer.weather.Observer;

public class Display implements Observer, DisplayElement {
    float temperature;
    float humidity;
    float pressure;
    WeatherData weatherData;

    public Display(WeatherData weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    public void beforeDisplayHook() {}

    @Override
    public void display() {
        System.out.println("Temperature: " + temperature);
        System.out.println("Humidity: " + humidity);
        System.out.println("Pressure: " + pressure);
    }

    public void afterDisplayHook() {}

    @Override
    public void update(float temp, float humidity, float pressure) {
        this.temperature = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        beforeDisplayHook();
        display();
        afterDisplayHook();
    }
}
