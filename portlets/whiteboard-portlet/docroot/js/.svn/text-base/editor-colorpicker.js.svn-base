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

YUI.add('color-picker', function (Y, NAME) {
    var EVT_COLOR_CHANGE = 'color-picker:change';
    
    var ColorPicker = Y.Base.create('color-picker', Y.Base, [], {
        hue: null,
        sat: null,
        lum: null,
        color: null,
        satValue: null,
        lumValue: null,
        opacity: null,
        sample: null,
        
        initializer: function () {
            this.bindUI();
        },
        
        bindUI: function () {
            var instance = this;
            var colorPickerContainer = this.get('container').one('.color-picker-container');
            colorPickerContainer.removeClass('hidden');
            this.sample = this.get('container').one('.sample');
            this.sample.on('click', function(e) {
                colorPickerContainer.removeClass('hidden');
            });
            this.get('container').one('.close-picker').on('click', function(e) {
                e.preventDefault();
                colorPickerContainer.addClass('hidden');
            });
            
            this.hue = new Y.Dial({
                min: 0,
                max: 360,
                stepsPerRevolution: 360,
                continuous: true,
                centerButtonDiameter: 0.4,
                render: this.get('container').one('.hue-dial')
            });
            this.sat = new Y.Slider({
                min: 0,
                max: 100,
                value: 100,
                render: this.get('container').one('.sat-slider')
            });
            this.lum = new Y.Slider({
                min: 0,
                max: 100,
                value: 50,
                render: this.get('container').one('.lum-slider')
            });
            
            this.satValue = this.get('container').one('.sat-slider span');
            this.lumValue = this.get('container').one('.lum-slider span');
            this.opacity = this.get('container').one('select');
            this.color = this.get('container').one('.color');
            
            this.hue.after('valueChange', function(e) {
                instance.updatePickerUI();
            });
            
            this.sat.after('thumbMove', function(e) {
                instance.updatePickerUI();
            });

            this.lum.after('thumbMove', function(e) {
                instance.updatePickerUI();
            });
            if (this.opacity) {
                this.opacity.on('change', function() {
                    instance.updatePickerUI();
                });
            }
            colorPickerContainer.addClass('hidden');
        },
        
        updatePickerUI: function() {
            var h = this.hue.get('value'),
                s = this.sat.get('value'),
                l = this.lum.get('value'),
                hslString = '',
                hexString = '';
            var rgbFormattedColor = '';
            if (this.opacity) {
                hslString = Y.Color.fromArray([h, s, l, this.opacity.get('value')], Y.Color.TYPES.HSLA);
                rgbFormattedColor = Y.Color.toRGBA(hslString);
            } else {
                hslString = Y.Color.fromArray([h, s, l], Y.Color.TYPES.HSL);
                rgbFormattedColor = Y.Color.toRGB(hslString);
            }
            hexString = Y.Color.toHex(hslString);
            
            this.satValue.set('text', s + '%');
            this.lumValue.set('text', l + '%');

            this.fire(EVT_COLOR_CHANGE, {color: rgbFormattedColor})
            this.color.setStyle('backgroundColor', rgbFormattedColor);
            this.sample.setStyle('backgroundColor', rgbFormattedColor);

        }

    }, {
        ATTRS: {
            
            container: {
                value: null
            },
            
            trigger: {
                value: '.color-picker-btn'
            }
            
        }
    });
    

    Y.ColorPicker = ColorPicker;

}, '@VERSION@', {
    "requires": ["yui-base", "base-build", "dial", "slider", "event-valuechange", "color"]
});