package com.opentext.patterns.observer.weather.impl;

public class CurrentConditionsDisplay extends  Display  {

    public CurrentConditionsDisplay(WeatherData weatherData) {
        super(weatherData);
    }

    @Override
    public void beforeDisplayHook() {
        System.out.println("Current Conditions Display");
    }
}
