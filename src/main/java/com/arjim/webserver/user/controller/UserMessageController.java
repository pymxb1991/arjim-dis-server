package com.arjim.webserver.user.controller;

import com.arjim.constant.Constants;
import com.arjim.webserver.base.controller.BaseController;
import com.arjim.webserver.user.model.UserMessageEntity;
import com.arjim.webserver.user.service.UserMessageService;
import com.arjim.webserver.util.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author arjim
 * @description
 * @date 2020-11-23 10:47:47
 */
@Controller
@RequestMapping("usermessage")
public class UserMessageController extends BaseController {
	@Autowired
	private UserMessageService userMessageServiceImpl;

	/**
	 * 页面
	 */
	@RequestMapping("/page")
	public String page(@RequestParam Map<String, Object> params) {
		return "user/usermessage";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object list(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		List<UserMessageEntity> userMessageList = userMessageServiceImpl.queryList(query);
		int total = userMessageServiceImpl.queryTotal(query);
		return putMsgToJsonString(Constants.WebSite.SUCCESS, "", total, userMessageList);
	}

	/**
	 * 信息
	 */
	@RequestMapping(value = "/info/{id}", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object info(@PathVariable("id") Long id) {
		UserMessageEntity userMessage = userMessageServiceImpl.queryObject(id);
		return putMsgToJsonString(Constants.WebSite.SUCCESS, "", 0, userMessage);
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object save(@RequestBody UserMessageEntity userMessage) {
		userMessageServiceImpl.save(userMessage);
		return putMsgToJsonString(Constants.WebSite.SUCCESS, "", 0, userMessage);
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/update", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object update(@RequestBody UserMessageEntity userMessage) {
		int result = userMessageServiceImpl.update(userMessage);
		return putMsgToJsonString(result, "", 0, "");
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(@RequestBody Long[] ids) {
		int result = userMessageServiceImpl.deleteBatch(ids);
		return putMsgToJsonString(result, "", 0, "");
	}

}
