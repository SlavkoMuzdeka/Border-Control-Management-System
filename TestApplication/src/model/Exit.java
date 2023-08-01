package model;

import java.io.Serializable;
import java.util.Arrays;

public class Exit implements Serializable {

	private static final long serialVersionUID = 1630586725120884274L;

	private byte[] data;
	private Integer id;
	private Integer personId;
	private String fileName;
	private Boolean isAvailable;
	private Boolean isPoliceControlAvailable;
	private Boolean isCustomsControlAvailable;

	public Exit() {
		super();
	}

	public Exit(byte[] data, Integer id, Integer personId, String fileName, Boolean isAvailable,
			Boolean isPoliceControlAvailable, Boolean isCustomsControlAvailable) {
		super();
		this.data = data;
		this.id = id;
		this.personId = personId;
		this.fileName = fileName;
		this.isAvailable = isAvailable;
		this.isPoliceControlAvailable = isPoliceControlAvailable;
		this.isCustomsControlAvailable = isCustomsControlAvailable;
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

	public Boolean getIsCustomsControlAvailable() {
		return isCustomsControlAvailable;
	}

	public void setIsCustomsControlAvailable(Boolean isCustomsControlAvailable) {
		this.isCustomsControlAvailable = isCustomsControlAvailable;
	}

	@Override
	public String toString() {
		return "Exit [data=" + Arrays.toString(data) + ", id=" + id + ", personId=" + personId + ", fileName="
				+ fileName + ", isAvailable=" + isAvailable + ", isPoliceControlAvailable=" + isPoliceControlAvailable
				+ ", isCustomsControlAvailable=" + isCustomsControlAvailable + "]";
	}

}