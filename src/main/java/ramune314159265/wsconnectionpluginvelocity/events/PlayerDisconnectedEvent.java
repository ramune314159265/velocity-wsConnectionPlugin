package ramune314159265.wsconnectionpluginvelocity.events;

public class PlayerDisconnectedEvent extends Event {
	public String playerId;

	public PlayerDisconnectedEvent(String playerId) {
		super();
		this.type = "player_disconnected";
		this.playerId = playerId;
	}
}
