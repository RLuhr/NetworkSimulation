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
            Utils.setupLog("AI_SI_HI_DI",i);
            Simulator sim = new Simulator(param);
            sim.run();
            Utils.closeLog();
        }

        //EXP set 1: change vehicle arrival chance
        System.out.println("ARRIVAL exp set");
        param.TRAFFICCHANCE = Parameters.HIGHTRAFFIC*Parameters.TIMESTEP;
        for (int i = 0; i < Parameters.REPLICATIONS; i++ ) {
            Utils.setupLog("AH_SI_HI_DI",i);
            Simulator sim = new Simulator(param);
            sim.run();
            Utils.closeLog();
        }
        param.TRAFFICCHANCE = Parameters.LOWTRAFFIC*Parameters.TIMESTEP;
        for (int i = 0; i < Parameters.REPLICATIONS; i++ ) {
            Utils.setupLog("AL_SI_HI_DI",i);
            Simulator sim = new Simulator(param);
            sim.run();
            Utils.closeLog();
        }

        param = new Parameters();
        System.out.println("SPEED exp set");
        //Exp set 2: change vehicle speed distribution
        param.SPEED = Parameters.HIGHSPEED;
        for (int i = 0; i < Parameters.REPLICATIONS; i++ ) {
            Utils.setupLog("AI_SH_HI_DI",i);
            Simulator sim = new Simulator(param);
            sim.run();
            Utils.closeLog();
        }
        param.SPEED = Parameters.LOWSPEED;
        for (int i = 0; i < Parameters.REPLICATIONS; i++ ) {
            Utils.setupLog("AI_SL_HI_DI",i);
            Simulator sim = new Simulator(param);
            sim.run();
            Utils.closeLog();
        }

        param = new Parameters();
        //Exp set 3: change heartbeat freq
        System.out.println("HEARTBEAT exp set");
        param.HEARTBEAT = Parameters.HIGHHEARTBEATFREQ;
        for (int i = 0; i < Parameters.REPLICATIONS; i++ ) {
            Utils.setupLog("AI_SI_HH_DI",i);
            Simulator sim = new Simulator(param);
            sim.run();
            Utils.closeLog();
        }
        param.HEARTBEAT = Parameters.LOWHEARTBEATFREQ;
        for (int i = 0; i < Parameters.REPLICATIONS; i++ ) {
            Utils.setupLog("AI_SI_HL_DI",i);
            Simulator sim = new Simulator(param);
            sim.run();
            Utils.closeLog();
        }

        param = new Parameters();
        //Exp set 4: change DTFO wait time
        System.out.println("WAITTIME exp set");
        param.DTFOWAITTIME = Parameters.HIGHWAIT;
        for (int i = 0; i < Parameters.REPLICATIONS; i++ ) {
            Utils.setupLog("AI_SI_HI_DH,",i);
            Simulator sim = new Simulator(param);
            sim.run();
            Utils.closeLog();
        }
        param.DTFOWAITTIME = Parameters.LOWWAIT;
        for (int i = 0; i < Parameters.REPLICATIONS; i++ ) {
            Utils.setupLog("AI_SI_HI_DL",i);
            Simulator sim = new Simulator(param);
            sim.run();
            Utils.closeLog();
        }
	}
}