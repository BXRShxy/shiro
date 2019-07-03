(function ($) {
    /**
     * 创建字典combobox
     * @param options
     */
    $.fn.xlauchDict = function (options) {
        var opts = $.extend({
            width: 200,
            valueField: 'val',
            textField: 'name',
            onLoadSuccess: function () { //加载完成后,设置选中第一项
                var _datas = $(this).combobox("getData");
                $(this).combobox("select", _datas[0].val);
            }
        }, options);

        //字典请求路径
        var _url = "/sys/sysDict/listByCode?code=" + opts.code;
        opts.url = _url;

        $(this).combobox(opts);
    };
})(jQuery);