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

YUI.add('text-editor', function (Y, NAME) {
    
    var EVT_TEXT_EDITED = 'text-editor:textedited';
    
    var TextEditor = Y.Base.create('text-editor', Y.Base, [], {
        
        initializer: function () {
            var panel = new Y.Panel({
                srcNode: this.get('textEditorNode'),
                headerContent: 'Edit Text',
                width: 250,
                zIndex: 10000,
                centered: true,
                visible: false,
                modal: true,
                render: true,
                plugins: [Y.Plugin.Drag]
            });
            this.set('textEditor', panel);
            this.bindTextEditor();
        },
        
        bindTextEditor: function() {
            var instance = this;
            this.get('textEditorNode').one('.cancel').on('click', function() {
                instance.get('textEditor').hide();
            });
            this.get('textEditorNode').one('.edit').on('click', function() {
                instance.get('currentTextComponent').text = instance.get('textEditorNode').one('.text').get('value');
                instance.get('currentTextComponent').fire('modified');
                instance.fire(EVT_TEXT_EDITED);
                instance.get('textEditor').hide();
            });
        },
        
        /**
         * Shows the text editor popup, called outside of this class
         * 
         */
        editText: function(textComponent) {
            this.set('currentTextComponent', textComponent);
            this.get('textEditor').show();
            this.get('textEditorNode').one('.text').set('value', textComponent.text);
        }
    }, {
        ATTRS: {
            textEditorNode: null,
            textEditor: null,
            currentTextComponent: null
        }
    });

    Y.TextEditor = TextEditor;

}, '@VERSION@', {
    "requires": ["yui-base", "base-build", "panel", "dd-plugin"]
});