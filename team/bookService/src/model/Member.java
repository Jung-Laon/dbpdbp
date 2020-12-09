package model;

public class Member {
   private String memberID;
   private String password; //�ϴ� id,pw int�� �ص����� string���� �ٲ� ����.
   private String name;
   private String email;
   private String phone;
   private int memberGrade;
   private Integer sellerGrade;
   private String address;
   private int point;
   private int gender;
   
   public Member() {
      super();
   }

   public Member(String memberID, String password, String name, String email, String phone, int gender, int memberGrade, int sellerGrade,
	         String address, int point) {
	      super();
	      this.memberID = memberID;
	      this.password = password;
	      this.name = name;
	      this.email = email;
	      this.phone = phone;
	      this.gender = gender;
	      this.memberGrade = memberGrade;
	      this.sellerGrade = sellerGrade;
	      this.address = address;
	      this.point = point;
	   }
	   
	   public Member(String memberID, String name, String email, String phone, String password, int gender, 
		         String address) {
		      super();
		      this.memberID = memberID;
		      this.password = password;
		      this.name = name;
		      this.email = email;
		      this.phone = phone;
		      this.gender = gender;
		      this.address = address;
		      
		      this.memberGrade = 0;
		      this.sellerGrade = null;
		      this.point = 0;
		   }
   
 public Member(String memberID, String name, String email, String phone,  
	         String address) {
	      super();
	      this.memberID = memberID;
	      this.name = name;
	      this.email = email;
	      this.phone = phone;
	      this.address = address;
	   } //��� ����Ʈ�� ���� �����ü
   
 


   public String getMemberID() {
      return memberID;
   }

   public void setMemberID(String memberID) {
      this.memberID = memberID;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPhone() {
      return phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public int getMemberGrade() {
      return memberGrade;
   }

   public void setMemberGrade(int memberGrade) {
      this.memberGrade = memberGrade;
   }

   public int getSellerGrade() {
      return sellerGrade;
   }

   public void setSellerGrade(int sellerGrade) {
      this.sellerGrade = sellerGrade;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public int getPoint() {
      return point;
   }

   public void setPoint(int point) {
      this.point = point;
   }
   
   public int getGender() {
	   return gender;
   }
   public void setGender(int gender) {
	   this.gender = gender;
   }
   
}