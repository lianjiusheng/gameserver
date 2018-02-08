package com.ljs.gameserver.core.codec;

import com.ljs.gameserver.core.packet.Request;
import com.ljs.gameserver.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class RequestPacketDecoder extends ByteToMessageDecoder
{
	
	@Override
	protected void decode(ChannelHandlerContext context, ByteBuf byteBuf, List<Object> out) throws Exception
	{
		Request request = new Request();
		request.setTid(byteBuf.readInt());
		request.setTid(byteBuf.readLong());
		request.setParams(ByteBufUtil.readUTF8(byteBuf));
		out.add(request);
	}
}
