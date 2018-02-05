$(function(){
    var order = ['0', '1', '2', '3', '4',]; /* （仮）配置情報　*/
    var sort = ['0','0' ,'0']; /* 並び替え用　*/

    $("#template_block_list .wrap_template_block .template_block_body").hide();

    $(".wrap_template_block")
        .on('dragstart', function(e){
            e.originalEvent.dataTransfer.setData('id', this.id);
        });

    $("#template_plate")
        .on('dragover', function(e){
            $(this).css('background', '#d1ffd5');
            e.preventDefault();
        })
        .on('dragenter', function(e){
            $(this).css('background', '#d1ffd5');
        })  
        .on('dragleave', function(e){
            $(this).css('background', '#fff');
        })
        .on('drop', function(e){
            $(this).css('background', '#fff');
            $('#template_content_list').append('<div class="template_content"></div>');
            var id = e.originalEvent.dataTransfer.getData('id');
            var obj = $('#' + id + ' .template_block_body').clone();
            $(obj).show();
            $('.template_content:last').append(obj);
            $(".remove").on('click', function(){
                $(this).parent().parent().fadeOut(300, function(){
                    $(this).remove();
                });
            });   
        });
        $(".remove").on('click', function(){ /*最初から配置されているもの用、上とまとめた方がよさげ*/
            $(this).parent().parent().fadeOut(300, function(){
                $(this).remove();
            });
        });
        $(".down").on('click', function(){
            if(order[order.length -1] != order[order.indexOf($(this).parent().parent().attr('id'))]){
                $(this).parent().parent().before($('#' + order[order.indexOf($(this).parent().parent().attr('id'))+1]));
                sort[0] = order.indexOf($(this).parent().parent().attr('id'));
                sort[1] = order.indexOf($(this).parent().parent().attr('id'))+1;
                sort[2] = order[order.indexOf($(this).parent().parent().attr('id'))];
                order[sort[0]] = order[order.indexOf($(this).parent().parent().attr('id'))+1];
                order[sort[1]] = sort[2];
            }
        });
        $(".up").on('click', function(){
            if(order[0] != order[order.indexOf($(this).parent().parent().attr('id'))]){
                $(this).parent().parent().after($('#' + order[order.indexOf($(this).parent().parent().attr('id'))-1]));
                sort[0] = order.indexOf($(this).parent().parent().attr('id'));
                sort[1] = order.indexOf($(this).parent().parent().attr('id'))-1;
                sort[2] = order[order.indexOf($(this).parent().parent().attr('id'))];
                order[sort[0]] = order[order.indexOf($(this).parent().parent().attr('id'))-1];
                order[sort[1]] = sort[2];
            }
        });
        $(function() {
            $('#template_content_list').sortable();
        });

});