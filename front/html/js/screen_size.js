var Ftsize =1;
$(function(){
    $('.resize').on('click',function(){
        var val = $(this).val();
        val = parseFloat(val);
        Ftsize = Ftsize + val;
        if(val == "0") Ftsize = 1;
        view_plate.style.zoom = Ftsize;
    });
});
