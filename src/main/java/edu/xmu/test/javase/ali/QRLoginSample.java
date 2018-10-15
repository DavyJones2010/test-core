package edu.xmu.test.javase.ali;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 模拟扫码登陆, 核心是k-v存储与中心存储
 * <ol>
 * <li>QRCode过期
 * <li>扫码状态细化
 * <li>用户账密正确性校验
 * </ol>
 * 
 * @author davyjones
 *
 */
public class QRLoginSample {
	static enum QRLoginStatus {
		NOT_LOGIN, LOGIN_SUCCESS;
	}

	static class QRCode {
		/**
		 * sessionId
		 */
		private String sid;

		public QRCode(String sid) {
			super();
			this.sid = sid;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((sid == null) ? 0 : sid.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			QRCode other = (QRCode) obj;
			if (sid == null) {
				if (other.sid != null)
					return false;
			} else if (!sid.equals(other.sid))
				return false;
			return true;
		}

		public String getSid() {
			return sid;
		}

	}

	static class UserInfo {
		String userName;
		Date gmtCreate;

		public UserInfo(String userName, Date gmtCreate) {
			super();
			this.userName = userName;
			this.gmtCreate = gmtCreate;
		}

		@Override
		public String toString() {
			return "UserInfo [userName=" + userName + ", gmtCreate=" + gmtCreate + "]";
		}

	}

	/**
	 * 读取二维码端,用来授权登录. 必须先登陆
	 * 
	 * @author davyjones
	 *
	 */
	static class QRScanner {
		SessionStore sessionStore = SessionStore.INSTANCE;
		String globalSid;

		public void requestLogin(String userName, String password) {
			if (validateUserInfo(userName, password)) {
				globalSid = sessionStore.userLogin(new UserInfo(userName, new Date()));
			}

		}

		private boolean validateUserInfo(String userName, String password) {
			return true;
		}

		public void scanQRCode(QRCode code) {
			// 1. Scanner已经登陆
			boolean login = sessionStore.isLogin(globalSid);
			if (login) {
				// 2. TODO: 验证qrCode未过期
				// 3. 建立登录态
				UserInfo userInfo = sessionStore.getUserInfo(globalSid);
				sessionStore.userLogin(code.getSid(), userInfo);
			}
		}
	}

	/**
	 * 生成二维码端,用来请求登陆
	 * 
	 * @author davyjones
	 *
	 */
	static class QRGenerator {
		private SessionStore sessionStore = SessionStore.INSTANCE;

		public QRCode generateQRCode() {
			return new QRCode(sessionStore.generateVisitorSid());
		}

		/**
		 * 轮询扫码登陆状态
		 * 
		 * @param qrCode
		 * @return
		 */
		public QRLoginStatus pollStatus(QRCode qrCode) {
			return sessionStore.isLogin(qrCode.getSid()) ? QRLoginStatus.LOGIN_SUCCESS : QRLoginStatus.NOT_LOGIN;
		}

		public UserInfo getUserInfo(QRCode qrCode) {
			return sessionStore.getUserInfo(qrCode.getSid());
		}
	}

	/**
	 * 中心生成/保存session数据
	 * 
	 * @author davyjones
	 *
	 */
	static class SessionStore {
		public static SessionStore INSTANCE = new SessionStore();

		private SessionStore() {
		}

		/**
		 * 存储所有登陆用户<br>
		 * sessionId:userInfo
		 */
		Map<String, UserInfo> sessionStore = Maps.newHashMap();
		/**
		 * 存储所有visitorSid
		 */
		Set<String> sessionIds = Sets.newHashSet();

		public String userLogin(UserInfo userInfo) {
			String sid = UUID.randomUUID().toString();
			sessionStore.put(sid, userInfo);
			return sid;
		}

		public String userLogin(String visitorSid, UserInfo userInfo) {
			if (sessionIds.contains(visitorSid)) {
				sessionStore.put(visitorSid, userInfo);
				sessionIds.remove(visitorSid);
			} else {
				// TODO: invalid sid
			}
			return visitorSid;
		}

		public String generateVisitorSid() {
			String visitorSid = UUID.randomUUID().toString();
			sessionIds.add(visitorSid);
			return visitorSid;
		}

		public boolean isLogin(String sid) {
			return sessionStore.containsKey(sid);
		}

		public UserInfo getUserInfo(String sid) {
			return sessionStore.get(sid);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		// 1. 模拟微信PC端生成二维码
		QRGenerator qrGenerator = new QRGenerator();
		QRCode qrCode = qrGenerator.generateQRCode();

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					// 每隔一秒轮询下扫码登陆状态
					QRLoginStatus pollStatus = qrGenerator.pollStatus(qrCode);
					if (pollStatus == QRLoginStatus.LOGIN_SUCCESS) {
						UserInfo userInfo = qrGenerator.getUserInfo(qrCode);
						System.out.printf("User %s login success. Date %s \n", userInfo, new Date());
						break;
					} else {
						try {
							TimeUnit.SECONDS.sleep(1L);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();

		// 2. 模拟微信SDK端登陆; SDK先登录, 再模拟开始扫描等待3s,
		QRScanner qrScanner = new QRScanner();
		qrScanner.requestLogin("davywalker", "whoami");
		TimeUnit.SECONDS.sleep(3L);
		new Thread(new Runnable() {
			@Override
			public void run() {
				qrScanner.scanQRCode(qrCode);
			}
		}).start();

		TimeUnit.SECONDS.sleep(13L);
	}
}
