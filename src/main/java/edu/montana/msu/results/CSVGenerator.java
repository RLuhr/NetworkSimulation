package edu.montana.msu.results;

import java.io.*;
import java.util.*;

/**
 * Created by ryannix on 12/4/14.
 */
public class CSVGenerator {
    public static void main(String []args) {
        File resultsDir = new File("results/");
        List<IMetricsCalculator> calculators = new ArrayList<IMetricsCalculator>();
        calculators.add(new UptimeAverage());

        try {
            for (File experimentDir : resultsDir.listFiles()) {
                if (experimentDir.isDirectory()) {
                    for (IMetricsCalculator metricGen : calculators) {
                        Map<Integer, Double> results = metricGen.calculate(experimentDir);
                        printToCSV(results, experimentDir, metricGen.toString());
                    }
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static void printToCSV(Map<Integer, Double> results, File directory, String metric) {
        try {
            PrintWriter writer = new PrintWriter(directory+"/"+metric+".csv", "UTF-8");
            for (int key: results.keySet()) {
                writer.println(key+", "+results.get(key));
            }
            writer.flush();
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
