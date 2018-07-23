package com.orwellg.bifrost.login;

import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.net.ssl.*;
import java.net.Socket;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import static com.orwellg.bifrost.login.configuration.LoginConfig.keyStore;


@SpringBootApplication
public class LoginApplication {

    public static void main(String[] args) throws Exception{
        //FIXME: REMOVE DISABLE TLS
       // enableSSL();
        //disableTLS();
      //  initSSL();
        SpringApplication.run(LoginApplication.class, args);

    }


//    static void disableTLS()throws Exception{
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
//    }
//     static void enableSSL()throws Exception{
//        //SSLContext sslContext = SSLContext.getInstance("TLS");
//      //  sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//
//
//         char[] password = "Tempo.99".toCharArray();
//
//         SSLContext sslContext = SSLContextBuilder.create()
//                 .loadKeyMaterial(keyStore("bifrost-truststore.jks", password), password)
//                 .loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
//         HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
//    }
//
//
//    static TrustManager[] trustAllCerts = new TrustManager[]{
//            new X509ExtendedTrustManager() {
//                @Override
//                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
//                }
//                @Override
//                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
//                }
//                @Override
//                public X509Certificate[] getAcceptedIssuers() {
//                    return null;
//                }
//                @Override
//                public void checkClientTrusted(X509Certificate[] x509Certificates, String s, Socket socket) throws CertificateException {
//                }
//                @Override
//                public void checkServerTrusted(X509Certificate[] x509Certificates, String s, Socket socket) throws CertificateException {
//                }
//
//                @Override
//                public void checkClientTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) throws CertificateException {
//                }
//                @Override
//                public void checkServerTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) throws CertificateException {
//                }
//            }
//    };
}
