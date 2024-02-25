package ramune314159265.wsconnectionpluginvelocity.events;

public class PlayerMovedEvent extends Event {
	public String joinedServerId;
	public String previousJoinedServerId;

	public PlayerMovedEvent(String joinedServerId, String previousJoinedServerId) {
		super();
		this.type = "player_moved";
		this.joinedServerId = joinedServerId;
		this.previousJoinedServerId = previousJoinedServerId;
	}
}
