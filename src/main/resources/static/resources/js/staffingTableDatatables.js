//var token = $("input[name='_csrf']").val();
//    var header = "X-CSRF-TOKEN";
//    $(document).ajaxSend(function(e, xhr, options) {
//        xhr.setRequestHeader(header, token);
//});
    
var t = $('#data_table').DataTable({
       "processing": true,
       "serverSide": true,
       "language": {
            "info":           "Отображается от _START_ до _END_ из _TOTAL_ записей",
            "infoPostFix":    "",
            "thousands":      ",",
            "lengthMenu":     "Показывать _MENU_ записей",
            "infoEmpty":      "Найдено 0 записей",
            "loadingRecords": "Загрузка...",
            "processing":     "Загрузка...",
            "search":         "Поиск:",
            "zeroRecords":    "Записей не найдено",
            "paginate": {
                "first":      "Первая",
                "last":       "Последняя",
                "next":       "След.",
                "previous":   "Пред."
            },
            "aria": {
                "sortAscending":  ": activate to sort column ascending",
                "sortDescending": ": activate to sort column descending"
            }
        },
       "ajax": {
           "url": document.URL,
           "type": "POST",
           "dataType": "json",
           "contentType": "application/json",
           "data": function (d) {
               console.log(d);
               return JSON.stringify(d);

           }
       },
       "columns": [
           {"data": "position.positionName", 
               "width": "40%",
               "defaultContent": "<i>Не указана</i>"
               
           },
           {"data": "department.departmentName", 
               "width": "40%",
               "defaultContent": "<i>Не указан</i>"
           },
           {"data": "salary", 
               "width": "5%",
               "defaultContent": "<i>Не указана</i>"
           },
           {"data": "employeesNumber", "width": "5%"},
       ]
});