$(document).ready(function () {
		myFunction();
});

function myFunction() {
    setInterval(function(){ 
    	get_new_events();
    	//alert("Hello"); 
    }, 3000);
}

function get_new_events()
{
	var url = '/CaseBased/Dashboard/refresh_events';
	$.post(url,{},function(returned_data){
		//console.log(returned_data);
		var html='';
		var html_string ='';
		$.each(returned_data,function(){
			var event_count = (this.event_count);
			var total_event_count = this.total_event_count;
			var percentage = (event_count/total_event_count)*100;
			var percent =(Math.round(percentage*10)/10);
			html+='<div class="col-lg-3" style="border:1px solid #5BC0DE; border-radius:7%; margin:3px; text-align: center; font-family: Open Sans,Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#f5f5f5" title="'+this.description+'">';
			html+='<a class="btn" href="#" > <i class=""></i>';
			html+='<span>  <h5>'+this.event_name+'</h5></span><br/>';
			html+='<span class="btn btn-info">'+this.event_count+'</span> <br/>';
			html+='<span class="">'+percent+'%'+'</span>';
			html+=' </a></div>';

			html_string+=' <span>'+this.event_name+'</span><span class="pull-right"><small>'+percent+'%</small></span>';
			html_string+='<div class="progress mini">';
			html_string+='<div class="progress-bar progress-bar-info" style="width: '+percent+'%"></div></div>';
		
		});

		$('.report_div').html('');
		$('.report_div').html(html);
		$('#graphical').html('');
		$('#graphical').html(html_string);
		
	},'json');

	var post_url='/CaseBased/Dashboard/get_total_event_counts';
	$.post(post_url,{},function(returned_data){
		var	total_event_count=returned_data.total_event_count;
		$('#total-events').html(total_event_count);
		
	},'json');
}
	



	
