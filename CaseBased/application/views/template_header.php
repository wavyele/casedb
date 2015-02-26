<!--[if !IE]><!--> <html lang="en"> <!--<![endif]-->

 <!-- BEGIN HEAD -->
<head>
      <meta charset="UTF-8" />
    <title>
        <?php 
                if (isset($title))
                {
                    $datas['title']=$title;
                }
            else{
                $datas['title']='';
            }
            echo $title;
        ?>
    </title>
     <meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="Mike Aono" />
     <!--[if IE]>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <![endif]-->
    <!-- GLOBAL STYLES -->
    <!-- GLOBAL STYLES -->
    <link rel="stylesheet" href="<?php echo base_url()?>assets/plugins/bootstrap/css/bootstrap.min.css" />
    <link rel="stylesheet" href="<?php echo base_url()?>assets/css/main.css" />
    <link rel="stylesheet" href="<?php echo base_url()?>assets/css/theme.css" />
    <link rel="stylesheet" href="<?php echo base_url()?>assets/css/MoneAdmin.css" />
    <link rel="stylesheet" href="<?php echo base_url()?>assets/plugins/Font-Awesome/css/font-awesome.css" />
    <link rel="shortcut icon" href="<?php echo base_url();?>assets/img/favicon.ico" type="image/x-icon">
    <link rel="icon" href="<?php echo base_url();?>assets/img/favicon.ico" type="image/x-icon">

    <!--END GLOBAL STYLES -->

    <!-- PAGE LEVEL STYLES -->
     <link rel="stylesheet" href="<?php echo base_url()?>assets/plugins/validationengine/css/validationEngine.jquery.css" />
     <link href="<?php echo base_url()?>assets/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet" />
       <!-- PAGE LEVEL STYLES for wizard management-->
    <link href="<?php echo base_url();?>assets/plugins/jquery-steps-master/demo/css/normalize.css" rel="stylesheet" />
    <link href="<?php echo base_url();?>assets/plugins/jquery-steps-master/demo/css/wizardMain.css" rel="stylesheet" />
    <link href="<?php echo base_url();?>assets/plugins/jquery-steps-master/demo/css/jquery.steps.css" rel="stylesheet" />   

    <!-- Include bootstrap  -->  
    <!--<link rel="stylesheet" href="<?php echo base_url()?>assets/css/bootstrap.min.css" />-->


    <!-- END PAGE LEVEL  STYLES -->
   <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
    
</head>
     <!-- END HEAD -->

     <!-- BEGIN BODY -->
