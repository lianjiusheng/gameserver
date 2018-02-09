package com.ljs.mg.util;

import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;

import java.io.UnsupportedEncodingException;

public class ByteBufUtil {


    public static int writeUTF8(ByteBuf buf, String s){

        int writeIndex=buf.writerIndex();

        String content = s;
        if (content == null)
        {
            buf.writeInt(0);
        }
        else
        {
            byte[] data = content.getBytes(CharsetUtil.UTF_8);
            buf.writeInt(data.length);
            buf.writeBytes(data);
        }

        return buf.writerIndex()-writeIndex;
    }



    public static String readUTF8(ByteBuf byteBuf) throws UnsupportedEncodingException
    {

        int length = byteBuf.readInt();

        ByteBuf buf = byteBuf.readBytes(length);

        return new String(buf.array(), CharsetUtil.UTF_8);
    }

}
