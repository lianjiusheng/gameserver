package com.ljs.gameserver.packet;

public class Response
{
	
	private int op;
	
	private int rs;
	
	private long tid;
	
	private String content;

	public int getOp()
	{
		return op;
	}

	public void setOp(int op)
	{
		this.op = op;
	}

	public int getRs()
	{
		return rs;
	}

	public void setRs(int rs)
	{
		this.rs = rs;
	}

	public long getTid()
	{
		return tid;
	}

	public void setTid(long tid)
	{
		this.tid = tid;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

}
