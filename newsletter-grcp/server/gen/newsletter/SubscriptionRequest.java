// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: newsletter.proto

// Protobuf Java Version: 4.26.1
package newsletter;

/**
 * Protobuf type {@code newsletter.SubscriptionRequest}
 */
public final class SubscriptionRequest extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:newsletter.SubscriptionRequest)
    SubscriptionRequestOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 26,
      /* patch= */ 1,
      /* suffix= */ "",
      SubscriptionRequest.class.getName());
  }
  // Use SubscriptionRequest.newBuilder() to construct.
  private SubscriptionRequest(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private SubscriptionRequest() {
    city_ = "";
    tags_ =
        com.google.protobuf.LazyStringArrayList.emptyList();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return newsletter.NewsletterProto.internal_static_newsletter_SubscriptionRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return newsletter.NewsletterProto.internal_static_newsletter_SubscriptionRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            newsletter.SubscriptionRequest.class, newsletter.SubscriptionRequest.Builder.class);
  }

  public static final int USERID_FIELD_NUMBER = 1;
  private int userId_ = 0;
  /**
   * <code>int32 userId = 1;</code>
   * @return The userId.
   */
  @java.lang.Override
  public int getUserId() {
    return userId_;
  }

  public static final int CITY_FIELD_NUMBER = 2;
  @SuppressWarnings("serial")
  private volatile java.lang.Object city_ = "";
  /**
   * <code>string city = 2;</code>
   * @return The city.
   */
  @java.lang.Override
  public java.lang.String getCity() {
    java.lang.Object ref = city_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      city_ = s;
      return s;
    }
  }
  /**
   * <code>string city = 2;</code>
   * @return The bytes for city.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getCityBytes() {
    java.lang.Object ref = city_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      city_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int TAGS_FIELD_NUMBER = 3;
  @SuppressWarnings("serial")
  private com.google.protobuf.LazyStringArrayList tags_ =
      com.google.protobuf.LazyStringArrayList.emptyList();
  /**
   * <code>repeated string tags = 3;</code>
   * @return A list containing the tags.
   */
  public com.google.protobuf.ProtocolStringList
      getTagsList() {
    return tags_;
  }
  /**
   * <code>repeated string tags = 3;</code>
   * @return The count of tags.
   */
  public int getTagsCount() {
    return tags_.size();
  }
  /**
   * <code>repeated string tags = 3;</code>
   * @param index The index of the element to return.
   * @return The tags at the given index.
   */
  public java.lang.String getTags(int index) {
    return tags_.get(index);
  }
  /**
   * <code>repeated string tags = 3;</code>
   * @param index The index of the value to return.
   * @return The bytes of the tags at the given index.
   */
  public com.google.protobuf.ByteString
      getTagsBytes(int index) {
    return tags_.getByteString(index);
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (userId_ != 0) {
      output.writeInt32(1, userId_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(city_)) {
      com.google.protobuf.GeneratedMessage.writeString(output, 2, city_);
    }
    for (int i = 0; i < tags_.size(); i++) {
      com.google.protobuf.GeneratedMessage.writeString(output, 3, tags_.getRaw(i));
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (userId_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, userId_);
    }
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(city_)) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(2, city_);
    }
    {
      int dataSize = 0;
      for (int i = 0; i < tags_.size(); i++) {
        dataSize += computeStringSizeNoTag(tags_.getRaw(i));
      }
      size += dataSize;
      size += 1 * getTagsList().size();
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof newsletter.SubscriptionRequest)) {
      return super.equals(obj);
    }
    newsletter.SubscriptionRequest other = (newsletter.SubscriptionRequest) obj;

    if (getUserId()
        != other.getUserId()) return false;
    if (!getCity()
        .equals(other.getCity())) return false;
    if (!getTagsList()
        .equals(other.getTagsList())) return false;
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + USERID_FIELD_NUMBER;
    hash = (53 * hash) + getUserId();
    hash = (37 * hash) + CITY_FIELD_NUMBER;
    hash = (53 * hash) + getCity().hashCode();
    if (getTagsCount() > 0) {
      hash = (37 * hash) + TAGS_FIELD_NUMBER;
      hash = (53 * hash) + getTagsList().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static newsletter.SubscriptionRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static newsletter.SubscriptionRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static newsletter.SubscriptionRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static newsletter.SubscriptionRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static newsletter.SubscriptionRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static newsletter.SubscriptionRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static newsletter.SubscriptionRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static newsletter.SubscriptionRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static newsletter.SubscriptionRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static newsletter.SubscriptionRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static newsletter.SubscriptionRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static newsletter.SubscriptionRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(newsletter.SubscriptionRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessage.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code newsletter.SubscriptionRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:newsletter.SubscriptionRequest)
      newsletter.SubscriptionRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return newsletter.NewsletterProto.internal_static_newsletter_SubscriptionRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return newsletter.NewsletterProto.internal_static_newsletter_SubscriptionRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              newsletter.SubscriptionRequest.class, newsletter.SubscriptionRequest.Builder.class);
    }

    // Construct using newsletter.SubscriptionRequest.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      userId_ = 0;
      city_ = "";
      tags_ =
          com.google.protobuf.LazyStringArrayList.emptyList();
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return newsletter.NewsletterProto.internal_static_newsletter_SubscriptionRequest_descriptor;
    }

    @java.lang.Override
    public newsletter.SubscriptionRequest getDefaultInstanceForType() {
      return newsletter.SubscriptionRequest.getDefaultInstance();
    }

    @java.lang.Override
    public newsletter.SubscriptionRequest build() {
      newsletter.SubscriptionRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public newsletter.SubscriptionRequest buildPartial() {
      newsletter.SubscriptionRequest result = new newsletter.SubscriptionRequest(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(newsletter.SubscriptionRequest result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.userId_ = userId_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.city_ = city_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        tags_.makeImmutable();
        result.tags_ = tags_;
      }
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof newsletter.SubscriptionRequest) {
        return mergeFrom((newsletter.SubscriptionRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(newsletter.SubscriptionRequest other) {
      if (other == newsletter.SubscriptionRequest.getDefaultInstance()) return this;
      if (other.getUserId() != 0) {
        setUserId(other.getUserId());
      }
      if (!other.getCity().isEmpty()) {
        city_ = other.city_;
        bitField0_ |= 0x00000002;
        onChanged();
      }
      if (!other.tags_.isEmpty()) {
        if (tags_.isEmpty()) {
          tags_ = other.tags_;
          bitField0_ |= 0x00000004;
        } else {
          ensureTagsIsMutable();
          tags_.addAll(other.tags_);
        }
        onChanged();
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 8: {
              userId_ = input.readInt32();
              bitField0_ |= 0x00000001;
              break;
            } // case 8
            case 18: {
              city_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000002;
              break;
            } // case 18
            case 26: {
              java.lang.String s = input.readStringRequireUtf8();
              ensureTagsIsMutable();
              tags_.add(s);
              break;
            } // case 26
            default: {
              if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                done = true; // was an endgroup tag
              }
              break;
            } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }
    private int bitField0_;

    private int userId_ ;
    /**
     * <code>int32 userId = 1;</code>
     * @return The userId.
     */
    @java.lang.Override
    public int getUserId() {
      return userId_;
    }
    /**
     * <code>int32 userId = 1;</code>
     * @param value The userId to set.
     * @return This builder for chaining.
     */
    public Builder setUserId(int value) {

      userId_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>int32 userId = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearUserId() {
      bitField0_ = (bitField0_ & ~0x00000001);
      userId_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object city_ = "";
    /**
     * <code>string city = 2;</code>
     * @return The city.
     */
    public java.lang.String getCity() {
      java.lang.Object ref = city_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        city_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string city = 2;</code>
     * @return The bytes for city.
     */
    public com.google.protobuf.ByteString
        getCityBytes() {
      java.lang.Object ref = city_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        city_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string city = 2;</code>
     * @param value The city to set.
     * @return This builder for chaining.
     */
    public Builder setCity(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      city_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>string city = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearCity() {
      city_ = getDefaultInstance().getCity();
      bitField0_ = (bitField0_ & ~0x00000002);
      onChanged();
      return this;
    }
    /**
     * <code>string city = 2;</code>
     * @param value The bytes for city to set.
     * @return This builder for chaining.
     */
    public Builder setCityBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      city_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }

    private com.google.protobuf.LazyStringArrayList tags_ =
        com.google.protobuf.LazyStringArrayList.emptyList();
    private void ensureTagsIsMutable() {
      if (!tags_.isModifiable()) {
        tags_ = new com.google.protobuf.LazyStringArrayList(tags_);
      }
      bitField0_ |= 0x00000004;
    }
    /**
     * <code>repeated string tags = 3;</code>
     * @return A list containing the tags.
     */
    public com.google.protobuf.ProtocolStringList
        getTagsList() {
      tags_.makeImmutable();
      return tags_;
    }
    /**
     * <code>repeated string tags = 3;</code>
     * @return The count of tags.
     */
    public int getTagsCount() {
      return tags_.size();
    }
    /**
     * <code>repeated string tags = 3;</code>
     * @param index The index of the element to return.
     * @return The tags at the given index.
     */
    public java.lang.String getTags(int index) {
      return tags_.get(index);
    }
    /**
     * <code>repeated string tags = 3;</code>
     * @param index The index of the value to return.
     * @return The bytes of the tags at the given index.
     */
    public com.google.protobuf.ByteString
        getTagsBytes(int index) {
      return tags_.getByteString(index);
    }
    /**
     * <code>repeated string tags = 3;</code>
     * @param index The index to set the value at.
     * @param value The tags to set.
     * @return This builder for chaining.
     */
    public Builder setTags(
        int index, java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      ensureTagsIsMutable();
      tags_.set(index, value);
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <code>repeated string tags = 3;</code>
     * @param value The tags to add.
     * @return This builder for chaining.
     */
    public Builder addTags(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      ensureTagsIsMutable();
      tags_.add(value);
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <code>repeated string tags = 3;</code>
     * @param values The tags to add.
     * @return This builder for chaining.
     */
    public Builder addAllTags(
        java.lang.Iterable<java.lang.String> values) {
      ensureTagsIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(
          values, tags_);
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <code>repeated string tags = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearTags() {
      tags_ =
        com.google.protobuf.LazyStringArrayList.emptyList();
      bitField0_ = (bitField0_ & ~0x00000004);;
      onChanged();
      return this;
    }
    /**
     * <code>repeated string tags = 3;</code>
     * @param value The bytes of the tags to add.
     * @return This builder for chaining.
     */
    public Builder addTagsBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      ensureTagsIsMutable();
      tags_.add(value);
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:newsletter.SubscriptionRequest)
  }

  // @@protoc_insertion_point(class_scope:newsletter.SubscriptionRequest)
  private static final newsletter.SubscriptionRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new newsletter.SubscriptionRequest();
  }

  public static newsletter.SubscriptionRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<SubscriptionRequest>
      PARSER = new com.google.protobuf.AbstractParser<SubscriptionRequest>() {
    @java.lang.Override
    public SubscriptionRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      Builder builder = newBuilder();
      try {
        builder.mergeFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(builder.buildPartial());
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(e)
            .setUnfinishedMessage(builder.buildPartial());
      }
      return builder.buildPartial();
    }
  };

  public static com.google.protobuf.Parser<SubscriptionRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<SubscriptionRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public newsletter.SubscriptionRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

