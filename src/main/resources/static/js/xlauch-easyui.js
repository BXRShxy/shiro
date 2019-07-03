var xlauch = $.extend({}, xlauch);

xlauch.RES_SUCC = 99999;


/**
 * 获取工程名称
 */
xlauch.getpn = function () {
    return top.document.location.pathname.substring(0, top.document.location.pathname.indexOf('\/', 1));
    //return "";
};

/**
 * 弹出提示框
 */
xlauch.alert = function (msg, callback) {
    parent.$.messager.alert('警告', msg);
};

/**
 * 弹出错误框
 */
xlauch.alertError = function (msg) {
    parent.$.messager.alert('错误', msg, "error");
};

/**
 * 弹出确认框
 */
xlauch.confirm = function (msg, okfun) {
    parent.$.messager.confirm('确认', msg, function (r) {
        if (r) {
            okfun();
        }
    });
};

/**
 * 右下角弹出提示框
 */
xlauch.messagerShow = function (options) {
    return $.messager.show(options);
};

/**
 * 避免验证tip屏幕跑偏
 */
var removeEasyuiTipFunction = function () {
    window.setTimeout(function () {
        $('div.validatebox-tip').remove();
    }, 0);
};

$.fn.panel.defaults.onClose = removeEasyuiTipFunction;
$.fn.window.defaults.onClose = removeEasyuiTipFunction;
$.fn.dialog.defaults.onClose = removeEasyuiTipFunction;


/**
 * 通用错误提示
 * 用于datagrid/treegrid/tree/combogrid/combobox/form加载数据出错时的操作
 */
var easyuiErrorFunction = function (XMLHttpRequest) {
    parent.$.messager.progress('close');
    var _msg = XMLHttpRequest.responseText;
    if (_msg) {
        var json = $.parseJSON(_msg);
        _msg = json._msg + "&nbsp; 错误代码:" + json._code;
    }

    if (!_msg) {
        _msg = "服务器无响应";
    }

    xlauch.alertError(_msg);
};

/**
 * datagrid加载错误时提示错误信息
 * @param data
 */
var datagridErrorFunction = function (data) {
    alert(9);
    if (data._code && data._code !== xlauch.RES_SUCC) {
        xlauch.alertError(data._msg);
    }
};


var panelErrorFunction = function (data) {
    parent.$.messager.alert('错误', '无法找到该页面，请联系管理员！', 'error');
};

$.fn.datagrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.treegrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.tree.defaults.onLoadError = easyuiErrorFunction;
$.fn.combogrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.combobox.defaults.onLoadError = easyuiErrorFunction;
$.fn.form.defaults.onLoadError = easyuiErrorFunction;

$.fn.dialog.defaults.onLoadError = panelErrorFunction;


/**
 * 增加表头菜单，用于显示或隐藏列，注意：冻结列不在此菜单中
 * 用于datagrid、treegrid
 */
