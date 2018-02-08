package com.ljs.gameserver.core.codec;

import com.ljs.gameserver.core.packet.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class ResponsePacketDecoder extends ByteToMessageDecoder
{
	
	@Override
	protected void decode(ChannelHandlerContext context, ByteBuf byteBuf, List<Object> out) throws Exception
	{
		Response request = new Response();
		request.setTid(byteBuf.readInt());
		request.setTid(byteBuf.readLong());
		request.setRs(byteBuf.readInt());
		request.setContent(readString(byteBuf));
		out.add(request);
	}
	
	private String readString(ByteBuf byteBuf) throws UnsupportedEncodingException
	{
		
		int length = byteBuf.readInt();
		
		ByteBuf buf = byteBuf.readBytes(length);
		
		return new String(buf.array(), CharsetUtil.UTF_8);
	}
	
}
