package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockDig;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;

public class BlockDig extends MiddleBlockDig {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		status = MiscSerializer.readVarIntEnum(clientdata, Action.CONSTANT_LOOKUP);
		PositionSerializer.readPositionTo(clientdata, position);
		face = clientdata.readUnsignedByte();
	}

}
