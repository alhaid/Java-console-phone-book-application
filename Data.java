package assign;

public class Data {
	private String number;
	private String name;
	private String address;
	
	public Data() {
		super();
	}

	public Data(String number, String name, String address) {
		super();
		this.number = number;
		this.name = name;
		this.address = address;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
