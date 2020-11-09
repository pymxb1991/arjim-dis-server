package com.arjim.webserver.user.controller;

import com.arjim.constant.Constants;
import com.arjim.webserver.base.controller.BaseController;
import com.arjim.webserver.user.model.UserInfoEntityOld;
import com.arjim.webserver.user.service.UserInfoService;
import com.arjim.webserver.util.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户信息表
 * 
 * @author arjim
 * @description
 * @date 2020-11-27 14:56:08
 */
@Controller
@RequestMapping("userinfo")
public class UserInfoController extends BaseController {
	@Autowired
	private UserInfoService userInfoServiceImpl;

	/**
	 * 页面
	 */
	@RequestMapping("/page")
	public String page(@RequestParam Map<String, Object> params) {
		return "userinfo";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object list(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		List<UserInfoEntityOld> userInfoList = userInfoServiceImpl.queryList(query);
		int total = userInfoServiceImpl.queryTotal(query);
		return putMsgToJsonString(Constants.WebSite.SUCCESS, "", total, userInfoList);
	}

	/**
	 * 信息
	 */
	@RequestMapping(value = "/info/{id}", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object info(@PathVariable("id") Long id) {
		UserInfoEntityOld userInfo = userInfoServiceImpl.queryObject(id);
		return putMsgToJsonString(Constants.WebSite.SUCCESS, "", 0, userInfo);
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object save(@ModelAttribute UserInfoEntityOld userInfo) {
		userInfoServiceImpl.save(userInfo);
		return putMsgToJsonString(Constants.WebSite.SUCCESS, "", 0, userInfo);
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/update", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object update(@ModelAttribute UserInfoEntityOld userInfo) {
		int result = userInfoServiceImpl.update(userInfo);
		return putMsgToJsonString(result, "", 0, "");
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(@RequestParam Long[] ids) {
		int result = userInfoServiceImpl.deleteBatch(ids);
		return putMsgToJsonString(result, "", 0, "");
	}

}
