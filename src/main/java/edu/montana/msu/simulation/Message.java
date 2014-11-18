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
	private int originId;
	private int destination;
	private int broadcaster;
    private int id;
    private boolean up;
	
	public Message(MessageType typ, int id, int originId, int dest, boolean up) {
		this.typ = typ;
		this.originId = originId;
		this.destination = dest;
        this.id = id;
        this.up = up;
	}

    public int id() {
        return this.id;
    }
	
	public MessageType type() {
		return typ;
	}
	
	public int origin() {
		return this.originId;
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

    public boolean Up() {
        return this.up;
    }

    public String toString() {
        return typ+ " : " + id + " : " + originId + " : " + destination + " : " + broadcaster;
    }
}
