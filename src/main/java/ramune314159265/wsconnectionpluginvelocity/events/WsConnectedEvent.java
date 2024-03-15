package ramune314159265.wsconnectionpluginvelocity.events;

import ramune314159265.wsconnectionpluginvelocity.WsConnectionPluginVelocity;

public class WsConnectedEvent extends Event{
	public String serverId;
	public WsConnectedEvent(){
		this.type = "ws_connected";
		this.serverId = WsConnectionPluginVelocity.serverId;
	}
}
