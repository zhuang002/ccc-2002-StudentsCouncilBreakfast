/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentscouncilbreakfast;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author zhuan
 */
public class StudentsCouncilBreakfast {

    static int[] prices=new int[4];
    static int fund=0;
    static String[] ticketColor={"PINK","GREEN","RED","ORANGE"};
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc=new Scanner(System.in);
        for (int i=0;i<4;i++) {
            prices[i]=sc.nextInt();
        }
        fund=sc.nextInt();
        
        
        ArrayList<int[]> ticketsList=getTicketsList(0,3,fund);
        int min=Integer.MAX_VALUE;
        for (int[] tickets:ticketsList) {
            int noTickets=printTickets(tickets);
            if (min>noTickets) min=noTickets;
        }
        System.out.println("Total combinations is "+ticketsList.size()+".");
        System.out.printf("Minimum number of tickets to print is %d.",min);
    }

    private static ArrayList<int[]> getTicketsList(int ticket1,int ticket2,int fund) {
        ArrayList<int[]> ticketsList=new ArrayList();
        if (fund==0) {
            int[] t= {0,0,0,0};
            ticketsList.add(t);
            return ticketsList;
        }
        if (ticket1==ticket2) {
            if (fund%prices[ticket1]==0) {
                int[] tickets=new int[4];
                tickets[ticket1]=fund/prices[ticket1];
                ticketsList.add(tickets);
            }
            return ticketsList;
        }
        
        int ticket1Count=0;
        int allocatedPrice=0;
        while (allocatedPrice<=fund) {
            ArrayList<int[]> tL=getTicketsList(ticket1+1,ticket2,fund-allocatedPrice);
            
            for (int[] tickets:tL){
                tickets[ticket1]=ticket1Count;
            }
            ticketsList.addAll(tL);
            
            ticket1Count++;
            allocatedPrice=ticket1Count*prices[ticket1];
        }
        return ticketsList;
    }

    private static int printTickets(int[] tickets) {
        int count=0;
        for (int i=0;i<4;i++) {
            count+=tickets[i];
            System.out.printf("# of %s is %d ", ticketColor[i],tickets[i]);
        }
        System.out.println();
        return count;
    }
    
}
