﻿/**
 * jQuery EasyUI 1.4.5
 *
 * Copyright (c) 2009-2016 www.jeasyui.com. All rights reserved.
 *
 * Licensed under the freeware license: http://www.jeasyui.com/license_freeware.php
 * To use it on other terms please contact us: info@jeasyui.com
 *
 */
/**
 * menu - jQuery EasyUI
 *
 */
(function ($) {
    $(function () {
        $(document).unbind('.menu').bind('mousedown.menu', function (e) {
            var m = $(e.target).closest('div.menu,div.combo-p');
            if (m.length) {
                return
            }
            $('body>div.menu-top:visible').not('.menu-inline').menu('hide');
            hideMenu($('body>div.menu:visible').not('.menu-inline'));
        });
    });

    /**
     * initialize the target menu, the function can be invoked only once
     */
    function init(target) {
        var opts = $.data(target, 'menu').options;
        $(target).addClass('menu-top');	// the top menu
        opts.inline ? $(target).addClass('menu-inline') : $(target).appendTo('body');
        $(target).bind('_resize', function (e, force) {
            if ($(this).hasClass('easyui-fluid') || force) {
                $(target).menu('resize', target);
            }
            return false;
        });

        var menus = splitMenu($(target));
        for (var i = 0; i < menus.length; i++) {
            createMenu(menus[i]);
        }

        function splitMenu(menu) {
            var menus = [];
            menu.addClass('menu');
            menus.push(menu);
            if (!menu.hasClass('menu-content')) {
                menu.children('div').each(function () {
                    var submenu = $(this).children('div');
                    if (submenu.length) {
//						submenu.insertAfter(target);
                        submenu.appendTo('body');
                        this.submenu = submenu;		// point to the sub menu
                        var mm = splitMenu(submenu);
                        menus = menus.concat(mm);
                    }
                });
            }
            return menus;
        }

        function createMenu(menu) {
            var wh = $.parser.parseOptions(menu[0], ['width', 'height']);
            menu[0].originalHeight = wh.height || 0;
            if (menu.hasClass('menu-content')) {
                menu[0].originalWidth = wh.width || menu._outerWidth();
            } else {
                menu[0].originalWidth = wh.width || 0;
                menu.children('div').each(function () {
                    var item = $(this);
                    var itemOpts = $.extend({}, $.parser.parseOptions(this, ['name', 'iconCls', 'href', {separator: 'boolean'}]), {
                        disabled: (item.attr('disabled') ? true : undefined)
                    });
                    if (itemOpts.separator) {
                        item.addClass('menu-sep');
                    }
                    if (!item.hasClass('menu-sep')) {
                        item[0].itemName = itemOpts.name || '';
                        item[0].itemHref = itemOpts.href || '';

                        var text = item.addClass('menu-item').html();
                        item.empty().append($('<div class="menu-text"></div>').html(text));
                        if (itemOpts.iconCls) {
                            $('<div class="menu-icon"></div>').addClass(itemOpts.iconCls).appendTo(item);
                        }
                        if (itemOpts.disabled) {
                            setDisabled(target, item[0], true);
                        }
                        if (item[0].submenu) {
                            $('<div class="menu-rightarrow"></div>').appendTo(item);	// has sub menu
                        }

                        bindMenuItemEvent(target, item);
                    }
                });
                $('<div class="menu-line"></div>').prependTo(menu);
            }
            setMenuSize(target, menu);
            if (!menu.hasClass('menu-inline')) {
                menu.hide();
            }

            bindMenuEvent(target, menu);
        }
    }

    function setMenuSize(target, menu) {
        var opts = $.data(target, 'menu').options;
        var style = menu.attr('style') || '';
        menu.css({
            display: 'block',
            left: -10000,
            height: 'auto',
            overflow: 'hidden'
        });
        menu.find('.menu-item').each(function () {
            $(this)._outerHeight(opts.itemHeight);
            $(this).find('.menu-text').css({
                height: (opts.itemHeight - 2) + 'px',
                lineHeight: (opts.itemHeight - 2) + 'px'
            });
        });
        menu.removeClass('menu-noline').addClass(opts.noline ? 'menu-noline' : '');

        var width = menu[0].originalWidth || 'auto';
        if (isNaN(parseInt(width))) {
            width = 0;
            menu.find('div.menu-text').each(function () {
                if (width < $(this)._outerWidth()) {
                    width = $(this)._outerWidth();
                }
            });
            width += 40;
        }

        var autoHeight = menu.outerHeight();
        var height = menu[0].originalHeight || 'auto';
        if (isNaN(parseInt(height))) {
            height = autoHeight;

            if (menu.hasClass('menu-top') && opts.alignTo) {
                var at = $(opts.alignTo);
                var h1 = at.offset().top - $(document).scrollTop();
                var h2 = $(window)._outerHeight() + $(document).scrollTop() - at.offset().top - at._outerHeight();
                height = Math.min(height, Math.max(h1, h2));
            } else if (height > $(window)._outerHeight()) {
                height = $(window).height();
            }
        }

        menu.attr('style', style);	// restore the original style
        menu._size({
            fit: (menu[0] == target ? opts.fit : false),
            width: width,
            minWidth: opts.minWidth,
            height: height
        });
        menu.css('overflow', menu.outerHeight() < autoHeight ? 'auto' : 'hidden');
        menu.children('div.menu-line')._outerHeight(autoHeight - 2);
    }

    /**
     * bind menu event
     */
    function bindMenuEvent(target, menu) {
        if (menu.hasClass('menu-inline')) {
            return
        }
        var state = $.data(target, 'menu');
        menu.unbind('.menu').bind('mouseenter.menu', function () {
            if (state.timer) {
                clearTimeout(state.timer);
                state.timer = null;
            }
        }).bind('mouseleave.menu', function () {
            if (state.options.hideOnUnhover) {
                state.timer = setTimeout(function () {
                    hideAll(target, $(target).hasClass('menu-inline'));
                }, state.options.duration);
            }
        });
    }

    /**
     * bind menu item event
     */
    function bindMenuItemEvent(target, item) {
        if (!item.hasClass('menu-item')) {
            return
        }
        item.unbind('.menu');
        item.bind('click.menu', function () {
            if ($(this).hasClass('menu-item-disabled')) {
                return;
            }
            // only the sub menu clicked can hide all menus
            if (!this.submenu) {
                hideAll(target, $(target).hasClass('menu-inline'));
                var href = this.itemHref;
                if (href) {
                    location.href = href;
                }
            }
            $(this).trigger('mouseenter');
            var item = $(target).menu('getItem', this);
            $.data(target, 'menu').options.onClick.call(target, item);
        }).bind('mouseenter.menu', function (e) {
            // hide other menu
            item.siblings().each(function () {
                if (this.submenu) {
                    hideMenu(this.submenu);
                }
                $(this).removeClass('menu-active');
            });
            // show this menu
            item.addClass('menu-active');

            if ($(this).hasClass('menu-item-disabled')) {
                item.addClass('menu-active-disabled');
                return;
            }

            var submenu = item[0].submenu;
            if (submenu) {
                $(target).menu('show', {
                    menu: submenu,
                    parent: item
                });
            }
        }).bind('mouseleave.menu', function (e) {
            item.removeClass('menu-active menu-active-disabled');
            var submenu = item[0].submenu;
            if (submenu) {
                if (e.pageX >= parseInt(submenu.css('left'))) {
                    item.addClass('menu-active');
                } else {
                    hideMenu(submenu);
                }

            } else {
                item.removeClass('menu-active');
            }
        });
    }

    /**
     * hide top menu and it's all sub menus
     */
    function hideAll(target, inline) {
        var state = $.data(target, 'menu');
        if (state) {
            if ($(target).is(':visible')) {
                hideMenu($(target));
                if (inline) {
                    $(target).show();
                } else {
                    state.options.onHide.call(target);
                }
            }
        }
        return false;
    }

    /**
     * show the menu, the 'param' object has one or more properties:
     * left: the left position to display
     * top: the top position to display
     * menu: the menu to display, if not defined, the 'target menu' is used
     * parent: the parent menu item to align to
     * alignTo: the element object to align to
     */
    function showMenu(target, param) {
        param = param || {};
        var left, top;
        var opts = $.data(target, 'menu').options;
        var menu = $(param.menu || target);
        $(target).menu('resize', menu[0]);
        if (menu.hasClass('menu-top')) {
            $.extend(opts, param);
            left = opts.left;
            top = opts.top;
            if (opts.alignTo) {
                var at = $(opts.alignTo);
                left = at.offset().left;
                top = at.offset().top + at._outerHeight();
                if (opts.align == 'right') {
                    left += at.outerWidth() - menu.outerWidth();
                }
            }
            if (left + menu.outerWidth() > $(window)._outerWidth() + $(document)._scrollLeft()) {
                left = $(window)._outerWidth() + $(document).scrollLeft() - menu.outerWidth() - 5;
            }
            if (left < 0) {
                left = 0;
            }
            top = _fixTop(top, opts.alignTo);
        } else {
            var parent = param.parent;	// the parent menu item
            left = parent.offset().left + parent.outerWidth() - 2;
            if (left + menu.outerWidth() + 5 > $(window)._outerWidth() + $(document).scrollLeft()) {
                left = parent.offset().left - menu.outerWidth() + 2;
            }
            top = _fixTop(parent.offset().top - 3);
        }

        function _fixTop(top, alignTo) {
            if (top + menu.outerHeight() > $(window)._outerHeight() + $(document).scrollTop()) {
                if (alignTo) {
                    top = $(alignTo).offset().top - menu._outerHeight();
                } else {
                    top = $(window)._outerHeight() + $(document).scrollTop() - menu.outerHeight();
                }
            }
            if (top < 0) {
                top = 0;
            }
            return top;
        }

        menu.css(opts.position.call(target, menu[0], left, top));
        menu.show(0, function () {
            if (!menu[0].shadow) {
                menu[0].shadow = $('<div class="menu-shadow"></div>').insertAfter(menu);
            }
            menu[0].shadow.css({
                display: (menu.hasClass('menu-inline') ? 'none' : 'block'),
                zIndex: $.fn.menu.defaults.zIndex++,
                left: menu.css('left'),
                top: menu.css('top'),
                width: menu.outerWidth(),
                height: menu.outerHeight()
            });
            menu.css('z-index', $.fn.menu.defaults.zIndex++);
            if (menu.hasClass('menu-top')) {
                opts.onShow.call(target);
            }
        });
    }

    function hideMenu(menu) {
        if (menu && menu.length) {
            hideit(menu);
            menu.find('div.menu-item').each(function () {
                if (this.submenu) {
                    hideMenu(this.submenu);
                }
                $(this).removeClass('menu-active');
            });
        }

        function hideit(m) {
            m.stop(true, true);
            if (m[0].shadow) {
                m[0].shadow.hide();
            }
            m.hide();
        }
    }

    function findItem(target, text) {
        var result = null;
        var tmp = $('<div></div>');

        function find(menu) {
            menu.children('div.menu-item').each(function () {
                var item = $(target).menu('getItem', this);
                var s = tmp.empty().html(item.text).text();
                if (text == $.trim(s)) {
                    result = item;
                } else if (this.submenu && !result) {
                    find(this.submenu);
                }
            });
        }

        find($(target));
        tmp.remove();
        return result;
    }

    function setDisabled(target, itemEl, disabled) {
        var t = $(itemEl);
        if (!t.hasClass('menu-item')) {
            return
        }

        if (disabled) {
            t.addClass('menu-item-disabled');
            if (itemEl.onclick) {
                itemEl.onclick1 = itemEl.onclick;
                itemEl.onclick = null;
            }
        } else {
            t.removeClass('menu-item-disabled');
            if (itemEl.onclick1) {
                itemEl.onclick = itemEl.onclick1;
                itemEl.onclick1 = null;
            }
        }
    }

    function appendItem(target, param) {
        var opts = $.data(target, 'menu').options;
        var menu = $(target);
        if (param.parent) {
            if (!param.parent.submenu) {
                var submenu = $('<div class="menu"><div class="menu-line"></div></div>').appendTo('body');
                submenu.hide();
                param.parent.submenu = submenu;
                $('<div class="menu-rightarrow"></div>').appendTo(param.parent);
            }
            menu = param.parent.submenu;
        }
        if (param.separator) {
            var item = $('<div class="menu-sep"></div>').appendTo(menu);
        } else {
            var item = $('<div class="menu-item"></div>').appendTo(menu);
            $('<div class="menu-text"></div>').html(param.text).appendTo(item);
        }
        if (param.iconCls) $('<div class="menu-icon"></div>').addClass(param.iconCls).appendTo(item);
        if (param.id) item.attr('id', param.id);
        if (param.name) {
            item[0].itemName = param.name
        }
        if (param.href) {
            item[0].itemHref = param.href
        }
        if (param.onclick) {
            if (typeof param.onclick == 'string') {
                item.attr('onclick', param.onclick);
            } else {
                item[0].onclick = eval(param.onclick);
            }
        }
        if (param.handler) {
            item[0].onclick = eval(param.handler)
        }
        if (param.disabled) {
            setDisabled(target, item[0], true)
        }

        bindMenuItemEvent(target, item);
        bindMenuEvent(target, menu);
        setMenuSize(target, menu);
    }

    function removeItem(target, itemEl) {
        function removeit(el) {
            if (el.submenu) {
                el.submenu.children('div.menu-item').each(function () {
                    removeit(this);
                });
                var shadow = el.submenu[0].shadow;
                if (shadow) shadow.remove();
                el.submenu.remove();
            }
            $(el).remove();
        }

        var menu = $(itemEl).parent();
        removeit(itemEl);
        setMenuSize(target, menu);
    }

    function setVisible(target, itemEl, visible) {
        var menu = $(itemEl).parent();
        if (visible) {
            $(itemEl).show();
        } else {
            $(itemEl).hide();
        }
        setMenuSize(target, menu);
    }

    function destroyMenu(target) {
        $(target).children('div.menu-item').each(function () {
            removeItem(target, this);
        });
        if (target.shadow) target.shadow.remove();
        $(target).remove();
    }

    $.fn.menu = function (options, param) {
        if (typeof options == 'string') {
            return $.fn.menu.methods[options](this, param);
        }

        options = options || {};
        return this.each(function () {
            var state = $.data(this, 'menu');
            if (state) {
                $.extend(state.options, options);
            } else {
                state = $.data(this, 'menu', {
                    options: $.extend({}, $.fn.menu.defaults, $.fn.menu.parseOptions(this), options)
                });
                init(this);
            }
            $(this).css({
                left: state.options.left,
                top: state.options.top
            });
        });
    };

    $.fn.menu.methods = {
        options: function (jq) {
            return $.data(jq[0], 'menu').options;
        },
        show: function (jq, pos) {
            return jq.each(function () {
                showMenu(this, pos);
            });
        },
        hide: function (jq) {
            return jq.each(function () {
                hideAll(this);
            });
        },
        destroy: function (jq) {
            return jq.each(function () {
                destroyMenu(this);
            });
        },
        /**
         * set the menu item text
         * param: {
		 * 	target: DOM object, indicate the menu item
		 * 	text: string, the new text
		 * }
         */
        setText: function (jq, param) {
            return jq.each(function () {
                $(param.target).children('div.menu-text').html(param.text);
            });
        },
        /**
         * set the menu icon class
         * param: {
		 * 	target: DOM object, indicate the menu item
		 * 	iconCls: the menu item icon class
		 * }
         */
        setIcon: function (jq, param) {
            return jq.each(function () {
                $(param.target).children('div.menu-icon').remove();
                if (param.iconCls) {
                    $('<div class="menu-icon"></div>').addClass(param.iconCls).appendTo(param.target);
                }
            });
        },
        /**
         * get the menu item data that contains the following property:
         * {
		 * 	target: DOM object, the menu item
		 *  id: the menu id
		 * 	text: the menu item text
		 * 	iconCls: the icon class
		 *  href: a remote address to redirect to
		 *  onclick: a function to be called when the item is clicked
		 * }
         */
        getItem: function (jq, itemEl) {
            var t = $(itemEl);
            var item = {
                target: itemEl,
                id: t.attr('id'),
                text: $.trim(t.children('div.menu-text').html()),
                disabled: t.hasClass('menu-item-disabled'),
//				href: t.attr('href'),
//				name: t.attr('name'),
                name: itemEl.itemName,
                href: itemEl.itemHref,
                onclick: itemEl.onclick
            }
            var icon = t.children('div.menu-icon');
            if (icon.length) {
                var cc = [];
                var aa = icon.attr('class').split(' ');
                for (var i = 0; i < aa.length; i++) {
                    if (aa[i] != 'menu-icon') {
                        cc.push(aa[i]);
                    }
                }
                item.iconCls = cc.join(' ');
            }
            return item;
        },
        findItem: function (jq, text) {
            return findItem(jq[0], text);
        },
        /**
         * append menu item, the param contains following properties:
         * parent,id,text,iconCls,href,onclick
         * when parent property is assigned, append menu item to it
         */
        appendItem: function (jq, param) {
            return jq.each(function () {
                appendItem(this, param);
            });
        },
        removeItem: function (jq, itemEl) {
            return jq.each(function () {
                removeItem(this, itemEl);
            });
        },
        enableItem: function (jq, itemEl) {
            return jq.each(function () {
                setDisabled(this, itemEl, false);
            });
        },
        disableItem: function (jq, itemEl) {
            return jq.each(function () {
                setDisabled(this, itemEl, true);
            });
        },
        showItem: function (jq, itemEl) {
            return jq.each(function () {
                setVisible(this, itemEl, true);
            });
        },
        hideItem: function (jq, itemEl) {
            return jq.each(function () {
                setVisible(this, itemEl, false);
            });
        },
        resize: function (jq, menuEl) {
            return jq.each(function () {
                setMenuSize(this, $(menuEl));
            });
        }
    };

    $.fn.menu.parseOptions = function (target) {
        return $.extend({}, $.parser.parseOptions(target, [
            {minWidth: 'number', itemHeight: 'number', duration: 'number', hideOnUnhover: 'boolean'},
            {fit: 'boolean', inline: 'boolean', noline: 'boolean'}
        ]));
    };

    $.fn.menu.defaults = {
        zIndex: 110000,
        left: 0,
        top: 0,
        alignTo: null,
        align: 'left',
        minWidth: 120,
        itemHeight: 22,
        duration: 100,	// Defines duration time in milliseconds to hide when the mouse leaves the menu.
        hideOnUnhover: true,	// Automatically hides the menu when mouse exits it
        inline: false,	// true to stay inside its parent, false to go on top of all elements
        fit: false,
        noline: false,
        position: function (target, left, top) {
            return {left: left, top: top}
        },
        onShow: function () {
        },
        onHide: function () {
        },
        onClick: function (item) {
        }
    };
})(jQuery);
