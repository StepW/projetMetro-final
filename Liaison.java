package trajetMetro;

import java.awt.Color;

public class Liaison {
	
	private int A;
	private int B;
	private int temps;
	private int distance;
	private Color c;
	private boolean corresp;
	
	public Liaison(int A, int B, int temps,Color c, boolean corresp){
		this.A = A;
		this.B = B;
		this.temps = temps;
		this.c = c;
		this.corresp = corresp;
	}
	
	public int getA() {
		return A;
	}

	public int getB() {
		return B;
	}
	
	public int getTemps() {
		return temps;
	}

	public void setTemps(int temps) {
		this.temps = temps;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public boolean isCorresp() {
		return corresp;
	}

	public void setCorresp(boolean corresp) {
		this.corresp = corresp;
	}
		
	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}

	public int Distance(int a, int b, int c, int d){
		double A = (double) a;
		double B = (double) b;
		double C = (double) c;
		double D = (double) d;
		double L = Math.sqrt(Math.pow(Math.abs(A-C), 2)+Math.pow(Math.abs(B-D), 2));
		L *= 25.7 ;
		// 1 mètre éauivaux à environ 25.7 mètre
		return (int) L;

	}

	@Override
	public String toString() {
		return "Liaison [A=" + A + ", B=" + B + ", temps=" + temps + ", distance=" + distance + ", color=" + c + ", corresp=" + corresp  + "]";
	}
	
}
