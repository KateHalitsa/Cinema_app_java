package Client.src.ClientWork;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class HallTable {
    public ImageIcon plusIcon;
    public ImageIcon minusIcon;
    public ImageIcon myIcon;
    public ImageIcon otherIcon;
    public JTable table;
    private boolean readOnly = false;

    private ArrayList<HallTableTicketsChanged> listeners = new ArrayList<HallTableTicketsChanged>();

    public void addListener(HallTableTicketsChanged toAdd) {
        listeners.add(toAdd);
    }

    public void hallTableTicketsChanged() {
        for (HallTableTicketsChanged hl : listeners)
            hl.tiketsChanged();
    }

    public HallTable(JPanel parentPanel, int rowCount, int seatCount)  {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIDefaults defaults = UIManager.getLookAndFeelDefaults();
            defaults.put("Table.alternateRowColor", null); // Prevent Zebra
        }
        catch(Exception e)
        {
        }

        int cellSize = 23;

        plusIcon = new ImageIcon("images/hall/plus.png");
        minusIcon = new ImageIcon("images/hall/minus.png");
        myIcon = new ImageIcon("images/hall/my.png");
        otherIcon = new ImageIcon("images/hall/other.png");

        table = new JTable(rowCount, seatCount + 1){
            public Class getColumnClass(int column) {
                return (column >= 0) ? Icon.class : Object.class;
            }
        };
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);

        for (int i = 0; i < table.getColumnCount(); i++)
        {
            TableColumn columnModel = table.getTableHeader().getColumnModel().getColumn(i);
            if (i == 0)
            {
                columnModel.setHeaderValue("");
            }
            else
            {
                columnModel.setHeaderValue(Integer.toString(i));
            }

            if (i > 0)
            {
                table.getColumnModel().getColumn(i).setPreferredWidth(cellSize);
            }
        }

        for (int j = 0; j < table.getRowCount(); j++)
        {
            table.setValueAt(j + 1, j, 0);
        }

        javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.setRowHeight(cellSize);
        table.setGridColor(new Color(220, 220, 220));
        table.setSelectionBackground(new Color(233, 227, 247));

        JScrollPane scrollPane= new JScrollPane( table );
        FixedColumnTable fct = new FixedColumnTable(1, scrollPane);
        JTable fixedTable = fct.getFixedTable();
        fixedTable.setRowHeight(cellSize);
        fixedTable.setGridColor(new Color(220, 220, 220));
        fixedTable.setBackground(new Color(250, 250, 250));
        fixedTable.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBorder(BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)));
        //scrollPane.getViewport().setBackground(Color.WHITE);
        //scrollPane.setBackground(Color.WHITE);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());
                clickOnCell(table, row, col);
            }
        });
        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode()==KeyEvent.VK_SPACE)
                {
                    int col = table.getSelectedColumn();
                    int row = table.getSelectedRow();
                    clickOnCell(table, row, col);
                }
            }
        });

        parentPanel.add(scrollPane);
    }

    private void clickOnCell(JTable table, int row, int col)
    {
        if (!readOnly)
        {
            if (row >= 0 && col >= 0) {
                Object val = table.getValueAt(row, col);
                if (val == null) {
                    table.setValueAt(plusIcon, row, col);
                } else if (val == plusIcon) {
                    table.setValueAt(null, row, col);
                } else if (val == myIcon) {
                    table.setValueAt(minusIcon, row, col);
                } else if (val == minusIcon) {
                    table.setValueAt(myIcon, row, col);
                }
                hallTableTicketsChanged();
            }
        }
    }

    public void setReadOnly(boolean value)
    {
        readOnly = value;
    }

    public class CellStats
    {
        public int myOrderedTickets = 0;
        public int otherOrderedTickets = 0;
        public int cancelledTickets = 0;
        public int addedtickets = 0;
    }
    public CellStats getHalltickets()
    {
        CellStats result = new CellStats();

        for (int i = 0; i < table.getColumnCount(); i++)
        {
            for (int j = 0; j < table.getRowCount(); j++)
            {
                Object val = table.getValueAt(j, i);
                if (val != null) {

                    if (val == otherIcon) {
                        result.otherOrderedTickets++;
                    } else if (val == plusIcon) {
                        result.addedtickets++;
                    } else if (val == myIcon) {
                        result.myOrderedTickets++;
                    } else if (val == minusIcon) {
                        result.myOrderedTickets++;
                        result.cancelledTickets++;
                    }
                }
            }
        }
        return result;
    }

}