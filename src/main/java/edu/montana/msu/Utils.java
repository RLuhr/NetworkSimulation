/**
 * 
 */
package edu.montana.msu;


import java.io.File;
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
		return Math.sqrt(Math.pow(loc1.x-loc2.x, 2) + Math.pow(loc1.y-loc2.y, 2));
	}

    public static void setupLog(String name, int version) {
        try {
            // This block configure the logger with handler and formatter

            new File("results/"+name+"/").mkdirs();
            fh = new FileHandler("results/"+name+"/"+name+"_run"+version+".log", false);
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

    public static void log(Object l) {
        log(l.toString());
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
