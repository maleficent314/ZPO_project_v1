import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Parameter> tableResults;

    @FXML
    private BarChart<?, ?> chart;

    @FXML
    private TextField editTextCity;

    @FXML
    private Button downloadButton;

    @FXML
    private TextField editTextPath;

    @FXML
    private Button loadButton;

    @FXML
    private Button saveButton;

    @FXML
    void downloadButtonOnClick(ActionEvent event) throws UnsupportedEncodingException {
        tableResults.visibleProperty().setValue(true);
        chart.visibleProperty().setValue(true);
        String city = editTextCity.getText();
        createTable((ObservableList) download(city));
        createChart((ObservableList) download(city));
    }

    @FXML
    void loadButtonOnClick(ActionEvent event) {
        tableResults.visibleProperty().setValue(true);
        chart.visibleProperty().setValue(true);
        downloadFromFile();
    }

    @FXML
    void saveButtononClick(ActionEvent event) throws UnsupportedEncodingException {
        String city = editTextCity.getText();
        saveAsJson((ObservableList) download(city), city);
    }

    public List download(String city) throws UnsupportedEncodingException {
        city = URLEncoder.encode(city, "UTF-8");
        Gson gson = new Gson();
        StringBuffer response = new StringBuffer();
        String url = "https://api.openaq.org/v1/latest?city=" + city;

        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            System.out.println("Response: " + responseCode);

            String inputLine;
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        AllDownloads a = gson.fromJson(String.valueOf(response), AllDownloads.class);

        String dates[] = new String[5];
        for (int i = 0; i < 5; i++) {
            dates[i] = "none";
        }
        int numberOfMeasurements[] = new int[5];
        double minimums[] = new double[5];
        double maximums[] = new double[5];
        double sums[] = new double[5];
        double averages[] = new double[5];
        double stds[] = new double[5];

        for (int i = 0; i < a.results.size(); i++) {
            for (int j = 0; j < a.results.get(i).measurements.size(); j++) {
                String p = a.results.get(i).measurements.get(j).parameter;
                double v = a.results.get(i).measurements.get(j).value;


                switch (p) {
                    case "pm10":

                        if (minimums[0] == 0) {
                            minimums[0] = v;
                        } else if (minimums[0] > v) {
                            minimums[0] = v;
                        }
                        if (maximums[0] < v) {
                            maximums[0] = v;
                        }

                        numberOfMeasurements[0] = numberOfMeasurements[0] + 1;
                        sums[0] = sums[0] + v;
                        averages[0] = sums[0] / numberOfMeasurements[0];
                        double y = 0;
                        for (int k = 0; k < numberOfMeasurements[0]; k++) {
                            double x = Math.pow(v - averages[0], 2);
                            y += x;
                        }
                        stds[0] = Math.sqrt(y / numberOfMeasurements[0]);

                        dates[0] = a.results.get(i).measurements.get(j).lastUpdated.replace('T', ' ').substring(0, 10);
                        break;
                    case "pm25":
                        if (minimums[1] == 0) {
                            minimums[1] = v;
                        } else if (minimums[1] > v) {
                            minimums[1] = v;
                        }
                        if (maximums[1] < v) {
                            maximums[1] = v;
                        }
                        numberOfMeasurements[1] = numberOfMeasurements[1] + 1;
                        sums[1] = sums[1] + v;
                        averages[1] = sums[1] / numberOfMeasurements[1];
                        double y1 = 0;
                        for (int k = 0; k < numberOfMeasurements[1]; k++) {
                            double x = Math.pow(v - averages[1], 2);
                            y1 += x;
                        }
                        stds[1] = Math.sqrt(y1 / numberOfMeasurements[1]);
                        dates[1] = a.results.get(i).measurements.get(j).lastUpdated.replace('T', ' ').substring(0, 10);
                        break;
                    case "so2":
                        if (minimums[2] == 0) {
                            minimums[2] = v;
                        } else if (minimums[2] > v) {
                            minimums[2] = v;
                        }
                        if (maximums[2] < v) {
                            maximums[2] = v;
                        }
                        numberOfMeasurements[2] = numberOfMeasurements[2] + 1;
                        sums[2] = sums[2] + v;
                        averages[2] = sums[2] / numberOfMeasurements[2];
                        double y2 = 0;
                        for (int k = 0; k < numberOfMeasurements[2]; k++) {
                            double x = Math.pow(v - averages[2], 2);
                            y2 += x;
                        }
                        stds[2] = Math.sqrt(y2 / numberOfMeasurements[2]);
                        dates[2] = a.results.get(i).measurements.get(j).lastUpdated.replace('T', ' ').substring(0, 10);
                        break;
                    case "o3":
                        if (minimums[3] == 0) {
                            minimums[3] = v;
                        } else if (minimums[3] > v) {
                            minimums[3] = v;
                        }
                        if (maximums[3] < v) {
                            maximums[3] = v;
                        }
                        numberOfMeasurements[3] = numberOfMeasurements[3] + 1;
                        sums[3] = sums[3] + v;
                        averages[3] = sums[3] / numberOfMeasurements[3];
                        double y3 = 0;
                        for (int k = 0; k < numberOfMeasurements[3]; k++) {
                            double x = Math.pow(v - averages[3], 2);
                            y3 += x;
                        }
                        stds[3] = Math.sqrt(y3 / numberOfMeasurements[3]);
                        dates[3] = a.results.get(i).measurements.get(j).lastUpdated.replace('T', ' ').substring(0, 10);
                        break;
                    case "no2":
                        if (minimums[4] == 0) {
                            minimums[4] = v;
                        } else if (minimums[4] > v) {
                            minimums[4] = v;
                        }
                        if (maximums[4] < v) {
                            maximums[4] = v;
                        }
                        numberOfMeasurements[4] = numberOfMeasurements[4] + 1;
                        sums[4] = sums[4] + v;
                        averages[4] = sums[4] / numberOfMeasurements[4];
                        double y4 = 0;
                        for (int k = 0; k < numberOfMeasurements[4]; k++) {
                            double x = Math.pow(v - averages[4], 2);
                            y4 += x;
                        }
                        stds[4] = Math.sqrt(y4 / numberOfMeasurements[4]);
                        dates[4] = a.results.get(i).measurements.get(j).lastUpdated.replace('T', ' ').substring(0, 10);
                        break;
                }

            }
        }

        String[] qualities = calculateQuality(averages);
        Parameter pm10 = new Parameter(dates[0], "pm10", numberOfMeasurements[0], minimums[0], maximums[0], averages[0], stds[0], "ug/m3", qualities[0]);
        Parameter pm25 = new Parameter(dates[1], "pm25", numberOfMeasurements[1], minimums[1], maximums[1], averages[1], stds[1], "ug/m3", qualities[1]);
        Parameter so2 = new Parameter(dates[2], "so2", numberOfMeasurements[2], minimums[2], maximums[2], averages[2], stds[2], "ug/m3", qualities[2]);
        Parameter o3 = new Parameter(dates[3], "o3", numberOfMeasurements[3], minimums[3], maximums[3], averages[3], stds[3], "ug/m3", qualities[3]);
        Parameter no2 = new Parameter(dates[4], "no2", numberOfMeasurements[4], minimums[4], maximums[4], averages[4], stds[4], "ug/m3", qualities[4]);
         ObservableList<Parameter> list = FXCollections.observableArrayList();
        list.addAll(pm10, pm25, so2, o3, no2);
        return list;
    }

    public String[] calculateQuality(double[] tabavg) {
        String qualities[] = new String[5];
        for(int i=0;i<5;i++){
            qualities[i]="none";
        }
        double[] pm10q = {201, 141, 101, 61, 21, 0};
        double[] pm25q = {121, 85, 61, 37, 13, 0};
        double[] o3q = {241, 181, 151, 121, 71, 0};
        double[] no2q = {401, 201, 151, 101, 41, 0};
        double[] so2q = {501, 351, 201, 101, 51, 0};
        String[] qualityIndex = {"Very bad", "Bad", "Sufficient", "Moderate", "Good", "Very good"};

        for(int i=0;i<6;i++) {
            if (tabavg[0] > pm10q[i])
                qualities[0] = qualityIndex[i];
            if (tabavg[1] > pm25q[i])
                qualities[1] = qualityIndex[i];
            if (tabavg[2] > so2q[i])
                qualities[2] = qualityIndex[i];
            if (tabavg[3] > o3q[i])
                qualities[3] = qualityIndex[i];
            if (tabavg[4] > no2q[i])
                qualities[4] = qualityIndex[i];
        }

        return qualities;
    }


    public void createTable(ObservableList<Parameter> list) {
        tableResults.getColumns().clear();
        tableResults.getItems().clear();


        TableColumn<Parameter, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Parameter, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Parameter, String> numberColumn = new TableColumn<>("Number");
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfMeasurements"));

        TableColumn<Parameter, String> minimumColumn = new TableColumn<>("Min");
        minimumColumn.setCellValueFactory(new PropertyValueFactory<>("minimum"));

        TableColumn<Parameter, String> maximumColumn = new TableColumn<>("Max");
        maximumColumn.setCellValueFactory(new PropertyValueFactory<>("maximum"));

        TableColumn<Parameter, String> averageColumn = new TableColumn<>("Average");
        averageColumn.setCellValueFactory(new PropertyValueFactory<>("average"));

        TableColumn<Parameter, String> stdColumn = new TableColumn<>("Deviation");
        stdColumn.setCellValueFactory(new PropertyValueFactory<>("std"));

        TableColumn<Parameter, String> unitColumn = new TableColumn<>("Unit");
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));

        TableColumn<Parameter, String> qualityColumn = new TableColumn<>("Quality");
        qualityColumn.setCellValueFactory(new PropertyValueFactory<>("quality"));

        tableResults.setItems(list);
        tableResults.getColumns().addAll(dateColumn, nameColumn, numberColumn, minimumColumn, maximumColumn, averageColumn, stdColumn, unitColumn, qualityColumn);
    }

    public void createChart(ObservableList<Parameter> list){
        chart.getData().clear();
        XYChart.Series standards = new XYChart.Series<>();
        standards.getData().add(new XYChart.Data("pm10", 141));
        standards.getData().add(new XYChart.Data("pm2.5", 85));
        standards.getData().add(new XYChart.Data("O3", 181));
        standards.getData().add(new XYChart.Data("NO2", 201));
        standards.getData().add(new XYChart.Data("SO2", 351));
        XYChart.Series measurement = new XYChart.Series<>();
        measurement.getData().add(new XYChart.Data("pm10", list.get(0).getAverage()));
        measurement.getData().add(new XYChart.Data("pm2.5", list.get(1).getAverage()));
        measurement.getData().add(new XYChart.Data("O3", list.get(2).getAverage()));
        measurement.getData().add(new XYChart.Data("NO2", list.get(3).getAverage()));
        measurement.getData().add(new XYChart.Data("SO2", list.get(4).getAverage()));
        chart.getData().addAll(standards);
        chart.getData().addAll(measurement);
    }

    public void saveAsJson(ObservableList<Parameter> list, String city) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File file = new File(city+".json");

        ObservableList<Parameter> parameter = list;
        try (FileWriter fileWriter = new FileWriter(file)) {
            gson.toJson(parameter, fileWriter);
        } catch (IOException e) {
            System.out.println("IO error");
        }

    }

    public List downloadFromFile(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String path = editTextPath.getText();
        Parameter[] fromJson = null;
        File file = new File(path);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            fromJson = gson.fromJson(bufferedReader, Parameter[].class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parameter pm10 = fromJson[0];
        Parameter pm25 = fromJson[1];
        Parameter so2= fromJson[2];
        Parameter o3 = fromJson[3];
        Parameter no2 = fromJson[4];
        ObservableList<Parameter> parameter = FXCollections.observableArrayList();
        parameter.add(pm10);
        parameter.add(pm25);
        parameter.add(so2);
        parameter.add(o3);
        parameter.add(no2);

        createTable(parameter);
        createChart(parameter);
        return parameter;
    }
    @FXML
    void initialize() {
        assert tableResults != null : "fx:id=\"tableResults\" was not injected: check your FXML file 'ComparAIRfxml.fxml'.";
        assert chart != null : "fx:id=\"chart\" was not injected: check your FXML file 'ComparAIRfxml.fxml'.";
        assert editTextCity != null : "fx:id=\"editTextCity\" was not injected: check your FXML file 'ComparAIRfxml.fxml'.";
        assert downloadButton != null : "fx:id=\"downloadButton\" was not injected: check your FXML file 'ComparAIRfxml.fxml'.";
        assert editTextPath != null : "fx:id=\"editTextPath\" was not injected: check your FXML file 'ComparAIRfxml.fxml'.";
        assert loadButton != null : "fx:id=\"loadButton\" was not injected: check your FXML file 'ComparAIRfxml.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'ComparAIRfxml.fxml'.";

        XYChart.Series standards = new XYChart.Series<>();
        standards.getData().add(new XYChart.Data("pm10", 141));
        standards.getData().add(new XYChart.Data("pm2.5", 85));
        standards.getData().add(new XYChart.Data("O3", 181));
        standards.getData().add(new XYChart.Data("NO2", 201));
        standards.getData().add(new XYChart.Data("SO2", 351));
        chart.getData().addAll(standards);

    }
}
