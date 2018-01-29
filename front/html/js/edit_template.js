$(function(){

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

            $(".content_remove").on('click', function(){
                $(this).parent().parent().remove();
            });
        });

    
});