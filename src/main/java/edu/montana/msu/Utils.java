/**
 * 
 */
package edu.montana.msu;


import edu.montana.msu.simulation.Agent;
import edu.montana.msu.simulation.Simulator;

import java.io.File;
import java.io.IOException;
import java.lang.Math;
import java.util.logging.*;

/**
 * @author Rachael Luhr, Ryan Nix, Kathryn Manning
 *
 */
public class Utils {

    private static Logger logger = Logger.getLogger("SimulationLog");
    private static FileHandler fh;


	public static double distance(Tuple<Double, Double> loc1, Tuple<Double, Double> loc2) {
		return Math.sqrt(Math.pow(loc1.x+loc2.x, 2) + Math.pow(loc1.y+loc2.y, 2)); 
	}

    public static void setupLog(String name) {
        try {
            // This block configure the logger with handler and formatter
            fh = new FileHandler(name+".log", true);
            logger.setUseParentHandlers(false);
            fh.setFormatter(new SimpleFormatter() {
                public String format(LogRecord record) {
                    return record.getMessage() + "\n";
                }
            });
            logger.addHandler(fh);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void log(String input) {
        try {
//            System.out.println("LOGGING :"+input);
//            logger.info(input);
            logger.info(input);

        } catch (SecurityException e) {
            e.printStackTrace();
        } finally {
            fh.flush();
        }
    }

    public static void closeLog() {
        if (fh != null) {
            fh.close();
            fh = null;
        }
    }

}
