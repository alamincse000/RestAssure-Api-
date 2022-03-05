package TestRunner;

import Pages.Customer;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.Test;
//import org.testng.annotations.Test;

import java.io.IOException;


public class TestRunner {
    Customer customer;

@org.testng.annotations.Test(priority = 0)
    public void doLogin() throws ConfigurationException, IOException {
        customer = new Customer();
        customer.customerLoginApi();
    }
@org.testng.annotations.Test(priority = 1)
    public void getListApi() throws ConfigurationException, IOException {
        customer = new Customer();
        customer.customerListApi();

    }
    @org.testng.annotations.Test(priority = 2)
    public void getCustomerSearch() throws IOException {
        customer =new Customer();
        customer.searchCustomer();
    }
@org.testng.annotations.Test(priority = 3)
    public void generateCustomerInfo() throws IOException, ConfigurationException {
        customer=new Customer();
        customer.GenerateCustomer();

    }
   @org.testng.annotations.Test(priority = 4)
    public void createCustomerApi() throws IOException {
        customer =new Customer();
        customer.createCustomer();
    }
 @org.testng.annotations.Test(priority = 5)
    public void customerUpdateApi() throws IOException {
        customer =new Customer();
        customer.customerUpdate();
    }
 @Test(priority = 6)
    public void customerDeleteApi() throws IOException {
        customer = new Customer();
        customer.customerDelete();
    }

}
