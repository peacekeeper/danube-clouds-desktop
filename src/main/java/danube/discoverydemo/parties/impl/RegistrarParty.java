package danube.discoverydemo.parties.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Properties;

import javax.mail.MessagingException;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import xdi2.client.XDIClient;
import xdi2.client.http.XDIHttpClient;
import xdi2.core.xri3.XDI3Segment;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.email.Email;
import danube.discoverydemo.parties.Party;
import danube.discoverydemo.parties.impl.GlobalRegistryParty.RegisterCloudNameResult;
import danube.discoverydemo.xdi.XdiEndpoint;

public class RegistrarParty extends AbstractRemoteParty implements Party {

	private RegistrarParty(XdiEndpoint xdiEndpoint) {

		super("Registrar", xdiEndpoint);
	}

	public static RegistrarParty create() {

		XDIClient xdiClient = new XDIHttpClient("http://xdi.respectnetwork.com/");

		XdiEndpoint xdiEndpoint = new XdiEndpoint(
				xdiClient, 
				XDI3Segment.create("@respect"), 
				XDI3Segment.create("[@]!:uuid:299089fd-9d81-3c59-2990-89fd9d813c59"), 
				"s3cr3t"
				);

		return new RegistrarParty(xdiEndpoint);
	}

	public void sendEmail(RegisterCloudNameResult registerCloudNameResult, String to) throws IOException, MessagingException {

		Properties properties = DiscoveryDemoApplication.getApp().getServlet().getProperties();

		// send e-mail

		String subject = "Complete Cloud Name Registration: " + registerCloudNameResult.getCloudName();
		String from = properties.getProperty("email-from");
		String server = properties.getProperty("email-server");

		String link = "http://clouds.projectdanube.org/clouds/main?" + registerCloudNameResult.getCloudNumber().toString().substring(10);

		StringWriter writer = new StringWriter();
		StringBuffer buffer;

		VelocityContext context = new VelocityContext(properties);
		context.put("cloudname", registerCloudNameResult.getCloudName());
		context.put("cloudnumber", registerCloudNameResult.getCloudNumber());
		context.put("link", link);

		Reader templateReader = new InputStreamReader(RegistrarParty.class.getResourceAsStream("doregister.vm"), "UTF-8");

		Velocity.evaluate(context, writer, "doregister.vm", templateReader);
		templateReader.close();
		buffer = writer.getBuffer();

		Email email = new Email(subject, from, to, server);
		email.println(buffer.toString());
		email.send();
	}
}
