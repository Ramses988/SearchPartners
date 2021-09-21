let template = '<div class="comment-group block-comments"><article class="comment"><div class="comment-aside">\n' +
    '           <div class="comment-avatar" style="background-color:sColor"><span>$initial</span>\n' +
    '           </div></div> <div class="comment-header"><div class="comment-header-inner"><p class="comment-title">$name</p>\n' +
    '           <span class="comment-time">$date</span></div></div><div class="comment-main">\n' +
    '           <div class="comment-text">$text</div><div class="comment-footer">\<ul class="comment-list"><li> <a class="comment-link" href="#">' +
    '           <span class="icon mdi mdi-thumb-up-outline"></span><span>0</span></a></li>\n' +
    '           <li><a class="comment-link" href="#"><span class="icon mdi mdi-comment-outline"></span><span>Ответить</span></a></li></ul>\n' +
    '           </div></div></article></div>';
let elementId = '';

function formatDate() {
    let now = new Date();
    months = 'янв,фев,мар,апр,мая,июн,июл,авг,сен,окт,дек'.split(',');
    return now.getDate() + " " + months[now.getMonth()] + ". " + now.getFullYear() + ", "
        + now.getHours() + ":" + (now.getUTCMinutes()<10?'0':'') + now.getUTCMinutes();
}

function addComment(data) {
    let str = template;
    let date = formatDate();
    str = str.replace("sColor", data.user.color);
    str = str.replace("$initial", data.user.initial);
    str = str.replace("$name", data.user.name);
    str = str.replace("$text", "<p>"+data.text+"</p>");
    str = str.replace("$date", date);
    let block = $('.block-comments');
    if (block.length) {
        block.first().before(str);
    } else {
        $('.comment-group').last().after(str);
    }
}

function addCommentInner(data) {
    let str = template;
    let date = formatDate();
    str = str.replace("sColor", data.user.color);
    str = str.replace("$initial", data.user.initial);
    str = str.replace("$name", data.user.name);
    str = str.replace("$text", data.text);
    str = str.replace("$date", date);
    str = str.replace("block-comments", "block-comments-inner");
    let block = elementId.parents('.block-comments').children('.block-comments-inner').last();
    if (block.length) {
        block.after(str);
    } else {
        elementId.parents('.comment').after(str);
    }
}

$('#sendToLK').click(function () {
    window.location.replace("/chat/" + $('#userIdLK').text());
});

$('#btn-addCommit').click(function () {
    $.ajax({
        type: 'POST',
        url: '/rest/comments/save-comment',
        data: $('#addPostComment').serialize(),
        success: function (data) {
            addComment(data);
            $('#comment-message').val('');
            successNoty('Комментарий успешно добавлен!');
        },
        error: function (data) {
            failNoty(data.responseText);
        }
    })
});

$('#btnAddPostCommentChildren').click(function () {
    $.ajax({
        type: 'POST',
        url: '/rest/comments/save-comment-children',
        data: $('#addPostCommentChildren').serialize(),
        success: function (data) {
            addCommentInner(data);
            $('.popup-fade').fadeOut();
            successNoty('Комментарий успешно добавлен!');
        },
        error: function (data) {
            failNoty(data.responseText);
        }
    })
});

$('.popup-open').click(function() {
    elementId = $(this);
    let parent = $(this).parents('.comment');
    $('#parent').val(parent.children(".number-parent").val());
    $('#children').val(parent.children(".number-children").val());
    $('#message').val('');
    $('.popup-fade').fadeIn();
    return false;
});

// Клик по ссылке "Закрыть".
$('.popup-close').click(function() {
    $(this).parents('.popup-fade').fadeOut();
    return false;
});

// Закрытие по клавише Esc.
$(document).keydown(function(e) {
    if (e.keyCode === 27) {
        e.stopPropagation();
        $('.popup-fade').fadeOut();
    }
});

// Клик по фону, но не по окну.
$('.popup-fade').click(function(e) {
    if ($(e.target).closest('.popup').length == 0) {
        $(this).fadeOut();
    }
});

// $('.block-comments').remove();