package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.id.IdSkipper;

public abstract class MiddleEntityEffectAdd extends MiddleEntity {

	protected int effectId;
	protected int amplifier;
	protected int duration;
	protected boolean hideParticles;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		effectId = serverdata.readByte();
		amplifier = serverdata.readByte();
		duration = VarNumberSerializer.readVarInt(serverdata);
		hideParticles = serverdata.readBoolean();
	}

	@Override
	public boolean postFromServerRead() {
		return !IdSkipper.EFFECT.getTable(connection.getVersion()).shouldSkip(effectId);
	}

}
