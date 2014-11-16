/**
 * 
 */
package edu.montana.msu;


import edu.montana.msu.simulation.Agent;
import edu.montana.msu.simulation.Simulator;

import java.io.IOException;
import java.lang.Math;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * @author Rachael Luhr, Ryan Nix, Kathryn Manning
 *
 */
public class Utils {

    private static Logger logger = Logger.getLogger("SimulationLog");


	public static double distance(Tuple<Double, Double> loc1, Tuple<Double, Double> loc2) {
		return Math.sqrt(Math.pow(loc1.x+loc2.x, 2) + Math.pow(loc1.y+loc2.y, 2)); 
	}

    public static void log(Agent a) {
        FileHandler fh;
        try {

            // This block configure the logger with handler and formatter
            fh = new FileHandler("SimulationLogfile.log", true);
            logger.addHandler(fh);

            logger.info(a.logInfo());

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log(Simulator sim) {

    }
}
