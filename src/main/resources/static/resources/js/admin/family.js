var family = {
    listFamilyMembers: $('#listFamilyMembers'),
    addFamilyMember: function(event) {
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
                    _this.listFamilyMembers.append(result);
                }
            }
        });      
    },
    saveFamilyMember: function(evnt, familyId) {
        evnt.preventDefault();
        var data = {};
        data["firstName"] = $("#family-first_name-" + familyId).val();
        data["lastName"] = $("#family-last_name-" + familyId).val();
        data["patronymic"] = $("#family-patronymic-" + familyId).val();
        data["relation"] = $("#family-relation-" + familyId).val();
        data["birthDate"] = $("#family-birth_date-" + familyId).val();
        
        $.ajax({
            url: document.URL + '/' + familyId,
            type: "POST",
            data: data,
            dataType: 'json'
        });     
    },
    deleteFamilyMember: function(event, familyId) {
        event.preventDefault();
        $.ajax({
            url: document.URL + '/' + familyId,
            type: "DELETE",
            processData: false,
            contentType: false,
            beforeSend: function(){

            },
            success: function(){
               
                $('.family-item-' + familyId).remove(); 
                
            }
        });       
    }
}