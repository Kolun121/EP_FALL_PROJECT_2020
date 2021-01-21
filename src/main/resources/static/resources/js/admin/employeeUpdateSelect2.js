$("#employeeStaffingTable").select2({
    theme: "classic",
    language: "ru",
    ajax: {
        url: document.URL + '/staffingTablesAjax',
        dataType: 'json',
        delay: 250,
        processResults: function (response) {
            return {
                results: response
            };
        },
        cache: true
    }
});