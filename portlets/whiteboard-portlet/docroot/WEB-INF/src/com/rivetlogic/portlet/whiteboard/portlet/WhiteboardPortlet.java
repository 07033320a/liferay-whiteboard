package com.rivetlogic.portlet.whiteboard.portlet;

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.rivetlogic.portlet.whiteboard.atmosphere.WhiteboardHandler;

import javax.portlet.PortletException;

public class WhiteboardPortlet extends MVCPortlet {

    @Override
    public void init() throws PortletException {
        super.init();
        @SuppressWarnings("rawtypes")
        PortalCache portalCache = MultiVMPoolUtil.getCache(WhiteboardHandler.CACHE_NAME);
        portalCache.removeAll();
    }
    
}
