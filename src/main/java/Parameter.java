import java.security.Signature;

public class Parameter {
    private String date;
    private String name;
    private int numberOfMeasurements;
    private double minimum;
    private double maximum;
    private double average;
    private double std;
    private String unit;
    private String quality;


    public Parameter(String date, String name, int numberOfMeasurements, double minimum, double maximum, double average, double std, String unit, String quality) {
        this.date = date;
        this.name = name;
        this.numberOfMeasurements = numberOfMeasurements;
        this.minimum = minimum;
        this.maximum = maximum;
        this.average = average;
        this.std = std;
        this.unit = unit;
        this.quality = quality;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfMeasurements() {
        return numberOfMeasurements;
    }

    public double getMinimum() {
        return minimum;
    }

    public double getMaximum() {
        return maximum;
    }

    public double getAverage() {
        return average;
    }

    public double getStd() {
        return std;
    }

    public String getUnit() {
        return unit;
    }

    public String getQuality() {
        return quality;
    }
}
