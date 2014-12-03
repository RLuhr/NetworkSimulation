package edu.montana.msu;

import edu.montana.msu.simulation.Parameters;
import edu.montana.msu.simulation.Simulator;

/**
 * @author Rachael Luhr, Ryan Nix, Kathryn Manning
 *
 */
public class Main{
	public static void main(String args[]) {

        System.out.println("INITIAL exp set");
        Parameters param = new Parameters();
        for (int i = 0; i < Parameters.REPLICATIONS; i++ ) {
            Utils.setupLog("Initial",i);
            Simulator sim = new Simulator(param);
            sim.run();
            Utils.closeLog();
        }

        //EXP set 1: change vehicle arrival chance
        System.out.println("ARRIVAL exp set");
        param.TRAFFICCHANCE = Parameters.HIGHTRAFFIC*Parameters.TIMESTEP;
        for (int i = 0; i < Parameters.REPLICATIONS; i++ ) {
            Utils.setupLog("ArrivalHigh",i);
            Simulator sim = new Simulator(param);
            sim.run();
            Utils.closeLog();
        }
        param.TRAFFICCHANCE = Parameters.LOWTRAFFIC*Parameters.TIMESTEP;
        for (int i = 0; i < Parameters.REPLICATIONS; i++ ) {
            Utils.setupLog("ArrivalLow",i);
            Simulator sim = new Simulator(param);
            sim.run();
            Utils.closeLog();
        }

        param = new Parameters();
        System.out.println("SPEED exp set");
        //Exp set 2: change vehicle speed distribution
        param.SPEED = Parameters.HIGHSPEED;
        for (int i = 0; i < Parameters.REPLICATIONS; i++ ) {
            Utils.setupLog("SpeedHigh",i);
            Simulator sim = new Simulator(param);
            sim.run();
            Utils.closeLog();
        }
        param.SPEED = Parameters.LOWSPEED;
        for (int i = 0; i < Parameters.REPLICATIONS; i++ ) {
            Utils.setupLog("SpeedLow",i);
            Simulator sim = new Simulator(param);
            sim.run();
            Utils.closeLog();
        }

        param = new Parameters();
        //Exp set 3: change heartbeat freq
        System.out.println("HEARTBEAT exp set");
        param.HEARTBEAT = Parameters.HIGHHEARTBEATFREQ;
        for (int i = 0; i < Parameters.REPLICATIONS; i++ ) {
            Utils.setupLog("HeartbeatHigh",i);
            Simulator sim = new Simulator(param);
            sim.run();
            Utils.closeLog();
        }
        param.HEARTBEAT = Parameters.LOWHEARTBEATFREQ;
        for (int i = 0; i < Parameters.REPLICATIONS; i++ ) {
            Utils.setupLog("HeartbeatLow",i);
            Simulator sim = new Simulator(param);
            sim.run();
            Utils.closeLog();
        }

        param = new Parameters();
        //Exp set 4: change DTFO wait time
        System.out.println("WAITTIME exp set");
        param.DTFOWAITTIME = Parameters.HIGHWAIT;
        for (int i = 0; i < Parameters.REPLICATIONS; i++ ) {
            Utils.setupLog("DTFOWaitTimeHigh",i);
            Simulator sim = new Simulator(param);
            sim.run();
            Utils.closeLog();
        }
        param.DTFOWAITTIME = Parameters.LOWWAIT;
        for (int i = 0; i < Parameters.REPLICATIONS; i++ ) {
            Utils.setupLog("DTFOWaitTimeLow",i);
            Simulator sim = new Simulator(param);
            sim.run();
            Utils.closeLog();
        }

        System.out.println("BROADCAST exp set");
        param.BROADCASTDISTANCE = Parameters.LONGBROADCASTDISTANCE;
        param.DISTANCETODEADZONE = param.BROADCASTDISTANCE;
        for (int i = 0; i < Parameters.REPLICATIONS; i++ ) {
                Utils.setupLog("BroadcastdistanceLong",i);
                Simulator sim = new Simulator(param);
                sim.run();
            Utils.closeLog();
        }

        param.BROADCASTDISTANCE = Parameters.LONGERBROADCASTDISTANCE;
        param.DISTANCETODEADZONE = param.BROADCASTDISTANCE;
        for (int i = 0; i < Parameters.REPLICATIONS; i++ ) {
            Utils.setupLog("BroadcastdistanceLonger",i);
            Simulator sim = new Simulator(param);
            sim.run();
            Utils.closeLog();
        }
        param.BROADCASTDISTANCE = Parameters.EXTREMBROADCASTDISTANCE;
        param.DISTANCETODEADZONE = param.BROADCASTDISTANCE;
        for (int i = 0; i < Parameters.REPLICATIONS; i++ ) {
            Utils.setupLog("BroadcastdistanceExtreme",i);
            Simulator sim = new Simulator(param);
            sim.run();
            Utils.closeLog();
        }
	}
}