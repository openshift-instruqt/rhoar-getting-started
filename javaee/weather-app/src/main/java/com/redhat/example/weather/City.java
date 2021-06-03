package com.redhat.example.weather;

import javax.persistence.*;

//TODO: Add Entity annotation
public class City {
    // TODO: add Id annotation
    private String id;
    private String name;
    private String weatherType;
    private Integer temp;
    private Integer tempFeelsLike;
    private Integer maxTemp;
    private Integer minTemp;
    private Integer humidity;
    private Integer wind;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(String weatherType) {
        this.weatherType = weatherType;
    }

    public Integer getTemp() {
        return temp;
    }

    public void setTemp(Integer temp) {
        this.temp = temp;
    }

    public Integer getTempFeelsLike() {
        return tempFeelsLike;
    }

    public void setTempFeelsLike(Integer tempFeelsLike) {
        this.tempFeelsLike = tempFeelsLike;
    }

    public Integer getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(Integer maxTemp) {
        this.maxTemp = maxTemp;
    }

    public Integer getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(Integer minTemp) {
        this.minTemp = minTemp;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getWind() {
        return wind;
    }

    public void setWind(Integer wind) {
        this.wind = wind;
    }

    public City(String id, String name, String weatherType, Integer temp, Integer tempFeelsLike, Integer maxTemp, Integer minTemp, Integer humidity, Integer wind) {
        this.id = id;
        this.name = name;
        this.weatherType = weatherType;
        this.temp = temp;
        this.tempFeelsLike = tempFeelsLike;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.humidity = humidity;
        this.wind = wind;
    }

    public City() {
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", weatherType=" + weatherType +
                ", temp=" + temp +
                ", tempFeelsLike=" + tempFeelsLike +
                ", maxTemp=" + maxTemp +
                ", minTemp=" + minTemp +
                ", humidity=" + humidity +
                ", wind=" + wind +
                '}';
    }
}
