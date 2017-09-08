window.earth = {}

Handlebars.registerHelper('ifCond', function (v1, v2, options) {
    if (v1 == v2) {
        return options.fn(this);
    }
    return options.inverse(this);
});
earth.buildHtml = function (selector, context) {
    var context = context || {};
    var source = $(selector).html();
    var template = Handlebars.compile(source);
    return template(context);
};
$(function() {

    var ww = $(window).width();
    var wh = $(window).height();

    $("body").css({
        "min-height": wh
    });

    $(".global > ul").css({
        "height": wh - 60
    });

    $(".global .navigation_btn").click(function() {
        $(".global > ul").fadeToggle();
    });

    $(".global > ul > li > div.name").click(function() {
        $(this).parent().siblings().find("ul").hide("fast");
        $(this).parent().find("ul").stop().fadeToggle("fast");
    });

    /*
      $(".global > ul > li").hover(function() {
         $(this).addClass("active");
      },
      function(){
        $(this).removeClass("active");
      });
    */

    $(".global > ul > li").click(function() {
        $(this).siblings().removeClass("active");
        $(this).addClass("active");
    });

});

$(window).on('resize', function(){

    var ww = $(window).width();
    var wh = $(window).height();

    $(".global > ul").css({
        "height": wh - 60
    });

    $("body").css({
        "min-height": wh
    });

});