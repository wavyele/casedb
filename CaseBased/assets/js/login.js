$(function () {
    $('#logins').click(function(){
    	$('#login').removeClass('active');
    	$('#signup').removeClass('active');
    	$('#forgot').removeClass('active');
    	$('#login').addClass('active');
    });
     $('#fogpass').click(function(){
    	$('#login').removeClass('active');
    	$('#signup').removeClass('active');
    	$('#forgot').removeClass('active');
    	$('#forgot').addClass('active');
    });

      $('#signups').click(function(){
    	$('#login').removeClass('active');
    	$('#signup').removeClass('active');
    	$('#forgot').removeClass('active');
    	$('#signup').addClass('active');
    	//alert('');
    });
    $('.list-inline li > a').click(function () {
        var activeForm = $(this).attr('href') + ' > form';
        //console.log(activeForm);
        $(activeForm).addClass('magictime swap');
        //set timer to 1 seconds, after that, unload the magic animation
        setTimeout(function () {
            $(activeForm).removeClass('magictime swap');
        }, 1000);
    });
});

