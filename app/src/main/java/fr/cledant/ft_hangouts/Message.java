package fr.cledant.ft_hangouts;

public class Message
{
	private long id;
	private String content;
	private long owner_id;
	private long dest;
	private long time;
	private long status;

	public Message(long id, String content, long owner_id, long dest, long time, long status)
	{
		this.id = id;
		this.content = content;
		this.owner_id = owner_id;
		this.dest = dest;
		this.time = time;
		this.status = status;
	}

	/*
		Setters
	 */

	public void setId(long id)
	{
		this.id = id;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public void setOwner_id(long owner_id)
	{
		this.owner_id = owner_id;
	}

	public void setDest(long dst)
	{
		this.dest = dst;
	}

	public void setTime(long time)
	{
		this.time = time;
	}

	public void setStatus(long status)
	{
		this.status = status;
	}

	/*
		Getters
	 */

	public long getId()
	{
		return id;
	}

	public String getContent()
	{
		return content;
	}

	public long getOwner_id()
	{
		return owner_id;
	}

	public long getDest()
	{
		return dest;
	}

	public long getStatus()
	{
		return status;
	}

	public long getTime()
	{
		return time;
	}
}
