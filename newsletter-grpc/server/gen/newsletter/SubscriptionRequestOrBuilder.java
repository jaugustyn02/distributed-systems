// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: newsletter.proto

// Protobuf Java Version: 4.26.1
package newsletter;

public interface SubscriptionRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:newsletter.SubscriptionRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int32 userId = 1;</code>
   * @return The userId.
   */
  int getUserId();

  /**
   * <code>string city = 2;</code>
   * @return The city.
   */
  java.lang.String getCity();
  /**
   * <code>string city = 2;</code>
   * @return The bytes for city.
   */
  com.google.protobuf.ByteString
      getCityBytes();

  /**
   * <code>repeated string tags = 3;</code>
   * @return A list containing the tags.
   */
  java.util.List<java.lang.String>
      getTagsList();
  /**
   * <code>repeated string tags = 3;</code>
   * @return The count of tags.
   */
  int getTagsCount();
  /**
   * <code>repeated string tags = 3;</code>
   * @param index The index of the element to return.
   * @return The tags at the given index.
   */
  java.lang.String getTags(int index);
  /**
   * <code>repeated string tags = 3;</code>
   * @param index The index of the value to return.
   * @return The bytes of the tags at the given index.
   */
  com.google.protobuf.ByteString
      getTagsBytes(int index);
}
