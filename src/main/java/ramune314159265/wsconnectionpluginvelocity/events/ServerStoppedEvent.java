package ramune314159265.wsconnectionpluginvelocity.events;

import ramune314159265.wsconnectionpluginvelocity.WsConnectionPluginVelocity;

public class ServerStoppedEvent extends Event {
	public String serverId;

	public ServerStoppedEvent() {
		this.type = "server_stopped";
		this.serverId = WsConnectionPluginVelocity.serverId;
	}
}
