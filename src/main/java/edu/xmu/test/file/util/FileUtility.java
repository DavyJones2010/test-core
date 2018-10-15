package edu.xmu.test.file.util;

import java.io.File;
import java.net.URL;
import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class FileUtility {
	private static final Logger logger = Logger.getLogger(FileUtility.class);

	/**
	 * <p>
	 * Pass across the HTTPS certification validation
	 * </p>
	 * 
	 * @throws Exception
	 */
	static void doTrustToCertificates() throws Exception {
		// Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws java.security.cert.CertificateException {
			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws java.security.cert.CertificateException {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		} };

		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		HostnameVerifier hv = new HostnameVerifier() {
			public boolean verify(String urlHostName, SSLSession session) {
				if (!urlHostName.equalsIgnoreCase(session.getPeerHost())) {
					logger.info("Warning: URL host '" + urlHostName + "' is different to SSLSession host '" + session.getPeerHost() + "'.");
				}
				return true;
			}
		};
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
	}

	public static File downloadFileFromServer(String remoteFileName, String downloadDir) throws Exception {
		logger.info("Start copyFileFromServerToWeb. remoteFileName:" + remoteFileName);
		if (StringUtils.startsWithIgnoreCase(remoteFileName, "https")) {
			doTrustToCertificates();
		}
		URL url = new URL(remoteFileName);
		String fileShortName = FilenameUtils.getName(remoteFileName);
		File localFile = new File(downloadDir, fileShortName);

		FileUtils.copyURLToFile(url, localFile);

		logger.info(String.format("Finished copyFileFromServerToWeb. localFile: [%s]", localFile.getName()));

		return localFile;
	}
}
