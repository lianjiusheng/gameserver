package com.ljs.gameserver.codec;

import com.ljs.gameserver.packet.Request;
import com.ljs.gameserver.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RequestPacketEncoder extends MessageToByteEncoder<Request>
{
	@Override
	protected void encode(ChannelHandlerContext ctx, Request msg, ByteBuf out) throws Exception
	{
		out.writeInt(msg.getOp());
		out.writeLong(msg.getTid());
		String params = msg.getParams();
		ByteBufUtil.writeUTF8(out,params);
	}
	
}
