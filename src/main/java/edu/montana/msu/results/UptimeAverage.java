package edu.montana.msu.results;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ryannix on 12/4/14.
 */
public class UptimeAverage implements IMetricsCalculator {

    @Override
    public Map<Integer, Double> calculate(File experimentDir) throws Exception{
        System.out.println("Generating average uptime for "+experimentDir);
        Map<Integer, List<Double>> averageUptime = new HashMap<Integer, List<Double>>();

        for (File experiment: experimentDir.listFiles()) {
            if (!experiment.isHidden()) {
                Map<Integer, Double> expAverage = calcUptime(experiment);
                for (int i : expAverage.keySet()) {
                    if (!averageUptime.containsKey(i)) {
                        averageUptime.put(i, new ArrayList<Double>());
                    }
                    averageUptime.get(i).add(expAverage.get(i));
                }
            }
        }

        Map<Integer, Double> totalAverage = new HashMap<Integer, Double>();
        for (int key : averageUptime.keySet()) {
            double sum = 0;
            for (double av: averageUptime.get(key)) {
                sum = sum + av;
            }
            sum = sum/averageUptime.get(key).size();
            totalAverage.put(key, sum);
        }
        return totalAverage;
    }

    private Map<Integer, Double> calcUptime(File experiment) throws Exception{
        //takes in a file, creates the average of each 1m distance bucket in terms of connected or not
        Map<Integer, List<Integer>> values = new HashMap<Integer, List<Integer>>();
        BufferedReader reader = new BufferedReader(new FileReader(experiment));
        int loc= 0;
        String line;
        boolean service = true;
        int connected = 0;
        while((line = reader.readLine()) != null) { //may need line assignment
            switch(line) {
                case "#Location:":
                    String l = reader.readLine();
                    loc = (int)Math.round(Double.parseDouble(l.substring(l.indexOf("(")+1, l.indexOf(","))));
                    break;
                case "#HasService:":
                    service = Boolean.parseBoolean(reader.readLine());
                    break;
                case "#ShouldBeConnected:":
                    switch (reader.readLine()) {
                        case "true":
                            connected = 1;
                            break;
                        default:
                            connected = 0;
                            break;
                    }
                    if(!service) {
                        if (values.containsKey(loc)) {
                            values.get(loc).add(connected);
                        }
                        else  {
                            List<Integer> nl = new ArrayList<Integer>();
                            nl.add(connected);
                            values.put(loc, nl);
                        }
                    }
                    break;
                default:
                    //do nothing
                    break;
            }
        }
        reader.close();
        Map<Integer, Double> average = new HashMap<Integer, Double>();
        for (int location: values.keySet()) {
            double sum = 0;
            for (int conn: values.get(location)) {
                sum = sum + conn;
            }
            double av = (sum/values.get(location).size());
            average.put(location, av);
        }
        return average;
    }

    public String toString() {
        return "UptimeAverage";
    }
}
