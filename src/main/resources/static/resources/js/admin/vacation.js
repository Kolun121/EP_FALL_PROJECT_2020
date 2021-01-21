var vacation = {
    listVacationItems: $('#listVacationItems'),
    addVacationItem: function(event) {
        event.preventDefault();

        var _this = this;
        $.ajax({
            url: document.URL + '/new',
            type: "POST",
            processData: false,
            contentType: false,
            beforeSend: function(){

            },
            success: function(result){
                if (result) {
                    _this.listVacationItems.append(result);
                }
            }
        });      
    },
    saveVacationItem: function(evnt, vacationId) {
        evnt.preventDefault();
        var data = {};
        data["vacationType"] = $("#vacation-vacation_type-" + vacationId).val();
        data["startDate"] = $("#vacation-start_date-" + vacationId).val();
        data["endDate"] = $("#vacation-end_date-" + vacationId).val();
        console.log($("#vacation-vacation_type-" + vacationId).val());
        $.ajax({
            url: document.URL + '/' + vacationId,
            type: "POST",
            data: data,
            dataType: 'json'
        });     
    },
    deleteVacationItem: function(event, vacationId) {
        event.preventDefault();
        $.ajax({
            url: document.URL + '/' + vacationId,
            type: "DELETE",
            processData: false,
            contentType: false,
            beforeSend: function(){

            },
            success: function(){
               
                $('.vacation-item-' + vacationId).remove(); 
                
            }
        });       
    }
}