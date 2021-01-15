var education = {
    listEducationItems: $('#listEducationItems'),
    addEducationItem: function(event) {
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
                    _this.listEducationItems.append(result);
                }
            }
        });      
    },
    saveEducationItem: function(evnt, educationId) {
        evnt.preventDefault();
        var data = {};
        data["specialty"] = $("#education-specialty-" + educationId).val();
        data["qualification"] = $("#education-qualification-" + educationId).val();
        data["graduationDate"] = $("#education-graduation_date-" + educationId).val();
        
        console.log($("#education-graduation_date-" + educationId).val());
        $.ajax({
            url: document.URL + '/' + educationId,
            type: "POST",
            data: data,
            dataType: 'json'
        });     
    },
    deleteEducationItem: function(event, educationId) {
        event.preventDefault();
        $.ajax({
            url: document.URL + '/' + educationId,
            type: "DELETE",
            processData: false,
            contentType: false,
            beforeSend: function(){

            },
            success: function(){
               
                $('.education-item-' + educationId).remove(); 
                
            }
        });       
    }
}