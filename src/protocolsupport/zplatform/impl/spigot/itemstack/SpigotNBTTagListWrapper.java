package protocolsupport.zplatform.impl.spigot.itemstack;

import java.util.Objects;

import net.minecraft.server.v1_11_R1.NBTTagByte;
import net.minecraft.server.v1_11_R1.NBTTagInt;
import net.minecraft.server.v1_11_R1.NBTTagList;
import net.minecraft.server.v1_11_R1.NBTTagString;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;

public class SpigotNBTTagListWrapper extends NBTTagListWrapper {

	protected final NBTTagList tag;
	protected SpigotNBTTagListWrapper(NBTTagList tag) {
		this.tag = tag;
	}

	public static SpigotNBTTagListWrapper create() {
		return new SpigotNBTTagListWrapper(new NBTTagList());
	}

	@Override
	public boolean isEmpty() {
		return tag.isEmpty();
	}

	@Override
	public int size() {
		return tag.size();
	}

	@Override
	public NBTTagCompoundWrapper getCompound(int index) {
		return new SpigotNBTTagCompoundWrapper(tag.get(index));
	}

	@Override
	public void addCompound(NBTTagCompoundWrapper wrapper) {
		tag.add(((SpigotNBTTagCompoundWrapper) wrapper).tag);
	}

	@Override
	public String getString(int index) {
		return tag.getString(index);
	}

	@Override
	public void addString(String value) {
		tag.add(new NBTTagString(value));
	}

	public int getNumber(int index) {
		return tag.c(index);
	}

	@Override
	public void addInt(int i) {
		tag.add(new NBTTagInt(i));
	}

	@Override
	public void addByte(int b) {
		tag.add(new NBTTagByte((byte) b));
	}

	@Override
	public int hashCode() {
		return tag != null ? tag.hashCode() : 0;
	}

	@Override
	public boolean equals(Object otherObj) {
		if (!(otherObj instanceof SpigotNBTTagListWrapper)) {
			return false;
		}
		SpigotNBTTagListWrapper other = (SpigotNBTTagListWrapper) otherObj;
		return Objects.equals(tag, other.tag);
	}

	@Override
	public String toString() {
		return tag.toString();
	}

	@Override
	public NBTTagType getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NBTTagListWrapper getList(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIntNumber(int index) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLongNumber(int index) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getFloatNumber(int index) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDoubleNumber(int index) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte[] getByteArray(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getIntArray(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long[] getLongArray(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addList(NBTTagListWrapper wrapper) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addShort(int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addLong(long value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addFloat(float value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addDouble(double value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addByteArray(byte[] value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addIntArray(int[] value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addLongArray(long[] value) {
		// TODO Auto-generated method stub
		
	}

}