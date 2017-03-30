package trajetMetro;

import java.awt.Color;

public class Station {
	
	private int id;
	private String nom;
	private int x;
	private int y;
	private Color c;
	private boolean ferme;
	
	public Station(int id,String n,int x,int y, Color c,boolean ferme){
		this.id = id;
		nom = n;
		this.x = x;
		this.y = y;
		this.c = c;
		this.ferme = ferme;
		
	}

	@Override
	public boolean equals(Object s){
		if (this == s) return true;
        if ( !(s instanceof Station) ) return false;
        final Station cat = (Station) s;
        if ( cat.getId() != getId() ) return false;
        return true;
	}
	@Override
    public int hashCode() {
        int result;
        result = getId();
        return result;
    }

	public String getNom() {
		return nom;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}

	public boolean isFerme() {
		return ferme;
	}

	public void setFerme(boolean ferme) {
		this.ferme = ferme;
	}

	@Override
	public String toString() {
		return "Station [id=" + id + ", nom=" + nom + ", x=" + x + ", y=" + y + ", c=" + c + ", ferme=" + ferme + "]";
	}

}