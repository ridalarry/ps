package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11;

import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetCooldown;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SetCooldown extends MiddleSetCooldown {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_SET_COOLDOWN_ID, connection.getVersion());
		VarNumberSerializer.writeVarInt(serializer, itemId);
		VarNumberSerializer.writeVarInt(serializer, cooldown);
		return RecyclableSingletonList.create(serializer);
	}

}
