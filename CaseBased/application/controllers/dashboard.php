<?php
 if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Dashboard extends CI_Controller
{
	public function __construct()
	{
		parent::__construct();
		$this->load->model('dashboard_model');
		$this->load->model('rbac_model');		
	}
	public function index(){
		$data['event_counts']= $this->dashboard_model->get_event_counts();
		$data['totals']=$this->dashboard_model->get_total_event_counts();
		$this->load->view('view_dashboard',$data);
	}

	public function refresh_events()
	{
		$event_counts=$this->dashboard_model->get_event_counts();
		echo json_encode($event_counts);

	}

	public function get_total_event_counts(){
		$event_counts=$this->dashboard_model->get_total_event_counts();
		echo json_encode($event_counts);
	}

}

 