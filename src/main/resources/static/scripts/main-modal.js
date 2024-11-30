function showModal(url, title) {
    $.ajax({
        url: url,
        method: "GET",
        dataType: "html",
        success: function (result) {
            $('#main-modal').find('.modal-title').text(title);

            $('#main-modal').find('.modal-content').html(result);

            $('#main-modal').modal('show');
        }
    });
}

function convertFormToJSON(form) {
    return $(form)
        .serializeArray()
        .reduce(function (json, { name, value }) {
            json[name] = value;
            return json;
        }, {});
}

var token = $("meta[name='_csrf']").attr("content");
function ajaxSubmit(url, data, dataTable = null){
    console.log(data);
    $.ajax({
        url: url,
        type: 'POST',
        data: data,
        dataType: 'html',
        headers: {
            'X-CSRF-TOKEN' : token
        },
        //contentType: 'application/json',
        success: function (result){
            $('#main-modal').find('.modal-content').html(result);

            var invalid = $('#main-modal').find(".invalid-feedback").length;
            if(invalid == 0) {
                $('#main-modal').modal('hide');

                if(dataTable !== null){
                    dataTable.ajax.reload();
                }
            }
        }
    });
}
    // $('#btn-add').click(function () {
    //     alert("Where are you duwing");
    // });