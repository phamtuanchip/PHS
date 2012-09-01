
package phs_project;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.util.*;
import sun.util.calendar.*;

public class DateCalender extends JDialog implements java.io.Serializable
{
	public static final int CALENDAR_APPROVE_OPTION = 0;
	public static final int CALENDAR_CANCEL_OPTION = 1;

	private JLabel jLabel1 = new JLabel();
	private JLabel jLabel2 = new JLabel();

	private Border border1;
	private Border border2;

	private TitledBorder titledBorder1;
	private BorderLayout borderLayout1 = new BorderLayout();

	private JPanel calendar_panel = new JPanel();
	private JPanel jPanel1 = new JPanel();

	private JScrollPane jScrollPane1 = new JScrollPane();
	private JScrollPane jScrollPane2 = new JScrollPane();

	private JComboBox cboMonth = new JComboBox();

	private SpinnerListModel model = new SpinnerListModel();
	private JSpinner lstYear = new JSpinner(model);

	private String[] strColumnName = {"Mon ","Tue ","Wed ","Thu ","Fri ","Sat ","Sun "};
	private Object[] row = new Object[7];
	private DefaultTableModel tablemodel = new RDefaultTableModel();

	private JTable myTable = new JTable();

	private Date date = new Date();

	public DateCalender()
	{
		super(new JFrame(),"Calendar",true);
		buildGUI();
		buildMonthAndYear();
		buildTable();
	}

	public Date getCalendarDate()
	{
		int select_year = (int)Integer.parseInt(lstYear.getValue().toString());

		int select_month = cboMonth.getSelectedIndex() + 1;

		String str = (String)tablemodel.getValueAt(myTable.getSelectedRow(),myTable.getSelectedColumn());


		int day = (int)Integer.parseInt(str);
		String day1;
		if(day<10)
		{
			day1="0"+day;
		}
		else
		{
			day1=""+day;
		}

		int day2=(int)Integer.parseInt(day1);

    	Date select_date = new Date(select_year-1900,select_month,day2);

    	return select_date;
	}

