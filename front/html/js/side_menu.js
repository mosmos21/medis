$(function(){

    // ================ メインコンテンツの幅 ================
    $('#wrap_main_content').css('width', $(window).width() - 320 + 'px');


    // ================ サイドメニュー開閉 ================    
    $('#side_menu_key').on('click', function(){
        if($('#wrap_side_menu').hasClass('active')){
            var main_content_close = $(window).width();
            $('#wrap_side_menu').stop().animate({left:'-320px'}, {queue:false}).removeClass('active');
            $('#wrap_main_content').stop().animate({left:'0px', width: main_content_close + 'px'}, {queue:false});
        } else {
            var main_content_open = $(window).width() - 320;
            $('#wrap_side_menu').stop().animate({left:'0'}).addClass('active');
            $('#wrap_main_content').stop().animate({left:'320px', width: main_content_open + 'px'}, {queue:false});
        }
    });

    // ================ ログアウトボタン ================
    $('#icon_logout').on('click', function(){
        window.location.href = 'login.html';
    });

    // ================ サイドメニュータブ切り替え ================
    $('#wrap_side_menu_content div[class="menu_content"]').hide();
    $('#wrap_side_menu_content div[id="menu_user_content"').show();
    $('#menu_user').addClass('side_menu_icon_active');
    $('#menu_user').css('background-image', 'url("./image/menu_user_bk.svg")');

    $('.side_menu_icon').on('click', function(){
        // 一旦消す
        $('#wrap_side_menu_content div[class="menu_content"]').hide();
        $('.side_menu_icon').each(function(){
            $(this).removeClass('side_menu_icon_active');
            $(this).css('background-image', 'url("./image/' + this.id + '.svg")');
        });

        // 選ばれているタブ
        $('#wrap_side_menu_content div[id=' + this.id + '_content]').show();
        $(this).addClass('side_menu_icon_active');
        $(this).css('background-image', 'url("./image/' + this.id + '_bk.svg")');
    });

    // ================ ウィンドウのリサイズ時の処理 ================
    $(window).on('load resize', function(){
        var new_size = $(window).width() - 320 - parseInt($('#wrap_side_menu').css('left'));
        $('#wrap_main_content').css('width', new_size + 'px');
    });

    // ================  検索されたタブの反映 ================
    // $('#search_tag_candidate .tag_base').click(function(){
    $(document).on('click', '#search_tag_candidate .tag_base', function(){
        if($('#selected_tags .tag_base').length >= 5){ return; }
        var tag_text = $(this).text();
        var size = $('#selected_tags .tag_base').filter(function(){ return $(this).text() == tag_text + 'x';}).length;
        if(size == 0){
            var new_tag_html = '<div class="tag_base tag_small tag_erasable">' + tag_text + '<div class="tag_remove">x</div></div>\n';
            $('#selected_tags').append(new_tag_html);
        }
        if($('#selected_tags .tag_base').length >= 5){
            $('#search_box input').hide();
        }

        $('.tag_remove').on('click', function(){
            $($(this).parent()).remove();
            if($('#selected_tags .tag_base').length == 4){
                $('#search_box input').show();
            }
        });
    });

    $('#search_box').on('click', function(){
        $('#search_box input').focus();
    });

});

