package com.arjim.webserver.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arjim.constant.Constants;
import com.arjim.server.model.MessageWrapper;
import com.arjim.server.model.Session;
import com.arjim.server.model.proto.MessageBodyProto;
import com.arjim.server.model.proto.MessageProto;
import com.arjim.server.proxy.MessageProxy;
import com.arjim.server.session.impl.SessionManagerImpl;
import com.arjim.util.ImUtils;
import com.arjim.webserver.base.controller.BaseController;
import com.arjim.webserver.dwrmanage.connertor.DwrConnertor;
import com.arjim.webserver.sys.service.FilesInfoService;
import com.arjim.webserver.user.model.*;
import com.arjim.webserver.user.service.*;
import com.arjim.webserver.util.Query;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@CrossOrigin
@Controller
@Slf4j
public class ImController extends BaseController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserAccountService userAccountServiceImpl;
	@Autowired
	private UserDepartmentService userDepartmentServiceImpl;
	@Autowired
	private FilesInfoService filesInfoServiceImpl;
	@Autowired
	private UserMessageService userMessageServiceImpl;
	@Autowired
	private DwrConnertor dwrConnertorImpl;
	@Autowired
	private MessageProxy proxy;
	@Autowired
	private  SessionManagerImpl sessionManagerImpl;

	@Autowired
	RedisTemplate redisTemplate;

	@Autowired
	private UserGroupService userGroupServiceImpl;

	@Autowired
	private UserRelationShipService userRelationShipServiceImpl;
	/**
	 * 登录IM
	 *
	 * with
	 * Credentials
	 */
	@ResponseBody
	@RequestMapping("/login")
	public ImUserData login(@RequestParam Map<String, Object> params, HttpServletResponse response, HttpServletRequest request) {
		response.setContentType("application/json; charset=UTF-8");

		Query query = new Query(params);
		UserAccountEntity userAccount = userAccountServiceImpl.validateUser(query);
		if (userAccount != null) {
			userAccountServiceImpl.addLoginUser(userAccount);
			String json = userAccount.getAccount()+":登陆即时通讯";
			String v = JSON.toJSONString(json);
			redisTemplate.opsForValue().set("USER-ONLINE-FLAG:"+userAccount.getId(), v,60);
		}
		ImUserData us = new ImUserData();
		us.setCode("0");
		us.setMsg("");
		us.setData("");
		return us;
	}


	@ResponseBody
	@RequestMapping(value = "/saveGroups",  produces = "text/html;charset=UTF-8",method = RequestMethod.POST)
	public ImUserData saveGroups(UserGroupEntity userGroup, HttpServletResponse response, HttpServletRequest request) throws Exception {

		response.setContentType("application/json; charset=UTF-8");
		ImUserData us = new ImUserData();

		UserAccountEntity loginUser = userAccountServiceImpl.getLoginUser(userGroup.getUserId());
		List<String> userListVo = userGroup.getUserList();
		if(null == userListVo && userListVo.isEmpty()){
			us.setCode("13");//数据不存在，没有用户列表，保存不了；群组必须有人；
			us.setMsg("");
			return us;
		}
		userGroup.setCreateBy(loginUser.getId());
		userGroup.setUpdateBy(loginUser.getId());
		if (StringUtils.isEmpty(userGroup.getGroupOwnerId())){//如果没有设置群主，则为当前用户
			userGroup.setGroupOwnerId(userGroup.getUserId());
		}
		if (StringUtils.isEmpty(userGroup.getGroupname())){//如果没有设置群名称，则为用户名称 + 的临时群组
			userGroup.setGroupname(loginUser.getName() + "的临时群组");
		}
		//根据ID，删除群组用户
		String groupId = userGroup.getId();
		if(!StringUtils.isEmpty(groupId)){
			userRelationShipServiceImpl.deleteRelByGroupId(userGroup.getId());
		}else{
			groupId = UUID.randomUUID().toString();
			userGroup.setId(groupId);
			userGroup.setCreateBy(loginUser.getId());
			userGroup.setCreateDate(new Date());
			userGroup.setUpdateBy(loginUser.getId());
			userGroup.setUpdateDate(new Date());
			userGroup.setDelFlag("0");
			userGroupServiceImpl.save(userGroup);
		}
		for(String userId : userListVo){
			UserRelationShipEntity UserRelationship = new UserRelationShipEntity();
			UserRelationship.setGroupId(groupId);
			UserAccountEntity user = userAccountServiceImpl.selectById(userId);
			UserRelationship.setUser(user);
			UserRelationship.setUserId(user.getId());
			UserRelationship.setCreateBy(loginUser.getId());
			UserRelationship.setUpdateBy(loginUser.getId());
			userRelationShipServiceImpl.save(UserRelationship);
		}
		userGroup.setType("group");
		us.setData(userGroup);
		us.setCode("0");
		return us;
	}

	/**
	 *   出警建群组
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addDisGroup",  produces = "text/html;charset=UTF-8",method = RequestMethod.POST)
	@ResponseBody
	public ImUserData addDisGroup(HttpServletResponse response, HttpServletRequest request) throws Exception {
		//解析数据
		Enumeration en = request.getParameterNames();
		AddDisGroupDTO userGroup = new AddDisGroupDTO();
		while (en.hasMoreElements()) {
			String k = (String) en.nextElement();
			String v = request.getParameter(k);
			switch (k) {
				case "id":
					userGroup.setId(v);
					break;
				case "groupname":
					userGroup.setGroupname(v);
					break;
				case "avatar":
					userGroup.setAvatar(v);
					break;
				case "groupOwnerId":
					userGroup.setGroupOwnerId(v);
					break;
				case "remarks":
					userGroup.setRemarks(v);
					break;
				case "createBy":
					userGroup.setCreateBy(v);
					break;
				case "updateBy":
					userGroup.setUpdateBy(v);
					break;
				case "policeUserId":
					userGroup.setPoliceUserId(v);
					break;
				case "handleUserId":
					userGroup.setHandleUserId(v);
					break;
			}
			System.out.println("k="+k+"    v="+v);
		}
		response.setContentType("application/json; charset=UTF-8");
		
		ImUserData us = new ImUserData();

		UserAccountEntity loginUser = userAccountServiceImpl.selectById(userGroup.getHandleUserId());

		List<String> userListVo = Arrays.asList(userGroup.getPoliceUserId().split(","));
		if(null == userListVo && userListVo.isEmpty()){
			us.setCode("13");//数据不存在，没有用户列表，保存不了；群组必须有人；
			us.setMsg("");
			return us;
		}
		userGroup.setCreateBy(loginUser.getId());
		userGroup.setUpdateBy(loginUser.getId());
		if (StringUtils.isEmpty(userGroup.getGroupOwnerId())){//如果没有设置群主，则为当前用户
			userGroup.setGroupOwnerId(userGroup.getHandleUserId());
		}
//		if (StringUtils.isEmpty(userGroup.getGroupname())){//如果没有设置群名称，则为用户名称 + 的临时群组
//			userGroup.setGroupname(loginUser.getName() + "警情组");
//		}
		//1、根据ID，查询群组，如果群组存在，则继续添加人员，否则创建群组，添加人员
		//2、如果群组中人员已经存在，不需要在进行添加， 先查出群组中人员进行取差集；
		String groupId = userGroup.getId();
		UserGroupEntity userGroupEntity = userGroupServiceImpl.selectById(groupId);
		List<String> groupUserIds = new ArrayList<>();
		List<String> userDtoList = new ArrayList<>();
		if(userGroupEntity != null){
			groupUserIds = userRelationShipServiceImpl.selectByGroupId(userGroup.getId());
			userDtoList = getUserList(userListVo,groupUserIds);
		}else{
			userGroupEntity = new UserGroupEntity();
			BeanUtils.copyProperties(userGroup,userGroupEntity);
			userGroupEntity.setId(groupId);
			userGroupEntity.setCreateDate(new Date());
			userGroupEntity.setUpdateDate(new Date());
			userGroupEntity.setDelFlag("0");
			userGroupServiceImpl.save(userGroupEntity);
		}
		for(String userId : userDtoList){
			UserRelationShipEntity UserRelationship = new UserRelationShipEntity();
			UserRelationship.setGroupId(groupId);
			UserAccountEntity user = userAccountServiceImpl.selectById(userId);
			UserRelationship.setUser(user);
			UserRelationship.setUserId(user.getId());
			UserRelationship.setCreateBy(loginUser.getId());
			UserRelationship.setUpdateBy(loginUser.getId());
			userRelationShipServiceImpl.save(UserRelationship);
		}
		us.setCode("200");
		us.setMsg("群组创建，人员添加成功");
		return us;
	}

	/**
	 *  过滤已经存在的人员；
	 * @param userListVo
	 * @param groupUserIds
	 * @return
	 */
	private List<String> getUserList(List<String> userListVo, List<String> groupUserIds) {
		List<String> userList = new ArrayList<>();
		for(String u1:groupUserIds){//旧用户
			for(String u2:userListVo){//新用户
				if(u1.equals(u2)){
					continue;
				}
				userList.add(u2);
			}
		}
		return userList;
	}

	/**
	 * 取得所有聊天用户
	 * 
	 * @param response
	 * @param request
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getusers", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ImUserData getAllUser(HttpServletResponse response, HttpServletRequest request,String userId) throws Exception {

		logger.info("当前正在执行的类名为》》》"+Thread.currentThread().getStackTrace()[1].getClassName());
		logger.info("当前正在执行的方法名为》》》"+Thread.currentThread().getStackTrace()[1].getMethodName());

		response.setContentType("application/json; charset=UTF-8");
		
		// 数据格式请参考文档
		UserAccountEntity loginUser = userAccountServiceImpl.getLoginUser(userId);
		if (loginUser != null) {
			List<ImGroupUserData> groups = userDepartmentServiceImpl.getGroup(loginUser);
			ImFriendUserInfoData my = new ImFriendUserInfoData();
			my.setId(loginUser.getId());
			my.setUsername(loginUser.getName());
			String file1 = "";
			String hostAddress = ImUtils.getHostAddress();
			final String serverPort = ImUtils.getServerPort(false);
			String fileUrl = "http://"+ hostAddress+":"+serverPort + file1;
		/*	if (StringUtils.isNotEmpty(file1)) {
				String fileUrl = Global.getConfig("FILE_UPLOAD_URL");
				eventIncident.setFile1(fileUrl + file1);
			}*/
			my.setAvatar( fileUrl + request.getContextPath() + loginUser.getProfilephoto());
			my.setSign(loginUser.getSignature());
			my.setStatus("online");
			// 获取用户分组 及用户
			List<ImFriendUserData> friends = new ArrayList<>();
            //final List<ImOfficeUser> queryOfficeUser = userDepartmentServiceImpl.queryOfficeUser();


			List<ImGroupUserData> ImOfficeDataList = userDepartmentServiceImpl.queryOffice();
			if(null != ImOfficeDataList || ImOfficeDataList.size() > 0) {
				for (ImGroupUserData groupBean : ImOfficeDataList) {
					List<ImFriendUserInfoData> listUser = userDepartmentServiceImpl.findUserByOffice(groupBean);
					//查询所有在线用户  修改在线状态
					Session[] sessions = sessionManagerImpl.getSessions();
					for (ImFriendUserInfoData userBean : listUser) {
						for (int i = 0; i < sessions.length; i++) {
							if(userBean.getId().equals(sessions[i].getAccount())) {
								userBean.setStatus("online");
								break;
							}
						}
						if(userBean.getId().equals(userId)) {
							userBean.setStatus("online");
						}
					}
					ImFriendUserData imFriendUserData = new ImFriendUserData(groupBean.getId(), groupBean.getGroupname(), listUser);
					friends.add(imFriendUserData);
				}
			}

			ImGroupUserResult imGroupUserResult = new ImGroupUserResult(my, friends, groups);
			ImUserData us = new ImUserData();
			us.setCode("0");
			us.setMsg("");
			us.setData(imGroupUserResult);
			return us;
		} else {
			return null;
		}
	}

	/**
	 * 图片上传
	 * 
	 * @param file
	 * @param response
	 * @param request
	 * @param userId
	 * @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/imgupload", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	public Object uploadImgFile(@RequestParam MultipartFile file, HttpServletResponse response, HttpServletRequest request,String userId) throws Exception {
		response.setContentType("application/json; charset=UTF-8");
		
		UserAccountEntity u = userAccountServiceImpl.getLoginUser(userId);
		String uid = u.getId();
		String path = request.getSession().getServletContext().getRealPath("upload/img/temp/");
		String files = filesInfoServiceImpl.savePicture(file, uid.toString() + UUID.randomUUID().toString(), path);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> submap = new HashMap<String, String>();
		if (files.length() > 0) {
			map.put("code", "0");
			map.put("msg", "上传过成功");
			submap.put("src","/upload/img/temp/" + files + "?" + new Date().getTime());
		} else {
			submap.put("src", "");
			map.put("code", "1");
			map.put("msg", "上传过程中出现错误，请重新上传");
		}
		map.put("data", submap);
		return JSONArray.toJSON(map);
	}

	/**
	 * 文件上传
	 * 
	 * @param file
	 * @param response
	 * @param request
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/fileupload", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	public Object uploadAllFile(@RequestParam MultipartFile file, HttpServletResponse response, HttpServletRequest request,String userId) throws Exception {
		response.setContentType("application/json; charset=UTF-8");
		
		UserAccountEntity u = userAccountServiceImpl.getLoginUser(userId);
		String uid = u.getId();
		String path = request.getSession().getServletContext().getRealPath("upload/file/temp/");
		String files = filesInfoServiceImpl.saveFiles(file, uid.toString() + UUID.randomUUID().toString(), path);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> submap = new HashMap<String, String>();
		if (files.length() > 0) {
			map.put("code", "0");
			map.put("msg", "上传过成功");
			submap.put("src", request.getContextPath() + "/upload/file/temp/" + files + "?" + new Date().getTime());
			submap.put("name", file.getOriginalFilename());
		} else {
			submap.put("src", "");
			map.put("code", "1");
			map.put("msg", "上传过程中出现错误，请上传[png|jpg|gif|jpeg|doc|xls|pdf|docx|xlsx]格式文件");
		}
		map.put("data", submap);
		return JSONArray.toJSON(map);
	}

	/**
	 * 取得离线消息
	 * 
	 * @param response
	 * @param request
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getofflinemsg", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object userMessageCount(HttpServletResponse response, HttpServletRequest request, String userId) throws Exception {
		response.setContentType("application/json; charset=UTF-8");
		
		Map<String, Object> map = new HashMap<String, Object>();
		ImUserData us = new ImUserData();
		us.setCode("200");
		us.setMsg("成功");
		final UserAccountEntity loginUser = userAccountServiceImpl.getLoginUser(userId);
		if (loginUser!= null) {
			map.put("receiveuser", userId);
			List<UserMessageEntity> list = userMessageServiceImpl.getOfflineMessageList(map);
			us.setData(list);
			return JSONArray.toJSON(list);
		} else {
			return JSONArray.toJSON(new ArrayList<UserMessageEntity>());
		}

	}

	/**
	 * 聊天记录
	 * 
	 * @param response
	 * @param request
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/historymessageajax", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public Object userHistoryMessages(HttpServletResponse response, HttpServletRequest request,String userId) throws Exception {
		response.setContentType("application/json; charset=UTF-8");
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<UserMessageEntity> list = new ArrayList<>();
		map.put("page", getSkipToPage());
		map.put("limit", getPageSize());
		map.put("senduser", userId);
		map.put("groupid", request.getParameter("id"));
		map.put("receiveuser", request.getParameter("id"));
		String type = request.getParameter("type");
		if ("group".equals(type)) {
			list = userMessageServiceImpl.getGroupHistoryMessageList(new Query(map));
		} else {
			list = userMessageServiceImpl.getHistoryMessageList(new Query(map));
		}
		Map<String, List<UserMessageEntity>> resultMap = new HashMap();
		resultMap.put("data", list);
		return JSONArray.toJSON(resultMap);
	}

	/**
	 * 聊天记录页面
	 * 
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/historymessage", method = RequestMethod.GET)
	public String userHistoryMessagesPage(HttpServletResponse response, HttpServletRequest request) throws Exception {
		response.setContentType("application/json; charset=UTF-8");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("senduser", request.getParameter("userId"));
		map.put("receiveuser", request.getParameter("id"));
		String type = request.getParameter("type");
		int totalsize = 0;
		if ("group".equals(type)) {
			totalsize = userMessageServiceImpl.getGroupHistoryMessageCount(map);
		} else {
			totalsize = userMessageServiceImpl.getHistoryMessageCount(map);
		}
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("skipToPage", getSkipToPage());
		resultMap.put("pageSize", getPageSize());
		resultMap.put("totalsize", totalsize);
		return JSONObject.toJSONString(resultMap);
	}

	@ResponseBody
	@RequestMapping(value = "/sendmsg", method = RequestMethod.GET)
	public Object sendMsg(HttpServletResponse response, HttpServletRequest request) throws Exception {
		response.setContentType("application/json; charset=UTF-8");
		
		String sessionid = request.getSession().getId();
		if (getLoginUser() != null) {
			sessionid = getLoginUser().getId().toString();
		}
		MessageProto.Model.Builder builder = MessageProto.Model.newBuilder();
		builder.setCmd(Constants.CmdType.MESSAGE);
		builder.setSender(sessionid);
		builder.setReceiver((String) request.getParameter("receiver"));
		builder.setMsgtype(Constants.ProtobufType.REPLY);
		MessageBodyProto.MessageBody.Builder msg = MessageBodyProto.MessageBody.newBuilder();
		msg.setContent((String) request.getParameter("content"));
		builder.setContent(msg.build().toByteString());
		MessageWrapper wrapper = proxy.convertToMessageWrapper(sessionid, builder.build());
		dwrConnertorImpl.pushMessage(sessionid, wrapper);
		return JSONArray.toJSON("");
	}
	
	@RequestMapping(value = "/sign", method = RequestMethod.GET)
	@ResponseBody
	public void personalizedSignature(HttpServletResponse response, HttpServletRequest request,String sign,String userId) throws Exception {
		response.setContentType("application/json; charset=UTF-8");
		
		ImFriendUserInfo imFriendUserInfo = new ImFriendUserInfo();
		imFriendUserInfo.setId(userId);
		imFriendUserInfo.setSign(sign);
		userDepartmentServiceImpl.updateSign(imFriendUserInfo);
	}



	@ResponseBody
	@RequestMapping(value = "/getGroupUser")
	public ImUserData getGroupUser(HttpServletResponse response, HttpServletRequest request,String id) throws Exception {
		response.setContentType("application/json; charset=UTF-8");
		
		//Map<String, Object> data = new HashMap<>();
        final ImGroupUserData groupById = userDepartmentServiceImpl.findGroupById(id);
        ImFriendUserData imFriendUserData = new ImFriendUserData();
		imFriendUserData.setId(id);
		List<ImFriendUserInfoData> list = userAccountServiceImpl.getGroupUser(imFriendUserData);

        ImGroupUserListData groupUserListData = new ImGroupUserListData();
        BeanUtils.copyProperties(groupById,groupUserListData);
        groupUserListData.setList(list);

		/*data.put("list", list);*/
		ImUserData us = new ImUserData();
		us.setCode("0");
		us.setMsg("");
		us.setData(groupUserListData);
		return us;
	}
}
