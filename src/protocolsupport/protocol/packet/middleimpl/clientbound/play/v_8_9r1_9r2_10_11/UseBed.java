package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11;

import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleUseBed;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class UseBed extends MiddleUseBed {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_BED_ID, connection.getVersion());
		VarNumberSerializer.writeVarInt(serializer, entityId);
		PositionSerializer.writePosition(serializer, bed);
		return RecyclableSingletonList.create(serializer);
	}

}
