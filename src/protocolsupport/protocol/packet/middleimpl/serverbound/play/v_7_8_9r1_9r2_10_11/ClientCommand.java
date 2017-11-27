package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_7_8_9r1_9r2_10_11;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleClientCommand;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.Utils;

public class ClientCommand extends MiddleClientCommand {

	private static final Command[] commandsById = new Command[] { Command.REQUEST_RESPAWN, Command.GET_STATS };

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		command = Utils.getFromArrayOrNull(commandsById, VarNumberSerializer.readVarInt(clientdata));
	}

}