		public String getCalendarDate1()
		{
			int select_year = (int)Integer.parseInt(lstYear.getValue().toString());

			int select_month = cboMonth.getSelectedIndex();

			String str = (String)tablemodel.getValueAt(myTable.getSelectedRow(),myTable.getSelectedColumn());


			int day = (int)Integer.parseInt(str);
			String day1;
			if(day<10)
			{
				day1="0"+day;
			}
			else
			{
				day1=""+day;
			}

			int day2=(int)Integer.parseInt(day1);

	    	String dd=new String(select_month+"/"+day1+"/"+select_year);

	    	return dd;
		}
	private void buildMonthAndYear()
	{
		cboMonth.addItem("January");
		cboMonth.addItem("February");
		cboMonth.addItem("March");
		cboMonth.addItem("April");
		cboMonth.addItem("May");
		cboMonth.addItem("June");
		cboMonth.addItem("July");
		cboMonth.addItem("August");
		cboMonth.addItem("September");
		cboMonth.addItem("October");
		cboMonth.addItem("December");
		cboMonth.addItem("September");

		cboMonth.setSelectedIndex(date.getMonth());

		cboMonth.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				buildTable();
			}
		});

		java.util.List list = new java.util.LinkedList();
		for(int l=1900;l<3000;l++)
		{
			list.add(String.valueOf(l));
		}
		model.setList(list);
		lstYear.setValue(String.valueOf(1900+date.getYear()));
		lstYear.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				buildTable();
			}
		});
	}
	private void buildTable()
	{
		try
		{
			int TABLE_ROW_COUNT = tablemodel.getRowCount();
			for(int cnt=0;cnt<TABLE_ROW_COUNT;cnt++)
			{
				tablemodel.removeRow(0);
			}
			Vector vtDate = new Vector();
			for(int i=1;i<36;i++)
			{
				int year = (int)Integer.parseInt(String.valueOf(lstYear.getValue()));
				int month = cboMonth.getSelectedIndex();
				Date datearr = new Date(year-1900,month,i);
				if(datearr.getDate()==i)
				{
					vtDate.addElement(new Integer(i));
					switch(datearr.getDay()+1)
					{
						case Calendar.MONDAY:
						{
							createRow(i,0);
							break;
						}
						case Calendar.TUESDAY:
						{
							createRow(i,1);
							break;
						}
						case Calendar.WEDNESDAY:
						{
							createRow(i,2);
							break;
						}
						case Calendar.THURSDAY:
						{
							createRow(i,3);
							break;
						}
						case Calendar.FRIDAY:
						{
							createRow(i,4);
							break;
						}
						case Calendar.SATURDAY:
						{
							createRow(i,5);
							break;
						}
						case Calendar.SUNDAY:
						{
							createRow(i,6);
							tablemodel.addRow(row);
							tablemodel.fireTableDataChanged();
							tablemodel.fireTableStructureChanged();
							row[0] = ""; row[1] = ""; row[2] = "";
							row[3] = ""; row[4] = ""; row[5] = ""; row[6] = "";

							break;
						}
						default:
						{
							createRow(i,5);
							break;
						}
					}
				}
			}
			tablemodel.addRow(row);
			tablemodel.fireTableDataChanged();
			tablemodel.fireTableStructureChanged();
			row[0] = ""; row[1] = ""; row[2] = "";
			row[3] = ""; row[4] = ""; row[5] = ""; row[6] = "";
			myTable.addMouseListener(new MouseAdapter()
			{
	    		public void mouseClicked(MouseEvent e)
	    		{
	    			if(e.getClickCount()==2)
	    			{

	    				dispose();
	    			}
	    	}});
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	private void createRow(int i, int index)
	{
		for(int j=0;j<7;j++)
		{
			if(j==index)
			{
				row[j] = String.valueOf(i);
			}
		}
		GridLayout grid = new GridLayout();
	}
	private void buildGUI()
	{
	    titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),"Calendar");
	    titledBorder1.setTitleFont(new java.awt.Font("SansSerif", 0, 11));
	    border1 = BorderFactory.createCompoundBorder(titledBorder1,BorderFactory.createEmptyBorder(3,3,3,3));
	    border2 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(99, 99, 99),new Color(142, 142, 142));

	    cboMonth.setBounds(new Rectangle(60, 20, 71, 18));
	    cboMonth.setFont(new java.awt.Font("SansSerif", 0, 11));
	    lstYear.setBounds(new Rectangle(179, 20, 73, 18));

	    jLabel1.setFont(new java.awt.Font("SansSerif", 0, 11));
	    jLabel1.setText("Month:");
	    jLabel1.setBounds(new Rectangle(22, 20, 40, 18));

	    jLabel2.setFont(new java.awt.Font("SansSerif", 0, 11));
	    jLabel2.setText("Year:");
	    jLabel2.setBounds(new Rectangle(143, 20, 36, 18));

	    jPanel1.setBorder(border2);
	    jPanel1.setBounds(new Rectangle(2, 49, 275, 148));
	    jPanel1.setLayout(borderLayout1);


	    // Build Table
	    tablemodel.setColumnIdentifiers(strColumnName);
	    tablemodel.setRowCount(0);
	    myTable.setModel(tablemodel);
	    myTable.setRowHeight(20);
	    myTable.setCellSelectionEnabled(true);
	    myTable.setIntercellSpacing(new Dimension(1,5));
	    myTable.setShowHorizontalLines(false);
	    myTable.setShowVerticalLines(false);
	    myTable.setCursor(new Cursor(Cursor.HAND_CURSOR));

	    jPanel1.add(jScrollPane2, BorderLayout.CENTER);
	    jScrollPane2.setBackground(Color.white);
	    jScrollPane2.getViewport().add(myTable, null);

	    calendar_panel.add(jLabel2, null);
	    calendar_panel.add(lstYear, null);
	    calendar_panel.add(cboMonth, null);
	    calendar_panel.add(jLabel1, null);
	    calendar_panel.add(jPanel1, null);
	    calendar_panel.setBorder(border1);
	    calendar_panel.setMaximumSize(new Dimension(280, 200));
	    calendar_panel.setMinimumSize(new Dimension(280, 200));
	    calendar_panel.setPreferredSize(new Dimension(280, 200));
	    calendar_panel.setLayout(null);
	    this.getContentPane().add("Center",calendar_panel);
	    this.setLocation(280,180);
	    this.pack();
	}

	private class RDefaultTableModel extends DefaultTableModel
	{
	    public boolean isCellEditable(int row, int column)
	    {
	        return false;
	    }
	}

		public static void main(String[] args)
		{



	    	DateCalender rp = new DateCalender();

	    	rp.addWindowListener(new WindowAdapter()
	    	{
	    		public void windowClosing(WindowEvent we)
		    	{
		    		System.exit(0);
		    	}


	    	});

		    rp.pack();
		    rp.setVisible(true);
		    String d=rp.getCalendarDate1();
		    System.out.println ("date "+d);

		}
}
