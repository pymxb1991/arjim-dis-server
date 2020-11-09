/**
 ***************************************************************************************
 *  @Author     1044053532@qq.com   
 *  @License    http://www.apache.org/licenses/LICENSE-2.0
 ***************************************************************************************
 */
package com.arjim.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arjim.constant.Constants;
import com.arjim.server.connertor.impl.ImConnertorImpl;
import com.arjim.server.exception.InitErrorException;
import com.arjim.server.model.proto.MessageProto;
import com.arjim.server.proxy.MessageProxy;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

public class ImServer {

	private final static Logger log = LoggerFactory.getLogger(ImServer.class);

	private ProtobufDecoder decoder = new ProtobufDecoder(MessageProto.Model.getDefaultInstance());
	private ProtobufEncoder encoder = new ProtobufEncoder();

	private MessageProxy proxy;
	private ImConnertorImpl connertor;
	private int port;
	 //parentGroup
	private final EventLoopGroup bossGroup = new NioEventLoopGroup();
	//childGroup
	private final EventLoopGroup workerGroup = new NioEventLoopGroup();
	//为什么创建这两个实例，作用是什么？
	// 可以这么理解，bossGroup 和 workerGroup 是两个线程池, 它们默认线程数为 CPU 核心数乘以 2，
	// bossGroup 用于接收客户端传过来的请求，接收到请求后将后续操作交由 workerGroup 处理。

	private Channel channel;

	public void init() throws Exception {
		log.info("start arjim server ...");

		// Server 服务启动
		ServerBootstrap bootstrap = new ServerBootstrap();

		bootstrap.group(bossGroup, workerGroup);
		//channel 方法用于指定服务器端监听套接字通道 NioServerSocketChannel，其内部管理了一个 Java NIO 中的ServerSocketChannel实例。
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast("frameDecoder", new ProtobufVarint32FrameDecoder());
				pipeline.addLast("decoder", decoder);
				pipeline.addLast("frameEncoder", new ProtobufVarint32LengthFieldPrepender());
				pipeline.addLast("encoder", encoder);
				pipeline.addLast(new IdleStateHandler(Constants.ImserverConfig.READ_IDLE_TIME,
						Constants.ImserverConfig.WRITE_IDLE_TIME, 0));
				////用于初始化 Channel 的 ChannelPipeline
				pipeline.addLast("handler", new ImServerHandler(proxy, connertor));
			}
		});

		// 可选参数
		bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
		// 绑定接口，同步等待成功
		log.info("start arjim server at port[" + port + "].");
		ChannelFuture future = bootstrap.bind(port).sync();
		channel = future.channel();
		future.addListener(new ChannelFutureListener() {
			public void operationComplete(ChannelFuture future) throws Exception {
				if (future.isSuccess()) {
					log.info("Server have success bind to " + port);
				} else {
					log.error("Server fail bind to " + port);
					throw new InitErrorException("Server start fail !", future.cause());
				}
			}
		});
		// future.channel().closeFuture().syncUninterruptibly();
	}

	public void destroy() {
		log.info("destroy arjim server ...");
		// 释放线程池资源
		if (channel != null) {
			channel.close();
		}
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		log.info("destroy arjim server complate.");
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setProxy(MessageProxy proxy) {
		this.proxy = proxy;
	}

	public void setConnertor(ImConnertorImpl connertor) {
		this.connertor = connertor;
	}

}
