package ramune314159265.wsconnectionpluginvelocity.events;

import java.time.Instant;
import java.util.Date;

public class Event {
	public String type;
	public Instant timestamp;
	public Event(){
		this.timestamp = new Date().toInstant();
	}
}
