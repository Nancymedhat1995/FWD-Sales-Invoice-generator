
package com.sig.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author DELL
 */
public class InvoiceDetailsTable extends AbstractTableModel {

    private ArrayList<SalesInvoiceLine> ArrayLines;
    private String[] columns = {"Item Name", "Unit Price", "Count", "Line Total"};

    public InvoiceDetailsTable(ArrayList<SalesInvoiceLine> ArrayLines) {
        this.ArrayLines = ArrayLines;
    }

    @Override
    public int getRowCount() {
        return ArrayLines == null ? 0 : ArrayLines.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (ArrayLines == null) {
            return "";
        } else {
            SalesInvoiceLine line = ArrayLines.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return line.getItem();
                case 1:
                    return line.getPrice();
                case 2:
                    return line.getCount();
                case 3:
                    return line.getLineTotal();
                default:
                    return "";
            }
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

}
