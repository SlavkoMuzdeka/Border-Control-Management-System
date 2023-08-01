package model;

import java.io.Serializable;
import java.util.Arrays;

public class Entrance implements Serializable {

	private static final long serialVersionUID = 2586504524548016279L;
	
	private byte[] data;
	private Integer id;
	private Integer personId;
	private String fileName;
	private Boolean isAvailable;
	private Boolean isPoliceControlAvailable;
	private Boolean isCustomControlAvailable;
	
	public Entrance() {
		super();
	}

	public Entrance(byte[] data, Integer id, Integer personId, String fileName, Boolean isAvailable,
			Boolean isPoliceControlAvailable, Boolean isCustomControlAvailable) {
		super();
		this.data = data;
		this.id = id;
		this.personId = personId;
		this.fileName = fileName;
		this.isAvailable = isAvailable;
		this.isPoliceControlAvailable = isPoliceControlAvailable;
		this.isCustomControlAvailable = isCustomControlAvailable;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Boolean getIsPoliceControlAvailable() {
		return isPoliceControlAvailable;
	}

	public void setIsPoliceControlAvailable(Boolean isPoliceControlAvailable) {
		this.isPoliceControlAvailable = isPoliceControlAvailable;
	}

	public Boolean getIsCustomControlAvailable() {
		return isCustomControlAvailable;
	}

	public void setIsCustomControlAvailable(Boolean isCustomControlAvailable) {
		this.isCustomControlAvailable = isCustomControlAvailable;
	}

	@Override
	public String toString() {
		return "Entrance [data=" + Arrays.toString(data) + ", id=" + id + ", personId=" + personId + ", fileName="
				+ fileName + ", isAvailable=" + isAvailable + ", isPoliceControlAvailable=" + isPoliceControlAvailable
				+ ", isCustomControlAvailable=" + isCustomControlAvailable + "]";
	}
	
}
