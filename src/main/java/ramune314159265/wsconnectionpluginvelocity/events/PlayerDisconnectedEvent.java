package ramune314159265.wsconnectionpluginvelocity.events;

public class PlayerDisconnectedEvent extends Event {
	public String previousJoinedServerId;
	public String playerId;

	public PlayerDisconnectedEvent(String playerId, String previousJoinedServerId) {
		super();
		this.type = "player_disconnected";
		this.previousJoinedServerId = previousJoinedServerId;
		this.playerId = playerId;
	}
}
