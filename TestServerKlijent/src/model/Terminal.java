package model;

import java.io.Serializable;
import java.util.Arrays;

public class Terminal implements Serializable {

	private static final long serialVersionUID = 3228354955053099947L;
	
	private Boolean isBlocked;
	private Integer numberOfEntrances;
	private Integer numberOfExits;
	private Integer id;
	private Exit[] exits;
	private Entrance[] entrances;
	private String naziv;
	
	public Terminal() {
		super();
	}

	public Terminal(Boolean isBlocked, Integer numberOfEntrances, Integer numberOfExits, Integer id, Exit[] exits,
			Entrance[] entrances, String naziv) {
		super();
		this.isBlocked = isBlocked;
		this.numberOfEntrances = numberOfEntrances;
		this.numberOfExits = numberOfExits;
		this.id = id;
		this.exits = exits;
		this.entrances = entrances;
		this.naziv = naziv;
	}

	public Boolean getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(Boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public Integer getNumberOfEntrances() {
		return numberOfEntrances;
	}

	public void setNumberOfEntrances(Integer numberOfEntrances) {
		this.numberOfEntrances = numberOfEntrances;
	}

	public Integer getNumberOfExits() {
		return numberOfExits;
	}

	public void setNumberOfExits(Integer numberOfExits) {
		this.numberOfExits = numberOfExits;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Exit[] getExits() {
		return exits;
	}

	public void setExits(Exit[] exits) {
		this.exits = exits;
	}

	public Entrance[] getEntrances() {
		return entrances;
	}

	public void setEntrances(Entrance[] entrances) {
		this.entrances = entrances;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	@Override
	public String toString() {
		return "Terminal [isBlocked=" + isBlocked + ", numberOfEntrances=" + numberOfEntrances + ", numberOfExits="
				+ numberOfExits + ", id=" + id + ", exits=" + Arrays.toString(exits) + ", entrances="
				+ Arrays.toString(entrances) + ", naziv=" + naziv + "]";
	}

}
