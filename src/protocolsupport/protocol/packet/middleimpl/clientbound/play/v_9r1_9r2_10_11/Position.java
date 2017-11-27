package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11;

import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePosition;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Position extends MiddlePosition {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_POSITION_ID, connection.getVersion());
		serializer.writeDouble(xOrig);
		serializer.writeDouble(yOrig);
		serializer.writeDouble(zOrig);
		serializer.writeFloat(yawOrig);
		serializer.writeFloat(pitchOrig);
		serializer.writeByte(flags);
		VarNumberSerializer.writeVarInt(serializer, teleportConfirmId);
		return RecyclableSingletonList.create(serializer);
	}

}
