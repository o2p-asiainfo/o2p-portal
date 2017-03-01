var Bill = function() {



    var handleDataTable = function() {

        $('#dt').dataTable({
            
            "searching": false,
            "lengthMenu": [5, 10, 15, "All"],
            
            "ordering":false,
            "aoColumnDefs": [{
                //设置第一列不排序
                "bSortable": false,
                "aTargets": [0]
            }],
            "initComplete": function() {
                App.initUniform();
            }
        });
        
        jQuery('#dt_wrapper .dataTables_length select').closest('.row').remove(); // modify table per page dropdown 

    }

    var handleDatePickers = function(model) {
        var options = {
                autoclose: true,
                minViewMode: 'months',
                format: 'mm/yyyy'
            }
        if (jQuery().datepicker) {
            $('.date-picker').datepicker(options);
        }
    }

    return {
        init: function() {
            handleDataTable();
            handleDatePickers();
         
        }
    }
}()
