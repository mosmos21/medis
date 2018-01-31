$(function(){

    // ================  登録されたタブの反映 ================
    // $('#search_tag_candidate .tag_base').click(function(){
    $(document).on('click', '#surveillance_tag_candidate .tag_base', function(){
        //if($('#surveillance_tags .tag_base').length >= 5){ return; }
        var tag_text = $(this).text();
        var size = $('#surveillance_tags .tag_base').filter(function(){ return $(this).text() == tag_text + 'x';}).length;
        if(size == 0){
            var new_tag_html = '<div class="tag_base tag_small tag_erasable surveillance_tag">' + tag_text + '<div class="tag_remove">x</div></div>\n';
            $('#surveillance_tags').append(new_tag_html);
        }
        if($('#surveillance_tags .surveillance_tag').length >= 5){
            $('#surveillance_box input').hide();
        }

        $('.tag_remove').on('click', function(){
            $($(this).parent()).remove();
            if($('#surveillance_tags .surveillance_tag').length == 4){
                $('#surveillance_box input').show();
            }
        });
    });

    $('#surveillance_box').on('click', function(){
        $('#surveillance_box input').focus();
    });

});

