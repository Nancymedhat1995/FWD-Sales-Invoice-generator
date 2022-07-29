/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sig.controller;

import com.sig.model.SalesInvoiceHeader;
import com.sig.model.SalesInvoiceLine;
import com.sig.model.InvoiceDetailsTable;
import com.sig.view.InvoiceFrame;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author DELL
 */
public class TableSelectionListener implements ListSelectionListener {

    private InvoiceFrame Frame;

    public TableSelectionListener(InvoiceFrame Frame) {
        this.Frame = Frame;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedInvIndex = Frame.getInvHTbl().getSelectedRow();
        System.out.println("Invoice selected: " + selectedInvIndex);
        if (selectedInvIndex != -1) {
            SalesInvoiceHeader selectedInv = Frame.getInvoicesArray().get(selectedInvIndex);
            ArrayList<SalesInvoiceLine> lines = selectedInv.getLines();
            InvoiceDetailsTable lineTableModel = new InvoiceDetailsTable(lines);
            Frame.setLinesArray(lines);
            Frame.getInvLTbl().setModel(lineTableModel);
            Frame.getCustNameLbl().setText(selectedInv.getCustomer());
            Frame.getInvNumLbl().setText("" + selectedInv.getNum());
            Frame.getInvTotalIbl().setText("" + selectedInv.getInvoiceTotal());
            Frame.getInvDateLbl().setText(InvoiceFrame.dateFormat.format(selectedInv.getInvDate()));
        }
    }

}
