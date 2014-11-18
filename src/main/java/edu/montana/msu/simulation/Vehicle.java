/**
 * 
 */
package edu.montana.msu.simulation;

import java.util.*;

import edu.montana.msu.Tuple;

/**
 * @author Rachael Luhr, Ryan Nix, Kathryn Manning
 *
 */
public class Vehicle implements Agent{

	private Queue<Message> messageQueue;
	private double velocity;
	private Tuple<Double,Double> location;
	private int id;
	private int parent;
	private List<Integer> children;
	private double timeSinceHeartbeat;
	private boolean hasService;
	private double timeToHeartbeat;
	private double timeSinceRTL;
	private Map<Message, Double> sentMessageDuration;
    private boolean connected; //NOT TO BE CONFUSED WITH PARENT, THIS SHOWS ACTUAL CONNECTION, PARENT SHOWS EXPECTED CONNECTION
	private Simulator sim;
    private double timeWithoutConnection;

	public Vehicle(Tuple<Double, Double> loc, double vel, int id, Simulator sim) {
		this.messageQueue = new LinkedList<Message>();
		this.location = loc;
		this.velocity = vel;
		this.id = id;
		this.parent = -1;
		this.children = new LinkedList<Integer>();
		this.timeSinceHeartbeat = 0;
		this.hasService = true;
		this.timeToHeartbeat = Parameters.HEARTBEAT;
		this.timeSinceRTL = -1; //this allows for instant sendoff of RTL when connection is lost
        this.sentMessageDuration = new HashMap<Message, Double>();
        this.sim = sim;
        this.connected = false;
        this.timeWithoutConnection = 0;
        this.hasService = true;
	}
	
	/* (non-Javadoc)
	 * @see edu.montana.msu.simulation.Agent#update(double)
	 */
	@Override
	public void update(double timestep, Road road) {
		//This should update position information 
		//as well as add any action that needs to be set in the queue
		
		//update location and timing info.
		this.location = road.newPosition(location, velocity*timestep);
        if(this.location.x > Parameters.DISTANCETODEADZONE) {
            this.hasService = false;
        }
        updateWaitTimes(timestep);

		//If you have children, and its time to heartbeat, do it
		if ((this.timeToHeartbeat <= 0) && (children.size() > 0)) {
			this.messageQueue.add(new Message(MessageType.HEARTBEAT, generateId(), this.id, -1, true));
		}

        if (!this.hasService) {
            //Check if parent heartbeat times out, if so then notify children of disconnect, and add RTL
            if(this.timeSinceHeartbeat > Parameters.HEARTBEAT) {
                //we have disconnected from the network!
                this.parent = -1;
                this.sendRTL();
                this.timeWithoutConnection = 0;
            }

            //if you have children and you wait an appropriate time without connection, DTFO
            if ((this.children.size() > 0) && (Parameters.DTFOWAITTIME > this.timeWithoutConnection)) {
                this.messageQueue.add(new Message(MessageType.DTFO, generateId(), this.id, -1, false)); //ORDER MATTERS
                this.children.clear();
            }

            //If a heartbeat goes by without confirmation of message forward, resend message
            this.checkMessageTimings();

            //If RTL has not been ok'd by the RTL send time, and no parent exists, reattempt RTL
            if ((this.timeSinceRTL > Parameters.RTLRESENDTIME) && (this.parent == -1)) {
                this.sendRTL();
            }
        }
        if (this.parent != -1) {
            this.connected = sim.isConnected(this.id, this.parent);
        }

	}

    private void checkMessageTimings() {
        for (Message m: sentMessageDuration.keySet()) {
            if ((sentMessageDuration.get(m) > Parameters.HEARTBEAT) && (this.parent != -1)){
                this.messageQueue.add(m);
                sentMessageDuration.put(m, 0.0);
            }
        }
    }


    private void updateWaitTimes(double timestep) {
        if (this.timeSinceRTL != -1) {
            this.timeSinceRTL += timestep;
        }
        for (Message m: sentMessageDuration.keySet()) {
            double time = sentMessageDuration.get(m);
            sentMessageDuration.put(m, time + timestep);
        }
        this.timeSinceHeartbeat += timestep;
        if(this.timeToHeartbeat > 0) {
            this.timeToHeartbeat -= timestep;
        }
        if ((this.parent == -1) && (this.hasService == false)) {
            this.timeWithoutConnection += timestep;
        }
    }
	
	private void sendRTL() {
		this.messageQueue.add(new Message(MessageType.RTL, generateId(), this.id, -1, true));
		this.timeSinceRTL = 0;
	}

