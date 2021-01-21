var form = document.getElementById("staffingTableForm");

$('#optionsbox').bootstrapDualListbox({
    nonSelectedListLabel: 'Все сотрудники',
    infoText: 'Показывается {0} сотрудника(ов)',
    selectedListLabel: 'Выбранные сотрудники',
    preserveSelectionOnMove: 'moved',
    moveSelectedLabel: true,
    moveOnSelect: false 
});

document.getElementById("staffingTableFormSubmit").addEventListener("click", function (event) {
//    event.preventDefault();
//    $('#bootstrap-duallistbox-nonselected-list_employees option').prop('selected', false);
    $('#bootstrap-duallistbox-nonselected-list_employees option').removeAttr('selected');
    form.submit();
});