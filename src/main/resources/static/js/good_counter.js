$(function () {
    $(".add_good_btn").on("click", function () {
        var updateId = $(this).val();
        $.ajax({
            url: "http://localhost:8080/ex-bbs/goodAdd",
            type: "POST",
            dataType: "json",
            data: {
                id: $(this).val()
            },
            async: true
        }).done(function (data) {
            console.dir(JSON.stringify(data));
            var label_id = "add_counter" + updateId;
            console.log(label_id);
            $("#"+label_id).html(data.goodCounter);
        }).fail(function (xmlHttpRequest, textStatus, errorThrown) {
            console.log("XMLHttpRequest :" + xmlHttpRequest);
            console.log("textStatus     :" + textStatus);
            console.log("errorThrown    :" + errorThrown);
        })
    });
});