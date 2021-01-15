var employee = {
    createEmployee: function(evnt) {
        evnt.preventDefault();
        $.ajax({
            url: document.URL + '/new',
            type: "POST",
            success: function(result){
                console.log(result);
                window.location = '/admin/employees/' + result;
            }
        });
    },
    deleteEmployee: function(event) {
        event.preventDefault;
        if(!confirm('Вы действительно хотите удалить сотрудника и все связанные с ним данные?')) {
            return false;
        }
        $.ajax({
            url: document.URL,
            type: "DELETE",
            beforeSend: function(){

            },
            success: function(){
                window.location = '/admin/employees';           
            },
            error: function (e) {
                console.log(e);
            }
        });
        
    }
}