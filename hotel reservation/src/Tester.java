import model.Customer;

public class Tester {
    public static void main(String[] args) {
        Customer customer = new Customer("first", "second","name@email.com");
        System.out.println(customer);
    }
}
