package ramune314159265.wsconnectionpluginvelocity.events;

import ramune314159265.wsconnectionpluginvelocity.WsConnectionPluginVelocity;

public class PlayerChattedEvent extends Event {
	public String content;
	public String playerId;
	public String serverId;
	public String proxyId;

	public PlayerChattedEvent(String content, String playerId,String serverId) {
		super();
		this.type = "player_chatted";
		this.content = content;
		this.playerId = playerId;
		this.serverId = serverId;
		this.proxyId = WsConnectionPluginVelocity.serverId;
	}
}
