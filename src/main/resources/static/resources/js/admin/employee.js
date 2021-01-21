var employee = {
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