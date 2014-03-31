/**
 * Copyright (C) 2005-2014 Rivet Logic Corporation.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; version 3 of the License.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package com.rivetlogic.portlet.whiteboard.atmosphere;

import java.io.IOException;
import java.net.URLDecoder;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.atmosphere.client.TrackMessageSizeInterceptor;
import org.atmosphere.config.service.AtmosphereHandlerService;
import org.atmosphere.config.service.Singleton;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;

import org.atmosphere.handler.AtmosphereHandlerAdapter;
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor;
import org.atmosphere.interceptor.BroadcastOnPostAtmosphereInterceptor;
import org.atmosphere.interceptor.SuspendTrackerInterceptor;
import org.atmosphere.util.SimpleBroadcaster;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;

@Singleton
@AtmosphereHandlerService(path = "/",
        supportSession = true,
        interceptors = {
        AtmosphereResourceLifecycleInterceptor.class,
        TrackMessageSizeInterceptor.class,
        BroadcastOnPostAtmosphereInterceptor.class,
        SuspendTrackerInterceptor.class }, broadcaster = SimpleBroadcaster.class)
public class WhiteboardHandler extends AtmosphereHandlerAdapter {
    
    private static final Log LOG = LogFactoryUtil.getLog(WhiteboardHandler.class);

    private final ConcurrentMap<String, UserData> loggedUserMap = new ConcurrentSkipListMap<String, UserData>();
    
    private final ConcurrentMap<String, JSONObject> whiteBoardDump = new ConcurrentSkipListMap<String, JSONObject>();
    
    @Override
    public void onRequest(AtmosphereResource resource) throws IOException {
        // user joined
        String sessionId = resource.session().getId();
        if (loggedUserMap.get(sessionId) == null) {
            String userImagePath = URLDecoder.decode(resource.getRequest().getParameter(WhiteboardHandlerUtil.USER_IMAGEPATH), "UTF-8");
            String userName = resource.getRequest().getParameter(WhiteboardHandlerUtil.USERNAME);
            loggedUserMap.put(resource.session().getId(), new UserData(userName, userImagePath));
            /* listens to disconnection event */
            resource.addEventListener(new WhiteBoardResourceEventListener(loggedUserMap, sessionId));
        }
    }

    @Override
    public void onStateChange(AtmosphereResourceEvent event) throws IOException {

        /* messages broadcasting */
        if (event.isSuspended()) {
            String message = event.getMessage() == null ? StringPool.BLANK : event.getMessage().toString();
           
            if (!message.equals(StringPool.BLANK)) {
                
                try {
                    JSONObject jsonMessage = JSONFactoryUtil.createJSONObject(message);
                    /* verify if user is signing in */
                    if (WhiteboardHandlerUtil.LOGIN.equals(jsonMessage.getString(WhiteboardHandlerUtil.TYPE))) {
                        JSONObject usersLoggedMessage = WhiteboardHandlerUtil.generateLoggedUsersJSON(loggedUserMap);
                        /* adds whiteboard dump to the message */
                        usersLoggedMessage.put("dump", WhiteboardHandlerUtil.loadWhiteboardDump(whiteBoardDump));
                        event.getResource().getBroadcaster().broadcast(usersLoggedMessage);
                    } else {
                        /* just broadcast the message */
                        LOG.info("Broadcasting = " + message);
                        /* adds whiteboard updates to the dump */
                        WhiteboardHandlerUtil.persistWhiteboardDump(whiteBoardDump, jsonMessage);
                        event.getResource().write(message);
                    }
                } catch (JSONException e) {
                    LOG.info("JSON parse failed");
                }
            }
            
        }
        
    }


}
