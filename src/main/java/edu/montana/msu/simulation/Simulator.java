/**
 * 
 */
package edu.montana.msu.simulation;

import java.util.HashMap;
import java.util.Map;

import com.sun.xml.internal.rngom.digested.DDataPattern;
import edu.montana.msu.simulation.Agent;
import edu.montana.msu.simulation.Message;
import edu.montana.msu.Tuple;
import edu.montana.msu.Utils;
import org.apache.commons.math3.distribution.NormalDistribution;
/**
 * @author Rachael Luhr, Ryan Nix, Kathryn Manning
 *
 */

//ASSUMPTIONS:
/*Units:
 * Meters
 * Seconds
 * Instantaneous broadcast
 * 
 */

public class Simulator {

	//Pull these into constants...
	private Tuple<Double, Double> startingPoint = new Tuple<Double, Double>(0.0, 0.0);
	private Map<Integer, Agent> agentMap = new HashMap<Integer, Agent>();
	private Road road = new Road();
	private int maxId = 0;
	
	public void run() {
		int time = 0;
        boolean done = false;
		while(!done) {
            Utils.log("#TIMESTEP: "+time);
            if (Parameters.TRAFFIC.sample() > 0.5) { //TODO: this needs fixing for the actual probability
                Agent a = this.buildAgent();
                agentMap.put(a.id(), a);
                Utils.log("#Vehicle generated: " + a.id());
            }
			for (Integer agentId:agentMap.keySet()) {
				Agent agent = agentMap.get(agentId);
				
				Message m = agentMap.get(agentId).getMessage();
				
				broadcast(m, agent);
				
				agent.update(Parameters.TIMESTEP, road);
                Utils.log(agent.logInfo());
			}
			time++;
            if (time > 10)  {
                done = true;
            }
		}
	}
	
	private Agent buildAgent() {
		this.maxId++;
		double vel = Parameters.SPEED.sample();//normal distribution around speed limit. 75 mph.
		return new Vehicle(startingPoint, vel, this.maxId, this);
	}

    public boolean isConnected(int id1, int id2) {
        return (Utils.distance(agentMap.get(id1).location(), agentMap.get(id2).location()) <= Parameters.BROADCASTDISTANCE);
    }
	
	private void broadcast(Message message, Agent origin) {
		for (Integer agentId: agentMap.keySet()) {
			Agent newAgent = agentMap.get(agentId);
			if (agentId != origin.id()) {
				if (Utils.distance(origin.location(), newAgent.location()) < Parameters.BROADCASTDISTANCE) {
					newAgent.receive(message);
				}
			}
		}
	}


}
