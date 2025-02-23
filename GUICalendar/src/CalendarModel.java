import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CalendarModel implements java.io.Serializable {
	
	//Data
	private Calendar myCal; 
	private GregorianCalendar myGCal; 
	private HashMap<LocalDate, ArrayList<Event>> events;

	//ChangeListeners
	private ArrayList<ChangeListener> listeners;
	
	public CalendarModel()
	{
		myCal = Calendar.getInstance(); 
		myGCal = new GregorianCalendar(); 
		listeners = new ArrayList<>();
		events = new HashMap<>();
	}
	
	public CalendarModel(HashMap<LocalDate, ArrayList<Event>> inEvents)
	{
		myCal = Calendar.getInstance(); 
		myGCal = new GregorianCalendar(); 
		listeners = new ArrayList<>();
		events = inEvents;
	}
	
	
	public boolean addEvent(Event e)
	{
		Date d = myGCal.getTime();
		LocalDate date = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		ArrayList<Event> dateEvents = events.get(date);
		int counter = 0;
		boolean valid = true;
		if(dateEvents == null)
		{
			ArrayList<Event> newArrayList = new ArrayList<>();
			events.put(date, newArrayList);
			events.get(date).add(e);
		}
		else
		{
			while(valid && (counter < dateEvents.size()))
			{
				if(dateEvents.get(counter).overlap(e))
				{
					valid = false;
				}
				counter++;
			}
			if(valid)
			{
				dateEvents.add(e);
				ChangeEvent event = new ChangeEvent(this);
				for(ChangeListener c: listeners)
				{
					c.stateChanged(event);
				}
			}
			return valid;
		}
		ChangeEvent event = new ChangeEvent(this);
		for(ChangeListener c: listeners)
		{
			c.stateChanged(event);
		}
		return true;
	}
	
	
	public void setCalendarDayOfMonth(int day)
	{
		myGCal.set(Calendar.DAY_OF_MONTH, day);
		ChangeEvent event = new ChangeEvent(this);
		for(ChangeListener c: listeners)
		{
			c.stateChanged(event);
		}
	}
	
	public void attach(ChangeListener c)
	{
		listeners.add(c);
	}
	
	public void addMonth(int amount)
	{
		myGCal.add(myCal.MONTH, amount);
		ChangeEvent event = new ChangeEvent(this);
		for(ChangeListener c: listeners)
		{
			c.stateChanged(event);
		}
	}
	
	public void addDays(int amount)
	{
		myGCal.add(myCal.DAY_OF_MONTH, amount);
		ChangeEvent event = new ChangeEvent(this);
		for(ChangeListener c: listeners)
		{
			c.stateChanged(event);
		}
	}
	
	public Calendar getCalendar()
	{
		return myCal;
	}
	
	public GregorianCalendar getGCalendar()
	{
		return myGCal;
	}
	
	public boolean serialize()
	{
		try
        {    
            //Saving of object in a file 
            FileOutputStream file = new FileOutputStream("events.txt"); 
            ObjectOutputStream out = new ObjectOutputStream(file); 
              
            // Method for serialization of object 
            out.writeObject(events); 
              
            out.close(); 
            file.close(); 
            
        	return true;
        } 
          
        catch(IOException ex) 
        { 
        	return false;
        } 
	}
	
	public ArrayList<Event> getEventList()
	{
		Date d = myGCal.getTime();
		LocalDate date = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return events.get(date);
	}
}

