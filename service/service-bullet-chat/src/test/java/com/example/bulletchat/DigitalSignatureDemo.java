package com.example.bulletchat;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public class DigitalSignatureDemo {

    public static void main(String[] args) throws Exception {
        String originalMessage = "Hello World!";

        // 生成密钥对
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // 签名
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(originalMessage.getBytes());
        byte[] digitalSignature = signature.sign();

        // 验证签名
        signature.initVerify(publicKey);
        signature.update(originalMessage.getBytes());
        boolean verified = signature.verify(digitalSignature);

        // 输出结果
        System.out.println("原始消息：" + originalMessage);
        System.out.println("数字签名：" + new String(digitalSignature));
        System.out.println("验证结果：" + verified);
    }

}
