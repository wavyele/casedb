<div id="content">
    <div class="inner">
        <div class="row">
            <div class="col-lg-12">
                <div class="box">
                    <header>
                        <div class="icons"><i class="icon-th-large"></i></div>
                        <h5>Welcome to OR Tracking Application</h5>
                        <div class="toolbar">
                            <ul class="nav">
                                <li>
                                    <div class="btn-group">
                                    </div>
                                </li>                
                            </ul>
                        </div>
                    </header>
                </div>
            </div>
        </div>
        <div class="row">
             <?php echo validation_errors()?>
          
                                            <?php
                                            $attributes = array('class' =>'form-horizontal', 'id'=>'block-validate','name'=>'search' ,'method'=>'post' );
                                                echo form_open('Work_item/create_work_items_form',$attributes);
                                             ?> 
                                        <div class="form-group">
                                            <label class="control-label col-lg-4" for="work-item">Search By </label>
                                            <div class="col-lg-4">
                                                <input type="radio" name="search-type" id="search-type" /> Work Item Name
                                                <input type="radio" name="search-type" id="search-type2" />Reference ID
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="row">
                                                <label for="search-text" class="control-label col-lg-4">Search Scientific Item</label>
                                                <div class="col-lg-4">
                                                    <input type="text" maxlength="150" value="<?php echo set_value('search-text'); ?>" id="search-text" name="search-text" class="form-control"   />
                                                </div>
                                                <div class="col-lg-2"> 
                                                    <button type="submit" name="btn-search" id="btn-search" class="btn btn-default"><span class="glyphicon glyphicon-search" ></span></button>
                                                    <button type="submit" title="add new work item" class="btn btn-info" name="create-new" id="create-new"><span class="glyphicon glyphicon-plus" ></span></button>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                        <label class="control-label col-lg-4" for="work-item"> </label>
                                        <div class="col-lg-4">
                                            <div class="hide-display" style="{display:none}">
                                                
                                            </div>
                                        </div>
                                        </div>


                                                                               
                                </form>

                                <div class="form-group">
                                    <label class="control-label col-lg-1" for="work-item"></label>
                                    <input type="hidden" id="base_url" value="<?php echo base_url() ?>">
                                    <div class="col-lg-10" id="result_table">
                                        
                                    </div>
                                </div>


            </div>
                                

    </div>
</div>