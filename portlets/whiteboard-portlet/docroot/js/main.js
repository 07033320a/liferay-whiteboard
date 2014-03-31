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

AUI().use('multiuser-whiteboard', function(A) {

    A.on('domready', function() {
        var containerWidth = A.one('.whiteboard-portlet .editor').get('offsetWidth');
        A.one('.whiteboard-portlet canvas').setAttribute('width', containerWidth);
        
        var canvas = new fabric.Canvas('editor-canvas');
        A.on(['orientationchange', 'resize'], function(e) {
            containerWidth = A.one('.whiteboard-portlet .editor').get('offsetWidth');
            canvas.setDimensions({width: containerWidth, height: 500 });
        });
        
        var editor = new A.MultiuserEditor({
            canvas: canvas,
            container: A.one('.whiteboard-portlet .editor'),
            textEditorNode: A.one('.whiteboard-portlet .text-editor'),
            editorId: (Liferay.ThemeDisplay.getUserId() + '-' + Math.floor((Math.random() * 10) + 100)),
            useAtmosphere: true,
            userName: (Liferay.ThemeDisplay.getUserName() != '') ? Liferay.ThemeDisplay.getUserName() : 'Guest',
            onlineUsersTemplate: A.one('#users-online-template').get('innerHTML'),
            usersTooltipsTemplate: A.one('#user-tooltips-template').get('innerHTML'),
            userImagePath: A.one('.whiteboard-portlet .profile-image-path').get('value'),
        });
        
    });

});