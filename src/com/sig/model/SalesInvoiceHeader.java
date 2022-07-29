
package com.sig.model;

import java.util.ArrayList;
import java.util.Date;

public class SalesInvoiceHeader {
    private int num;
    private String customer;
    private Date invDate;
    private ArrayList<SalesInvoiceLine> lines;

    public SalesInvoiceHeader() {
    }

    public SalesInvoiceHeader(int num, String customer, Date invDate) {
        this.num = num;
        this.customer = customer;
        this.invDate = invDate;
    }

    public Date getInvDate() {
        return invDate;
    }

    public void setInvDate(Date invDate) {
        this.invDate = invDate;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public ArrayList<SalesInvoiceLine> getLines() {
        if (lines == null) {
            lines = new ArrayList<>();
        }
        return lines;
    }

    public void setLines(ArrayList<SalesInvoiceLine> lines) {
        this.lines = lines;
    }
    
    public double getInvoiceTotal() {
        double total = 0.0;
        
        for (int i = 0; i < getLines().size(); i++) {
            total += getLines().get(i).getLineTotal();
        }
        
        return total;
    }

    @Override
    public String toString() {
        return "InvoiceHeader{" + "num=" + num + ", customer=" + customer + ", invDate=" + invDate + '}';
    }
    
}
