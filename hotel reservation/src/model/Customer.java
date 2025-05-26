package model;
import java.util.Objects;
import java.util.regex.Pattern;

public class Customer{
    private final String firstName;
    private final String lastName;
    private final String email;
    public Customer(String firstName,String lastName,String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        if (validateEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException();
        }
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getEmail(){
        return email;
    }
    private static boolean validateEmail(String email){
        String emailRegex = "^(.+)@(.+).(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    @Override
    public String toString() {
        return "Customer" +'\n'+
                "First Name: " + firstName + '\n' +
                "Last Name: " + lastName +'\n'+
                "Email: " + email + '\n';
    }
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Customer)) return false;
        Customer customer =(Customer) o;
        return Objects.equals(firstName, customer.firstName) &&
                Objects.equals(lastName, customer.lastName) &&
                Objects.equals(email, customer.email);
    }
    @Override
    public int hashCode(){
        return Objects.hash(firstName,lastName,email);
    }
}