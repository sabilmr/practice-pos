$(document).ready (function(){
    var dt_category_table = $('#table-category'),
        statusObj = {
            0: {title: "Non Aktif"},
            1: {title: "Aktif"},
        },
        riwayatObj = {
            0: {title: "Non Aktif"},
            1: {title: "Aktif"},
        };

    if (dt_category_table.length > 0) {
        var ajaxUrl = $('#category-title').attr('href');
        var dt_category = dt_category_table.DataTable({
            ajax: ajaxUrl,
            columns: [
                {data: 'id'},
                {data: 'name'},
                {data: 'description'},
                {data: ''}
            ],
            columnDefs: [
                {
                    className: 'control',
                    searchable: false,
                    orderable: false,
                    responsivePriority: 2,
                    targets: 0,
                },
                {
                    targets: 1,
                    searchable: true,
                    orderable: true,
                    render: (data, type, full, meta) => {
                        var $item = full['name'];
                        return '<span>'+$item +'</span>';
                    }
                },
                {
                    targets: 2,
                    searchable: true,
                    orderable: true,
                    render: (data, type, full, meta) => {
                        var $item = full['description'];
                        return '<span>'+$item +'</span>';
                    }
                },
                {
                    targets: -1,
                    title: 'Actions',
                    searchable: false,
                    orderable: false,
                    render: (data, type, full, meta) => {
                        var id = full['id'];
                        var editUrl = ajaxUrl.replace('data','edit') + '/' + id;
                        var deleteUrl = ajaxUrl.replace('data','delete') + '/' + id;
                        return (
                            '<div class="d-inline-block text-nowrap">' +
                            '<button class="btn btn-xs btn-primary btn-edit" href="'+ editUrl +'">Edit</button> &nbsp;' +
                            '<button class="btn btn-xs btn-danger btn-delete" href="'+ deleteUrl +'">Delete</button>' +
                            '</div>'
                        )
                    }
                }
            ],
            lengthMenu: [5, 10, 20, 50, 70, 100]
        });

        dt_category.on('order.dt search.dt', function () {
            let i = 1;

            dt_category.cells(null, 0, { search: 'applied', order: 'applied' })
                .every(function (cell) {
                    this.data(i++);
                });
        }).draw();
    }

    $('.dataTables_length').addClass('mt-2 mt-sm-0 mt-md-3 me-2');
    $('.dt-buttons').addClass('d-flex flex-wrap');

    $('#btn-add').click(function () {
        var form = $(this).attr('href');
        showModal(form, ' ');
    });

    $('#main-modal').on('submit','#form-category', function (event) {
        event.preventDefault();
        var ajaxUrl = $(this).attr('action');
        const data = convertFormToJSON($(this));
        ajaxSubmit(ajaxUrl, data, dt_category);
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