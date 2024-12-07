$(document).ready(function(){
    var dt_product_table = $('#table-product'),
        statusObj = {
            0: {title: "Non Aktif"},
            1: {title: "Aktif"},
        },
        riwayatObj = {
            0: {title: "Non Aktif"},
            1: {title: "Aktif"},
        };

    if (dt_product_table.length > 0) {
        var ajaxUrl = $('#product-title').attr('href');
        var dt_product = dt_product_table.DataTable({
            ajax: ajaxUrl,
            columns: [
                {data : 'id'},
                {data : 'name'},
                {data : 'categoryName'},
                {data : 'quantity'},
                {data : 'price'},
                {data : 'stock'},
                {data : 'onOrder'},
                {data : ''}
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
                    searchable: false,
                    orderable: false,
                    render: (data, type, full, meta) => {
                        var $item = full['name'];
                        return '<span>' + $item + '</span>';
                    }
                },
                {
                    targets: 2,
                    searchable: false,
                    orderable: false,
                    render: (data, type, full, meta) => {
                        var $item = full['categoryName'];
                        return '<span>' + $item + '</span>';
                    }
                },
                {
                    targets: 3,
                    searchable: false,
                    orderable: false,
                    render: (data, type, full, meta) => {
                        var $item = full['quantity'];
                        return '<span>' + $item + '</span>';
                    }
                },
                {
                    targets: 4,
                    searchable: false,
                    orderable: false,
                    render: (data, type, full, meta) => {
                        var $item = full['price'];
                        return '<span>' + $item + '</span>';
                    }
                },
                {
                    targets: 5,
                    searchable: false,
                    orderable: false,
                    render: (data, type, full, meta) => {
                        var $item = full['stock'];
                        return '<span>' + $item + '</span>';
                    }
                },
                {
                    targets: 6,
                    searchable: false,
                    orderable: false,
                    render: (data, type, full, meta) => {
                        var $item = full['onOrder'];
                        return '<span>' + $item + '</span>';
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

        dt_product.on('order.dt search.dt', function () {
            let i = 1;

            dt_product.cells(null, 0, {search: 'applied', order: 'applied'})
                .every(function (cell) {
                    this.data(i++);
                });
        }).draw();
    }

    $('.dataTables_length').addClass('mt-2 mt-sm-0 mt-md-3 me-2');
    $('.dt-buttons').addClass('d-flex flex-wrap');

    $('#btn-add').click(function () {
        var url = $(this).attr('href');
        showModal(url,'large');
    });

    $('#main-modal').on('submit','#form-product', function (event) {
        event.preventDefault();
        var ajaxUrl = $(this).attr('action');
        const data = convertFormToJSON($(this));
        ajaxSubmit(ajaxUrl, data, dt_product);
    });

    $('#table-product').on('click', '.btn-edit', function (event) {
        event.preventDefault();

        var url = $(this).closest('button').attr('href');
        showModal(url,'large');

    });

    $('#table-product').on('click', '.btn-delete', function (event) {
        event.preventDefault();

        var url = $(this).closest('button').attr('href');
        showModal(url,'large');
    });
});