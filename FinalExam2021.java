package edu.loyola.cs.se;

import java.text.SimpleDateFormat;
import java.text.NumberFormat;

public class FinalExam2021 {
    // Refactor #3 Replace with constants
    public static final double FIVE_PERCENT = 0.05;
    //public static final double FIVE_PERCENT = 0.05;

    
    public static void main(String[] args)
    {
        User user = new User();
        System.out.println(user.getName());
        Order order = new Order();
        System.out.println(order.getProduct());
        Order[] orders = new Order[5]; // combining both statements in one
        FinalExam2021 check = new FinalExam2021();

        int x = 0;
        while ( x < 5)
        {
            orders[x] = new Order();
            x++;
        }
        System.out.println(check.generateReceipt(user, orders));

        //System.out.println(generateReceipt( new User, order));
    }

    // Refactor #1 extract method
    public String generateHeader()
    {
        String out = "";

        // Header text for the receipt
        out += "Company XY Sales - Receipt";
        SimpleDateFormat dtFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        out += "\nDate: "+dtFormatter.format(new java.util.Date());
        out += "\n";
        return out;
    }

    // Refactor #2 extract method
    public String generateClientInfo(User user, Order[] orders){
        //Client info if it exists
        if(user.getId() > 0)
            return  "\n Client: "+ user.getName() +" "+user.getRewardsNumber();
        else
            return "\n Unregistered Client (sign up to earn rewards!)";
    }

    // Refector #5 extract method
    public String generateFinalCalculations(User user, double total, int rewardPoints){
    //Final calculations
    String out = "";
    NumberFormat moneyFormatter = NumberFormat.getCurrencyInstance();
    out += "\n===========================";
    out += "\n Rewards Points gained: "+rewardPoints;
    if(user.getId() > 0) user.addRewardPoints(rewardPoints);
    out += "\n Total without Taxes: "+moneyFormatter.format( total );
    out += "\n Taxes: "+moneyFormatter.format( total*FIVE_PERCENT ); //5%
    // Refactor #6
    out += "\n Total + Taxes: "+moneyFormatter.format( total+total*FIVE_PERCENT );
    return out;
    }

    // Refactor #7 extraction
    public String generateOrderCalculations(NumberFormat moneyFormatter, Order o){
        String out = "";
        out += "\n "+ o.getProduct();
        out += " \t "+moneyFormatter.format( o.getUnitPrice() );
        out += " \t\t "+ o.getQuantity();
        out += " \t "+moneyFormatter.format(o.getTotal());
        return out;
    }

    public String generateReceipt(User user, Order[] orders){
        String out = "";
        out = out + generateHeader();

        out = out + generateClientInfo(user, orders);
        

        //Orders Calculations
        double total = 0;
        int rewardPoints = 0;
        out += "\n===========================";
        out += "\n Product \t Price \t Items \t Subtotal ";
        NumberFormat moneyFormatter = NumberFormat.getCurrencyInstance();
        for(Order o : orders){
            out = out + generateOrderCalculations(moneyFormatter, o);

            total += o.getTotal();
            rewardPoints += (o.getTotal()*100)/2;
        }
        
        out = out + generateFinalCalculations(user, total, rewardPoints);
        return out;
    }
}

//Classes below just to support the above code, assume they have real implementations
//You may change the classes bellow if you want to test your refactorings
class Order {
    public Product getProduct(){ return new Product(); }
    public double getUnitPrice(){return 1.0;}
    public int getQuantity(){ return 3; }
    //Refactor #4 extraction
    public double getTotal(){return getUnitPrice()*getQuantity();}
}

class Product {
    @Override public String toString(){
        return "prod_name";
    }
}

class User {
    public String getName() { return "J.Doe"; }
    public int getId(){ return 1;}
    public String getRewardsNumber() {return "RW123456789";}
    public void addRewardPoints(int points){ }
}
