var token = $("input[name='_csrf']").val();
    var header = "X-CSRF-TOKEN";
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
    
var t = $('#data_table').DataTable({
        columnDefs: [{
            orderable: false,
            targets: [6]
        },
//        {
//            "targets": 6,
//            "data": "id",
//            "render": function ( data, type, row, meta ) {
//                console.log(data);
//                return '<a text="Перейти" href="/admin/employees/' + data + '">Перейти</a>';
//            }
//        }
        ],
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
           {"data": "firstName", 
               "width": "15%",
               "defaultContent": "<i>Не указана</i>"
           },
           {"data": "lastName", 
               "width": "15%",
               "defaultContent": "<i>Не указана</i>"          
           },
           {"data": "patronymic", 
               "width": "15%",
               "defaultContent": "<i>Не указана</i>"
           },
           {"data": "staffingTable.position.positionName", 
               "width": "15%",
               "defaultContent": "<i>Не указана</i>"
           },
           {"data": "staffingTable.department.departmentName", 
               "width": "15%",
               "defaultContent": "<i>Не указан</i>"
           },
           {"data": "hireDate", 
               "width": "50%",
               "defaultContent": "<i>Не указан</i>"
           },
           {
                "className":      'details-control',
                "orderable":      false,
                "data":           null,
                "defaultContent": ''
            }
       ]
});

$('#data_table tbody').on('click', 'td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = t.row( tr );
        console.log(row.data());
        if ( row.child.isShown() ) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            // Open this row
            row.child( format(row.data()) ).show();
            tr.addClass('shown');
        }
    } );
   
function format ( d ) {
    var passport = d.passport;
    if(passport.issuedBy === null){
       passport.issuedBy = "Не указано кем выдан паспорт"; 
    }
    if(passport.issueDate === null){
       passport.issueDate = "Дата выдачи не указана"; 
    }
    if(passport.passportID === null){
       passport.passportID = "ID паспорта не указан"; 
    }
    
    var educations = d.educations;
    educations.forEach(function(education, i, educations) {
        education.specialty;
        education.qualification;
        education.graduationDate;
    });
    
    var familyMembers = d.familyMembers;
    familyMembers.forEach(function(familyMember, i, familyMembers) {
        familyMember.firstName;
        familyMember.lastName;
        familyMember.patronymic;
        familyMember.relation;
        familyMember.birthDate;
    });
    
    var jobHistoryList = d.jobHistoryList;
    jobHistoryList.forEach(function(job, i, jobHistoryList) {
        job.jobTitle;
        job.startDate;
        job.endDate;
    });
    
    var vacationsList = d.vacationsList;
    vacationsList.forEach(function(vac, i, vacationsList) {
        vac.vacationType;
        vac.startDate;
        vac.endDate;
    });
    
    var vacationsListHtml = '<div class="card-deck">'+
  '<div class="card">' +
    '<div class="card-body">' +
      '<h5 class="card-title">Card title</h5>' +
      '<p class="card-text">This is a longer card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>' +
      '<p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>' +
    '</div>' +
  '</div>' +
  '<div class="card">' +
    '<div class="card-body">' +
      '<h5 class="card-title">Предыдущая должность</h5>' +
      '<ul class="list-group list-group-flush">' +
    '<li class="list-group-item">Название должности: 123123123</li>' +
   '<li class="list-group-item">Дата вступления на должность:</li>' +
    '<li class="list-group-item">Дата ухода с должности:</li>' +
  '</ul>' +
    '</div>' +
  '</div>' +
  '<div class="card">' +
    '<div class="card-body">' +
      '<h5 class="card-title">Card title</h5>' +
      '<p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content. This card has even longer content than the first to show that equal height action.</p>' +
      '<p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>' +
    '</div>' +
  '</div>' +
'</div>';
    console.log(passport);
    var passportRow = '<div class="card text-center">' +
  '<div class="card-header"> Паспорт' +
  '</div>' +
  '<ul class="list-group list-group-flush">' +
    '<li class="list-group-item">Cras justo odio</li>' +
   '<li class="list-group-item">Dapibus ac facilisis in</li>' +
    '<li class="list-group-item">Vestibulum at eros</li>' +
  '</ul>' +
'</div>';
    return vacationsListHtml + vacationsListHtml ;
//    return '<table cellpadding="15" cellspacing="0" border="0" style="padding-left:50px;">'+
//        '<tr>'+
//            '<td>Full name:</td>'+
//            '<td>'+d.firstName+'</td>'+
//        '</tr>'+
//        '<tr>'+
//            '<td>Extension number:</td>'+
//            '<td>'+d.firstName+'</td>'+
//        '</tr>'+
//        '<tr>'+
//            '<td>Extra info:</td>'+
//            '<td>And any further details here (images etc)...</td>'+
//        '</tr>'+
//    '</table>'+
//    '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'+
//        '<tr>'+
//            '<td>Full name:</td>'+
//            '<td>'+d.firstName+'</td>'+
//        '</tr>'+
//        '<tr>'+
//            '<td>Extension number:</td>'+
//            '<td>'+d.firstName+'</td>'+
//        '</tr>'+
//        '<tr>'+
//            '<td>Extra info:</td>'+
//            '<td>And any further details here (images etc)...</td>'+
//        '</tr>'+
//    '</table>';
}