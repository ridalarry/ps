package protocolsupport.protocol.packet.middleimpl.serverbound.status.v_7_8_9r1_9r2_10_11;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.status.MiddlePing;

public class Ping extends MiddlePing {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		pingId = clientdata.readLong();
	}

}
