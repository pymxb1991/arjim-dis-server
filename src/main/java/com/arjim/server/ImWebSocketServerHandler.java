/**
 ***************************************************************************************
 *  @Author     1044053532@qq.com   
 *  @License    http://www.apache.org/licenses/LICENSE-2.0
 ***************************************************************************************
 */
package com.arjim.server;

import com.arjim.constant.Constants;
import com.arjim.server.connertor.impl.ImConnertorImpl;
import com.arjim.server.model.MessageWrapper;
import com.arjim.server.model.proto.MessageProto;
import com.arjim.server.proxy.MessageProxy;
import com.arjim.util.ImUtils;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

//＃1客户端/用户连接到服务器并加入聊天
@Sharable
public class ImWebSocketServerHandler extends SimpleChannelInboundHandler<MessageProto.Model> {

	private final static Logger log = LoggerFactory.getLogger(ImWebSocketServerHandler.class);
	private ImConnertorImpl connertor = null;
	private MessageProxy proxy = null;
	public ImWebSocketServerHandler(MessageProxy proxy, ImConnertorImpl connertor) {
		this.connertor = connertor;
		this.proxy = proxy;
	}

    /**
     * 超时处理
     * 如果60没有接受客户端的心跳，就触发;
     */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object o) throws Exception {
		String sessionId = ctx.channel().attr(Constants.SessionConfig.SERVER_SESSION_ID).get();
		// // 服务端发个心跳包，客户端要回一个才行（
		if (o instanceof IdleStateEvent && ((IdleStateEvent) o).state().equals(IdleState.WRITER_IDLE)) {
			if (StringUtils.isNotEmpty(sessionId)) {
				MessageProto.Model.Builder builder = MessageProto.Model.newBuilder();
				builder.setCmd(Constants.CmdType.HEARTBEAT);
				builder.setMsgtype(Constants.ProtobufType.SEND);
				ctx.channel().writeAndFlush(builder);
			}
			System.out.println("心跳发送 ：sessionId: "+sessionId +"  Date:" + new Date());
			log.info ("心跳发送 ：sessionId: "+sessionId +"  Date:" + new Date());
			log.debug ("心跳发送 ：sessionId: "+sessionId +"  Date:" + new Date());
			log.debug(IdleState.WRITER_IDLE + "... from " + sessionId + "-->" + ctx.channel().remoteAddress() + " nid:"
					+ ctx.channel().id().asShortText());
		}

		// 如果心跳请求发出70秒内没收到响应，则关闭连接
		if (o instanceof IdleStateEvent && ((IdleStateEvent) o).state().equals(IdleState.READER_IDLE)) {
			//ALL_IDLE: 一段时间内没有数据接收或者发送
			//READER_IDLE ： 一段时间内没有数据接收
			//WRITER_IDLE ： 一段时间内没有数据发送
			System.out.println("心跳超时 ：sessionId: "+sessionId +"  Date:" + new Date());
			log.info ("心跳超时 ：sessionId: "+sessionId +"  Date:" + new Date());
			log.debug ("心跳超时 ：sessionId: "+sessionId +"  Date:" + new Date());
			log.debug(IdleState.READER_IDLE + "... from " + sessionId + " nid:" + ctx.channel().id().asShortText());
            //服务端收到上一次的心跳响应后会设置这个响应时间
			Long lastTime = (Long) ctx.channel().attr(Constants.SessionConfig.SERVER_SESSION_HEARBEAT).get();
			if (lastTime == null
					|| ((System.currentTimeMillis() - lastTime) / 1000 >= Constants.ImserverConfig.PING_TIME_OUT)) {
				connertor.close(ctx);
			}
			// ctx.channel().attr(Constants.SessionConfig.SERVER_SESSION_HEARBEAT).set(null);
		}
	}

	//读到客户端的内容并且向客户端去写内容
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MessageProto.Model message) throws Exception {
		try {
			String sessionId = connertor.getChannelSessionId(ctx);
			// inbound
			if (message.getMsgtype() == Constants.ProtobufType.SEND) {
				ctx.channel().attr(Constants.SessionConfig.SERVER_SESSION_HEARBEAT).set(System.currentTimeMillis());
				MessageWrapper wrapper = proxy.convertToMessageWrapper(sessionId, message);
				if (wrapper != null)
					receiveMessages(ctx, wrapper);
			}
			// outbound
			if (message.getMsgtype() == Constants.ProtobufType.REPLY) {
				MessageWrapper wrapper = proxy.convertToMessageWrapper(sessionId, message);
				if (wrapper != null)
					receiveMessages(ctx, wrapper);
			}
		} catch (Exception e) {
			log.error("ImWebSocketServerHandler channerRead error.", e);
			throw e;
		}

	}

	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		log.info("ImWebSocketServerHandler  join from " + ImUtils.getRemoteAddress(ctx) + " nid:"
				+ ctx.channel().id().asShortText());
	}

	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		log.debug("ImWebSocketServerHandler Disconnected from {" + ctx.channel().remoteAddress() + "--->"
				+ ctx.channel().localAddress() + "}");
	}

	/**
     * 建立 连接时调用
	 * 当建立一个新的连接时调用 ChannelActive()
	 * @param ctx
	 * @throws Exception
	 */
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		log.debug("ImWebSocketServerHandler channelActive from (" + ImUtils.getRemoteAddress(ctx) + ")");
	}

    /**
     * 关闭连接时调用
     * @param ctx
     * @throws Exception
     */
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		log.debug("ImWebSocketServerHandler channelInactive from (" + ImUtils.getRemoteAddress(ctx) + ")");
		String sessionId = connertor.getChannelSessionId(ctx);
		receiveMessages(ctx, new MessageWrapper(MessageWrapper.MessageProtocol.CLOSE, sessionId, null, null));
	}

    /**
     * 服务器监听到客户端异常调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.warn("ImWebSocketServerHandler (" + ImUtils.getRemoteAddress(ctx)
				+ ") -> Unexpected exception from downstream." + cause);
	}

	/**
	 * to send message
	 *
	 * @param hander
	 * @param wrapper
	 */
	private void receiveMessages(ChannelHandlerContext hander, MessageWrapper wrapper) {
		// 设置消息来源为Websocket
		wrapper.setSource(Constants.ImserverConfig.WEBSOCKET);
		if (wrapper.isConnect()) {
			connertor.connect(hander, wrapper);
		} else if (wrapper.isClose()) {
			connertor.close(hander, wrapper);
		} else if (wrapper.isHeartbeat()) {
			connertor.heartbeatToClient(hander, wrapper);
		} else if (wrapper.isGroup()) {
			connertor.pushGroupMessage(wrapper);
		} else if (wrapper.isSend()) {
			connertor.pushMessage(wrapper);
		} else if (wrapper.isReply()) {
			connertor.pushMessage(wrapper.getSessionId(), wrapper);
		}
	}
}
