package lk.ijse.poultryfarm.controller.temperature;

import com.jfoenix.controls.JFXSlider;
import javafx.beans.property.DoubleProperty;

public class SimulateTemperatureController {
    public JFXSlider sliderTemperature;
    private DoubleProperty temperatureProperty;

    public void setTemperatureProperty(DoubleProperty temperatureProperty) {
        this.temperatureProperty = temperatureProperty;

        sliderTemperature.setMin(0);
        sliderTemperature.setMax(100);
        sliderTemperature.setValue(temperatureProperty.get());

        temperatureProperty.bindBidirectional(sliderTemperature.valueProperty());
    }
}
