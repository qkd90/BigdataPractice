var About = {
    init: function () {
        About.initComp();
        About.initEvt();
        About.initPosition();
    },
    initComp: function () {
        // init sel
        var url = document.URL
        var cat = url.split("#")[1];
        if (cat && cat != "") {
            $('.ft_right a li').removeClass('li_active');
            $('.ft_right a[href = "#' + cat + '"] li').addClass('li_active');
        } else {
            $('.ft_right a li:eq(0)').addClass('li_active');
        }
    },
    initEvt: function() {
        $('.ft_right a li').on('click', function(event) {
            event.stopPropagation();
            $('.ft_right a li').removeClass('li_active');
            $(this).addClass('li_active');
        });
    },
    initPosition: function(){
        var posTop = $('.total').position().top + 70;
        var rightDiv = $('.total .ft_right');
        var left = rightDiv.position().left;
            $(window).scroll(function(){
                var scrollHeight = $(window).scrollTop();
                if(scrollHeight > posTop){
                    rightDiv.css({'position':'fixed','left':left,'top':"18px"})
                }else{
                    rightDiv.css({'position':'static'})
                }
               console.info(scrollHeight +"|"+left);
            })

    }
};
$(function(){
    About.init();
});

