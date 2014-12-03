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
    public static final int DURATION = 600000; // at .001 timestep is 10 minutes of data.
    public static final double NORMALHEARTBEATFREQ = 0.25;
    public static final double LOWHEARTBEATFREQ = 0.15;
    public static final double HIGHHEARTBEATFREQ = 0.35;//250 ms
    public static final int REPLICATIONS = 3;
    public static final NormalDistribution MEDIUMSPEED = new NormalDistribution(33.5, 2.7);//mps mean of 75 mph, sd of 6 mph
    public static final NormalDistribution HIGHSPEED = new NormalDistribution(38, 2.7);
    public static final NormalDistribution LOWSPEED = new NormalDistribution(29, 2.7);
	public static final double NORMALBROADCASTDISTANCE = 300; //meters
    public static final double LONGBROADCASTDISTANCE = 600; //meters
    public static final double LONGERBROADCASTDISTANCE = 900; //meters
    public static final double EXTREMBROADCASTDISTANCE = 1200; //meters

	public static final double TIMESTEP = .001; //This must be >1 to workiva
	public static final double RTLRESENDTIME = 0.25;
    public static final double HIGHTRAFFIC = 0.104; //% car per second
    public static final double LOWTRAFFIC = 0.042;
    public static final double MEDIUMTRAFFIC = 0.073;
    public static final UniformRealDistribution TRAFFICRATE = new UniformRealDistribution();
    public static final double DEADZONEDISTANCE = 1000;
    public static final double NORMALWAIT = 0.1;
    public static final double LOWWAIT = 0.15;
    public static final double HIGHWAIT = 0.05;

    public double BROADCASTDISTANCE = NORMALBROADCASTDISTANCE;
    public double DISTANCETODEADZONE = BROADCASTDISTANCE;
    public double TOTALDISTANCE = 2*DISTANCETODEADZONE + DEADZONEDISTANCE;
    public double HEARTBEAT = NORMALHEARTBEATFREQ;//250 ms
    public NormalDistribution SPEED = MEDIUMSPEED; //mps mean of 75 mph, sd of 6 mph
    public double TRAFFICCHANCE = MEDIUMTRAFFIC*TIMESTEP;
    public double DTFOWAITTIME = NORMALWAIT; //in seconds

    public String toString() {
        return "\n#BroadcastDistance: " +BROADCASTDISTANCE
               +"\n#DistanceToDeadzone: " +BROADCASTDISTANCE
               +"\n#DeadzoneSize" + DEADZONEDISTANCE
               +"\n#TotalDistance: " +TOTALDISTANCE
               +"\n#Heartbeat: " +HEARTBEAT
               +"\n#Speed: " +SPEED.getMean()
               +"\n#TrafficChance: "+(TRAFFICCHANCE/TIMESTEP)
               +"\nDTFOWaitTime: " + DTFOWAITTIME;
    }
}

