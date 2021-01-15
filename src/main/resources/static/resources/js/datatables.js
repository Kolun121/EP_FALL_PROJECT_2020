 $('#data_table').DataTable({
        "processing": true,
        "serverSide": true,
        "ajax": {
            "url": "/",
            "type": "POST",
            "dataType": "json",
            "contentType": "application/json",
            "data": function (d) {
                return JSON.stringify(d);
            }
        },
        "columns": [
            {"data": "name", "width": "20%"},
            {"data": "position","width": "20%"},
            {"data": "office", "width": "20%"},
            {"data": "start_date", "width": "20%"},
            {"data": "salary", "width": "20%"}
        ]
    });

