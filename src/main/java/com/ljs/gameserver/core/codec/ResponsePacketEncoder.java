package com.ljs.gameserver.core.codec;

import com.ljs.gameserver.core.packet.Response;
import com.ljs.gameserver.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ResponsePacketEncoder extends MessageToByteEncoder<Response>
{
	@Override
	protected void encode(ChannelHandlerContext ctx, Response msg, ByteBuf out) throws Exception
	{
		out.writeInt(msg.getOp());
		out.writeLong(msg.getTid());
		out.writeInt(msg.getRs());
		ByteBufUtil.writeUTF8(out,msg.getContent());
	}
	
}
