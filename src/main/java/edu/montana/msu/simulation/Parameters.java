/**
 * 
 */
package edu.montana.msu.simulation;

import org.apache.commons.math3.distribution.NormalDistribution;

/**
 * @author Rachael Luhr, Ryan Nix, Kathryn Manning
 *
 */
public class Parameters {
	public static final double HEARTBEAT = 0.25;//250 ms
	public static final NormalDistribution SPEED = new NormalDistribution(33.5, 2.7); //mps mean of 75 mph, sd of 6 mph
	public static final double BROADCASTDISTANCE = 300.00; //meters
	public static final double TIMESTEP = 0.001; //This must be >1 to workiva
	public static final double RTLRESENDTIME = HEARTBEAT;
    public static final NormalDistribution TRAFFIC = new NormalDistribution(1, 1);//TODO; fill me in
}
