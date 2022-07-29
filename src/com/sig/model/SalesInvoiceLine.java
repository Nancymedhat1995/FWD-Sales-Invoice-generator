
package com.sig.model;

public class SalesInvoiceLine {
    private String item;
    private double price;
    private int count;
    private SalesInvoiceHeader header;

    public SalesInvoiceLine() {
    }

    public SalesInvoiceLine(String item, double price, int count, SalesInvoiceHeader header) {
        this.item = item;
        this.price = price;
        this.count = count;
        this.header = header;
    }

    public SalesInvoiceHeader getHeader() {
        return header;
    }

    public void setHeader(SalesInvoiceHeader header) {
        this.header = header;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    public double getLineTotal() {
        return price * count;
    }

    @Override
    public String toString() {
        return "InvoiceLine{" + "item=" + item + ", price=" + price + ", count=" + count + ", header=" + header + '}';
    }

    
    
}
