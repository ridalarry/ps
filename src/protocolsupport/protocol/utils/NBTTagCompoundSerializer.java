package protocolsupport.protocol.utils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.MessageFormat;

import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;

public class NBTTagCompoundSerializer {

	public static void writeTag(DataOutput os, NBTTagCompoundWrapper tag) throws IOException {
		if (tag.isNull()) {
			os.writeByte(NBTTagType.END.getId());
			return;
		}
		writeTagHeader(os, "", NBTTagType.COMPOUND);
		writeCompoundPayload(os, tag);
	}

	private static void writeTagHeader(DataOutput os, String name, NBTTagType tag) throws IOException {
		os.writeByte(tag.getId());
		os.writeUTF(name);
	}

	private static void writeCompoundPayload(DataOutput os, NBTTagCompoundWrapper tag) throws IOException {
		for (String key : tag.getKeys()) {
			NBTTagType type = tag.getTagType(key);
			writeTagHeader(os, key, type);
			switch (type) {
				case BYTE: {
					os.writeByte(tag.getIntNumber(key));
					break;
				}
				case SHORT: {
					os.writeShort(tag.getIntNumber(key));
					break;
				}
				case INT: {
					os.writeInt(tag.getIntNumber(key));
					break;
				}
				case LONG: {
					os.writeLong(tag.getLongNumber(key));
					break;
				}
				case FLOAT: {
					os.writeFloat(tag.getFloatNumber(key));
					break;
				}
				case DOUBLE: {
					os.writeDouble(tag.getDoubleNumber(key));
					break;
				}
				case BYTE_ARRAY: {
					byte[] array = tag.getByteArray(key);
					os.writeInt(array.length);
					os.write(array);
					break;
				}
				case INT_ARRAY: {
					int[] array = tag.getIntArray(key);
					os.writeInt(array.length);
					for (int v : array) {
						os.writeInt(v);
					}
					break;
				}
				case LONG_ARRAY: {
					long[] array = tag.getLongArray(key);
					os.writeInt(array.length);
					for (long v : array) {
						os.writeLong(v);
					}
					break;
				}
				case STRING: {
					os.writeUTF(tag.getString(key));
					break;
				}
				case COMPOUND: {
					writeCompoundPayload(os, tag.getCompound(key));
					break;
				}
				case LIST: {
					writeListPayload(os, tag.getList(key));
					break;
				}
				default: {
					throw new IOException(MessageFormat.format("Unknown or unsupported tag type {0}", type));
				}
			}
		}
		os.writeByte(NBTTagType.END.getId());
	}

	private static void writeListPayload(DataOutput os, NBTTagListWrapper tag) throws IOException {
		NBTTagType type = tag.getType();
		os.writeByte(type.getId());
		os.writeInt(tag.size());
		for (int i = 0; i < tag.size(); i++) {
			switch (type) {
				case END: {
					break;
				}
				case BYTE: {
					os.writeByte(tag.getIntNumber(i));
					break;
				}
				case SHORT: {
					os.writeShort(tag.getIntNumber(i));
					break;
				}
				case INT: {
					os.writeInt(tag.getIntNumber(i));
					break;
				}
				case LONG: {
					os.writeLong(tag.getLongNumber(i));
					break;
				}
				case FLOAT: {
					os.writeFloat(tag.getFloatNumber(i));
					break;
				}
				case DOUBLE: {
					os.writeDouble(tag.getDoubleNumber(i));
					break;
				}
				case BYTE_ARRAY: {
					byte[] array = tag.getByteArray(i);
					os.writeInt(array.length);
					os.write(array);
					break;
				}
				case INT_ARRAY: {
					int[] array = tag.getIntArray(i);
					os.writeInt(array.length);
					for (int v : array) {
						os.writeInt(v);
					}
					break;
				}
				case LONG_ARRAY: {
					long[] array = tag.getLongArray(i);
					os.writeInt(array.length);
					for (long v : array) {
						os.writeLong(v);
					}
					break;
				}
				case STRING: {
					os.writeUTF(tag.getString(i));
					break;
				}
				case COMPOUND: {
					writeCompoundPayload(os, tag.getCompound(i));
					break;
				}
				case LIST: {
					writeListPayload(os, tag.getList(i));
					break;
				}
				default: {
					throw new IOException(MessageFormat.format("Unknown or unsupported tag type {0}", type));
				}
			}
		}
	}


