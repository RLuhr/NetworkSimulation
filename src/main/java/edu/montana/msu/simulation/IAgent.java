/**
 * 
 */
package edu.montana.msu.simulation;

import edu.montana.msu.Tuple;
/**
 * @author Rachael Luhr, Ryan Nix, Kathryn Manning
 *
 */
public interface IAgent {
	
	public Message getMessage();
	
	public int id();
	
	public Tuple<Double, Double> location();

	public boolean update(double timestep, Road road);
	
	public void receive(Message m);
	
	public void sendMessage();

}
