$(function () {
    $('#btn-add').click(function () {
        var form = $(this).attr('href');
        showModal(form, ' ');
    });

    $('#main-modal').on('submit','#form-category', function (event) {
        event.preventDefault();
        var ajaxUrl = $(this).attr('action');
        const data = convertFormToJSON($(this));
        ajaxSubmit(ajaxUrl, data);
    });

    $('#table-category').on('click', '.btn-edit', function (e) {
        e.preventDefault(); // Mencegah aksi default tombol
        var url = $(this).closest('button').attr('href'); // Ambil href dari tombol
        showModal(url, ' ');
    });

    $('#table-category').on('click', '.btn-delete', function (e) {
        e.preventDefault();
        var url = $(this).closest('button').attr('href');
        showModal(url, ' ');
    });
});