/**
 * 
 */
package edu.montana.msu.simulation;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import edu.montana.msu.Tuple;
import edu.montana.msu.simulation.Message;

import edu.montana.msu.simulation.MessageType;

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
	private Message sentMessage;
	
	public Vehicle(Tuple<Double, Double> loc, double vel, int id) {
		this.messageQueue = new LinkedList<Message>();
		this.location = loc;
		this.velocity = vel;
		this.id = id;
		this.parent = -1;
		this.children = new LinkedList<Integer>();
		this.timeSinceHeartbeat = 0;
		this.hasService = true;
		this.timeToHeartbeat = Parameters.HEARTBEAT;
		this.timeSinceRTL = -1;
	}
	
	/* (non-Javadoc)
	 * @see edu.montana.msu.simulation.Agent#update(double)
	 */
	@Override
	public void update(double timestep, Road road) {
		//This should update position information 
		//as well as add any action that needs to be set in the queue
		
		//update location
		this.location = road.newPosition(location, velocity*timestep);
		this.timeSinceHeartbeat = this.timeSinceHeartbeat + timestep;
		
		//send out personal heartbeat
		if(this.timeToHeartbeat > 0) {
			this.timeToHeartbeat = this.timeToHeartbeat - timestep;
		}
		
		//update parent heartbeat info
		if(this.timeSinceHeartbeat > Parameters.HEARTBEAT) {
			//we have disconnected from the network!
			this.parent = -1;
			this.messageQueue.add(new Message(MessageType.DTFO, this.id, -1)); //ORDER MATTERS
			this.sendRTL();
		}
				
		//check for need to resend message
		
		
		//check for resending RTL
		if ((this.timeSinceRTL > Parameters.RTLRESENDTIME) && (this.parent != -1)) {
			this.sendRTL();
		}
		this.timeSinceRTL = this.timeSinceRTL + timestep;
	}
	
	private void sendRTL() {
		this.messageQueue.add(new Message(MessageType.RTL, this.id, -1));
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
		this.messageQueue.add(new Message(MessageType.REGULARMESSAGE, this.id, -1));
		this.timeSinceMessage
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
				receiveHeartbeat();
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
				receivedDTFO(m);
			default: 
				//log something terrible happened.
				break;
		}
		
	}
	private void receivedDTFO(Message m) {
		this.parent = -1;
		this.messageQueue.add(new Message(MessageType.RTL, this.id, -1));
	}
	private void receiveRTL(Message m) {
		this.messageQueue.add(new Message(MessageType.OKTL, this.id, m.sender()));
	}
	private void receiveOKTL(Message m) {
		//update personal map for parent
		this.parent = m.sender();
		this.timeSinceHeartbeat = 0;
	}
	private void receiveXTL(Message m) {
		this.children.add(m.sender());
	}
	private void receiveHeartbeat() {
		this.timeSinceHeartbeat = 0;
	}
	private void receiveRegularMessage(Message m) {
		if(this.children.contains(m.broadcaster()) || this.parent == m.broadcaster()) {
			m.setBroadcaster(this.id);
			this.messageQueue.add(m);
		} else if (this.id == m.destination()) {
			// message received! yay
			//log
		}
	}
	private void receiveOutOfService() {
		this.sendRTL();
		this.hasService = false;
	}
	private void receiveRegainService() {
		this.hasService = true;
	}

	
}
