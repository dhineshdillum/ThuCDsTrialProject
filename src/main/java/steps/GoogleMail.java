package steps;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.model.*;
import net.serenitybdd.screenplay.Performable;
import org.apache.commons.codec.binary.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.jayway.awaitility.Awaitility;
import net.thucydides.core.pages.PageObject;
import net.serenitybdd.screenplay.Actor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import static java.util.concurrent.TimeUnit.SECONDS;



/**
 * Created by dhinesh.dillum on 17/02/17.
 */

//https://developers.google.com/gmail/api/quickstart/java


public class GoogleMail extends PageObject {

     private static final String APPLICATION_NAME = "Gmail OA";
     private static final File DATA_STORE_DIR = new File(System.getProperty("user.dir"), ".credentials/gmail-oa");
     private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
     private static FileDataStoreFactory DATA_STORE_FACTORY;
     private static HttpTransport HTTP_TRANSPORT;
     private static final List<String> SCOPES = Arrays.asList(GmailScopes.MAIL_GOOGLE_COM);
     private ListMessagesResponse list;
     public static String resetPasswordLink;
     public static String userId = "Me";
     public static MimeMessage email;
     public static Draft draft;

     public <T extends Actor> void getResetPasswordLink(T actor) {
          try {
               System.out.println(" Starting Google Email Verification " );

               // Build a new authorized API client service.
               Gmail service = getGmailService();

               Awaitility.await().atMost(30, SECONDS).pollInterval(5, SECONDS).until(inboxHasMessages());
               List<Message> messages = list.getMessages();
               String id = messages.get(0).getId();
               System.out.println("id is = " + id);
               Message message = service.users().messages().get("me", id).execute();
               List<MessagePart> body1 = message.getPayload().getParts();
               System.out.println("body1 = " + body1);
//             System.out.println("body size is "+ message.getPayload().getBody().getSize());
//             System.out.println("mmetype = " +message.getPayload().getMimeType());
               for (MessagePart p : body1) {
                    //if (p.getMimeType().equals("text/html")) {}
                    String body = (String) p.getBody().getData();
                    System.out.println("body = " + body);

                    Base64 decoder = new Base64(true);
                    String decodeBody = new String(decoder.decode(body));
                    Document doc = Jsoup.parse(decodeBody);
                    System.out.println("doc = " + doc);

                    Elements links = doc.getElementsByTag("a");
                    System.out.println("links.size() = " + links.size());

                    resetPasswordLink = links.get(1).text();
                    System.out.println("resetPasswordLink = " + resetPasswordLink);

//                    for (Element link : links) {
//                         String linkHref = link.attr("href");
//                         String linkText = link.text();
//                         System.out.println("linkHref = " + linkHref);
//                         System.out.println("linkText = " + linkText);
//                    }
//               for(int kk=0;kk<links.size();kk++){
//                    System.out.println("linkHref number = " +kk +" = " + links.attr("href"));
//                    System.out.println("linkText number = " +kk +" = "+ links.text());
//               }

                    //Robin's code
//                    Elements links = doc.select("a[href]");
//                    actor.remember("passwordLink", links.attr("href"));
//
                //Deleting email
                    service.users().messages().trash("me", message.getId()).execute();
               }
          } catch (IOException e) {
               e.printStackTrace();
          }

     }

     private Callable<Boolean> inboxHasMessages() {
          return new Callable<Boolean>() {
               public Boolean call() {
                    String query = "in:inbox is:unread subject:\"Reset your eBay password\"";
                    try {
                         Gmail service = getGmailService();
                         list = service.users().messages().list("me").setQ(query).execute();
                         return list.getMessages().size() > 0;
                    } catch (IOException e) {
                         System.out.println("Waiting on email to be received: " + e.getMessage());
                         return false;
                    }
               }
          };
     }

     /**
      * Creates an authorized Credential object.
      * @return an authorized Credential object.
      * @throws IOException
      */
     private static Credential authorize() throws IOException {
          File file = new File(System.getProperty("user.dir") + "/client_secret.json");
          InputStream in = new FileInputStream(file);
          GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
          GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                  HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                  .setDataStoreFactory(DATA_STORE_FACTORY)
                  .setAccessType("online").build();
          Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver())
                  .authorize("user");

          return credential;
     }

     /**
      * Build and return an authorized Gmail client service.
      * @return an authorized Gmail client service
      * @throws IOException
      */
     public static Gmail getGmailService() throws IOException {
          Credential credential = authorize();
          return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                  .setApplicationName(APPLICATION_NAME)
                  .build();
     }

     static {
          try {
          HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
          DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
     } catch (Throwable t) {
          t.printStackTrace();
     }
     }

     public static MimeMessage createEmail(String to, String from, String Subject, String bodyText) throws MessagingException {
          Properties props = new Properties();
          Session session = Session.getDefaultInstance(props, null);

          email = new MimeMessage(session);
          email.setFrom(new InternetAddress(from));
          email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
          email.setSubject(Subject);
          email.setText(bodyText);
          return email;
     }

     public static MimeMessage createEmailWithAttachment(String to, String from, String Subject, String bodyText, File file) throws MessagingException {
          Properties props = new Properties();
          Session session = Session.getDefaultInstance(props, null);

          email = new MimeMessage(session);
          email.setFrom(new InternetAddress(from));
          email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
          email.setSubject(Subject);

          MimeBodyPart mimeBodyPart = new MimeBodyPart();
          mimeBodyPart.setContent(bodyText, "text/plain");

          Multipart multipart = new MimeMultipart();
          multipart.addBodyPart(mimeBodyPart);

          mimeBodyPart = new MimeBodyPart();
          DataSource source = new FileDataSource(file);

          mimeBodyPart.setDataHandler(new DataHandler((source)));
          mimeBodyPart.setFileName(file.getName());

          multipart.addBodyPart(mimeBodyPart);
          email.setContent(multipart);

          return email;
     }

     //Create a Message from an email
     public static Message createMessageWithEmail(MimeMessage emailContent) throws IOException, MessagingException {
          ByteArrayOutputStream buffer = new ByteArrayOutputStream();
          emailContent.writeTo(buffer);
          byte[] bytes = buffer.toByteArray();
          String encodedEmail = com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(bytes);
          Message message = new Message();
          message.setRaw(encodedEmail);
          return message;
     }

     public static Message sendMessage() throws IOException, MessagingException {
          Message message = createMessageWithEmail(email);
          message = getGmailService().users().messages().send(userId, message).execute();

          System.out.println("Message id: " + message.getId());
          System.out.println(message.toPrettyString());
          return message;
     }

     public static Draft createDraftMessage() throws IOException, MessagingException {
          Message message = createMessageWithEmail(email);
          draft = new Draft();
          draft.setMessage(message);
          draft = getGmailService().users().drafts().create(userId, draft).execute();

          System.out.println("Draft id: " + draft.getId());
          System.out.println(draft.toPrettyString());
          return draft;
     }
}
