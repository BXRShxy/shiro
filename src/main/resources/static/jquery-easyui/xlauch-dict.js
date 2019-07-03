var dict = $.extend({} , dict);

dict.yesOrNot = function(value,row,index){
    if (value == "1"){
        return "是";
    } else {
        return "<font color='red'>否</font>";
    }
}