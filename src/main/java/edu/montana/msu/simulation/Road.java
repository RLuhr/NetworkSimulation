/**
 * 
 */
package edu.montana.msu.simulation;

import edu.montana.msu.Tuple;

/**
 * @author Rachael Luhr, Ryan Nix, Kathryn Manning
 *
 */
public class Road {
	
	
//Assumptions: straight line, horizontal along the x axis. 
	//TODO: FIGURE OUT POSITIONAL UPDATE on curved roads
	public Tuple<Double, Double> newPosition(Tuple<Double, Double> oldPosition, double velocity) {
		return new Tuple<Double, Double>(oldPosition.x+velocity, oldPosition.y);
	}
}
