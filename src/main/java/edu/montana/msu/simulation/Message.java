/**
 * 
 */
package edu.montana.msu.simulation;

/**
 * @author Rachael Luhr, Ryan Nix, Kathryn Manning
 *
 */
public class Message {
	private MessageType typ;
	private int senderId;
	private int destination;
	private int broadcaster;
	
	public Message(MessageType typ, int senderId, int dest) {
		this.typ = typ;
		this.senderId = senderId;
		this.destination = dest;
	}
	
	public MessageType type() {
		return typ;
	}
	
	public int sender() {
		return this.senderId;
	}
	
	public int destination() {
		return this.destination;
	}
	
	public int broadcaster() {
		return this.broadcaster;
	}
	
	public void setBroadcaster(int id) {
		this.broadcaster = id;
	}
}
