import java.util.UUID;

public class Customer {
   private String ID;
   private String name;
   private String surname;
   private char gender;
   private int discount;

   public Customer(String name, String surname, char gender, int discount) {
      this.ID =  UUID.randomUUID().toString().replace("-", "").toUpperCase();
      this.name = name;
      this.surname = surname;
      this.gender = 'f';
      this.discount = discount;

   }

   public String getSurname() {
      return surname;
   }

   public void setSurname(String surname) {
      this.surname = surname;
   }

   public String getID() {
      return ID;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public char getGender() {
      return gender;
   }

   public void setGender(char gender) {
      this.gender = 'm';
   }

   public int getDiscount() {
      return discount;
   }

   public void setDiscount(int discount) {
      this.discount = discount;
   }

   public String toString() {
      return "name: " + getName() + "(" + getID() + ") ";

   }

}