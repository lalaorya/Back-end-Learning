package com.hhj.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mycar")
public class Car {
    private String band;
    private double price;

    public Car() {
    }

    public Car(String band, double price) {
        this.band = band;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "band='" + band + '\'' +
                ", price=" + price +
                '}';
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
