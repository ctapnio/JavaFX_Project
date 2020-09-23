/*
 * Christian Tapnio
 * 991-359-879
 * Project
 */
package christiantapnio_project.content;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class OrderFile {

   public static ArrayList<Order> getRecords() throws IOException{
        ArrayList<Order> orderList = new ArrayList<>();
        
        FileReader fr = new FileReader("Order.dat");
        BufferedReader br = new BufferedReader(fr);
        
        String line = br.readLine();
        while(line != null){
            StringTokenizer st = new StringTokenizer(line, ",");
            
            String orderID = st.nextToken();
            String customerID = st.nextToken();
            String product = st.nextToken();
            String shippingMethod = st.nextToken();
            
            Order one = new Order(orderID);
            one.setCustomerID(customerID);
            one.setProduct(product);
            one.setShippingMethod(shippingMethod);
            
            orderList.add(one);
            
            line = br.readLine();
            
        }
        
        br.close();
        fr.close();
        return orderList;
    }
    public static void setRecords (ArrayList <Order> orderList) throws IOException{
        //writes data to .dat with the arraylist parameter
       
        FileWriter fw = new FileWriter("Order.dat");
        BufferedWriter bw = new BufferedWriter(fw);        
        
        for (int sub = 0; sub < orderList.size(); sub++){
            
            Order one = orderList.get(sub);
            
            String record = one.getOrderID() + "," + one.getCustomerID() 
                    + "," + one.getProduct() + "," + one.getShippingMethod();
            
            bw.write(record);
            bw.newLine();
        }
        bw.close();
        fw.close();

    }
   
     
}
    
