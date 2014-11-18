/**
 * 
 */
package edu.montana.msu.simulation;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;

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
    public static final double HIGHTRAFFIC = 0.104; //% car per second
    public static final double LOWTRAFFIC = 0.042;
    public static final double TRAFFICCHANCE = HIGHTRAFFIC*TIMESTEP;
    public static final UniformRealDistribution TRAFFICRATE = new UniformRealDistribution();
}
