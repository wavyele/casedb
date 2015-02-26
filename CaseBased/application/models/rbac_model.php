<?php
class Rbac_model extends CI_Model
{
	public function __construct()
	{
		parent::__construct();
		
	}
	//get permision by role
	public function get_right_by_role($role)
	{
		$sql='	SELECT r.description FROM rights r
				JOIN role_right rr ON rr.right_id = r.right_id
				JOIN roles ro ON rr.role_id = ro.role_id
				WHERE ro.role_id='.$role;
		$query=$this->db->query($sql);
		if($query->num_rows()>0)
		{
			foreach ($query->result() as $row) 
			{
				# code...
				$rows[]=$row;
			}
			return $rows;
		}
		else
		{
			return false;
		}
	}


}