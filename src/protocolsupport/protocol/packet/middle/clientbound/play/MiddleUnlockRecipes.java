package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.utils.EnumConstantLookups;

public abstract class MiddleUnlockRecipes extends ClientBoundMiddlePacket {

	protected Action action;
	protected boolean openBook;
	protected boolean enableFiltering;
	protected int[] recipes1;
	protected int[] recipes2;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		action = MiscSerializer.readVarIntEnum(serverdata, Action.CONSTANT_LOOKUP);
		openBook = serverdata.readBoolean();
		enableFiltering = serverdata.readBoolean();
		recipes1 = ArraySerializer.readVarIntVarIntArray(serverdata);
		if (action == Action.INIT) {
			recipes2 = ArraySerializer.readVarIntVarIntArray(serverdata);
		}
	}

	protected static enum Action {
		INIT, ADD, REMOVE;
		public static final EnumConstantLookups.EnumConstantLookup<Action> CONSTANT_LOOKUP = new EnumConstantLookups.EnumConstantLookup<>(Action.class);
	}

}
