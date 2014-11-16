/**
 * 
 */
package edu.montana.msu;


import edu.montana.msu.Tuple;
import java.lang.Math;
/**
 * @author Rachael Luhr, Ryan Nix, Kathryn Manning
 *
 */
public class Utils {
	public static double distance(Tuple<Double, Double> loc1, Tuple<Double, Double> loc2) {
		return Math.sqrt(Math.pow(loc1.x+loc2.x, 2) + Math.pow(loc1.y+loc2.y, 2)); 
	}
}
