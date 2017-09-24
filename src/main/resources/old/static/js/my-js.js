/**
 * Created by Admin on 13.05.2017.
 */

var defaultTweetLength = 10000;

function showHidePassword(href, divPass, divConfirm) {
    var type = $("#"+divPass+" input").attr("type");
    if (type === 'password') {
        $("#"+href).html('Hide password and confirm password');
        $("#"+divPass+" input, #"+divConfirm+" input").attr("type", "text");
    } else {
        $("#"+href).html('Show password and confirm password');
        $("#"+divPass+" input, #"+divConfirm+" input").attr("type", "password");
    }
}


$(document).ready(function () {

    $(".xx-large").css("font-size", "xx-large");
    $(".x-large").css("font-size", "x-large");
    $(".support-block").addClass("block-background");
    $(".form-element").attr("autocomplete", "off");

    $("head").show();

    $("#head-navbar").slideDown("slow");

    // $("a, h1, h2, h3, h4, label, p").css("color", "white");

    $("#brand").click(function () {
        $("body").hide();
        $("body").fadeToggle("slow");
    })

    $("#head-navbar").slideDown("slow");

    $("#side").show("slow");

    $("#inputMessage").val("");

    $("#inputMessage").attr("maxlength", defaultTweetLength);

    $(".div-white a, .div-white p, .div-white h4").css("color", "white");
})

function adminPanel() {
    var check = $("#adminPanel").css("display");
    if (check == 'none'){
        $("#adminPanel").slideDown(500);
    } else {
        $("#adminPanel").slideUp(500);
    }
}

function actionBtn(idBtn, disabled) {
    if (disabled) {
        $("#" + idBtn).addClass("disabled");
        $("#" + idBtn).prop('disabled', true);
    } else {
        $("#" + idBtn).removeClass("disabled");
        $("#" + idBtn).prop('disabled', false);
    }
}