	public static NBTTagCompoundWrapper readTag(DataInput is) throws IOException {
		NBTTagType type = NBTTagType.fromId(is.readByte());
		if (type == NBTTagType.END) {
			return NBTTagCompoundWrapper.NULL;
		}
		if (type != NBTTagType.COMPOUND) {
			throw new IOException(MessageFormat.format("Root tag must be compound, got: {0}", type));
		}
		is.readUTF();
		NBTTagCompoundWrapper tag = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
		readCompoundPayload(is, tag);
		return tag;
	}

	private static void readCompoundPayload(DataInput is, NBTTagCompoundWrapper tag) throws IOException {
		NBTTagType type = null;
		while ((type = NBTTagType.fromId(is.readByte())) != NBTTagType.END) {
			String name = is.readUTF();
			switch (type) {
				case BYTE: {
					tag.setByte(name, is.readByte());
					break;
				}
				case SHORT: {
					tag.setShort(name, is.readShort());
					break;
				}
				case INT: {
					tag.setInt(name, is.readInt());
					break;
				}
				case LONG: {
					tag.setLong(name, is.readLong());
					break;
				}
				case FLOAT: {
					tag.setFloat(name, is.readFloat());
					break;
				}
				case DOUBLE: {
					tag.setDouble(name, is.readDouble());
					break;
				}
				case BYTE_ARRAY: {
					byte[] array = new byte[is.readInt()];
					is.readFully(array);
					tag.setByteArray(name, array);
					break;
				}
				case INT_ARRAY: {
					int[] array = new int[is.readInt()];
					for (int j = 0; j < array.length; j++) {
						array[j] = is.readInt();
					}
					tag.setIntArray(name, array);
					break;
				}
				case LONG_ARRAY: {
					long[] array = new long[is.readInt()];
					for (int j = 0; j < array.length; j++) {
						array[j] = is.readLong();
					}
					tag.setLongArray(name, array);
					break;
				}
				case STRING: {
					tag.setString(name, is.readUTF());
					break;
				}
				case COMPOUND: {
					NBTTagCompoundWrapper compound = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
					readCompoundPayload(is, compound);
					tag.setCompound(name, compound);
					break;
				}
				case LIST: {
					NBTTagListWrapper list = ServerPlatform.get().getWrapperFactory().createEmptyNBTList();
					readListPayload(is, list);
					tag.setList(name, list);
					break;
				}
				default: {
					throw new IOException(MessageFormat.format("Unknown or unsupported tag type {0}", type));
				}
			}
		}
	}

	private static void readListPayload(DataInput is, NBTTagListWrapper tag) throws IOException {
		NBTTagType type = NBTTagType.fromId(is.readByte());
		int size = is.readInt();
		if ((type == NBTTagType.END) && (size > 0)) {
			throw new IOException("Missing type");
		}
		for (int i = 0; i < size; i++) {
			switch (type) {
				case BYTE: {
					tag.addByte(is.readByte());
					break;
				}
				case SHORT: {
					tag.addShort(is.readShort());
					break;
				}
				case INT: {
					tag.addInt(is.readInt());
					break;
				}
				case LONG: {
					tag.addLong(is.readLong());
					break;
				}
				case FLOAT: {
					tag.addFloat(is.readFloat());
					break;
				}
				case DOUBLE: {
					tag.addDouble(is.readDouble());
					break;
				}
				case BYTE_ARRAY: {
					byte[] array = new byte[is.readInt()];
					is.readFully(array);
					tag.addByteArray(array);
					break;
				}
				case INT_ARRAY: {
					int[] array = new int[is.readInt()];
					for (int j = 0; j < array.length; j++) {
						array[j] = is.readInt();
					}
					tag.addIntArray(array);
					break;
				}
				case LONG_ARRAY: {
					long[] array = new long[is.readInt()];
					for (int j = 0; j < array.length; j++) {
						array[j] = is.readLong();
					}
					tag.addLongArray(array);
					break;
				}
				case STRING: {
					tag.addString(is.readUTF());
					break;
				}
				case COMPOUND: {
					NBTTagCompoundWrapper compound = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
					readCompoundPayload(is, compound);
					tag.addCompound(compound);
					break;
				}
				case LIST: {
					NBTTagListWrapper list = ServerPlatform.get().getWrapperFactory().createEmptyNBTList();
					readListPayload(is, list);
					tag.addList(list);
					break;
				}
				default: {
					throw new IOException(MessageFormat.format("Unknown or unsupported tag type {0}", type));
				}
			}
		}
	}

}
