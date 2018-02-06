package com.ljs.gameserver.packet;

public class Request
{
	private int op;
	
	private long tid;
	
	private String params;

	public int getOp()
	{
		return op;
	}

	public void setOp(int op)
	{
		this.op = op;
	}

	public long getTid()
	{
		return tid;
	}

	public void setTid(long tid)
	{
		this.tid = tid;
	}

	public String getParams()
	{
		return params;
	}

	public void setParams(String params)
	{
		this.params = params;
	}

}
