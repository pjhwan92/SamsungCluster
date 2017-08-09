/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package alluxio.thrift;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
/**
 * Information about a key-value partition.
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2017-08-08")
public class PartitionInfo implements org.apache.thrift.TBase<PartitionInfo, PartitionInfo._Fields>, java.io.Serializable, Cloneable, Comparable<PartitionInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PartitionInfo");

  private static final org.apache.thrift.protocol.TField KEY_START_FIELD_DESC = new org.apache.thrift.protocol.TField("keyStart", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField KEY_LIMIT_FIELD_DESC = new org.apache.thrift.protocol.TField("keyLimit", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField BLOCK_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("blockId", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField KEY_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("keyCount", org.apache.thrift.protocol.TType.I32, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new PartitionInfoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new PartitionInfoTupleSchemeFactory());
  }

  private ByteBuffer keyStart; // required
  private ByteBuffer keyLimit; // required
  private long blockId; // required
  private int keyCount; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    KEY_START((short)1, "keyStart"),
    KEY_LIMIT((short)2, "keyLimit"),
    BLOCK_ID((short)3, "blockId"),
    KEY_COUNT((short)4, "keyCount");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // KEY_START
          return KEY_START;
        case 2: // KEY_LIMIT
          return KEY_LIMIT;
        case 3: // BLOCK_ID
          return BLOCK_ID;
        case 4: // KEY_COUNT
          return KEY_COUNT;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __BLOCKID_ISSET_ID = 0;
  private static final int __KEYCOUNT_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.KEY_START, new org.apache.thrift.meta_data.FieldMetaData("keyStart", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , true)));
    tmpMap.put(_Fields.KEY_LIMIT, new org.apache.thrift.meta_data.FieldMetaData("keyLimit", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , true)));
    tmpMap.put(_Fields.BLOCK_ID, new org.apache.thrift.meta_data.FieldMetaData("blockId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.KEY_COUNT, new org.apache.thrift.meta_data.FieldMetaData("keyCount", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PartitionInfo.class, metaDataMap);
  }

  public PartitionInfo() {
  }

  public PartitionInfo(
    ByteBuffer keyStart,
    ByteBuffer keyLimit,
    long blockId,
    int keyCount)
  {
    this();
    this.keyStart = org.apache.thrift.TBaseHelper.copyBinary(keyStart);
    this.keyLimit = org.apache.thrift.TBaseHelper.copyBinary(keyLimit);
    this.blockId = blockId;
    setBlockIdIsSet(true);
    this.keyCount = keyCount;
    setKeyCountIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public PartitionInfo(PartitionInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetKeyStart()) {
      this.keyStart = org.apache.thrift.TBaseHelper.copyBinary(other.keyStart);
    }
    if (other.isSetKeyLimit()) {
      this.keyLimit = org.apache.thrift.TBaseHelper.copyBinary(other.keyLimit);
    }
    this.blockId = other.blockId;
    this.keyCount = other.keyCount;
  }

  public PartitionInfo deepCopy() {
    return new PartitionInfo(this);
  }

  @Override
  public void clear() {
    this.keyStart = null;
    this.keyLimit = null;
    setBlockIdIsSet(false);
    this.blockId = 0;
    setKeyCountIsSet(false);
    this.keyCount = 0;
  }

  public byte[] getKeyStart() {
    setKeyStart(org.apache.thrift.TBaseHelper.rightSize(keyStart));
    return keyStart == null ? null : keyStart.array();
  }

  public ByteBuffer bufferForKeyStart() {
    return org.apache.thrift.TBaseHelper.copyBinary(keyStart);
  }

  public PartitionInfo setKeyStart(byte[] keyStart) {
    this.keyStart = keyStart == null ? (ByteBuffer)null : ByteBuffer.wrap(Arrays.copyOf(keyStart, keyStart.length));
    return this;
  }

  public PartitionInfo setKeyStart(ByteBuffer keyStart) {
    this.keyStart = org.apache.thrift.TBaseHelper.copyBinary(keyStart);
    return this;
  }

  public void unsetKeyStart() {
    this.keyStart = null;
  }

  /** Returns true if field keyStart is set (has been assigned a value) and false otherwise */
  public boolean isSetKeyStart() {
    return this.keyStart != null;
  }

  public void setKeyStartIsSet(boolean value) {
    if (!value) {
      this.keyStart = null;
    }
  }

  public byte[] getKeyLimit() {
    setKeyLimit(org.apache.thrift.TBaseHelper.rightSize(keyLimit));
    return keyLimit == null ? null : keyLimit.array();
  }

  public ByteBuffer bufferForKeyLimit() {
    return org.apache.thrift.TBaseHelper.copyBinary(keyLimit);
  }

  public PartitionInfo setKeyLimit(byte[] keyLimit) {
    this.keyLimit = keyLimit == null ? (ByteBuffer)null : ByteBuffer.wrap(Arrays.copyOf(keyLimit, keyLimit.length));
    return this;
  }

  public PartitionInfo setKeyLimit(ByteBuffer keyLimit) {
    this.keyLimit = org.apache.thrift.TBaseHelper.copyBinary(keyLimit);
    return this;
  }

  public void unsetKeyLimit() {
    this.keyLimit = null;
  }

  /** Returns true if field keyLimit is set (has been assigned a value) and false otherwise */
  public boolean isSetKeyLimit() {
    return this.keyLimit != null;
  }

  public void setKeyLimitIsSet(boolean value) {
    if (!value) {
      this.keyLimit = null;
    }
  }

  public long getBlockId() {
    return this.blockId;
  }

  public PartitionInfo setBlockId(long blockId) {
    this.blockId = blockId;
    setBlockIdIsSet(true);
    return this;
  }

  public void unsetBlockId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __BLOCKID_ISSET_ID);
  }

  /** Returns true if field blockId is set (has been assigned a value) and false otherwise */
  public boolean isSetBlockId() {
    return EncodingUtils.testBit(__isset_bitfield, __BLOCKID_ISSET_ID);
  }

  public void setBlockIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __BLOCKID_ISSET_ID, value);
  }

  public int getKeyCount() {
    return this.keyCount;
  }

  public PartitionInfo setKeyCount(int keyCount) {
    this.keyCount = keyCount;
    setKeyCountIsSet(true);
    return this;
  }

  public void unsetKeyCount() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __KEYCOUNT_ISSET_ID);
  }

  /** Returns true if field keyCount is set (has been assigned a value) and false otherwise */
  public boolean isSetKeyCount() {
    return EncodingUtils.testBit(__isset_bitfield, __KEYCOUNT_ISSET_ID);
  }

  public void setKeyCountIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __KEYCOUNT_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case KEY_START:
      if (value == null) {
        unsetKeyStart();
      } else {
        setKeyStart((ByteBuffer)value);
      }
      break;

    case KEY_LIMIT:
      if (value == null) {
        unsetKeyLimit();
      } else {
        setKeyLimit((ByteBuffer)value);
      }
      break;

    case BLOCK_ID:
      if (value == null) {
        unsetBlockId();
      } else {
        setBlockId((Long)value);
      }
      break;

    case KEY_COUNT:
      if (value == null) {
        unsetKeyCount();
      } else {
        setKeyCount((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case KEY_START:
      return getKeyStart();

    case KEY_LIMIT:
      return getKeyLimit();

    case BLOCK_ID:
      return getBlockId();

    case KEY_COUNT:
      return getKeyCount();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case KEY_START:
      return isSetKeyStart();
    case KEY_LIMIT:
      return isSetKeyLimit();
    case BLOCK_ID:
      return isSetBlockId();
    case KEY_COUNT:
      return isSetKeyCount();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof PartitionInfo)
      return this.equals((PartitionInfo)that);
    return false;
  }

  public boolean equals(PartitionInfo that) {
    if (that == null)
      return false;

    boolean this_present_keyStart = true && this.isSetKeyStart();
    boolean that_present_keyStart = true && that.isSetKeyStart();
    if (this_present_keyStart || that_present_keyStart) {
      if (!(this_present_keyStart && that_present_keyStart))
        return false;
      if (!this.keyStart.equals(that.keyStart))
        return false;
    }

    boolean this_present_keyLimit = true && this.isSetKeyLimit();
    boolean that_present_keyLimit = true && that.isSetKeyLimit();
    if (this_present_keyLimit || that_present_keyLimit) {
      if (!(this_present_keyLimit && that_present_keyLimit))
        return false;
      if (!this.keyLimit.equals(that.keyLimit))
        return false;
    }

    boolean this_present_blockId = true;
    boolean that_present_blockId = true;
    if (this_present_blockId || that_present_blockId) {
      if (!(this_present_blockId && that_present_blockId))
        return false;
      if (this.blockId != that.blockId)
        return false;
    }

    boolean this_present_keyCount = true;
    boolean that_present_keyCount = true;
    if (this_present_keyCount || that_present_keyCount) {
      if (!(this_present_keyCount && that_present_keyCount))
        return false;
      if (this.keyCount != that.keyCount)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_keyStart = true && (isSetKeyStart());
    list.add(present_keyStart);
    if (present_keyStart)
      list.add(keyStart);

    boolean present_keyLimit = true && (isSetKeyLimit());
    list.add(present_keyLimit);
    if (present_keyLimit)
      list.add(keyLimit);

    boolean present_blockId = true;
    list.add(present_blockId);
    if (present_blockId)
      list.add(blockId);

    boolean present_keyCount = true;
    list.add(present_keyCount);
    if (present_keyCount)
      list.add(keyCount);

    return list.hashCode();
  }

  @Override
  public int compareTo(PartitionInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetKeyStart()).compareTo(other.isSetKeyStart());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetKeyStart()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.keyStart, other.keyStart);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetKeyLimit()).compareTo(other.isSetKeyLimit());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetKeyLimit()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.keyLimit, other.keyLimit);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBlockId()).compareTo(other.isSetBlockId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBlockId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.blockId, other.blockId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetKeyCount()).compareTo(other.isSetKeyCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetKeyCount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.keyCount, other.keyCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("PartitionInfo(");
    boolean first = true;

    sb.append("keyStart:");
    if (this.keyStart == null) {
      sb.append("null");
    } else {
      org.apache.thrift.TBaseHelper.toString(this.keyStart, sb);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("keyLimit:");
    if (this.keyLimit == null) {
      sb.append("null");
    } else {
      org.apache.thrift.TBaseHelper.toString(this.keyLimit, sb);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("blockId:");
    sb.append(this.blockId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("keyCount:");
    sb.append(this.keyCount);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class PartitionInfoStandardSchemeFactory implements SchemeFactory {
    public PartitionInfoStandardScheme getScheme() {
      return new PartitionInfoStandardScheme();
    }
  }

  private static class PartitionInfoStandardScheme extends StandardScheme<PartitionInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, PartitionInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // KEY_START
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.keyStart = iprot.readBinary();
              struct.setKeyStartIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // KEY_LIMIT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.keyLimit = iprot.readBinary();
              struct.setKeyLimitIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // BLOCK_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.blockId = iprot.readI64();
              struct.setBlockIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // KEY_COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.keyCount = iprot.readI32();
              struct.setKeyCountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, PartitionInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.keyStart != null) {
        oprot.writeFieldBegin(KEY_START_FIELD_DESC);
        oprot.writeBinary(struct.keyStart);
        oprot.writeFieldEnd();
      }
      if (struct.keyLimit != null) {
        oprot.writeFieldBegin(KEY_LIMIT_FIELD_DESC);
        oprot.writeBinary(struct.keyLimit);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(BLOCK_ID_FIELD_DESC);
      oprot.writeI64(struct.blockId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(KEY_COUNT_FIELD_DESC);
      oprot.writeI32(struct.keyCount);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PartitionInfoTupleSchemeFactory implements SchemeFactory {
    public PartitionInfoTupleScheme getScheme() {
      return new PartitionInfoTupleScheme();
    }
  }

  private static class PartitionInfoTupleScheme extends TupleScheme<PartitionInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, PartitionInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetKeyStart()) {
        optionals.set(0);
      }
      if (struct.isSetKeyLimit()) {
        optionals.set(1);
      }
      if (struct.isSetBlockId()) {
        optionals.set(2);
      }
      if (struct.isSetKeyCount()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetKeyStart()) {
        oprot.writeBinary(struct.keyStart);
      }
      if (struct.isSetKeyLimit()) {
        oprot.writeBinary(struct.keyLimit);
      }
      if (struct.isSetBlockId()) {
        oprot.writeI64(struct.blockId);
      }
      if (struct.isSetKeyCount()) {
        oprot.writeI32(struct.keyCount);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, PartitionInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.keyStart = iprot.readBinary();
        struct.setKeyStartIsSet(true);
      }
      if (incoming.get(1)) {
        struct.keyLimit = iprot.readBinary();
        struct.setKeyLimitIsSet(true);
      }
      if (incoming.get(2)) {
        struct.blockId = iprot.readI64();
        struct.setBlockIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.keyCount = iprot.readI32();
        struct.setKeyCountIsSet(true);
      }
    }
  }

}

