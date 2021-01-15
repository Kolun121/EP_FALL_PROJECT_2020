var job_history = {
    listJobHistoryItems: $('#listJobHistoryItems'),
    addJobHistoryItem: function(event) {
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
                    _this.listJobHistoryItems.append(result);
                }
            }
        });      
    },
    saveJobHistoryItem: function(evnt, jobHistoryId) {
        evnt.preventDefault();
        var data = {};
        data["jobTitle"] = $("#job_history-job_title-" + jobHistoryId).val();
        data["startDate"] = $("#job_history-start_date-" + jobHistoryId).val();
        data["endDate"] = $("#job_history-end_date-" + jobHistoryId).val();
        
        $.ajax({
            url: document.URL + '/' + jobHistoryId,
            type: "POST",
            data: data,
            dataType: 'json'
        });     
    },
    deleteJobHistoryItem: function(event, jobHistoryId) {
        event.preventDefault();
        $.ajax({
            url: document.URL + '/' + jobHistoryId,
            type: "DELETE",
            processData: false,
            contentType: false,
            beforeSend: function(){

            },
            success: function(){
               
                $('.job_history-item-' + jobHistoryId).remove(); 
                
            }
        });       
    }
}