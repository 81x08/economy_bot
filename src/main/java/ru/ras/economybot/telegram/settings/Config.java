package ru.ras.economybot.telegram.settings;

public class Config {

    public static final String BOT_TOKEN = "***";
    public static final String BOT_NAME = "ieiu_surgu_bot";

    private static final int PORT = 8443;
    public static final String WEBHOOK_NAME = "webhook";
    public static final String EXTERNAL_WEBHOOK_URL = "***" + PORT;
    public static final String INTERNAL_WEBHOOK_URL = EXTERNAL_WEBHOOK_URL;

    public static final String pathToCertificatePublicKey = "./publicKey.pem";
    public static final String pathToCertificateStore = "./sertificate.jks";
    public static final String certificateStorePassword = "***";

}