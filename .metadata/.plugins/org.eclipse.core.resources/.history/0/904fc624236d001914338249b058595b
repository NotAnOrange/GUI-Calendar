import java.time.LocalTime;

public class Event {

	private String myName;
	private LocalTime myStartTime;
	private LocalTime myEndTime;
	
	public Event(String name, LocalTime start, LocalTime end)
	{
		myName = name;
		myStartTime = start;
		myEndTime = end;
	}
	
	public boolean overlap(Event e)
	{
		if((this.myStartTime.isBefore(e.getEnd())) && (this.myStartTime.isAfter(e.getEnd())))
		{
			return true;
		}
		else if((this.myStartTime.isBefore(e.getStart())) && (this.myEndTime.isAfter(e.getStart())))
		{
			return true;
		}		
		return false;
	}
	
	public LocalTime getStart()
	{
		return myStartTime;
	}
	
	public LocalTime getEnd()
	{
		return myEndTime;
	}
	
	public String getName()
	{
		return myName;
	}
	
	public String toString()
	{
		return myStartTime.toString() + " - " + myEndTime.toString();
	}
}
