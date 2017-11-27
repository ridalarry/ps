package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleTabComplete;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class TabComplete extends MiddleTabComplete {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		string = StringSerializer.readString(clientdata, version);
		if (clientdata.readBoolean()) {
			position = PositionSerializer.readPosition(clientdata);
		}
	}

}
