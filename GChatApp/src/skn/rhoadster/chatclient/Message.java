package skn.rhoadster.chatclient;

import java.io.Serializable;

public class Message implements Serializable
{
	private static final long serialVersionUID = -2374106916463521289L;
	public String content;
	public String room;
	public String sender;
	public Object payload;
}
