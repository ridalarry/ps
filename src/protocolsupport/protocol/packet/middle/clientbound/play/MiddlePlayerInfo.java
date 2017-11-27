package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.events.PlayerPropertiesResolveEvent.ProfileProperty;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.utils.EnumConstantLookups;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.types.GameMode;
import protocolsupport.utils.Utils;

public abstract class MiddlePlayerInfo extends ClientBoundMiddlePacket {

	protected Action action;
	protected Info[] infos;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		action = MiscSerializer.readVarIntEnum(serverdata, Action.CONSTANT_LOOKUP);
		infos = new Info[VarNumberSerializer.readVarInt(serverdata)];
		for (int i = 0; i < infos.length; i++) {
			Info info = new Info();
			info.uuid = MiscSerializer.readUUID(serverdata);
			switch (action) {
				case ADD: {
					info.username = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC, 16);
					info.properties = new ProfileProperty[VarNumberSerializer.readVarInt(serverdata)];
					for (int j = 0; j < info.properties.length; j++) {
						String name = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC);
						String value = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC);
						String signature = null;
						if (serverdata.readBoolean()) {
							signature = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC);
						}
						info.properties[j] = new ProfileProperty(name, value, signature);
					}
					info.gamemode = GameMode.getById(VarNumberSerializer.readVarInt(serverdata));
					info.ping = VarNumberSerializer.readVarInt(serverdata);
					if (serverdata.readBoolean()) {
						info.displayNameJson = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC);
					}
					break;
				}
				case GAMEMODE: {
					info.gamemode = GameMode.getById(VarNumberSerializer.readVarInt(serverdata));
					break;
				}
				case PING: {
					info.ping = VarNumberSerializer.readVarInt(serverdata);
					break;
				}
				case DISPLAY_NAME: {
					if (serverdata.readBoolean()) {
						info.displayNameJson = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC);
					}
					break;
				}
				case REMOVE: {
					break;
				}
			}
			infos[i] = info;
		}
	}

	@Override
	public boolean postFromServerRead() {
		for (Info info : infos) {
			info.previousinfo = cache.getPlayerListEntry(info.uuid);
			if (info.previousinfo != null) {
				info.previousinfo = info.previousinfo.clone();
			}
			switch (action) {
				case ADD: {
					NetworkDataCache.PlayerListEntry entry = new NetworkDataCache.PlayerListEntry(info.username);
					entry.setDisplayNameJson(info.displayNameJson);
					for (ProfileProperty property : info.properties) {
						entry.getProperties().add(property);
					}
					cache.addPlayerListEntry(info.uuid, entry);
					break;
				}
				case DISPLAY_NAME: {
					NetworkDataCache.PlayerListEntry entry = cache.getPlayerListEntry(info.uuid);
					if (entry != null) {
						entry.setDisplayNameJson(info.displayNameJson);
					}
					break;
				}
				case REMOVE: {
					cache.removePlayerListEntry(info.uuid);
					break;
				}
				default: {
					break;
				}
			}
		}
		return true;
	}

	protected static enum Action {
		ADD, GAMEMODE, PING, DISPLAY_NAME, REMOVE;
		public static final EnumConstantLookups.EnumConstantLookup<Action> CONSTANT_LOOKUP = new EnumConstantLookups.EnumConstantLookup<>(Action.class);
	}

	protected static class Info {
		public UUID uuid;
		public NetworkDataCache.PlayerListEntry previousinfo;
		public String username;
		public int ping;
		public GameMode gamemode;
		public String displayNameJson;
		public ProfileProperty[] properties;

		public String getName(String locale) {
			return displayNameJson == null ? username : ChatAPI.fromJSON(displayNameJson).toLegacyText(locale);
		}

		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}
	}

}
