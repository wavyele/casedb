<?php
class Right_model extends CI_Model
{
	public function __construct()
	{
		parent::__construct();
		
	}
	//create new right
	public function create_right($data)
	{
		$this->db->insert('right',$data);

	}
	//update right by id
	public function	update_right_by_id($right_id,$data)
	{
		$this->db->where('id', $right_id);
		$this->db->update('right',$data);
	}
	//void right
	public function void_right_by_id($right_id)
	{
		$data= array('is_deleted'=>1);
		$this->db->where('id',$right_id);
		$this->db->update('right',$data);
	}
	

	public function void_multiple_right_by_id($ids)
	{
		$data= array('is_deleted'=>1);
		$this->db->where_in('id',$ids);
		$this->db->update('right',$data);
	}



	//re-instate right
	
	public function recreate_right_by_id($id)
	{
		$data= array('is_deleted'=>0);
		$this->db->where('id',$id);
		$this->db->update('right',$data);
	}

	public function recreate_multiple_right_by_id($ids)
	{
		$data= array('is_deleted'=>0);
		$this->db->where_in('id',$ids);
		$this->db->update('right',$data);
	}
	//get all rights

	public function get_all_rights()
	{
		$this->db->select();
		$query=$this->db->get('right');

		if($query->num_rows>0)
		{
			return $query->result();
		}
		else
		{
			return false;
		}

	}

	//get right by id
	public function get_right_by_id($id)
	{
		$this->db->where('id',$id);
		$this->db->select();
		$query=$this->db->get('right');

		if($query->num_rows()==1)
		{
			return $query->result();
		}
		else
		{
			return false;
		}
	}


}-