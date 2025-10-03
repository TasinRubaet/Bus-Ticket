import java.util.List;
import java.util.StringJoiner;

public class Info
{
	private String destination;
	private String busType;
	private List<String> selectedSeatsList;
	private int individualTicketPrice;
	private int finalTotalPrice;

	public Info(String destination, String busType, List<String> selectedSeatsList, int individualTicketPrice)
	{
		this.destination = destination;
		this.busType = busType;
		this.selectedSeatsList = selectedSeatsList;
		this.individualTicketPrice = individualTicketPrice;
		this.finalTotalPrice = calculateFinalTotalPrice();
	}

	public String getDestination(){return destination;}
	public String getBusType(){return busType;}
	public List<String> getSelectedSeatsList(){return selectedSeatsList;}
	public String getSelectedSeatsString() {
		if (selectedSeatsList == null || selectedSeatsList.isEmpty()) {
			return "";
		}
		StringJoiner joiner = new StringJoiner(";");
		for (String seat : selectedSeatsList) {
			joiner.add(seat);
		}
		return joiner.toString();
	}
	public int getIndividualTicketPrice(){return individualTicketPrice;}
	public int getFinalTotalPrice(){return finalTotalPrice;}

	private int calculateFinalTotalPrice()
	{
		if(selectedSeatsList == null) {
			return 0;
		}
		return selectedSeatsList.size() * individualTicketPrice;
	}
}