//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.10
//
// <auto-generated>
//
// Generated from file `calculator.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Demo;

public interface CalcPrx extends com.zeroc.Ice.ObjectPrx
{
    default long add(int a, int b)
    {
        return add(a, b, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default long add(int a, int b, java.util.Map<String, String> context)
    {
        return _iceI_addAsync(a, b, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<java.lang.Long> addAsync(int a, int b)
    {
        return _iceI_addAsync(a, b, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<java.lang.Long> addAsync(int a, int b, java.util.Map<String, String> context)
    {
        return _iceI_addAsync(a, b, context, false);
    }

    /**
     * @hidden
     * @param iceP_a -
     * @param iceP_b -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<java.lang.Long> _iceI_addAsync(int iceP_a, int iceP_b, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<java.lang.Long> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "add", null, sync, null);
        f.invoke(true, context, null, ostr -> {
                     ostr.writeInt(iceP_a);
                     ostr.writeInt(iceP_b);
                 }, istr -> {
                     long ret;
                     ret = istr.readLong();
                     return ret;
                 });
        return f;
    }

    default long subtract(int a, int b)
    {
        return subtract(a, b, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default long subtract(int a, int b, java.util.Map<String, String> context)
    {
        return _iceI_subtractAsync(a, b, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<java.lang.Long> subtractAsync(int a, int b)
    {
        return _iceI_subtractAsync(a, b, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<java.lang.Long> subtractAsync(int a, int b, java.util.Map<String, String> context)
    {
        return _iceI_subtractAsync(a, b, context, false);
    }

    /**
     * @hidden
     * @param iceP_a -
     * @param iceP_b -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<java.lang.Long> _iceI_subtractAsync(int iceP_a, int iceP_b, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<java.lang.Long> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "subtract", null, sync, null);
        f.invoke(true, context, null, ostr -> {
                     ostr.writeInt(iceP_a);
                     ostr.writeInt(iceP_b);
                 }, istr -> {
                     long ret;
                     ret = istr.readLong();
                     return ret;
                 });
        return f;
    }

    default void op(A a1, short b1)
    {
        op(a1, b1, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void op(A a1, short b1, java.util.Map<String, String> context)
    {
        _iceI_opAsync(a1, b1, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<Void> opAsync(A a1, short b1)
    {
        return _iceI_opAsync(a1, b1, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> opAsync(A a1, short b1, java.util.Map<String, String> context)
    {
        return _iceI_opAsync(a1, b1, context, false);
    }

    /**
     * @hidden
     * @param iceP_a1 -
     * @param iceP_b1 -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_opAsync(A iceP_a1, short iceP_b1, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "op", null, sync, null);
        f.invoke(false, context, null, ostr -> {
                     A.ice_write(ostr, iceP_a1);
                     ostr.writeShort(iceP_b1);
                 }, null);
        return f;
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static CalcPrx checkedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, ice_staticId(), CalcPrx.class, _CalcPrxI.class);
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static CalcPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, context, ice_staticId(), CalcPrx.class, _CalcPrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static CalcPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, ice_staticId(), CalcPrx.class, _CalcPrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static CalcPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, context, ice_staticId(), CalcPrx.class, _CalcPrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @return A proxy for this type.
     **/
    static CalcPrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, CalcPrx.class, _CalcPrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type.
     **/
    static CalcPrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, facet, CalcPrx.class, _CalcPrxI.class);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the per-proxy context.
     * @param newContext The context for the new proxy.
     * @return A proxy with the specified per-proxy context.
     **/
    @Override
    default CalcPrx ice_context(java.util.Map<String, String> newContext)
    {
        return (CalcPrx)_ice_context(newContext);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the adapter ID.
     * @param newAdapterId The adapter ID for the new proxy.
     * @return A proxy with the specified adapter ID.
     **/
    @Override
    default CalcPrx ice_adapterId(String newAdapterId)
    {
        return (CalcPrx)_ice_adapterId(newAdapterId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoints.
     * @param newEndpoints The endpoints for the new proxy.
     * @return A proxy with the specified endpoints.
     **/
    @Override
    default CalcPrx ice_endpoints(com.zeroc.Ice.Endpoint[] newEndpoints)
    {
        return (CalcPrx)_ice_endpoints(newEndpoints);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator cache timeout.
     * @param newTimeout The new locator cache timeout (in seconds).
     * @return A proxy with the specified locator cache timeout.
     **/
    @Override
    default CalcPrx ice_locatorCacheTimeout(int newTimeout)
    {
        return (CalcPrx)_ice_locatorCacheTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the invocation timeout.
     * @param newTimeout The new invocation timeout (in seconds).
     * @return A proxy with the specified invocation timeout.
     **/
    @Override
    default CalcPrx ice_invocationTimeout(int newTimeout)
    {
        return (CalcPrx)_ice_invocationTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for connection caching.
     * @param newCache <code>true</code> if the new proxy should cache connections; <code>false</code> otherwise.
     * @return A proxy with the specified caching policy.
     **/
    @Override
    default CalcPrx ice_connectionCached(boolean newCache)
    {
        return (CalcPrx)_ice_connectionCached(newCache);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoint selection policy.
     * @param newType The new endpoint selection policy.
     * @return A proxy with the specified endpoint selection policy.
     **/
    @Override
    default CalcPrx ice_endpointSelection(com.zeroc.Ice.EndpointSelectionType newType)
    {
        return (CalcPrx)_ice_endpointSelection(newType);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for how it selects endpoints.
     * @param b If <code>b</code> is <code>true</code>, only endpoints that use a secure transport are
     * used by the new proxy. If <code>b</code> is false, the returned proxy uses both secure and
     * insecure endpoints.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default CalcPrx ice_secure(boolean b)
    {
        return (CalcPrx)_ice_secure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the encoding used to marshal parameters.
     * @param e The encoding version to use to marshal request parameters.
     * @return A proxy with the specified encoding version.
     **/
    @Override
    default CalcPrx ice_encodingVersion(com.zeroc.Ice.EncodingVersion e)
    {
        return (CalcPrx)_ice_encodingVersion(e);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its endpoint selection policy.
     * @param b If <code>b</code> is <code>true</code>, the new proxy will use secure endpoints for invocations
     * and only use insecure endpoints if an invocation cannot be made via secure endpoints. If <code>b</code> is
     * <code>false</code>, the proxy prefers insecure endpoints to secure ones.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default CalcPrx ice_preferSecure(boolean b)
    {
        return (CalcPrx)_ice_preferSecure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the router.
     * @param router The router for the new proxy.
     * @return A proxy with the specified router.
     **/
    @Override
    default CalcPrx ice_router(com.zeroc.Ice.RouterPrx router)
    {
        return (CalcPrx)_ice_router(router);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator.
     * @param locator The locator for the new proxy.
     * @return A proxy with the specified locator.
     **/
    @Override
    default CalcPrx ice_locator(com.zeroc.Ice.LocatorPrx locator)
    {
        return (CalcPrx)_ice_locator(locator);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for collocation optimization.
     * @param b <code>true</code> if the new proxy enables collocation optimization; <code>false</code> otherwise.
     * @return A proxy with the specified collocation optimization.
     **/
    @Override
    default CalcPrx ice_collocationOptimized(boolean b)
    {
        return (CalcPrx)_ice_collocationOptimized(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses twoway invocations.
     * @return A proxy that uses twoway invocations.
     **/
    @Override
    default CalcPrx ice_twoway()
    {
        return (CalcPrx)_ice_twoway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses oneway invocations.
     * @return A proxy that uses oneway invocations.
     **/
    @Override
    default CalcPrx ice_oneway()
    {
        return (CalcPrx)_ice_oneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch oneway invocations.
     * @return A proxy that uses batch oneway invocations.
     **/
    @Override
    default CalcPrx ice_batchOneway()
    {
        return (CalcPrx)_ice_batchOneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses datagram invocations.
     * @return A proxy that uses datagram invocations.
     **/
    @Override
    default CalcPrx ice_datagram()
    {
        return (CalcPrx)_ice_datagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch datagram invocations.
     * @return A proxy that uses batch datagram invocations.
     **/
    @Override
    default CalcPrx ice_batchDatagram()
    {
        return (CalcPrx)_ice_batchDatagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, except for compression.
     * @param co <code>true</code> enables compression for the new proxy; <code>false</code> disables compression.
     * @return A proxy with the specified compression setting.
     **/
    @Override
    default CalcPrx ice_compress(boolean co)
    {
        return (CalcPrx)_ice_compress(co);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection timeout setting.
     * @param t The connection timeout for the proxy in milliseconds.
     * @return A proxy with the specified timeout.
     **/
    @Override
    default CalcPrx ice_timeout(int t)
    {
        return (CalcPrx)_ice_timeout(t);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection ID.
     * @param connectionId The connection ID for the new proxy. An empty string removes the connection ID.
     * @return A proxy with the specified connection ID.
     **/
    @Override
    default CalcPrx ice_connectionId(String connectionId)
    {
        return (CalcPrx)_ice_connectionId(connectionId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except it's a fixed proxy bound
     * the given connection.@param connection The fixed proxy connection.
     * @return A fixed proxy bound to the given connection.
     **/
    @Override
    default CalcPrx ice_fixed(com.zeroc.Ice.Connection connection)
    {
        return (CalcPrx)_ice_fixed(connection);
    }

    static String ice_staticId()
    {
        return "::Demo::Calc";
    }
}
