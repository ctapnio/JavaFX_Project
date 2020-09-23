/*
 * Christian Tapnio
 * 991-359-879
 * Project
 */
package christiantapnio_project.content;

public class Order {
    
    public Order(String orderID){
        this.orderID = orderID;
    }
    private final String orderID;
    private String customerID;
    private String product;
    private String shippingMethod;

    public String getOrderID() {
        return orderID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }
}
