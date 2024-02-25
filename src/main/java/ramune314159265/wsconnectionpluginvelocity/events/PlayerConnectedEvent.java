package ramune314159265.wsconnectionpluginvelocity.events;

public class PlayerConnectedEvent extends Event {
	public String joinedServerId;
	public String playerId;

	public PlayerConnectedEvent(String joinedServerId, String playerId) {
		super();
		this.type = "player_moved";
		this.joinedServerId = joinedServerId;
		this.playerId = playerId;
	}
}
