package vo;

public class Resultdb {
	protected int 	id;
	protected String 	date;
	protected String 	customer;
	protected String 	counsellor;
	protected String 	result;
	protected double 	confidence;
	
	public int getid() {
		return id;
	}
	public Resultdb setid(int id) {
		this.id = id;
		return this;
	}
	
	
	public String getdate() {
		return date;
	}
	public Resultdb setdate(String date) {
		this.date = date;
		return this;
	}
	
	
	public String getcustomer() {
		return customer;
	}
	public Resultdb setcustomere(String customer) {
		this.customer = customer;
		return this;
	}
	
	
	public String getcounsellor() {
		return counsellor;
	}
	public Resultdb setcounsellor(String counsellor) {
		this.counsellor = counsellor;
		return this;
	}
	
	
	public String getresult() {
		return result;
	}
	public Resultdb setresult(String result) {
		this.result = result;
		return this;
	}
	
	
	public double getconfidence() {
		return confidence;
	}
	public Resultdb setconfidence(double confidence) {
		this.confidence = confidence;
		return this;
	}
}
