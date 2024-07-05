package ramune314159265.wsconnectionpluginvelocity;

public class ReceivedData {
	public String type;
	public String content;
	public Number timestamp;
	public ReceivedData(String type,String content,Number timestamp){
		this.type = type;
		this.content = content;
		this.timestamp = timestamp;
	}
}
