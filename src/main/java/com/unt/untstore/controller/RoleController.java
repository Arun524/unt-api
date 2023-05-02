package com.unt.untstore.controller;

import com.unt.untstore.dto.Response;
import com.unt.untstore.dto.RoleDto;
import com.unt.untstore.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private IRoleService roleService;
	
	@GetMapping("getall")
	public Response<List<RoleDto>> getAllRoles(){
		return null;
	}
	
	@PostMapping("save")
	public Response<RoleDto> saveRole(){
		return null;
	}
	
	@PutMapping("update")
	public Response<RoleDto> updateRole(){
		return null;
	}
	
	@DeleteMapping("delete/{uid}")
	public Response<RoleDto> deleteRole(@PathVariable String uid){
		return null;
	}
}
