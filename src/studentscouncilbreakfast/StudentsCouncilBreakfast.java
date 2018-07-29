/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentscouncilbreakfast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author zhuan
 */
public class StudentsCouncilBreakfast {

    static int[] prices = new int[4];
    static int fund = 0;
    static String[] ticketColor = {"PINK", "GREEN", "RED", "ORANGE"};
    static HashMap<Key, ArrayList<int[]>> getTicketsListBackup = new HashMap();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 4; i++) {
            prices[i] = sc.nextInt();
        }
        fund = sc.nextInt();

        ArrayList<int[]> ticketsList = getTicketsList(0, 3, fund);
        int min = Integer.MAX_VALUE;
        for (int[] tickets : ticketsList) {
            int noTickets = printTickets(tickets);
            if (min > noTickets) {
                min = noTickets;
            }
        }
        System.out.println("Total combinations is " + ticketsList.size() + ".");
        System.out.printf("Minimum number of tickets to print is %d.", min);
    }

    private static ArrayList<int[]> getTicketsList(int ticket1, int ticket2, int fund) {

        ArrayList<int[]> ticketsList = new ArrayList();
        Key key = new Key(ticket1, ticket2, fund);
        if (getTicketsListBackup.containsKey(key)) {
            return cloneTicketsList(getTicketsListBackup.get(key));
        }
        if (fund == 0) {
            int[] t = {0, 0, 0, 0};
            ticketsList.add(t);
            getTicketsListBackup.put(key, ticketsList);
            return ticketsList;
        }
        if (ticket1 == ticket2) {
            if (fund % prices[ticket1] == 0) {
                int[] tickets = new int[4];
                tickets[ticket1] = fund / prices[ticket1];
                ticketsList.add(tickets);
            }
            getTicketsListBackup.put(key, ticketsList);
            return ticketsList;
        }

        int ticket1Count = 0;
        int allocatedPrice = 0;
        while (allocatedPrice <= fund) {
            ArrayList<int[]> tL = getTicketsList(ticket1 + 1, ticket2, fund - allocatedPrice);

            for (int[] tickets : tL) {
                tickets[ticket1] = ticket1Count;
            }
            ticketsList.addAll(tL);

            ticket1Count++;
            allocatedPrice = ticket1Count * prices[ticket1];
        }
        getTicketsListBackup.put(key, ticketsList);
        return ticketsList;
    }

    private static int printTickets(int[] tickets) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            count += tickets[i];
            System.out.printf("# of %s is %d ", ticketColor[i], tickets[i]);
        }
        System.out.println();
        return count;
    }

    private static ArrayList<int[]> cloneTicketsList(ArrayList<int[]> ticketsList) {
        ArrayList<int[]> ret=new ArrayList();
        for (int[] ia:ticketsList) {
            int[] newIa=Arrays.copyOf(ia, ia.length);
            ret.add(newIa);
        }
        return ret;
    }

}

class Key {

    int i1;
    int i2;
    int i3;

    public Key(int ticket1, int ticket2, int fund) {
        this.i1 = ticket1;
        this.i2 = ticket2;
        this.i3 = fund;
    }

    @Override
    public boolean equals(Object obj) {
        Key k = (Key) obj;
        return this.i1 == k.i1 && this.i2 == k.i2 && this.i3 == this.i3;

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.i1;
        hash = 31 * hash + this.i2;
        hash = 31 * hash + this.i3;
        return hash;
    }
}
