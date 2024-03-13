package ramune314159265.wsconnectionpluginvelocity.events;

public class PlayerChattedEvent extends Event {
	public String content;
	public String playerId;

	public PlayerChattedEvent(String content, String playerId) {
		super();
		this.type = "player_chatted";
		this.content = content;
		this.playerId = playerId;
	}
}
