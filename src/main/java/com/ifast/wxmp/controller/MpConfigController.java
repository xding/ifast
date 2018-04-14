package com.ifast.wxmp.controller;


import java.util.Arrays;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ifast.wxmp.domain.MpConfigDO;
import com.ifast.wxmp.service.MpConfigService;
import com.ifast.common.utils.Result;

/**
 * 
 * <pre>
 * 微信配置表
 * </pre>
 * <small> 2018-04-11 23:27:06 | Aron</small>
 */
@Controller
@RequestMapping("/wxmp/mpConfig")
public class MpConfigController {
	@Autowired
	private MpConfigService mpConfigService;
	
	@GetMapping()
	@RequiresPermissions("wxmp:mpConfig:mpConfig")
	String MpConfig(){
	    return "wxmp/mpConfig/mpConfig";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("wxmp:mpConfig:mpConfig")
	public Result<Page<MpConfigDO>> list(Integer pageNumber, Integer pageSize, MpConfigDO mpConfigDTO){
		// 查询列表数据
        Page<MpConfigDO> page = new Page<>(pageNumber, pageSize);
        
        Wrapper<MpConfigDO> wrapper = new EntityWrapper<MpConfigDO>(mpConfigDTO);
        page = mpConfigService.selectPage(page, wrapper);
        int total = mpConfigService.selectCount(wrapper);
        page.setTotal(total);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("wxmp:mpConfig:add")
	String add(){
	    return "wxmp/mpConfig/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("wxmp:mpConfig:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		MpConfigDO mpConfig = mpConfigService.selectById(id);
		model.addAttribute("mpConfig", mpConfig);
	    return "wxmp/mpConfig/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("wxmp:mpConfig:add")
	public Result<String> save( MpConfigDO mpConfig){
		if(mpConfigService.insert(mpConfig)){
			return Result.ok();
		}
		return Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("wxmp:mpConfig:edit")
	public Result<String>  update( MpConfigDO mpConfig){
		mpConfigService.updateById(mpConfig);
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("wxmp:mpConfig:remove")
	public Result<String>  remove( Integer id){
		if(mpConfigService.deleteById(id)){
            return Result.ok();
		}
		return Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("wxmp:mpConfig:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Integer[] ids){
		mpConfigService.deleteBatchIds(Arrays.asList(ids));
		return Result.ok();
	}
	
}