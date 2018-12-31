package question5;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

import javax.swing.*;

public class ShoppingCard extends JFrame
{
    private JPanel listPanel;
    private JPanel shoppingPanel;
    private JPanel buttonsPanel;
    private JList listItems;
	
	private JButton addButton;
	private JButton removeButton;
	private JButton clearButton;
	private JButton checkoutButton;
	
	private String[] listArray = new String[7];
	private List cartItems = new List();
	
	private final double salesTax = 0.06;

	public ShoppingCard() throws FileNotFoundException
	{
		setTitle("Shopping Card System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(1,3));
		setLocationRelativeTo(null);
		
		buildListPanel();
		buildShoppingPanel();
		buildButtonsPanel();
		
		add(listPanel);
		add(buttonsPanel);
		add(shoppingPanel);
			
		pack();
		setVisible(true);
	}
	
	
	private void buildListPanel() 
		throws FileNotFoundException
	{
		listPanel = new JPanel();
		listPanel.setLayout(new BorderLayout());
		listPanel.setBorder(BorderFactory.createEmptyBorder());
		
		JLabel label = new JLabel("Select A Book Title");
		
		label.setFont(new Font("Times New Roman", Font.BOLD, 18));
		
		String line;
		int index = 0;
		
		File file = new File("BookPrices.txt");
		Scanner fileReader = new Scanner(file);
		
		while(fileReader.hasNext())
		{
			line = fileReader.nextLine();
			String [] titles = line.split(",");
			listArray[index] = titles[0];
			index++;	
		}
		
		listItems = new JList(listArray);
		listPanel.add(label, BorderLayout.NORTH);
		listPanel.add(listItems, BorderLayout.CENTER);	
	}


	private void buildShoppingPanel() 
	{
		shoppingPanel = new JPanel();
		shoppingPanel.setLayout(new BorderLayout());
		shoppingPanel.setBorder(BorderFactory.createEmptyBorder() );
		
		JLabel label1 = new JLabel("Card");
		
		label1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		shoppingPanel.add(label1,BorderLayout.NORTH);
		shoppingPanel.add(cartItems, BorderLayout.CENTER);
	}

	private void buildButtonsPanel() 
	{
		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(4,1));
		
		addButton = new JButton("Add To Card");
		addButton.addActionListener(new AddButtonListener());
		
		removeButton = new JButton("Remove From Card");
		removeButton.addActionListener(new RemoveButtonListener ());
		
		clearButton = new JButton("Clear Card");
		clearButton.addActionListener(new ClearButtonListener());
		
		checkoutButton = new JButton("Check Out");
		checkoutButton.addActionListener(new CheckoutButtonListener());

		buttonsPanel.add(addButton);
		buttonsPanel.add(removeButton);
		buttonsPanel.add(clearButton);
		buttonsPanel.add(checkoutButton);
		
		
	}
	public class AddButtonListener implements ActionListener 
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			 String value;
			 value = (String) listItems.getSelectedValue();
			 cartItems.add(value);
		}

	}
	
	public class RemoveButtonListener implements ActionListener 
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			 String value;
			 value = (String) listItems.getSelectedValue();
			 cartItems.remove(value);
		}

	}
	
	public class CheckoutButtonListener implements ActionListener 
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			String line;
			double totalCost = 0.0, costOfItem =  0.0;
			
			File file = new File("BookPrices.txt");
			Scanner fileReader = null;
			
			try
			{
				fileReader = new Scanner(file);	
			} catch(FileNotFoundException el)
			{
				el.printStackTrace();
			}
			
			while(fileReader.hasNextLine())
			{
			   line = fileReader.nextLine();
			   String[] cost = line.split(",");
			   
			   String title = cost[0];
			   costOfItem = Double.parseDouble(cost[1]);
			   
			   for(int i = 0; i < cartItems.getItemCount(); i++ )
			   {
				   if(title.equals(cartItems.getItem(i)))
					   totalCost += costOfItem;
			   }
			}
			
			double tax = salesTax * totalCost;
			DecimalFormat myFormatter = new DecimalFormat("###.##");
			JOptionPane.showMessageDialog(null, "Total Cost: $" +
					myFormatter.format(tax + totalCost));
			
		}

	}
	
	public class  ClearButtonListener implements ActionListener 
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			 String value;
			 value = (String) listItems.getSelectedValue();
			 cartItems.removeAll();
		}

	}
	
	
	public static void main(String[] args) 
				throws FileNotFoundException
	{
		new ShoppingCard();
	}

}