<%--
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
 */
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>

<liferay-theme:defineObjects />
<portlet:defineObjects />

<input type="hidden" class="profile-image-path" value="<%= HtmlUtil.escape(user.getPortraitURL(themeDisplay)) %>"/>
<div class="editor">
    <div class="users-online">
        <a href="#" class="expand-collapse-btn"><i class="icon-user"></i> <span class="count"></span> User(s) Online</a>
        <div class="users-online-wrapper">
            <header><h6>Currently being viewed by:</h6></header>
            <div class="bd"></div>
        </div>
    </div>
    <div class="user-modification-tooltips">
    </div>
    <menu>
        <div class="btn-group btn-group-vertical">
            <button class="btn add" data-shape="rectangle" title="Add rectangle"><span></span></button>
            <button class="btn add" data-shape="line" title="Add line"><span></span></button>
            <button class="btn add" data-shape="circle" title="Add circle"><span></span></button>
            <button class="btn free"><i class="icon-pencil" title="Free draw"></i></button>
            <button class="btn add" data-shape="text" title="Add text"><span></span></button>
            <button class="btn delete" title="Remove object"><i class="icon-remove"></i></button>
            <button class="btn clean" title="Clean canvas"><i class="icon-trash"></i></button>
            <div class="color-picker stroke yui3-skin-sam"><label>Stroke:</label>
                <span class="sample" style="background-color: #000000;"></span>
                <div class="color-picker-container hidden">
                    <a href="#" class="close-picker">close</a>
                    <div class="picker">
                        <div id="hue-dial" class="hue-dial"></div>
                        <div class="sliders">
                            <div id="sat-slider" class="sat-slider"><strong>Saturation: <span></span></strong></div>
                            <div id="lum-slider" class="lum-slider"><strong>Luminance: <span></span></strong></div>
                        </div>
                        <div class="color" style="background-color: #000000;"></div>
                    </div>
                </div>
            </div>
            <div class="color-picker fill yui3-skin-sam"><label>Fill:</label>
                <span class="sample" style="background-color: #FFFFFF;"></span>
                <div class="color-picker-container hidden">
                    <a href="#" class="close-picker">close</a>
                    <div class="picker">
                        <div id="hue-dial" class="hue-dial"></div>
                        <div class="sliders">
                            <div id="sat-slider" class="sat-slider"><strong>Saturation: <span></span></strong></div>
                            <div id="lum-slider" class="lum-slider"><strong>Luminance: <span></span></strong></div>
                        </div>
                        <div class="color" style="background-color: #FFFFFF;"></div>
                    </div>
                </div>
                <span class="opacity">
                    <label>Opacity:</label>
                    <select>
                        <option value="1">100%</option>
                        <option value="0.9">90%</option>
                        <option value="0.8">80%</option>
                        <option value="0.7">70%</option>
                        <option value="0.6">60%</option>
                        <option value="0.5">50%</option>
                        <option value="0.4">40%</option>
                        <option value="0.3">30%</option>
                        <option value="0.2">20%</option>
                        <option value="0.1">10%</option>
                        <option value="0.000001">0%</option>
                    </select>
                </span>
            </div>
        </div>

    </menu>
    <canvas id="editor-canvas" width="500" height="500"></canvas>
    <div class="text-editor">
        <textarea class="text"></textarea>
        <button class="btn btn-primary edit">Edit</button>
        <button class="btn cancel">Cancel</button>
    </div>
</div>

<script id="users-online-template" type="text/x-handlebars-template">
    <ul class="unstyled">
        {{#each users}}
        <li><img src="{{userImagePath}}"/><span>{{userName}}</span></li>
        {{/each}}
    </ul>
</script>

<script id="user-tooltips-template" type="text/x-handlebars-template">
    <div id="{{id}}" style="top: {{top}}px; left: {{left}}px"><span class="sub-wrapper"><img src="{{userImagePath}}"/><span>{{userName}}</span></span></div>
</script>