var createGridHeaderContextMenu = function (e, field) {
    e.preventDefault();
    var grid = $(this);
    /* grid本身 */
    var headerContextMenu = this.headerContextMenu;
    /* grid上的列头菜单对象 */
    if (!headerContextMenu) {
        var tmenu = $('<div style="width:100px;"></div>').appendTo('body');
        var fields = grid.datagrid('getColumnFields');
        for (var i = 0; i < fields.length; i++) {
            var fildOption = grid.datagrid('getColumnOption', fields[i]);
            if (!fildOption.hidden) {
                $('<div iconCls="icon-ok" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(tmenu);
            } else {
                $('<div iconCls="icon-empty" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(tmenu);
            }
        }
        headerContextMenu = this.headerContextMenu = tmenu.menu({
            onClick: function (item) {
                var field = $(item.target).attr('field');
                if (item.iconCls === 'icon-ok') {
                    grid.datagrid('hideColumn', field);
                    $(this).menu('setIcon', {
                        target: item.target,
                        iconCls: 'icon-empty'
                    });
                } else {
                    grid.datagrid('showColumn', field);
                    $(this).menu('setIcon', {
                        target: item.target,
                        iconCls: 'icon-ok'
                    });
                }
            }
        });
    }
    headerContextMenu.menu('show', {
        left: e.pageX,
        top: e.pageY
    });
};
$.fn.datagrid.defaults.onHeaderContextMenu = createGridHeaderContextMenu;
$.fn.treegrid.defaults.onHeaderContextMenu = createGridHeaderContextMenu;


/**
 * panel关闭时回收内存
 */
$.fn.panel.defaults.onBeforeDestroy = function () {
    var frame = $('iframe', this);
    try {
        if (frame.length > 0) {
            for (var i = 0; i < frame.length; i++) {
                frame[i].contentWindow.document.write('');
                frame[i].contentWindow.close();
            }
            frame.remove();
            if ($.browser.msie) {
                CollectGarbage();
            }
        }
    } catch (e) {
    }
};

/**
 * 更换EasyUI主题的方法
 * @param themeName  主题名称
 */
xlauch.changeTheme = function (themeName) {
    var $easyuiTheme = $('#easyuiTheme');
    var url = $easyuiTheme.attr('href');
    var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
    $easyuiTheme.attr('href', href);

    var $iframe = $('iframe');
    if ($iframe.length > 0) {
        for (var i = 0; i < $iframe.length; i++) {
            var ifr = $iframe[i];
            $(ifr).contents().find('#easyuiTheme').attr('href', href);
        }
    }
};


/**
 * @param options
 */
xlauch.dialog = function (options, id) {
    var opts = $.extend({
        modal: true,
        onClose: function () {
            $(this).dialog('destroy');
        }
    }, options);
    if (id) {
        return $('<div id="' + id + '"/>').dialog(opts);
    } else {
        return $('<div/>').dialog(opts);
    }
};


/**
 * 获取弹窗属性
 */
xlauch.getDialogOptions = function (_options) {
    var _url = "";
    if (!_options.href || _options.href.length < 4) {
        xlauch.alertError("请求的URL配置错误");
        return;
    }

    //url：表单提交的url ， 默认是href删除后4为，例如：
    // href: /deve/exp/addInit
    // url : /deve/exp/add
    _url = _options.href.replace("Init", "");

    if (_options.url) {
        _url = _options.url;
    }

    //判断是否是iframe模式
    if (_options.isIframe) {
        _options.content = '<iframe src="' + _options.href + '" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>';
        _options.href = null;
    }

    var _opts = $.extend({
        modal: true,
        href: null,
        url: _url,
        onClose: function () {
            $(this).dialog('destroy');
        }
    }, _options);


    return _opts;
}

/**
 * 根据iframe对象获取iframe的window对象
 * @param frame
 * @returns {Boolean}
 */
xlauch.GetFrameWindow = function (frame) {
    return frame && typeof(frame) === 'object' && frame.tagName === 'IFRAME' && frame.contentWindow;
};

/**
 * 新增记录
 */
xlauch.addRow = function (_options, type) {
    if (!_options.dataGrid) {
        _options.dataGrid = $("#dataGrid");
    } else {
        _options.dataGrid = $("#" + _options.dataGrid);
    }
    _options.iconCls = _options.iconCls || "xlauch-add";

    var p = parent.xlauch.createCommonDialog(_options, type);
    return p;
};

/**
 * 修改记录验证，如果验证通过返回修改的对象，否则返回空
 */
xlauch.editRowCheck = function (dataGrid, r) {
    if (r) {
        return r;
    }

    if (!dataGrid) {
        dataGrid = $("#dataGrid");
    } else {
        dataGrid = $("#" + dataGrid);
    }

    var rows = dataGrid.datagrid('getSelections');
    if (rows.length === 0) {
        parent.$.messager.alert("提示", "请先选择数据！");
        return;
    } else if (rows.length > 1) {
        parent.$.messager.alert("提示", "只允许对单条数据进行处理！");
        return;
    }

    return rows[0];
};

/**
 * 修改记录
 */
xlauch.editRow = function (_options, type) {
    if (!_options.dataGrid) {
        _options.dataGrid = $("#dataGrid");
    } else {
        _options.dataGrid = $("#" + _options.dataGrid);
    }
    _options.iconCls = _options.iconCls || "xlauch-edit";

    var row = xlauch.editRowCheck("dataGrid", _options.row);
    _options.row = row;
    if (row) {
        return parent.xlauch.createCommonDialog(_options, type);
    }
};


/**
 * 创建通用窗口
 */
xlauch.createCommonDialog = function (_options, type) {
    var _opts = xlauch.getDialogOptions(_options);
    var p = parent.$('<div/>');

    //无自定义onload方法
    if (!_opts.onLoad) {
        _opts = $.extend({
            onLoad: function () {
                var f = p.find('form');
                f.form('load', _opts.row);
            }
        }, _opts);
    }
    if (!_opts.hiddenButton) {
        _opts.buttons = _opts.buttons || [{
            text: '保存',
            iconCls: "icon-save",
            handler: function () {
                var f = p.find('form');
                if (f.form('validate')) {
                    var options = {
                        type: "post",
                        url: _opts.url,
                        cache: "false",
                        contentType: "application/x-www-form-urlencoded;charset=utf-8",
                        beforeSend: function () {
                            parent.$.messager.progress({
                                text: '正在处理中...'
                            });
                        },
                        success: function (json) {
                            parent.$.messager.progress('close');
                            var _msg = json._msg;
                            if (json._code === xlauch.RES_SUCC) {
                                p.dialog('close');
                                if (type === "treeGrid") {
                                    _opts.dataGrid.treegrid('load');
                                } else {
                                    _opts.dataGrid.datagrid('load');
                                }

                                _msg = _opts.title + "成功";
                            }
                            parent.xlauch.messagerShow({
                                msg: _msg,
                                title: '提示'
                            });
                        },
                        error: function (json) {
                            json = json.responseJSON;
                            var _msg = json["_msg"] + "错误代码:" + json._code;
                            parent.$.messager.alert("提示", _opts.title + "失败，" + _msg);
                            parent.$.messager.progress('close');
                        }
                    };
                    // 异步提交
                    f.ajaxSubmit(options);
                }
            }
        }, {
            text: "关闭",
            iconCls: "icon-remove",
            handler: function () {
                p.dialog('close');
            }
        }];
    }

    p.dialog(_opts);
    p.getRow = function () {
        return _opts.row;
    };
    return p;
};


/**
 * 删除记录
 */
xlauch.deleteRow = function (_options, type) {
    var _opts = $.extend({
        dataGrid: "dataGrid",
        msg: "删除",
        pk: 'id'
    }, _options);

    var rows = $('#' + _opts.dataGrid).datagrid('getSelections');
    var ids = [];
    if (rows.length > 0) {
        parent.$.messager.confirm(_opts.msg + "确认!", "确定" + _opts.msg + "选择的数据吗?", function (res) {
            if (res) {
                for (var i = 0; i < rows.length; i++) {
                    //ids.push(rows[i].id);
                    ids.push(eval("rows[i]." + _opts.pk));
                }
                $.post(
                    _opts.url,
                    {
                        ids: ids.join(',')
                    },
                    function (json) {
                        var _msg = json._msg;
                        if (json._code === xlauch.RES_SUCC) {
                            if (type === "treeGrid") {
                                $('#' + _opts.dataGrid).treegrid('load');
                            } else {
                                $('#' + _opts.dataGrid).datagrid('load');
                            }
                            _msg = "成功" + _opts.msg + json._result + "条记录!";
                        }
                        parent.xlauch.messagerShow({
                            msg: _msg,
                            title: '提示'
                        });
                    }
                );
            }
        });
    } else {
        parent.$.messager.alert("提示", "请选择要删除的对象!");
    }
};


/**
 * 序列表单对象
 */
xlauch.serializeForm = function (_form) {
    var _obj = {};		//定义对象
    //把表单序列化成数组
    $.each(_form.serializeArray(), function (index) {
        var _name = this['name'];
        var _value = this['value'];
        if (_value != null && _value !== undefined && _value !== '') {
            if (!_obj[_name]) {
                _obj[_name] = _value;
            } else {
                var _oldValue = _obj[_name];
                _obj[_name] = _oldValue + "," + _value;
            }
        }
    });
    return _obj;
};

/**
 * 文件上传
 * @param _options
 */
xlauch.upload = function (_options) {
    var _opts = $.extend({
        upNum: 5,
        filters: {
            max_file_size: '10mb', //最大只能上传10MB的文件
            prevent_duplicates: true //不允许选取重复文件
        }
    }, _options);

    var _url = xlauch.getpn() + "/file/toUploadPage";
    var uploader = $('<iframe/>');
    uploader.attr({'src': _url, width: 550, height: 314, border: '0', frameborder: '0', scrolling: 'no'});

    var p = parent.xlauch.dialog({
        title: "&nbsp;上传文件",
        iconCls: "icon-add",
        border: false,
        height: 350,
        width: 550,
        modal: true,
        //minimizable: true,  //最小化，默认false
        //collapsible: true,  //可折叠，默认false
        maximizable: true,  //最大化，默认false
        resizable: true,  //可伸缩，通过拖动改变大小，默认false
        content: uploader,
        onClose: function () {
            var fw = xlauch.GetFrameWindow(uploader[0]);
            var files = fw.succ;
            if (_opts.okfun) {
                _opts.okfun(files);
            }
        },
        onOpen: function () {
            var fw = xlauch.GetFrameWindow(uploader[0]);
            fw.onload = function () {
                fw.target = p;
                fw._opts = _opts;
                fw.data = _opts.data;
                fw.uploadFile();
            };
        }
    });
};

/**
 * 选择图标
 * @param _options
 */
xlauch.selIcon = function (_options, ctxPath) {
    var _opts = $.extend({}, _options);
    var p = parent.xlauch.dialog({
        title: '选择图标',
        width: 605,
        height: 400,
        modal: true,
        href: ctxPath + '/sys/sysPermission/iconInit',
        cache: false,
        onClose: function () {
            var iconUrl = p.find("#_commSelIconUrl").val();
            var iconCss = p.find("#_commSelIconCss").val();
            if (_opts.selfun) {
                _opts.selfun(iconUrl, iconCss);
            }
            p.dialog('destroy');
        }
    }, "_commSelIcon");
};


/**
 * 全局ajax请求设置
 */
$.ajaxSetup({
    type: 'POST',
    error: function (XMLHttpRequest, textStatus, errorThrown) {
        easyuiErrorFunction(XMLHttpRequest);
    }
});