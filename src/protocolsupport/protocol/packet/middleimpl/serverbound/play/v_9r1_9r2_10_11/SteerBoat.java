package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSteerBoat;

public class SteerBoat extends MiddleSteerBoat {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		rightPaddleTurning = clientdata.readBoolean();
		leftPaddleTurning = clientdata.readBoolean();
	}

}
