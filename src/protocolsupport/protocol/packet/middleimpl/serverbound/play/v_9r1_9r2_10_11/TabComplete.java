package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleTabComplete;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class TabComplete extends MiddleTabComplete {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		string = StringSerializer.readString(clientdata, connection.getVersion());
		assumecommand = clientdata.readBoolean();
		if (clientdata.readBoolean()) {
			position = PositionSerializer.readPosition(clientdata);
		} else {
			position = null;
		}
	}

}
