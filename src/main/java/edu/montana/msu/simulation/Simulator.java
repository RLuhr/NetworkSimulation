/**
 * 
 */
package edu.montana.msu.simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import edu.montana.msu.Tuple;
import edu.montana.msu.Utils;

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

    private Tuple<Double, Double> startingPoint = new Tuple<Double, Double>(0.0, 0.0);
    private Map<Integer, IAgent> agentMap = new HashMap<Integer, IAgent>();
    private Road road = new Road();
    private int maxId = 0;
    private List<Integer> removalList = new ArrayList<Integer>();
    public Parameters parameters;

    public Simulator(Parameters parameters) {
        this.parameters = parameters;
    }
	
	public void run() {
		int time = 0;
        boolean done = false;
        Utils.log(parameters);
		while(!done) {
            removalList.clear();
            Utils.log("#=================TIMESTEP: "+time);
            if (Parameters.TRAFFICRATE.sample() < parameters.TRAFFICCHANCE) {
                IAgent a = this.buildAgent();
                agentMap.put(a.id(), a);
                Utils.log("#Vehicle generated: " + a.id());
            }
			for (Integer agentId:agentMap.keySet()) {
				IAgent IAgent = agentMap.get(agentId);
				
				Message m = agentMap.get(agentId).getMessage();

                if (m != null) {
                    broadcast(m, IAgent);
                }

				if (!IAgent.update(Parameters.TIMESTEP, road)) {
                    removalList.add(agentId);
                }

                Utils.log(IAgent);
			}
			time++;
            if (time > Parameters.DURATION)  {
                done = true;
            }
            for (Integer a: removalList) {
                agentMap.remove(a);
            }
		}
	}
	
	private IAgent buildAgent() {
		this.maxId++;
		double vel = parameters.SPEED.sample();//normal distribution around speed limit. 75 mph.
		return new Vehicle(startingPoint, vel, this.maxId, this);
	}

    public boolean isConnected(int id1, int id2) {
        if (!agentMap.containsKey(id1) || !agentMap.containsKey(id2)) {
            return false;
        }
        return (Utils.distance(agentMap.get(id1).location(), agentMap.get(id2).location()) <= this.parameters.BROADCASTDISTANCE);
    }
	
	private void broadcast(Message message, IAgent origin) {
		for (Integer agentId: agentMap.keySet()) {
			IAgent newIAgent = agentMap.get(agentId);
			if (agentId != origin.id()) {
				if (Utils.distance(origin.location(), newIAgent.location()) < this.parameters.BROADCASTDISTANCE) {
					newIAgent.receive(message);
				}
			}
		}
	}


}
