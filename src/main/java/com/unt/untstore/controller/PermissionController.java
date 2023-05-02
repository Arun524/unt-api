package com.unt.untstore.controller;

import com.unt.untstore.dto.PermissionDto;
import com.unt.untstore.dto.Response;
import com.unt.untstore.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {

	@Autowired
	private IPermissionService permissionService;
	
	@GetMapping("getall")
	public Response<List<PermissionDto>> getAllPermissions(){
		return null;
	}
	
	@PostMapping("save")
	public Response<PermissionDto> savePermission(){
		return null;
	}
	
	@PutMapping("update")
	public Response<PermissionDto> updatePermission(){
		return null;
	}
	
	@DeleteMapping("delete/{uid}")
	public Response<PermissionDto> deletePermission(@PathVariable String uid){
		return null;
	}
	
}
