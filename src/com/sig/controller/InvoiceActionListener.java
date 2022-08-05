/*
 * SalesInvoice By Nancy .
 */
package com.sig.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.sig.model.SalesInvoiceHeader;
import com.sig.model.InvoiceHeaderTable;
import com.sig.model.SalesInvoiceLine;
import com.sig.model.InvoiceDetailsTable;
import com.sig.view.InvoiceFrame;
import com.sig.view.InvoiceHeadDialog;
import com.sig.view.InvoiceLineDialog;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



/**
 *
 * @author DELL
 */
public class InvoiceActionListener implements ActionListener {

    private final InvoiceFrame Frame;
    private InvoiceHeadDialog headDialog;
    private InvoiceLineDialog lineDialog;

    public InvoiceActionListener(InvoiceFrame Frame) {
        this.Frame = Frame;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()) {
            case "Save Files":
                saveFiles();
                break;

                
            case "Create Invoice":
                createNewInvoice();
                break;

            case "Delete Invoice":
                deleteInvoice();
                break;
                
                
            case "newInvoiceOK":
                newInvoiceDialogOK();
                break;
                
            case "Load Files":
                loadFiles();
                break;

            
            case "New item":
                createNewLine();
                break;

            case "Delete item":
                deleteLine();
                break;

            

            case "newInvoiceCancel":
                newInvoiceDialogCancel();
                break;

            case "newLineCancel":
                newLineDialogCancel();
                break;

            case "newLineOK":
                newLineDialogOK();
                break;
        }
    }

    private void loadFiles() {
        JFileChooser file = new JFileChooser();
        try {
            int actionResult = file.showOpenDialog(Frame);
            if (actionResult == JFileChooser.APPROVE_OPTION) {
                File headFile = file.getSelectedFile();
                Path headPath = Paths.get(headFile.getAbsolutePath());
                List<String> headerLines = Files.readAllLines(headPath);
                ArrayList<SalesInvoiceHeader> invoiceHeaders = new ArrayList<>();
                for (String headerLine : headerLines) {
                    String[] A = headerLine.split(",");
                    String A1 = A[0];
                    String A2 = A[1];
                    String A3 = A[2];
                    int code = Integer.parseInt(A1);
                    Date invoiceDate = InvoiceFrame.dateFormat.parse(A2);
                    SalesInvoiceHeader header1 = new SalesInvoiceHeader(code, A3, invoiceDate);
                    invoiceHeaders.add(header1);
                     System.out.println("files read succefully");
                     System.out.println("*************" );
 System.out.println("InVoiceHeader" );
 System.out.println("-------------" );
                         System.out.println("ClientName: " +A3+" , "+"Date:" +A2);
                          
                }
                Frame.setInvoicesArray(invoiceHeaders);

                actionResult = file.showOpenDialog(Frame);
                if (actionResult == JFileChooser.APPROVE_OPTION) {
                    File lineFile = file.getSelectedFile();
                    Path linePath = Paths.get(lineFile.getAbsolutePath());
                    List<String> lineLines = Files.readAllLines(linePath);
                    ArrayList<SalesInvoiceLine> InvoiceLines;
                    InvoiceLines = new ArrayList<>();
                    for (String lineLine : lineLines) {
                        String[] A = lineLine.split(",");
                        String A1 = A[0];    // invoice num (intger)
                        String A2 = A[1];    // item name   (String)
                        String A3 = A[2];    // price       (double)
                        String A4 = A[3];    // no.       (intger)
                        int invCode = Integer.parseInt(A1);
                        double price = Double.parseDouble(A3);
                        int count = Integer.parseInt(A4);
                        SalesInvoiceHeader inv = Frame.getInvObject(invCode);
                        SalesInvoiceLine line = new SalesInvoiceLine(A2, price, count, inv);
                        inv.getLines().add(line);
                         System.out.println("InVoiceLine" );
                        System.out.println("-------------" );
                        System.out.println("ItemName: " +A2+" , "+ "No. of Items: " +A4+" , "+"ItemPrice: " +A3);
                       

                    }
                }
                InvoiceHeaderTable headerTableModel = new InvoiceHeaderTable(invoiceHeaders);
                Frame.setHeaderTableModel(headerTableModel);
                Frame.getInvHTbl().setModel(headerTableModel);
               
                  
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(Frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(Frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createNewInvoice() {
        headDialog = new InvoiceHeadDialog(Frame);
        headDialog.setVisible(true);
    }


    
    

   
     private void createNewLine() {
        lineDialog = new InvoiceLineDialog(Frame);
        lineDialog.setVisible(true);
    }
    
    
    
    
    private void saveFiles() {
    }

    private void newInvoiceDialogCancel() {
        headDialog.setVisible(false);
        headDialog.dispose();
        headDialog = null;
    }

    private void newInvoiceDialogOK() {
        headDialog.setVisible(false);

        String custName = headDialog.getCustNameField().getText();
        String str = headDialog.getInvDateField().getText();
        Date d = new Date();
        try {
            d = InvoiceFrame.dateFormat.parse(str);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(Frame, "resetting today date!", "Invalid date format", JOptionPane.ERROR_MESSAGE);
        }

        int invNum = 0;
        for (SalesInvoiceHeader inv : Frame.getInvoicesArray()) {
            if (inv.getNum() > invNum) {
                invNum = inv.getNum();
            }
        }
        invNum++;
        SalesInvoiceHeader newInv = new SalesInvoiceHeader(invNum, custName, d);
        Frame.getInvoicesArray().add(newInv);
        Frame.getHeaderTableModel().fireTableDataChanged();
        headDialog.dispose();
        headDialog = null;
    }

    private void newLineDialogCancel() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }

    private void newLineDialogOK() {
        lineDialog.setVisible(false);
        
        String name = lineDialog.getItemNameField().getText();
        String A1 = lineDialog.getItemCountField().getText();
        String A2 = lineDialog.getItemPriceField().getText();
        int count = 1;
        double price = 1;
        try {
            count = Integer.parseInt(A1);
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(Frame, "Cannot convert number", "Invalid number format", JOptionPane.ERROR_MESSAGE);
        }
        
        try {
            price = Double.parseDouble(A2);
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(Frame, "Cannot convert the price format", "Invalid data type!", JOptionPane.ERROR_MESSAGE);
        } 
        int selectedInvHeader = Frame.getInvHTbl().getSelectedRow();
        if (selectedInvHeader != -1) {
            SalesInvoiceHeader invHeader = Frame.getInvoicesArray().get(selectedInvHeader);
            SalesInvoiceLine line = new SalesInvoiceLine(name, price, count, invHeader);
            //invHeader.getLines().add(line);
            Frame.getLinesArray().add(line);
            InvoiceDetailsTable lineTableModel = (InvoiceDetailsTable) Frame.getInvLTbl().getModel();
            lineTableModel.fireTableDataChanged();
            Frame.getHeaderTableModel().fireTableDataChanged();
        }
        Frame.getInvHTbl().setRowSelectionInterval(selectedInvHeader, selectedInvHeader);
        lineDialog.dispose();
        lineDialog = null;
    }

  private void deleteInvoice() {
        int selectedInvoiceIndex = Frame.getInvHTbl().getSelectedRow();
        if (selectedInvoiceIndex != -1) {
            Frame.getInvoicesArray().remove(selectedInvoiceIndex);
            Frame.getHeaderTableModel().fireTableDataChanged();

            Frame.getInvLTbl().setModel(new InvoiceDetailsTable(null));
            Frame.setLinesArray(null);
            Frame.getCustNameLbl().setText("");
            Frame.getInvNumLbl().setText("");
            Frame.getInvTotalIbl().setText("");
            Frame.getInvDateLbl().setText("");
        }
    }
     private void deleteLine() {
        int selectedLineIndex = Frame.getInvLTbl().getSelectedRow();
        int selectedInvoiceIndex = Frame.getInvHTbl().getSelectedRow();
        if (selectedLineIndex != -1) {
            Frame.getLinesArray().remove(selectedLineIndex);
            InvoiceDetailsTable lineTableModel = (InvoiceDetailsTable) Frame.getInvLTbl().getModel();
            lineTableModel.fireTableDataChanged();
            Frame.getInvTotalIbl().setText(""+Frame.getInvoicesArray().get(selectedInvoiceIndex).getInvoiceTotal());
            Frame.getHeaderTableModel().fireTableDataChanged();
            Frame.getInvHTbl().setRowSelectionInterval(selectedInvoiceIndex, selectedInvoiceIndex);
        }
    }
}
