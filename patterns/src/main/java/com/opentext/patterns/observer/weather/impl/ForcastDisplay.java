package com.opentext.patterns.observer.weather.impl;

public class ForcastDisplay extends Display {

    public ForcastDisplay(WeatherData weatherData) {
        super(weatherData);
    }

    @Override
    public void beforeDisplayHook(){
        System.out.println("Forcast Display");
    }

    @Override
    public void display() {
        if(temperature > 90 && humidity > 90 && pressure > 90) {
            System.out.println("Stay Home.. its too hot outside.. conditions are very bad");
        }else if(temperature < 30 && humidity < 20 && pressure < 20) {
            System.out.println("A good day for going out and playing");
        }else{
            System.out.println("Its a normal day");
        }
    }
}
