package vo;


public class Exceldb {
	protected int 	id;
	protected String 	mresult;
	protected double 	prototypicality;
	protected double 	familiarity;
	protected double 	vitalization;
	protected double 	pleasant;
	protected String 	type;
	
	public int getid() {
		return id;
	}
	public Exceldb setid(int id) {
		this.id = id;
		return this;
	}
	
	
	public String getmresult() {
		return mresult;
	}
	public Exceldb setmresult(String mresult) {
		this.mresult = mresult;
		return this;
	}
	
	
	public double getprototypicality() {
		return prototypicality;
	}
	public Exceldb setprototypicality(double prototypicality) {
		this.prototypicality = prototypicality;
		return this;
	}
	
	
	public double getfamiliarity() {
		return familiarity;
	}
	public Exceldb setfamiliarity(double familiarity) {
		this.familiarity = familiarity;
		return this;
	}
	
	
	public double getvitalization() {
		return vitalization;
	}
	public Exceldb setvitalization(double vitalization) {
		this.vitalization = vitalization;
		return this;
	}
	
	
	public double getpleasant() {
		return pleasant;
	}
	public Exceldb setpleasant(double pleasant) {
		this.pleasant = pleasant;
		return this;
	}
	
	public String gettype() {
		return type;
	}
	public Exceldb settype(String type) {
		this.type = type;
		return this;
	}
}
