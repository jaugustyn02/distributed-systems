package newsletter;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: newsletter.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class NewsletterServiceGrpc {

  private NewsletterServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "newsletter.NewsletterService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<newsletter.SubscriptionRequest,
      newsletter.SubscriptionResponse> getSubscribeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Subscribe",
      requestType = newsletter.SubscriptionRequest.class,
      responseType = newsletter.SubscriptionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<newsletter.SubscriptionRequest,
      newsletter.SubscriptionResponse> getSubscribeMethod() {
    io.grpc.MethodDescriptor<newsletter.SubscriptionRequest, newsletter.SubscriptionResponse> getSubscribeMethod;
    if ((getSubscribeMethod = NewsletterServiceGrpc.getSubscribeMethod) == null) {
      synchronized (NewsletterServiceGrpc.class) {
        if ((getSubscribeMethod = NewsletterServiceGrpc.getSubscribeMethod) == null) {
          NewsletterServiceGrpc.getSubscribeMethod = getSubscribeMethod =
              io.grpc.MethodDescriptor.<newsletter.SubscriptionRequest, newsletter.SubscriptionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Subscribe"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  newsletter.SubscriptionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  newsletter.SubscriptionResponse.getDefaultInstance()))
              .setSchemaDescriptor(new NewsletterServiceMethodDescriptorSupplier("Subscribe"))
              .build();
        }
      }
    }
    return getSubscribeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<newsletter.UserRequest,
      newsletter.SubscriptionResponse> getUnsubscribeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Unsubscribe",
      requestType = newsletter.UserRequest.class,
      responseType = newsletter.SubscriptionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<newsletter.UserRequest,
      newsletter.SubscriptionResponse> getUnsubscribeMethod() {
    io.grpc.MethodDescriptor<newsletter.UserRequest, newsletter.SubscriptionResponse> getUnsubscribeMethod;
    if ((getUnsubscribeMethod = NewsletterServiceGrpc.getUnsubscribeMethod) == null) {
      synchronized (NewsletterServiceGrpc.class) {
        if ((getUnsubscribeMethod = NewsletterServiceGrpc.getUnsubscribeMethod) == null) {
          NewsletterServiceGrpc.getUnsubscribeMethod = getUnsubscribeMethod =
              io.grpc.MethodDescriptor.<newsletter.UserRequest, newsletter.SubscriptionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Unsubscribe"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  newsletter.UserRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  newsletter.SubscriptionResponse.getDefaultInstance()))
              .setSchemaDescriptor(new NewsletterServiceMethodDescriptorSupplier("Unsubscribe"))
              .build();
        }
      }
    }
    return getUnsubscribeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<newsletter.UserRequest,
      newsletter.EventNotification> getGetNotificationsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetNotifications",
      requestType = newsletter.UserRequest.class,
      responseType = newsletter.EventNotification.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<newsletter.UserRequest,
      newsletter.EventNotification> getGetNotificationsMethod() {
    io.grpc.MethodDescriptor<newsletter.UserRequest, newsletter.EventNotification> getGetNotificationsMethod;
    if ((getGetNotificationsMethod = NewsletterServiceGrpc.getGetNotificationsMethod) == null) {
      synchronized (NewsletterServiceGrpc.class) {
        if ((getGetNotificationsMethod = NewsletterServiceGrpc.getGetNotificationsMethod) == null) {
          NewsletterServiceGrpc.getGetNotificationsMethod = getGetNotificationsMethod =
              io.grpc.MethodDescriptor.<newsletter.UserRequest, newsletter.EventNotification>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetNotifications"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  newsletter.UserRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  newsletter.EventNotification.getDefaultInstance()))
              .setSchemaDescriptor(new NewsletterServiceMethodDescriptorSupplier("GetNotifications"))
              .build();
        }
      }
    }
    return getGetNotificationsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static NewsletterServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NewsletterServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NewsletterServiceStub>() {
        @java.lang.Override
        public NewsletterServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NewsletterServiceStub(channel, callOptions);
        }
      };
    return NewsletterServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static NewsletterServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NewsletterServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NewsletterServiceBlockingStub>() {
        @java.lang.Override
        public NewsletterServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NewsletterServiceBlockingStub(channel, callOptions);
        }
      };
    return NewsletterServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static NewsletterServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NewsletterServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NewsletterServiceFutureStub>() {
        @java.lang.Override
        public NewsletterServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NewsletterServiceFutureStub(channel, callOptions);
        }
      };
    return NewsletterServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void subscribe(newsletter.SubscriptionRequest request,
        io.grpc.stub.StreamObserver<newsletter.SubscriptionResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSubscribeMethod(), responseObserver);
    }

    /**
     */
    default void unsubscribe(newsletter.UserRequest request,
        io.grpc.stub.StreamObserver<newsletter.SubscriptionResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUnsubscribeMethod(), responseObserver);
    }

    /**
     */
    default void getNotifications(newsletter.UserRequest request,
        io.grpc.stub.StreamObserver<newsletter.EventNotification> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetNotificationsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service NewsletterService.
   */
  public static abstract class NewsletterServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return NewsletterServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service NewsletterService.
   */
  public static final class NewsletterServiceStub
      extends io.grpc.stub.AbstractAsyncStub<NewsletterServiceStub> {
    private NewsletterServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NewsletterServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NewsletterServiceStub(channel, callOptions);
    }

    /**
     */
    public void subscribe(newsletter.SubscriptionRequest request,
        io.grpc.stub.StreamObserver<newsletter.SubscriptionResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSubscribeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void unsubscribe(newsletter.UserRequest request,
        io.grpc.stub.StreamObserver<newsletter.SubscriptionResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUnsubscribeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getNotifications(newsletter.UserRequest request,
        io.grpc.stub.StreamObserver<newsletter.EventNotification> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetNotificationsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service NewsletterService.
   */
  public static final class NewsletterServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<NewsletterServiceBlockingStub> {
    private NewsletterServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NewsletterServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NewsletterServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public newsletter.SubscriptionResponse subscribe(newsletter.SubscriptionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSubscribeMethod(), getCallOptions(), request);
    }

    /**
     */
    public newsletter.SubscriptionResponse unsubscribe(newsletter.UserRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUnsubscribeMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<newsletter.EventNotification> getNotifications(
        newsletter.UserRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetNotificationsMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service NewsletterService.
   */
  public static final class NewsletterServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<NewsletterServiceFutureStub> {
    private NewsletterServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NewsletterServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NewsletterServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<newsletter.SubscriptionResponse> subscribe(
        newsletter.SubscriptionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSubscribeMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<newsletter.SubscriptionResponse> unsubscribe(
        newsletter.UserRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUnsubscribeMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SUBSCRIBE = 0;
  private static final int METHODID_UNSUBSCRIBE = 1;
  private static final int METHODID_GET_NOTIFICATIONS = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SUBSCRIBE:
          serviceImpl.subscribe((newsletter.SubscriptionRequest) request,
              (io.grpc.stub.StreamObserver<newsletter.SubscriptionResponse>) responseObserver);
          break;
        case METHODID_UNSUBSCRIBE:
          serviceImpl.unsubscribe((newsletter.UserRequest) request,
              (io.grpc.stub.StreamObserver<newsletter.SubscriptionResponse>) responseObserver);
          break;
        case METHODID_GET_NOTIFICATIONS:
          serviceImpl.getNotifications((newsletter.UserRequest) request,
              (io.grpc.stub.StreamObserver<newsletter.EventNotification>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getSubscribeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              newsletter.SubscriptionRequest,
              newsletter.SubscriptionResponse>(
                service, METHODID_SUBSCRIBE)))
        .addMethod(
          getUnsubscribeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              newsletter.UserRequest,
              newsletter.SubscriptionResponse>(
                service, METHODID_UNSUBSCRIBE)))
        .addMethod(
          getGetNotificationsMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              newsletter.UserRequest,
              newsletter.EventNotification>(
                service, METHODID_GET_NOTIFICATIONS)))
        .build();
  }

  private static abstract class NewsletterServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    NewsletterServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return newsletter.NewsletterProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("NewsletterService");
    }
  }

  private static final class NewsletterServiceFileDescriptorSupplier
      extends NewsletterServiceBaseDescriptorSupplier {
    NewsletterServiceFileDescriptorSupplier() {}
  }

  private static final class NewsletterServiceMethodDescriptorSupplier
      extends NewsletterServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    NewsletterServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (NewsletterServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new NewsletterServiceFileDescriptorSupplier())
              .addMethod(getSubscribeMethod())
              .addMethod(getUnsubscribeMethod())
              .addMethod(getGetNotificationsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