<body class="padTop53">
    <!-- MAIN WRAPPER -->
    <div id="wrap">
        <!--header section-->
         <div id="top">

            <nav class="navbar navbar-inverse navbar-fixed-top " style="padding-top: 10px;">
                <a data-original-title="Show/Hide Menu" data-placement="bottom" data-tooltip="tooltip" class="accordion-toggle btn btn-primary btn-sm visible-xs" data-toggle="collapse" href="#menu" id="menu-toggle">
                    <i class="icon-align-justify"></i>
                </a>
                <!-- LOGO SECTION -->
                <header class="navbar-header">

                    <a href="<?php echo base_url()?>Home/load_home_page" class="navbar-brand">
                    <img src="<?php echo base_url()?>assets/img/logo1.jpg" alt="OR TRACKING SYSTEM" /></a>
                </header>
                <!-- END LOGO SECTION -->
                <ul class="nav navbar-top-links navbar-right">

                    <!-- MESSAGES SECTION -->
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                            <span class="label label-success">2</span>    <i class="icon-envelope-alt"></i>&nbsp; <i class="icon-chevron-down"></i>
                        </a>

                        <ul class="dropdown-menu dropdown-messages">
                            <li>
                                <a href="#">
                                    <div>
                                       <strong>John Smith</strong>
                                        <span class="pull-right text-muted">
                                            <em>Today</em>
                                        </span>
                                    </div>
                                    <div>Lorem ipsum dolor sit amet, consectetur adipiscing.
                                        <br />
                                        <span class="label label-primary">Important</span> 

                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="#">
                                    <div>
                                        <strong>Raphel Jonson</strong>
                                        <span class="pull-right text-muted">
                                            <em>Yesterday</em>
                                        </span>
                                    </div>
                                    <div>Lorem ipsum dolor sit amet, consectetur adipiscing.
                                         <br />
                                        <span class="label label-success"> Moderate </span> 
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="#">
                                    <div>
                                        <strong>Chi Ley Suk</strong>
                                        <span class="pull-right text-muted">
                                            <em>26 Jan 2014</em>
                                        </span>
                                    </div>
                                    <div>Lorem ipsum dolor sit amet, consectetur adipiscing.
                                         <br />
                                        <span class="label label-danger"> Low </span> 
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a class="text-center" href="#">
                                    <strong>Read All Messages</strong>
                                    <i class="icon-angle-right"></i>
                                </a>
                            </li>
                        </ul>

                    </li>
                    <!--END MESSAGES SECTION -->

                    <!--TASK SECTION -->
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                            <span class="label label-danger">5</span>   <i class="icon-tasks"></i>&nbsp; <i class="icon-chevron-down"></i>
                        </a>

                        <ul class="dropdown-menu dropdown-tasks">
                            <li>
                                <a href="#">
                                    <div>
                                        <p>
                                            <strong> Profile </strong>
                                            <span class="pull-right text-muted">40% Complete</span>
                                        </p>
                                        <div class="progress progress-striped active">
                                            <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">
                                                <span class="sr-only">40% Complete (success)</span>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="#">
                                    <div>
                                        <p>
                                            <strong> Pending Tasks </strong>
                                            <span class="pull-right text-muted">20% Complete</span>
                                        </p>
                                        <div class="progress progress-striped active">
                                            <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width: 20%">
                                                <span class="sr-only">20% Complete</span>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="#">
                                    <div>
                                        <p>
                                            <strong> Work Completed </strong>
                                            <span class="pull-right text-muted">60% Complete</span>
                                        </p>
                                        <div class="progress progress-striped active">
                                            <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%">
                                                <span class="sr-only">60% Complete (warning)</span>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="#">
                                    <div>
                                        <p>
                                            <strong> Summary </strong>
                                            <span class="pull-right text-muted">80% Complete</span>
                                        </p>
                                        <div class="progress progress-striped active">
                                            <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: 80%">
                                                <span class="sr-only">80% Complete (danger)</span>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a class="text-center" href="#">
                                    <strong>See All Tasks</strong>
                                    <i class="icon-angle-right"></i>
                                </a>
                            </li>
                        </ul>

                    </li>
                    <!--END TASK SECTION -->

                    <!--ALERTS SECTION -->
                    <li class="chat-panel dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                            <span class="label label-info">8</span>   <i class="icon-comments"></i>&nbsp; <i class="icon-chevron-down"></i>
                        </a>

                        <ul class="dropdown-menu dropdown-alerts">

                            <li>
                                <a href="#">
                                    <div>
                                        <i class="icon-comment" ></i> New Comment
                                    <span class="pull-right text-muted small"> 4 minutes ago</span>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="#">
                                    <div>
                                        <i class="icon-twitter info"></i> 3 New Follower
                                    <span class="pull-right text-muted small"> 9 minutes ago</span>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="#">
                                    <div>
                                        <i class="icon-envelope"></i> Message Sent
                                    <span class="pull-right text-muted small" > 20 minutes ago</span>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="#">
                                    <div>
                                        <i class="icon-tasks"></i> New Task
                                    <span class="pull-right text-muted small"> 1 Hour ago</span>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="#">
                                    <div>
                                        <i class="icon-upload"></i> Server Rebooted
                                    <span class="pull-right text-muted small"> 2 Hour ago</span>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a class="text-center" href="#">
                                    <strong>See All Alerts</strong>
                                    <i class="icon-angle-right"></i>
                                </a>
                            </li>
                        </ul>

                    </li>
                    <!-- END ALERTS SECTION -->

                    <!--ADMIN SETTINGS SECTIONS -->

                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                            <i class="icon-user "></i>&nbsp; <i class="icon-chevron-down "></i>
                        </a>

                        <ul class="dropdown-menu dropdown-user">
                            <li><a href="#"><i class="icon-user"></i> User Profile </a>
                            </li>
                            <li><a href="#"><i class="icon-gear"></i> Settings </a>
                            </li>
                            <li class="divider"></li>
                             <li><a href="<?php echo base_url();?>Users/user_signout"><i class="icon-signout"></i> Logout </a>
                            </li>
                        </ul>

                    </li>
                    <!--END ADMIN SETTINGS -->
                </ul>

            </nav>

        </div>
        <!-- END HEADER SECTION -->



        <!-- MENU SECTION -->
       <!--menu section-->
        <div id="left">
            <div class="media user-media well-small">
                <a class="user-link" href="#">
                    <img class="media-object img-thumbnail user-img" alt="" src="<?php //echo base_url();?>assets/img/user.gif" />
                </a>
                <br />
                <div class="media-body">
                    <h5 class="media-heading"> <?php echo( $this->session->userdata('staff_name')); ?></h5>
                    <ul class="list-unstyled user-info">
                        
                        <li>
                             <a class="btn btn-success btn-xs btn-circle" style="width: 10px;height: 12px;"></a> Online
                           
                        </li>
                        
                    </ul>
                </div>
                <br />
            </div>


               <?php 

                    if (isset($rights))
                        {

                            if (is_array($rights))
                                {  //print_r($rights);
                                    ?>
            <ul id="menu" class="collapse">
                <li class="panel">
                    <a href="<?php echo base_url()?>Home/load_home_page" >
                        <i class="icon-table"></i> Home
                    </a>                   
                </li>
               <?php
               $right_array = array( );
               foreach ($rights as $r) {
                   # code...
                    $right_array[]=$r->description;
               }
                if(in_array("manage work items", $right_array, true))
                        {
                                 
                        
               ?>
                <li class="panel ">
                    <a href="#" data-parent="#menu" data-toggle="collapse" class="accordion-toggle" data-target="#work-items">
                        <i class="icon-tasks"> </i> Work Items     
       
                        <span class="pull-right">
                          <i class="icon-angle-left"></i>
                        </span>
                       &nbsp; <span class="label label-default">5</span>&nbsp;
                    </a>
                    <ul class="collapse" id="work-items">
                        <?php if(in_array("list work items", $right_array, true)) 
                        {?>
                            <li class=""><a href="<?php echo base_url();?>Work_Item/list_work_items"><i class="icon-angle-right"></i> Manage Work Items </a></li>
                        <?php } ?>
                        <?php if(in_array("list work item types", $right_array, true)) 
                        {?>
                            <li class=""><a href="<?php echo base_url();?>Work_item_type/list_work_item_type"><i class="icon-angle-right"></i> Work Item Type </a></li>
                        <?php } ?>
                        <?php if(in_array("list stages", $right_array, true)) 
                        {?>
                            <li class=""><a href="<?php echo base_url();?>Stage/list_stages"><i class="icon-angle-right"></i> Stages </a></li>  
                        <?php } ?>
                        <?php if(in_array("list work item type stage", $right_array, true)) 
                        {?>
                            <li class=""><a href="<?php echo base_url();?>Work_item_type_stage/list_work_type_stage"><i class="icon-angle-right"></i>Work Item Type Stage</a></li>                      
                        <?php } ?>
                    </ul>

                </li>
                <?php } 

                if(in_array("manage authors", $right_array, true))
                        {
              ?>
                <li class="panel ">
                    <a href="#" data-parent="#menu" data-toggle="collapse" class="accordion-toggle collapsed" data-target="#form-nav">
                        <i class="icon-pencil"></i> Authors
       
                        <span class="pull-right">
                            <i class="icon-angle-left"></i>
                        </span>
                          &nbsp; <span class="label label-success">3</span>&nbsp;
                    </a>
                    <ul class="collapse" id="form-nav">
                    <?php if(in_array("list authors", $right_array, true)) 
                        {?>
                            <li class=""><a href="<?php echo base_url();?>Authors/list_authors"><i class="icon-angle-right"></i> Manage Authors </a></li>
                       <?php } ?>
                       <?php if(in_array("list author types", $right_array, true)) 
                        {?>
                        <li class=""><a href="<?php echo base_url();?>Author_type/get_author_type_list"><i class="icon-angle-right"></i> Author Type </a></li>
                        <?php } ?>
                        <?php if(in_array("list work item author", $right_array, true)) 
                        {?>
                            <li class=""><a href="<?php echo base_url();?>Work_item_author/list_work_item_author"><i class="icon-angle-right"></i> Work Item Author </a></li>
                        <?php } ?>
                    </ul>
                </li>
                <?php } 

                if(in_array("manage processes", $right_array, true))
                        {
              ?>
                <li class="panel ">
                    <a href="#" data-parent="#menu" data-toggle="collapse" class=" collapsed" data-target="#processes">
                        <i class="icon-tasks"></i> Processes<span class="pull-right">
                            <i class="icon-angle-left"></i>
                        </span>
                          &nbsp; <span class="label label-success">5</span>&nbsp;
                    </a>
                    <ul class="collapse" id="processes">
                        <?php if(in_array("list status", $right_array, true)) 
                            {?>
                                <li class=""><a href="<?php echo base_url();?>Status/list_status"><i class="icon-angle-right"></i> Status </a></li>
                        <?php } ?>
                        <?php if(in_array("list work item status", $right_array, true)) 
                            {?>
                                <li class=""><a href="<?php echo base_url();?>Work_item_status/list_work_item_status"><i class="icon-angle-right"></i> Work Item Status </a></li>
                        <?php } ?>
                      <!--  <li class=""><a href="<?php echo base_url();?>Work_Item/list_work_items"><i class="icon-angle-right"></i> Reviews </a></li>
                        <li class=""><a href="<?php echo base_url();?>Work_item_type/list_work_item_type"><i class="icon-angle-right"></i> Review Types </a></li>
                        <li class=""><a href="#"><i class="icon-angle-right"></i> Submissions </a></li>-->
                        <?php if(in_array("list work item stage", $right_array, true)) 
                            {?>
                                <li class=""><a href="<?php echo base_url();?>Work_item_stage/list_work_item_stage"><i class="icon-angle-right"></i> Work Item Stage </a></li>  
                        <?php } ?>
                         <?php if(in_array("list work item stage status", $right_array, true)) 
                            {?>
                                <li class=""><a href="<?php echo base_url();?>Work_item_stage_status/list_work_item_stage_status"><i class="icon-angle-right"></i> Work Item Stage Status</a></li>  
                         <?php } ?>
                        <?php if(in_array("list outputs", $right_array, true)) 
                            {?>
                                <li class=""><a href="#"><i class="icon-angle-right"></i> Output </a></li>    
                        <?php } ?>
                        <?php if(in_array("list work item stage outputs", $right_array, true)) 
                            {?>
                                <li class=""><a href="<?php echo base_url();?>Work_item_stage_output/list_work_item_stage_output"><i class="icon-angle-right"></i> Work Item Stage Output </a></li>

                            <?php } ?>

                            
                                <li class=""><a href="<?php echo base_url();?>Work_item_stage_output/receive_output_form"><i class="icon-angle-right"></i> Receive Document</a></li>  
                    </ul>

                </li>
                <?php } 

                if(in_array("manage output", $right_array, true))
                        {
              ?>
                <li class="panel ">
                    <a href="#" data-parent="#menu" data-toggle="collapse" class="accordion-toggle" data-target="#output">
                        <i class="icon-tasks"> </i> Output     
       
                        <span class="pull-right">
                          <i class="icon-angle-left"></i>
                        </span>
                       &nbsp; <span class="label label-default">5</span>&nbsp;
                    </a>
                    <ul class="collapse" id="output">                       
                        <li class=""><a href="<?php echo base_url();?>Work_Item/list_work_items"><i class="icon-angle-right"></i> Manage Outputs </a></li>                                             
                    </ul>

                </li>
                <?php } 

                if(in_array("manage reviews", $right_array, true))
                        {
              ?>
                <li class="panel">
                    <a href="#" data-parent="#menu" data-toggle="collapse" class="accordion-toggle" data-target="#DDL-nav">
                        <i class=" icon-sitemap"></i> Reviews
       
                        <span class="pull-right">
                            <i class="icon-angle-left"></i>
                        </span>
                    </a>
                    <ul class="collapse" id="DDL-nav">
                        <li>
                            <a href="#" data-parent="#DDL-nav" data-toggle="collapse" class="accordion-toggle" data-target="#DDL1-nav">
                                <i class="icon-sitemap"></i>&nbsp; PRG
       
                        <span class="pull-right" style="margin-right: 20px;">
                            <i class="icon-angle-left"></i>
                        </span>


                            </a>
                            <ul class="collapse" id="DDL1-nav">
                                <li>
                                    <a href="#"><i class="icon-angle-right"></i> Manage PRG </a>

                                </li>
                                
                            </ul>

                        </li>
                         <li>
                            <a href="#" data-parent="#DDL-nav" data-toggle="collapse" class="accordion-toggle" data-target="#submissions">
                                <i class="icon-sitemap"></i>&nbsp; Submissions
       
                        <span class="pull-right" style="margin-right: 20px;">
                            <i class="icon-angle-left"></i>
                        </span>


                            </a>
                            <ul class="collapse" id="DDL1-nav">
                                <li>
                                    <a href="#"><i class="icon-angle-right"></i> Manage Submissions </a>

                                </li>
                                <li>
                                    <a href="#"><i class="icon-angle-right"></i> Submission Types </a>

                                </li>
                                
                                
                            </ul>

                        </li>
                     </ul>
                </li>
                <?php } 

                if(in_array("manage documents", $right_array, true))
                        {
              ?>
                 <li class="panel">
                    <a href="#" data-parent="#menu" data-toggle="collapse" class="accordion-toggle" data-target="#documents">
                        <i class=" icon-sitemap"></i> Documents
       
                        <span class="pull-right">
                            <i class="icon-angle-left"></i>
                        </span>
                    </a>
                    <ul class="collapse" id="documents">
                        <li>
                            <a href="#" data-parent="#DDL-nav" data-toggle="collapse" class="accordion-toggle" data-target="#DDL1-nav">
                                <i class="icon-sitemap"></i>&nbsp; PRG
       
                        <span class="pull-right" style="margin-right: 20px;">
                            <i class="icon-angle-left"></i>
                        </span>


                            </a>
                            <ul class="collapse" id="DDL1-nav">
                                <li>
                                    <a href="#"><i class="icon-angle-right"></i> Manage PRG </a>
                                </li>
                                 <li class=""><a href="forms_fileupload.html"><i class="icon-angle-right"></i> FileUpload </a></li>                                
                            </ul>

                        </li>
                         <li>
                            <a href="#" data-parent="#DDL-nav" data-toggle="collapse" class="accordion-toggle" data-target="#DDL1-nav">
                                <i class="icon-sitemap"></i>&nbsp; Submissions
       
                        <span class="pull-right" style="margin-right: 20px;">
                            <i class="icon-angle-left"></i>
                        </span>


                            </a>
                            <ul class="collapse" id="DDL1-nav">
                                <li>
                                    <a href="#"><i class="icon-angle-right"></i> Manage Submissions </a>

                                </li>
                                <li>
                                    <a href="#"><i class="icon-angle-right"></i> Submission Types </a>

                                </li>
                                
                                
                            </ul>

                        </li>
                     </ul>
                </li>
                <?php } 

                if(in_array("manage settings", $right_array, true))
                        {
              ?>
                <li class="panel">
                    <a href="#" data-parent="#menu" data-toggle="collapse" class="accordion-toggle" data-target="#settings">
                        <i class=" icon-sitemap"></i> Settings
       
                        <span class="pull-right">
                            <i class="icon-angle-left"></i>
                        </span>
                    </a>
                    <ul class="collapse" id="settings">
                        <li>
                            <a href="#" data-parent="#settings" data-toggle="collapse" class="accordion-toggle" data-target="#manage-staff">
                                <i class="icon-sitemap"></i>&nbsp; Staff
       
                        <span class="pull-right" style="margin-right: 20px;">
                            <i class="icon-angle-left"></i>
                        </span>
                            </a>
                            <ul class="collapse" id="manage-staff">
                                <li>
                                    <a href="<?php echo base_url();?>Staff_controller/get_staffs"><i class="icon-angle-right"></i> Manage Staff </a>

                                </li>
                                <li>
                                    <a href="#"><i class="icon-angle-right"></i> Station</a></li>
                                <li>
                                    <a href="#"><i class="icon-angle-right"></i> Designation </a>
                                </li>
                                 <li>
                                    <a href="#"><i class="icon-angle-right"></i> Staff Contact </a>
                                </li>
                                 <li>
                                    <a href="#"><i class="icon-angle-right"></i> Staff Contact Type </a>
                                </li>

                            </ul>

                        </li>
                         <li>
                            <a href="#" data-parent="#settings" data-toggle="collapse" class="accordion-toggle" data-target="#users">
                                <i class="icon-sitemap"></i>&nbsp; Users       
                        <span class="pull-right" style="margin-right: 20px;">
                            <i class="icon-angle-left"></i>
                        </span>
                            <ul class="collapse" id="users">
                                <li>
                                    <a href="<?php echo base_url();?>Users/get_users_list"><i class="icon-angle-right"></i> Manage Users</a>
                                </li>
                                <li>
                                    <a href="<?php echo base_url();?>User_type_controller/get_user_type_list"><i class="icon-angle-right"></i> Manage User Types </a>
                                </li>
                            </ul>

                        </li>
                         <li>
                            <a href="#" data-parent="#DDL-nav" data-toggle="collapse" class="accordion-toggle" data-target="#DDL1-nav">
                                <i class="icon-sitemap"></i>&nbsp; Rights and Roles
       
                        <span class="pull-right" style="margin-right: 20px;">
                            <i class="icon-angle-left"></i>
                        </span>


                            </a>
                            <ul class="collapse" id="DDL1-nav">
                                <li>
                                    <a href="#"><i class="icon-angle-right"></i> Rights </a>

                                </li>
                                <li>
                                    <a href="#"><i class="icon-angle-right"></i> Roles</a></li>
                                <li>
                                    <a href="#"><i class="icon-angle-right"></i> Role to Right </a>
                                </li>
                                 <li>
                                    <a href="#"><i class="icon-angle-right"></i> Right to User </a>
                                </li>

                            </ul>

                        </li>
                        
                    </ul>
                </li>
                <?php }                 
              ?>
            </ul>
               <?php } } 
               ?>
        </div>
        <!--END MENU SECTION -->
        <!--end menu section-->
    