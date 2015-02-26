<?php
class Role_model extends CI_Model
{
	public function __construct()
	{
		parent::__construct();
		
	}
	//create new role
	public function create_role($data)
	{
		$this->db->insert('role',$data);

	}
	//update role by id
	public function	update_role_by_id($role_id,$data)
	{
		$this->db->where('id', $role_id);
		$this->db->update('role',$data);
	}
	//void role
	public function void_role_by_id($role_id)
	{
		$data= array('is_deleted'=>1);
		$this->db->where('id',$role_id);
		$this->db->update('role',$data);
	}
	

	public function void_multiple_role_by_id($ids)
	{
		$data= array('is_deleted'=>1);
		$this->db->where_in('id',$ids);
		$this->db->update('role',$data);
	}



	//re-instate role
	
	public function recreate_role_by_id($id)
	{
		$data= array('is_deleted'=>0);
		$this->db->where('id',$id);
		$this->db->update('role',$data);
	}

	public function recreate_multiple_role_by_id($ids)
	{
		$data= array('is_deleted'=>0);
		$this->db->where_in('id',$ids);
		$this->db->update('role',$data);
	}
	//get all roles

	public function get_all_roles()
	{
		$this->db->select();
		$query=$this->db->get('role');

		if($query->num_rows>0)
		{
			return $query->result();
		}
		else
		{
			return false;
		}

	}

	//get role by id
	public function get_role_by_id($id)
	{
		$this->db->where('id',$id);
		$this->db->select();
		$query=$this->db->get('role');

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