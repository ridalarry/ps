package protocolsupport.protocol.pipeline.version;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.Connection;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.NetworkDataCache;

public class AbstractModernWithoutReorderPacketDecoder extends AbstractPacketDecoder {

	public AbstractModernWithoutReorderPacketDecoder(Connection connection, NetworkDataCache sharedstorage) {
		super(connection, sharedstorage);
	}

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf input, List<Object> list) throws Exception {
		if (!input.isReadable()) {
			return;
		}
		ServerBoundMiddlePacket packetTransformer = null;
		try {
			packetTransformer = registry.getTransformer(connection.getNetworkState(), VarNumberSerializer.readVarInt(input));
			packetTransformer.readFromClientData(input);
			if (input.isReadable()) {
				throw new DecoderException("Did not read all data from packet " + packetTransformer.getClass().getName() + ", bytes left: " + input.readableBytes());
			}
			addPackets(packetTransformer.toNative(), list);
		} catch (Exception e) {
			throwFailedTransformException(e, packetTransformer, input);
		}
	}

}
