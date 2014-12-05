package edu.montana.msu.results;

import java.io.File;
import java.util.Map;

/**
 * Created by ryannix on 12/4/14.
 */
public interface IMetricsCalculator {
    public Map<Integer, Double> calculate(File file) throws Exception;
}
