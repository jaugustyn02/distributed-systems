# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: newsletter.proto
# Protobuf Python Version: 5.26.1
"""Generated protocol buffer code."""
from google.protobuf import descriptor as _descriptor
from google.protobuf import descriptor_pool as _descriptor_pool
from google.protobuf import symbol_database as _symbol_database
from google.protobuf.internal import builder as _builder
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()




DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n\x10newsletter.proto\x12\nnewsletter\"\x1d\n\x0bUserRequest\x12\x0e\n\x06userId\x18\x01 \x01(\x05\"A\n\x13SubscriptionRequest\x12\x0e\n\x06userId\x18\x01 \x01(\x05\x12\x0c\n\x04\x63ity\x18\x02 \x01(\t\x12\x0c\n\x04tags\x18\x03 \x03(\t\"8\n\x14SubscriptionResponse\x12\x0f\n\x07success\x18\x01 \x01(\x08\x12\x0f\n\x07message\x18\x02 \x01(\t\"\xdc\x01\n\x11\x45ventNotification\x12<\n\x04type\x18\x02 \x01(\x0e\x32..newsletter.EventNotification.NotificationType\x12\r\n\x05title\x18\x03 \x01(\t\x12\x13\n\x0b\x64\x65scription\x18\x04 \x01(\t\x12\x0c\n\x04\x63ity\x18\x05 \x01(\t\x12\x0c\n\x04tags\x18\x06 \x03(\t\"I\n\x10NotificationType\x12\r\n\tNEW_EVENT\x10\x00\x12\x11\n\rEVENT_UPDATED\x10\x01\x12\x13\n\x0f\x45VENT_CANCELLED\x10\x02\x32\x81\x02\n\x11NewsletterService\x12P\n\tSubscribe\x12\x1f.newsletter.SubscriptionRequest\x1a .newsletter.SubscriptionResponse\"\x00\x12J\n\x0bUnsubscribe\x12\x17.newsletter.UserRequest\x1a .newsletter.SubscriptionResponse\"\x00\x12N\n\x10GetNotifications\x12\x17.newsletter.UserRequest\x1a\x1d.newsletter.EventNotification\"\x00\x30\x01\x42\x1f\n\nnewsletterB\x0fNewsletterProtoP\x01\x62\x06proto3')

_globals = globals()
_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, _globals)
_builder.BuildTopDescriptorsAndMessages(DESCRIPTOR, 'newsletter_pb2', _globals)
if not _descriptor._USE_C_DESCRIPTORS:
  _globals['DESCRIPTOR']._loaded_options = None
  _globals['DESCRIPTOR']._serialized_options = b'\n\nnewsletterB\017NewsletterProtoP\001'
  _globals['_USERREQUEST']._serialized_start=32
  _globals['_USERREQUEST']._serialized_end=61
  _globals['_SUBSCRIPTIONREQUEST']._serialized_start=63
  _globals['_SUBSCRIPTIONREQUEST']._serialized_end=128
  _globals['_SUBSCRIPTIONRESPONSE']._serialized_start=130
  _globals['_SUBSCRIPTIONRESPONSE']._serialized_end=186
  _globals['_EVENTNOTIFICATION']._serialized_start=189
  _globals['_EVENTNOTIFICATION']._serialized_end=409
  _globals['_EVENTNOTIFICATION_NOTIFICATIONTYPE']._serialized_start=336
  _globals['_EVENTNOTIFICATION_NOTIFICATIONTYPE']._serialized_end=409
  _globals['_NEWSLETTERSERVICE']._serialized_start=412
  _globals['_NEWSLETTERSERVICE']._serialized_end=669
# @@protoc_insertion_point(module_scope)
