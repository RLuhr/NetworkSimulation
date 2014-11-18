package edu.montana.msu;

import edu.montana.msu.simulation.Simulator;

import org.apache.commons.math3.distribution.UniformRealDistribution;

/**
 * @author Rachael Luhr, Ryan Nix, Kathryn Manning
 *
 */
public class Main{
	public static void main(String args[]) {
		Simulator sim = new Simulator();
		sim.run();
	}
}