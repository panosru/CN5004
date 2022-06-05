package com.cn5004ap.payroll.controller;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.chart.ChartData;
import eu.hansolo.tilesfx.chart.TilesFXSeries;
import eu.hansolo.tilesfx.skins.BarChartItem;
import eu.hansolo.tilesfx.skins.LeaderBoardItem;
import eu.hansolo.tilesfx.tools.FlowGridPane;
import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import java.util.Random;

public class StatisticsController
    extends BaseController
{
    @FXML
    private BarChart<String, Number> barChart;

    private static final    Random RND = new Random();
    private static final    double TILE_WIDTH = 242;
    private static final    double TILE_HEIGHT = 235;

    final static String austria = "Austria";
    final static String brazil = "Brazil";
    final static String france = "France";
    final static String italy = "Italy";
    final static String usa = "USA";

    @FXML
    private FlowGridPane fgp;

    private long lastTimeCall;
    private AnimationTimer timer;
    private DoubleProperty value;

    @Override
    public void initialize()
    {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        barChart.setTitle("Country Summary");
        xAxis.setLabel("Country");
        yAxis.setLabel("Value");

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("2003");
        series1.getData().add(new XYChart.Data<>(austria, 25601.34));
        series1.getData().add(new XYChart.Data<>(brazil, 20148.82));
        series1.getData().add(new XYChart.Data<>(france, 10000.00));
        series1.getData().add(new XYChart.Data<>(italy, 35407.15));
        series1.getData().add(new XYChart.Data<>(usa, 12000.00));

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("2004");
        series2.getData().add(new XYChart.Data<>(austria, 57401.85));
        series2.getData().add(new XYChart.Data<>(brazil, 41941.19));
        series2.getData().add(new XYChart.Data<>(france, 45263.37));
        series2.getData().add(new XYChart.Data<>(italy, 117320.16));
        series2.getData().add(new XYChart.Data<>(usa, 14845.27));

        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        series3.setName("2005");
        series3.getData().add(new XYChart.Data<>(austria, 45000.65));
        series3.getData().add(new XYChart.Data<>(brazil, 44835.76));
        series3.getData().add(new XYChart.Data<>(france, 18722.18));
        series3.getData().add(new XYChart.Data<>(italy, 17557.31));
        series3.getData().add(new XYChart.Data<>(usa, 92633.68));

        barChart.getData().addAll(series1, series2, series3);

        Tile percentageTile = TileBuilder.create()
                                         .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                         .skinType(Tile.SkinType.PERCENTAGE)
                                         .title("Percentage Tile")
                                         .unit("\u0025")
                                         .maxValue(60)
                                         .build();

        BarChartItem barChartItem1 = new BarChartItem("Gerrit", 47, Tile.BLUE);
        BarChartItem barChartItem2 = new BarChartItem("Sandra", 43, Tile.RED);
        BarChartItem barChartItem3 = new BarChartItem("Lilli", 12, Tile.GREEN);
        BarChartItem barChartItem4 = new BarChartItem("Anton", 8, Tile.ORANGE);

        barChartItem1.setFormatString("%.1f kWh");


        Tile barChartTile = TileBuilder.create()
                                       .skinType(Tile.SkinType.BAR_CHART)
                                       .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                       .title("BarChart Tile")
                                       .text("Whatever text")
                                       .barChartItems(barChartItem1, barChartItem2, barChartItem3, barChartItem4)
                                       .decimals(0)
                                       .build();


        XYChart.Series<String, Number> series11 = new XYChart.Series<>();
        series11.setName("Whatever");
        series11.getData().add(new XYChart.Data<>("MO", 23));
        series11.getData().add(new XYChart.Data<>("TU", 21));
        series11.getData().add(new XYChart.Data<>("WE", 20));
        series11.getData().add(new XYChart.Data<>("TH", 22));
        series11.getData().add(new XYChart.Data<>("FR", 24));
        series11.getData().add(new XYChart.Data<>("SA", 22));
        series11.getData().add(new XYChart.Data<>("SU", 20));

        // LineChart Data
        XYChart.Series<String, Number> series22 = new XYChart.Series<>();
        series22.setName("Inside");
        series22.getData().add(new XYChart.Data<>("MO", 8));
        series22.getData().add(new XYChart.Data<>("TU", 5));
        series22.getData().add(new XYChart.Data<>("WE", 0));
        series22.getData().add(new XYChart.Data<>("TH", 2));
        series22.getData().add(new XYChart.Data<>("FR", 4));
        series22.getData().add(new XYChart.Data<>("SA", 3));
        series22.getData().add(new XYChart.Data<>("SU", 5));

        XYChart.Series<String, Number> series33 = new XYChart.Series<>();
        series33.setName("Outside");
        series33.getData().add(new XYChart.Data<>("MO", 8));
        series33.getData().add(new XYChart.Data<>("TU", 5));
        series33.getData().add(new XYChart.Data<>("WE", 0));
        series33.getData().add(new XYChart.Data<>("TH", 2));
        series33.getData().add(new XYChart.Data<>("FR", 4));
        series33.getData().add(new XYChart.Data<>("SA", 3));
        series33.getData().add(new XYChart.Data<>("SU", 5));

        ChartData smoothChartData1 = new ChartData("Item 1", RND.nextDouble() * 25, Tile.BLUE);
        ChartData smoothChartData2 = new ChartData("Item 2", RND.nextDouble() * 25, Tile.BLUE);
        ChartData smoothChartData3 = new ChartData("Item 3", RND.nextDouble() * 25, Tile.BLUE);
        ChartData smoothChartData4 = new ChartData("Item 4", RND.nextDouble() * 25, Tile.BLUE);

        Tile areaChartTile = TileBuilder.create()
                                        .skinType(Tile.SkinType.SMOOTHED_CHART)
                                        .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                        .title("SmoothedChart Tile")
                                        .chartType(Tile.ChartType.AREA)
                                        .animated(true)
                                        .smoothing(true)
                                        .tooltipTimeout(1000)
                                        .tilesFxSeries(new TilesFXSeries<>(series11,
                                                                           Tile.BLUE,
                                                                           new LinearGradient(0, 0, 0, 1,
                                                                                              true, CycleMethod.NO_CYCLE,
                                                                                              new Stop(0, Tile.BLUE),
                                                                                              new Stop(1, Color.TRANSPARENT))))
                                        .build();


        Tile smoothAreaChartTile = TileBuilder.create().skinType(Tile.SkinType.SMOOTH_AREA_CHART)
                                              .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                              .minValue(0)
                                              .maxValue(40)
                                              .title("SmoothAreaChart Tile")
                                              .unit("Unit")
                                              .text("Test")
                                              //.chartType(ChartType.LINE)
                                              //.dataPointsVisible(true)
                                              .chartData(smoothChartData1, smoothChartData2, smoothChartData3, smoothChartData4)
                                              .tooltipText("")
                                              .animated(true)
                                              .build();



        Tile lineChartTile = TileBuilder.create()
                                        .skinType(Tile.SkinType.SMOOTHED_CHART)
                                        .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                        .title("SmoothedChart Tile")
                                        .animated(true)
                                        .smoothing(false)
                                        .series(series22, series33)
                                        .build();


        LeaderBoardItem leaderBoardItem1 = new LeaderBoardItem("Gerrit", 47);
        LeaderBoardItem leaderBoardItem2 = new LeaderBoardItem("Sandra", 43);
        LeaderBoardItem leaderBoardItem3 = new LeaderBoardItem("Lilli", 12);
        LeaderBoardItem leaderBoardItem4 = new LeaderBoardItem("Anton", 8);


        Tile leaderBoardTile = TileBuilder.create()
                                          .skinType(Tile.SkinType.LEADER_BOARD)
                                          .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                          .title("LeaderBoard Tile")
                                          .text("Whatever text")
                                          .leaderBoardItems(leaderBoardItem1, leaderBoardItem2, leaderBoardItem3, leaderBoardItem4)
                                          .build();

        Tile sparkLineTile = TileBuilder.create()
                                        .skinType(Tile.SkinType.SPARK_LINE)
                                        .prefSize(TILE_WIDTH, TILE_HEIGHT)
                                        .title("SparkLine Tile")
                                        .unit("mb")
                                        .gradientStops(new Stop(0, Tile.GREEN),
                                                       new Stop(0.5, Tile.YELLOW),
                                                       new Stop(1.0, Tile.RED))
                                        .strokeWithGradient(true)
                                        .smoothing(true)
                                        .build();

        //value = new SimpleDoubleProperty(0);
        //sparkLineTile.valueProperty().bind(value);

        fgp.setNoOfCols(3);
        fgp.setNoOfRows(2);
        fgp.add(percentageTile, 1, 1);
        fgp.add(barChartTile, 2, 1);
        fgp.add(areaChartTile, 3, 1);
        fgp.add(lineChartTile, 1, 2);
        fgp.add(leaderBoardTile, 2, 2);
        fgp.add(sparkLineTile, 3, 2);

        lastTimeCall = System.nanoTime();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now)
            {
                if (now > lastTimeCall + 3_500_000_000L)
                {
                    percentageTile.setValue(RND.nextDouble() * percentageTile.getRange() * 1.5 + percentageTile.getMinValue());

                    barChartTile.getBarChartItems().get(RND.nextInt(3)).setValue(RND.nextDouble() * 80);

                    series11.getData().forEach(data -> data.setYValue(RND.nextInt(100)));
                    series22.getData().forEach(data -> data.setYValue(RND.nextInt(30)));
                    series33.getData().forEach(data -> data.setYValue(RND.nextInt(10)));

                    smoothChartData1.setValue(smoothChartData2.getValue());
                    smoothChartData2.setValue(smoothChartData3.getValue());
                    smoothChartData3.setValue(smoothChartData4.getValue());
                    smoothChartData4.setValue(RND.nextDouble() * 25);

                    leaderBoardTile.getLeaderBoardItems().get(RND.nextInt(3)).setValue(RND.nextDouble() * 80);

                    sparkLineTile.setValue(RND.nextDouble() * sparkLineTile.getRange() * 1.5 + sparkLineTile.getMinValue());
                    //value.set(RND.nextDouble() * sparkLineTile.getRange() * 1.5 + sparkLineTile.getMinValue());
                    //sparkLineTile.setValue(20);

                    lastTimeCall = now;
                }
            }
        };
        timer.start();
    }
}
