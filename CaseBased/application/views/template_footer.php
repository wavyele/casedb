    </div><!--end MAIN WRAPPER-->
</body><!--end body-->
<!-- FOOTER -->
<div id="footer">
    <p>&copy;  kemri/cghr or tracking system &nbsp;2014 &nbsp;</p>
</div>
<!--END FOOTER -->


<!-- GLOBAL SCRIPTS -->
<script src="<?php echo base_url()?>assets/plugins/jquery-2.0.3.min.js"></script>
<script src="<?php echo base_url()?>assets/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="<?php echo base_url()?>assets/plugins/modernizr-2.6.2-respond-1.1.0.min.js"></script>
<!-- END GLOBAL SCRIPTS -->

<!-- PAGE LEVEL SCRIPTS -->
<script src="<?php echo base_url(); ?>assets/js/myjquery.js"></script>
<script src="<?php echo base_url()?>assets/js/jquery-ui.min.js"></script>
<script src="<?php echo base_url()?>assets/plugins/uniform/jquery.uniform.min.js"></script>
<script src="<?php echo base_url()?>assets/plugins/inputlimiter/jquery.inputlimiter.1.3.1.min.js"></script>
<script src="<?php echo base_url()?>assets/plugins/chosen/chosen.jquery.min.js"></script>
<script src="<?php echo base_url()?>assets/plugins/colorpicker/js/bootstrap-colorpicker.js"></script>
<script src="<?php echo base_url()?>assets/plugins/tagsinput/jquery.tagsinput.min.js"></script>
<script src="<?php echo base_url()?>assets/plugins/validVal/js/jquery.validVal.min.js"></script>
<script src="<?php echo base_url()?>assets/plugins/daterangepicker/daterangepicker.js"></script>
<script src="<?php echo base_url()?>assets/plugins/daterangepicker/moment.min.js"></script>
<script src="<?php echo base_url()?>assets/plugins/datepicker/js/bootstrap-datepicker.js"></script>
<script src="<?php echo base_url()?>assets/plugins/timepicker/js/bootstrap-timepicker.min.js"></script>
<script src="<?php echo base_url()?>assets/plugins/switch/static/js/bootstrap-switch.min.js"></script>
<script src="<?php echo base_url()?>assets/plugins/jquery.dualListbox-1.3/jquery.dualListBox-1.3.min.js"></script>
<script src="<?php echo base_url()?>assets/plugins/autosize/jquery.autosize.min.js"></script>
<script src="<?php echo base_url()?>assets/plugins/jasny/js/bootstrap-inputmask.js"></script>
<script src="<?php echo base_url()?>assets/plugins/validationengine/js/jquery.validationEngine.js"></script>
<script src="<?php echo base_url()?>assets/plugins/validationengine/js/languages/jquery.validationEngine-en.js"></script>
<script src="<?php echo base_url()?>assets/plugins/jquery-validation-1.11.1/dist/jquery.validate.min.js"></script>
<script src="<?php echo base_url()?>assets/js/validationInit.js"></script>
<!--javascript for data tables-->
<script src="<?php echo base_url()?>assets/plugins/dataTables/jquery.dataTables.js"></script>
<script src="<?php echo base_url()?>assets/plugins/dataTables/dataTables.bootstrap.js"></script>

<!--include jquery steps master for wizards-->
<script src="<?php echo base_url()?>assets/plugins/jquery-steps-master/lib/jquery.cookie-1.3.1.js"></script>
<script src="<?php echo base_url()?>assets/plugins/jquery-steps-master/build/jquery.steps.js"></script>  
<!--<script src="<?php echo base_url()?>assets/js/WizardInit.js"></script> -->

<!--include bootstrap file-->
<!--<script src="<?php echo base_url()?>assets/js/bootstrap.min.js"></script>-->
<script src="<?php echo base_url()?>assets/js/jquery.bootstrap.wizard.js"></script>
<!--<script src="<?php echo base_url()?>assets/js/jquery-1.11.2.min.js"></script>-->
<!--<script src="<?php echo base_url()?>assets/js/jquery-latest.js"></script>-->
<!-- <script src="http://code.jquery.com/jquery-latest.js"></script> -->

<script>
    $(function () { formValidation(); });
</script>
<script src="<?php echo base_url()?>assets/js/formsInit.js"></script>
<script>
    function DataTablesExample(table){
    var indexOfMyCol = 0;
    var oTable = table.DataTable( {
        initComplete: function () {
            var api = this.api();
            
          //var colnum = oTable.fnGetData().length;
            
            api.columns().indexes().flatten().each( function ( i ) {
                var column = api.column(i);
                indexOfMyCol=i;
                console.log("i: "+i);
                var select = $('<select><option value=""></option></select>').appendTo( $(column.footer()).empty() ).on( 'change', function () {
                       // alert($(this).val());
                       //var val = $.fn.dataTable.util.escapeRegex($(this).val()); 
                        //alert(val);
                        column.search( $(this).val() ? '^'+$(this).val()+'$' : '', true, false ).draw();
                    } );

                column.data().unique().sort().each( function ( d, j ) {
                    select.append( '<option value="'+d+'">'+d+'</option>' )
                } );
            } );
        }
    } );

    oTable.column(indexOfMyCol).footer().innerHTML="<p></p>";
  } 
 $(document).ready(function() {
       
    var indexOfMyCol = 0;
    var oTable = $('#dataTables-example').DataTable( {
        initComplete: function () {
            var api = this.api();
            
          //var colnum = oTable.fnGetData().length;
            
            api.columns().indexes().flatten().each( function ( i ) {
                var column = api.column(i);
                indexOfMyCol=i;
                var select = $('<select><option value=""></option></select>').appendTo( $(column.footer()).empty() ).on( 'change', function () {
                       // alert($(this).val());
                       //var val = $.fn.dataTable.util.escapeRegex($(this).val()); 
                        //alert(val);
                        column.search( $(this).val() ? '^'+$(this).val()+'$' : '', true, false ).draw();
                    } );

                column.data().unique().sort().each( function ( d, j ) {
                    select.append( '<option value="'+d+'">'+d+'</option>' )
                } );
            } );
        }
    } );

    oTable.column(indexOfMyCol).footer().innerHTML="<p></p>";
});


</script>
<!-- END PAGE LEVEL SCRIPTS -->
</body>
<!-- END BODY -->
</html>
