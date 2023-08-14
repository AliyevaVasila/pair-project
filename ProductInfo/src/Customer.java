public class Customer {
    private String ID;
    private String name;
    private String surname;
    private char gender;
    private int discount;

    public Customer(String name, String surname, char gender, int discount) {
        this.ID = Util.getRandomString();
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.discount = discount;

    }



   public Customer(String iD, String name, String surname, char gender, int discount) {
        ID = iD;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.discount = discount;
    }



public String parseTo() {
      return ID + "," + name + "," + surname + "," + gender + "," + discount;
   }

   public String parseTo(Customer customerInstance) {
      return customerInstance.parseTo();
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
        this.gender = gender;
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