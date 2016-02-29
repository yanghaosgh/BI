// **********************************************************************
//
// Copyright (c) 2003-2011 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.4.2
//
// <auto-generated>
//
// Generated from file `BannerIPrxHelper.java'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package TuiService;

public final class BannerIPrxHelper extends Ice.ObjectPrxHelperBase implements BannerIPrx
{
    public String
    GetInfo(String[] bannerid)
    {
        return GetInfo(bannerid, null, false);
    }

    public String
    GetInfo(String[] bannerid, java.util.Map<String, String> __ctx)
    {
        return GetInfo(bannerid, __ctx, true);
    }

    private String
    GetInfo(String[] bannerid, java.util.Map<String, String> __ctx, boolean __explicitCtx)
    {
        if(__explicitCtx && __ctx == null)
        {
            __ctx = _emptyContext;
        }
        int __cnt = 0;
        while(true)
        {
            Ice._ObjectDel __delBase = null;
            try
            {
                __checkTwowayOnly("GetInfo");
                __delBase = __getDelegate(false);
                _BannerIDel __del = (_BannerIDel)__delBase;
                return __del.GetInfo(bannerid, __ctx);
            }
            catch(IceInternal.LocalExceptionWrapper __ex)
            {
                __handleExceptionWrapper(__delBase, __ex);
            }
            catch(Ice.LocalException __ex)
            {
                __cnt = __handleException(__delBase, __ex, null, __cnt);
            }
        }
    }

    private static final String __GetInfo_name = "GetInfo";

    public Ice.AsyncResult begin_GetInfo(String[] bannerid)
    {
        return begin_GetInfo(bannerid, null, false, null);
    }

    public Ice.AsyncResult begin_GetInfo(String[] bannerid, java.util.Map<String, String> __ctx)
    {
        return begin_GetInfo(bannerid, __ctx, true, null);
    }

    public Ice.AsyncResult begin_GetInfo(String[] bannerid, Ice.Callback __cb)
    {
        return begin_GetInfo(bannerid, null, false, __cb);
    }

    public Ice.AsyncResult begin_GetInfo(String[] bannerid, java.util.Map<String, String> __ctx, Ice.Callback __cb)
    {
        return begin_GetInfo(bannerid, __ctx, true, __cb);
    }

    public Ice.AsyncResult begin_GetInfo(String[] bannerid, Callback_BannerI_GetInfo __cb)
    {
        return begin_GetInfo(bannerid, null, false, __cb);
    }

    public Ice.AsyncResult begin_GetInfo(String[] bannerid, java.util.Map<String, String> __ctx, Callback_BannerI_GetInfo __cb)
    {
        return begin_GetInfo(bannerid, __ctx, true, __cb);
    }

    private Ice.AsyncResult begin_GetInfo(String[] bannerid, java.util.Map<String, String> __ctx, boolean __explicitCtx, IceInternal.CallbackBase __cb)
    {
        __checkAsyncTwowayOnly(__GetInfo_name);
        IceInternal.OutgoingAsync __result = new IceInternal.OutgoingAsync(this, __GetInfo_name, __cb);
        try
        {
            __result.__prepare(__GetInfo_name, Ice.OperationMode.Normal, __ctx, __explicitCtx);
            IceInternal.BasicStream __os = __result.__os();
            IdsHelper.write(__os, bannerid);
            __os.endWriteEncaps();
            __result.__send(true);
        }
        catch(Ice.LocalException __ex)
        {
            __result.__exceptionAsync(__ex);
        }
        return __result;
    }

    public String end_GetInfo(Ice.AsyncResult __result)
    {
        Ice.AsyncResult.__check(__result, this, __GetInfo_name);
        if(!__result.__wait())
        {
            try
            {
                __result.__throwUserException();
            }
            catch(Ice.UserException __ex)
            {
                throw new Ice.UnknownUserException(__ex.ice_name(), __ex);
            }
        }
        String __ret;
        IceInternal.BasicStream __is = __result.__is();
        __is.startReadEncaps();
        __ret = __is.readString();
        __is.endReadEncaps();
        return __ret;
    }

    public static BannerIPrx
    checkedCast(Ice.ObjectPrx __obj)
    {
        BannerIPrx __d = null;
        if(__obj != null)
        {
            try
            {
                __d = (BannerIPrx)__obj;
            }
            catch(ClassCastException ex)
            {
                if(__obj.ice_isA(ice_staticId()))
                {
                    BannerIPrxHelper __h = new BannerIPrxHelper();
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }

    public static BannerIPrx
    checkedCast(Ice.ObjectPrx __obj, java.util.Map<String, String> __ctx)
    {
        BannerIPrx __d = null;
        if(__obj != null)
        {
            try
            {
                __d = (BannerIPrx)__obj;
            }
            catch(ClassCastException ex)
            {
                if(__obj.ice_isA(ice_staticId(), __ctx))
                {
                    BannerIPrxHelper __h = new BannerIPrxHelper();
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }

    public static BannerIPrx
    checkedCast(Ice.ObjectPrx __obj, String __facet)
    {
        BannerIPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            try
            {
                if(__bb.ice_isA(ice_staticId()))
                {
                    BannerIPrxHelper __h = new BannerIPrxHelper();
                    __h.__copyFrom(__bb);
                    __d = __h;
                }
            }
            catch(Ice.FacetNotExistException ex)
            {
            }
        }
        return __d;
    }

    public static BannerIPrx
    checkedCast(Ice.ObjectPrx __obj, String __facet, java.util.Map<String, String> __ctx)
    {
        BannerIPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            try
            {
                if(__bb.ice_isA(ice_staticId(), __ctx))
                {
                    BannerIPrxHelper __h = new BannerIPrxHelper();
                    __h.__copyFrom(__bb);
                    __d = __h;
                }
            }
            catch(Ice.FacetNotExistException ex)
            {
            }
        }
        return __d;
    }

    public static BannerIPrx
    uncheckedCast(Ice.ObjectPrx __obj)
    {
        BannerIPrx __d = null;
        if(__obj != null)
        {
            try
            {
                __d = (BannerIPrx)__obj;
            }
            catch(ClassCastException ex)
            {
                BannerIPrxHelper __h = new BannerIPrxHelper();
                __h.__copyFrom(__obj);
                __d = __h;
            }
        }
        return __d;
    }

    public static BannerIPrx
    uncheckedCast(Ice.ObjectPrx __obj, String __facet)
    {
        BannerIPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            BannerIPrxHelper __h = new BannerIPrxHelper();
            __h.__copyFrom(__bb);
            __d = __h;
        }
        return __d;
    }

    public static final String[] __ids =
    {
        "::Ice::Object",
        "::TuiService::BannerI"
    };

    public static String
    ice_staticId()
    {
        return __ids[1];
    }

    protected Ice._ObjectDelM
    __createDelegateM()
    {
        return new _BannerIDelM();
    }

    protected Ice._ObjectDelD
    __createDelegateD()
    {
        return new _BannerIDelD();
    }

    public static void
    __write(IceInternal.BasicStream __os, BannerIPrx v)
    {
        __os.writeProxy(v);
    }

    public static BannerIPrx
    __read(IceInternal.BasicStream __is)
    {
        Ice.ObjectPrx proxy = __is.readProxy();
        if(proxy != null)
        {
            BannerIPrxHelper result = new BannerIPrxHelper();
            result.__copyFrom(proxy);
            return result;
        }
        return null;
    }
}