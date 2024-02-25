package ramune314159265.wsconnectionpluginvelocity.events;

public class PlayerConnectedEvent extends Event{
	public String joinedServerId;
	public PlayerConnectedEvent(String joinedServerId){
		super();
		this.type = "player_moved";
		this.joinedServerId = joinedServerId;
	}
}
