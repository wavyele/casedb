<?php
class Dashboard_model extends CI_Model
{
	public function __construct()
	{
		parent::__construct();
		
	}
	//get permision by role
	public function get_event_counts()
	{
		$sql='	SELECT COUNT( p_e.event_id ) AS event_count, p_e.event_id, e.name AS event_name, e.description, ((SELECT COUNT( p.event_id ) AS total_event_count
				FROM personevents p)) AS total_event_count
				FROM personevents p_e
				JOIN EVENTS e ON e.idevent = p_e.event_id
				GROUP BY p_e.event_id';
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

	public function get_total_event_counts()
	{

		$sql='	SELECT COUNT( p_e.event_id ) AS total_event_count
				FROM personevents p_e';

		$query=$this->db->query($sql);

		if($query->num_rows()==1)
		{
			return $query->row();
		}
		else
		{
			return false;
		}

	}


}
