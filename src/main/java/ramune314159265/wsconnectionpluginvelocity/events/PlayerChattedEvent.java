package ramune314159265.wsconnectionpluginvelocity.events;

public class PlayerChattedEvent extends Event {
	public String content;
	public String playerId;
	public String serverId;

	public PlayerChattedEvent(String content, String playerId,String serverId) {
		super();
		this.type = "player_chatted";
		this.content = content;
		this.playerId = playerId;
		this.serverId = serverId;
	}
}
