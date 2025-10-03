import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;


public class FinalProject extends JFrame implements ActionListener, MouseListener
{
	JLabel seatplanLable, departureLable, busTypeLable;
	JTextArea purchaseHistoryArea;
	JButton clearBtn, cartBtn, purchaseBtn;
	JScrollPane scrollPane;
	JPanel panel;
	JCheckBox ac1, ac2, ac3, ac4, ac5, ac6, ac7, ac8, ac9, ac10;
	JComboBox combo1, combo2;

	private List<JCheckBox> allSeatCheckBoxes;
	private Set<String> bookedSeats; 
	private final String PASSWORD_FOR_CLEAR = "123"; 


	public FinalProject()
	{
		super("Bus Ticket Booking");
		this.setSize(1000, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		panel.setLayout(null);

		allSeatCheckBoxes = new ArrayList<>();
		bookedSeats = new HashSet<>();


		clearBtn = new JButton("Clear");
		clearBtn.setBounds(270, 150, 150, 30);
		clearBtn.addActionListener(this);
		panel.add(clearBtn);

		cartBtn = new JButton("Confirm Ticket");
		cartBtn.setBounds(270, 180, 150, 30);
		cartBtn.addActionListener(this);
		panel.add(cartBtn);

		purchaseBtn = new JButton("Purchase Ticket");
		purchaseBtn.setBounds(270, 210, 150, 30);
		purchaseBtn.addActionListener(this);
		panel.add(purchaseBtn);



		purchaseHistoryArea = new JTextArea();
		purchaseHistoryArea.setEditable(false);
		scrollPane = new JScrollPane(purchaseHistoryArea);
		scrollPane.setBounds(550, 20, 400, 300);
		panel.add(scrollPane);

		seatplanLable = new JLabel("Seat Plan");
		seatplanLable.setBounds(50, 10, 160, 30);
		panel.add(seatplanLable);

		ac1 = new JCheckBox("A1");
		ac1.setBounds(50, 50, 50, 20);
		allSeatCheckBoxes.add(ac1);
		panel.add(ac1);

		ac2 = new JCheckBox("A2");
		ac2.setBounds(150, 50, 50, 20);
		allSeatCheckBoxes.add(ac2);
		panel.add(ac2);

		ac3 = new JCheckBox("B1");
		ac3.setBounds(50, 100, 50, 20);
		allSeatCheckBoxes.add(ac3);
		panel.add(ac3);

		ac4 = new JCheckBox("B2");
		ac4.setBounds(150, 100, 50, 20);
		allSeatCheckBoxes.add(ac4);
		panel.add(ac4);

		ac5 = new JCheckBox("C1");
		ac5.setBounds(50, 150, 50, 20);
		allSeatCheckBoxes.add(ac5);
		panel.add(ac5);

		ac6 = new JCheckBox("C2");
		ac6.setBounds(150, 150, 50, 20);
		allSeatCheckBoxes.add(ac6);
		panel.add(ac6);

		ac7 = new JCheckBox("D1");
		ac7.setBounds(50, 200, 50, 20);
		allSeatCheckBoxes.add(ac7);
		panel.add(ac7);

		ac8 = new JCheckBox("D2");
		ac8.setBounds(150, 200, 50, 20);
		allSeatCheckBoxes.add(ac8);
		panel.add(ac8);

		ac9 = new JCheckBox("E1");
		ac9.setBounds(50, 250, 50, 20);
		allSeatCheckBoxes.add(ac9);
		panel.add(ac9);

		ac10 = new JCheckBox("E2");
		ac10.setBounds(150, 250, 50, 20);
		allSeatCheckBoxes.add(ac10);
		panel.add(ac10);

		departureLable = new JLabel("Dhaka to ----");
		departureLable.setBounds(250, 50, 100, 30);
		panel.add(departureLable);

		String items[] = {"Bagerhat", "Sylhet", "Rangpur", "Barishal", "Khulna"};
		combo1 = new JComboBox(items);
		combo1.setBounds(350, 50, 100, 30);
		panel.add(combo1);

		busTypeLable = new JLabel("Select Bus Type");
		busTypeLable.setBounds(250, 100, 100, 30);
		panel.add(busTypeLable);

		String items1[] = {"AC 1200tk", "Non AC 600tk"};
		combo2 = new JComboBox(items1);
		combo2.setBounds(350, 100, 120, 30);
		panel.add(combo2);

		this.add(panel);
		this.setLocationRelativeTo(null);
	}



	public void actionPerformed(ActionEvent ae)
	{
		String command = ae.getActionCommand();

		if(cartBtn.getText().equals(command))
		{
			confirmTicket();
		}
		else if(purchaseBtn.getText().equals(command))
		{
			displayPurchasedTickets();
		}
		else if(clearBtn.getText().equals(command))
		{
			clearAllSelections();
		}
	}



	public void mouseClicked(MouseEvent me){}
	public void mousePressed(MouseEvent me){}
	public void mouseReleased(MouseEvent me){}
	public void mouseEntered(MouseEvent me){}
	public void mouseExited(MouseEvent me){}



	private void confirmTicket()
	{
		String currentDestination = combo1.getSelectedItem().toString();
		String currentBusType = combo2.getSelectedItem().toString();
		int pricePerSeat;

		if (currentBusType.startsWith("AC")) {
			pricePerSeat = 1200;
		} else {
			pricePerSeat = 600;
		}

		List<String> currentSelectedSeatNames = new ArrayList<>();
		for (JCheckBox seatBox : allSeatCheckBoxes) {
			if (seatBox.isSelected()) {
				String seatIdentifier = currentDestination + "|" + currentBusType + "|" + seatBox.getText();
				if (bookedSeats.contains(seatIdentifier)) {
					JOptionPane.showMessageDialog(this, "Seat " + seatBox.getText() + 
														" is already booked for " + currentDestination + " (" + currentBusType + ")!", 
												  "Booking Error", JOptionPane.ERROR_MESSAGE);
					return; 
				}
				currentSelectedSeatNames.add(seatBox.getText());
			}
		}

		if (currentSelectedSeatNames.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please select at least one seat.", "Selection Error", JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Create Info object with the actual destination and bus type for this booking
		Info newBooking = new Info(currentDestination, currentBusType, currentSelectedSeatNames, pricePerSeat);

		try
		{
			FileWriter writer = new FileWriter("PurchaseData.txt", true); 
			writer.write(newBooking.getDestination() + "," +
						 newBooking.getBusType() + "," +
						 newBooking.getIndividualTicketPrice() + "," +
						 newBooking.getSelectedSeatsString() + "," +
						 newBooking.getFinalTotalPrice() + "\n");
			writer.close();

			for (String seatName : currentSelectedSeatNames) {
				String bookedSeatIdentifier = newBooking.getDestination() + "|" + newBooking.getBusType() + "|" + seatName;
				bookedSeats.add(bookedSeatIdentifier);
			}
			JOptionPane.showMessageDialog(this, "Ticket details confirmed and stored!\n" +
													   currentSelectedSeatNames.size() + " seat(s) booked for " +
													   newBooking.getDestination() + " (" + newBooking.getBusType() + ").\n" +
													   "Total Price: " + newBooking.getFinalTotalPrice() + " Tk",
										  "Booking Confirmed", JOptionPane.INFORMATION_MESSAGE);
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error saving ticket data.", "File Error", JOptionPane.ERROR_MESSAGE);
		}
	}



	private void displayPurchasedTickets()
	{
		try
		{
			File dataFile = new File("PurchaseData.txt");
			if (!dataFile.exists()) {
				purchaseHistoryArea.setText("No purchase history found.");
				return;
			}

			BufferedReader reader = new BufferedReader(new FileReader(dataFile));
			String line;
			StringBuilder content = new StringBuilder();
			int ticketCount = 1;

			while( (line = reader.readLine()) != null )
			{
				String[] parts = line.split(",");

				if(parts.length >= 5)
				{
					String destination = parts[0].trim();
					String busType = parts[1].trim();
					String seats = parts[3].trim();
					String total = parts[4].trim();

					content.append("------------------------------ \n");
					content.append("Ticket #").append(ticketCount++).append("\n");
					content.append("Destination: Dhaka to ").append(destination).append("\n");
					content.append("Bus Type: ").append(busType).append("\n");
					content.append("Seats: ").append(seats.replace(";", ", ")).append("\n");
					content.append("Total Price: ").append(total).append(" Tk\n");
					content.append("------------------------------ \n\n");
				}
			}

			reader.close();
			if (content.length() == 0) {
				purchaseHistoryArea.setText("Purchase history is empty or data is not in the expected format.");
			} else {
				purchaseHistoryArea.setText(content.toString());
			}

		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error reading purchase history.", "File Error", JOptionPane.ERROR_MESSAGE);
		}
	}



	private void clearAllSelections()
	{
		String password = JOptionPane.showInputDialog(this, "Enter Administrator Password to Clear All:", "Admin Access", JOptionPane.WARNING_MESSAGE);
		if (password != null) {
			if (PASSWORD_FOR_CLEAR.equals(password)) {
				bookedSeats.clear(); // Clears all route-specific booked seats for the session

				for (JCheckBox seatBox : allSeatCheckBoxes) {
					seatBox.setSelected(false);
					seatBox.setEnabled(true); // Ensure all checkboxes are enabled
				}

				combo1.setSelectedIndex(0);
				combo2.setSelectedIndex(0);
				purchaseHistoryArea.setText("");
                JOptionPane.showMessageDialog(this, "All selections cleared and seats are reset for this session.", "Selections Cleared", JOptionPane.INFORMATION_MESSAGE);

			} else {
				JOptionPane.showMessageDialog(this, "Incorrect password. Clear operation cancelled.", "Access Denied", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}