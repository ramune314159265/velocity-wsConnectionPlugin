package ramune314159265.wsconnectionpluginvelocity.events;

import java.util.Date;

public class Event {
	public String type;
	public Long timestamp;

	public Event() {
		this.timestamp = new Date().getTime();
	}
}