	/* (non-Javadoc)
	 * @see edu.montana.msu.simulation.Agent#getAction()
	 */
	@Override
	public Message getMessage() {
		return messageQueue.poll();
	}
	
	@Override
	public void sendMessage() {
        //send an empty message to test the simulation
        Message m = new Message(MessageType.REGULARMESSAGE, generateId(), this.id, -1, true);
        m.setBroadcaster(this.id);
        this.messageQueue.add(m);
        this.sentMessageDuration.put(m, 0.0);
    }

    private void rebroadcast(Message m) {
        m.setBroadcaster(this.id);
        this.sentMessageDuration.put(m, 0.0);
        this.messageQueue.add(m);
    }

    private void returnACK(Message m) {
        Message newM = new Message(MessageType.REGULARMESSAGE, generateId(), -1, m.origin(), false);
        m.setBroadcaster(this.id);
        this.messageQueue.add(newM);
        this.sentMessageDuration.put(newM, 0.0);
    }

	/* (non-Javadoc)
	 * @see edu.montana.msu.simulation.Agent#id()
	 */
	@Override
	public int id() {
		// TODO Auto-generated method stub
		return this.id;
	}

	/* (non-Javadoc)
	 * @see edu.montana.msu.simulation.Agent#location()
	 */
	@Override
	public Tuple<Double, Double> location() {
		return this.location;
	}

	/* (non-Javadoc)
	 * @see edu.montana.msu.simulation.Agent#receive(edu.montana.msu.simulation.Message)
	 */
	@Override
	public void receive(Message m) {
		switch (m.type()) {
			case RTL:
				receiveRTL(m);
				break;
			case OKTL:
				receiveOKTL(m);
				break;
			case XTLEXCLAMATIONPOINT:
				receiveXTL(m);
				break;
			case HEARTBEAT:
				receiveHeartbeat(m);
				break;
			case OUTOFSERVICE:
				receiveOutOfService();
				break;
			case REGAINSERVICE:
				receiveRegainService();
				break;
			case REGULARMESSAGE:
				receiveRegularMessage(m);
				break;
			case DTFO:
				receivedDTFO();
			default: 
				//log something terrible happened.
				break;
		}
		
	}
	private void receivedDTFO() {
		this.parent = -1;
        this.connected = false;
		this.sendRTL();
	}
	private void receiveRTL(Message m) {
		this.messageQueue.add(new Message(MessageType.OKTL, generateId(), this.id, m.origin(), false));
	}
	private void receiveOKTL(Message m) {
		//update personal map for parent
		this.parent = m.origin();
        this.connected = true;
		this.timeSinceHeartbeat = 0;
	}
	private void receiveXTL(Message m) {
		this.children.add(m.origin());
	}
	private void receiveHeartbeat(Message m) {
        if (this.parent == m.origin()) {
            this.timeSinceHeartbeat = 0;
        }
	}

    private int generateId() {
        return (int)(Math.random() * 1000000); //may need to change this up
    }

    //could be a forward, message for me, or resend of previous attempt
	private void receiveRegularMessage(Message m) {
        if(this.children.contains(m.broadcaster())) {
            if (this.parent == -1) {
                this.returnACK(m);
            }
            else {
                this.rebroadcast(m);
            }
        }
        else if (this.parent == m.broadcaster()) {
            if (m.Up()) {
                for (Message oldM: this.sentMessageDuration.keySet()) {
                    if (oldM.id() == m.id()) {
                        sentMessageDuration.remove(oldM);
                    }
                }
            }
            else {
                this.rebroadcast(m);
            }
        }
        else if (this.id == m.destination()) {
            //it has reached the destination!
        }
        else {
            System.out.println("SOMETHING WENT TERRIBLY WRONG");
        }
	}
	private void receiveOutOfService() {
		this.sendRTL();
		this.hasService = false;
	}
	private void receiveRegainService() {
		this.hasService = true;
	}


    public String logInfo() {
        String info = "#Vehicle:\n"+this.id;
        info += "\n#Location:\n"+this.location;
        info += "\n#Parent:\n"+this.parent;
        info += "\n#Children:";
        for (int child:this.children) {
            info += "\n"+child;
        }
        info += "\n#MessageQueue:";
        for (Message m:this.messageQueue) { //this may destroy the message queue... check this
            info += "\n"+m.toString();
        }
        info += "\n#ShouldBeConnected:\n"+this.connected;

        return info;
    }
	
}
