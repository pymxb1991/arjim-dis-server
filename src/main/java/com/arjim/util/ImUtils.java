/*
 * Copyright (c) 2016, LinkedKeeper
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * - Neither the name of LinkedKeeper nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.arjim.util;

import io.netty.channel.ChannelHandlerContext;

import javax.management.*;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.rmi.UnknownHostException;
import java.security.KeyStore;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;

public class ImUtils {

	/**
	 * byte数组转换成16进制字符串
	 *
	 * @param src
	 * @return
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 获取IP地址及端口
	 * 
	 * @param socketaddress
	 * @return {ip}:{prot} 字符串
	 */
	public static String getIpAndProt(InetSocketAddress socketaddress) {
		String address = "";
		if (address != null) {
			address = getIp(socketaddress) + ":" + socketaddress.getPort();
		}
		return address;
	}

	/**
	 * 获取IP地址
	 * 
	 * @param socketaddress
	 * @return {ip} 字符串
	 */
	public static String getIp(InetSocketAddress socketaddress) {
		String ip = "";
		if (socketaddress != null) {
			InetAddress address = socketaddress.getAddress();
			ip = (address == null) ? socketaddress.getHostName() : address.getHostAddress();
		}
		return ip;
	}

	public static String getRemoteAddress(ChannelHandlerContext ctx) {
		InetSocketAddress remote = (InetSocketAddress) ctx.channel().remoteAddress();
		return getIpAndProt(remote);
	}

	public static String getLocalAddress(ChannelHandlerContext ctx) {
		InetSocketAddress local = (InetSocketAddress) ctx.channel().localAddress();
		return getIpAndProt(local);
	}

	public static SSLContext createSSLContext(String type , String path , String password) throws Exception {
		KeyStore ks = KeyStore.getInstance(type); /// "JKS"
		InputStream ksInputStream = new FileInputStream(path); /// 证书存放地址
		ks.load(ksInputStream, password.toCharArray());
		//KeyManagerFactory充当基于密钥内容源的密钥管理器的工厂。
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());//getDefaultAlgorithm:获取默认的 KeyManagerFactory 算法名称。
		kmf.init(ks, password.toCharArray());
		//SSLContext的实例表示安全套接字协议的实现，它充当用于安全套接字工厂或 SSLEngine 的工厂。
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(kmf.getKeyManagers(), null, null);
		return sslContext;
	}
	/**
	 * 获取本地IP地址
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getHostAddress() throws Exception {
		Enumeration netInterfaces = null;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
				Enumeration ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					InetAddress ip = (InetAddress) ips.nextElement();
					if (ip.isSiteLocalAddress()) {
						return ip.getHostAddress();
					}
				}
			}
		} catch (Exception e) {
			//log.error(e.getMessage(), e);
		}
		return InetAddress.getLocalHost().getHostAddress();
	}
	/**
	 * 获取服务端口号
	 * @return 端口号
	 * @throws ReflectionException
	 * @throws MBeanException
	 * @throws InstanceNotFoundException
	 * @throws AttributeNotFoundException
	 */
	public static String getServerPort(boolean secure) throws Exception {
		MBeanServer mBeanServer = null;
		if (MBeanServerFactory.findMBeanServer(null).size() > 0) {
			mBeanServer = (MBeanServer)MBeanServerFactory.findMBeanServer(null).get(0);
		}

		if (mBeanServer == null) {
			//log.debug("调用findMBeanServer查询到的结果为null");
			return "";
		}

		Set names = null;
		try {
			names = mBeanServer.queryNames(new ObjectName("Catalina:type=Connector,*"), null);
		} catch (Exception e) {
			return "";
		}
		Iterator it = names.iterator();
		ObjectName oname = null;
		while (it.hasNext()) {
			oname = (ObjectName)it.next();
			String protocol = (String)mBeanServer.getAttribute(oname, "protocol");
			String scheme = (String)mBeanServer.getAttribute(oname, "scheme");
			Boolean secureValue = (Boolean)mBeanServer.getAttribute(oname, "secure");
			Boolean SSLEnabled = (Boolean)mBeanServer.getAttribute(oname, "SSLEnabled");
			if (SSLEnabled != null && SSLEnabled) {// tomcat6开始用SSLEnabled
				secureValue = true;// SSLEnabled=true但secure未配置的情况
				scheme = "https";
			}
			if (protocol != null && ("HTTP/1.1".equals(protocol) || protocol.contains("http"))) {
				if (secure && "https".equals(scheme) && secureValue) {
					return ((Integer)mBeanServer.getAttribute(oname, "port")).toString();
				} else if (!secure && !"https".equals(scheme) && !secureValue) {
					return ((Integer)mBeanServer.getAttribute(oname, "port")).toString();
				}
			}
		}
		return "";
	}
}