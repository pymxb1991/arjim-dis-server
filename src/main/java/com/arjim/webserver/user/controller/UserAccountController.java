package com.arjim.webserver.user.controller;

import com.arjim.constant.Constants;
import com.arjim.webserver.base.controller.BaseController;
import com.arjim.webserver.user.model.UserAccountEntity;
import com.arjim.webserver.user.service.UserAccountService;
import com.arjim.webserver.util.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户帐号
 * 
 * @author arjim
 * @description
 * @date 2020-11-27 14:56:08
 */
@Controller
@RequestMapping("useraccount")
public class UserAccountController extends BaseController {
	@Autowired
	private UserAccountService userAccountServiceImpl;

	/**
	 * 页面
	 */
	@RequestMapping("/page")
	public String page(@RequestParam Map<String, Object> params) {
		return "useraccount";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object list(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		List<UserAccountEntity> userAccountList = userAccountServiceImpl.queryList(query);
		int total = userAccountServiceImpl.queryTotal(query);
		return putMsgToJsonString(Constants.WebSite.SUCCESS, "", total, userAccountList);
	}

	/**
	 * 信息
	 */
	@RequestMapping(value = "/info/{id}", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object info(@PathVariable("id") Long id) {
		UserAccountEntity userAccount = userAccountServiceImpl.queryObject(id);
		return putMsgToJsonString(Constants.WebSite.SUCCESS, "", 0, userAccount);
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object save(@ModelAttribute UserAccountEntity userAccount) {
		userAccount.setDisablestate(0);
		userAccount.setIsdel(0);
		userAccountServiceImpl.save(userAccount);
		return putMsgToJsonString(Constants.WebSite.SUCCESS, "", 0, userAccount);
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/update", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object update(@ModelAttribute UserAccountEntity userAccount) {
		int result = userAccountServiceImpl.update(userAccount);
		return putMsgToJsonString(result, "", 0, "");
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(@RequestParam Long[] ids) {
		int result = userAccountServiceImpl.deleteBatch(ids);
		return putMsgToJsonString(result, "", 0, "");
	}

}
