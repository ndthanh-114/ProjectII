package project.web.dto;

public class DisplayProject {
	private int id;
	private String name;
	private String createDate;
	private String endDate;
	private int tong;
	private int daHoanThanh;
	
	public DisplayProject() {
		super();
	}
	
	
	
	public DisplayProject(int id,String name, int tong) {
		super();
		this.id = id;
		this.name = name;
		this.tong = tong;
		this.createDate="0";
		this.endDate="0";
		this.daHoanThanh=0;
	}



	public DisplayProject(int id, String name, String createDate, String endDate, int tong) {
		super();
		this.id =id;
		this.name = name;
		this.createDate = createDate;
		this.endDate = endDate;
		this.tong = tong;
		this.daHoanThanh=0;
	}
	public DisplayProject(int id, String name, String createDate, String endDate, int tong, int daHoanThanh) {
		super();
		this.id = id;
		this.name = name;
		this.createDate = createDate;
		this.endDate = endDate;
		this.tong = tong;
		this.daHoanThanh = daHoanThanh;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getTong() {
		return tong;
	}
	public void setTong(int tong) {
		this.tong = tong;
	}
	public int getDaHoanThanh() {
		return daHoanThanh;
	}
	public void setDaHoanThanh(int daHoanThanh) {
		this.daHoanThanh = daHoanThanh;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	@Override
	public String toString() {
		return "DisplayProject [id=" + id + ", name=" + name + ", createDate=" + createDate + ", endDate=" + endDate
				+ ", tong=" + tong + ", daHoanThanh=" + daHoanThanh + "]";
	}



	

	
	
	
}
