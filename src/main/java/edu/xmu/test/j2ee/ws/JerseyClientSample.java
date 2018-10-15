package edu.xmu.test.j2ee.ws;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class JerseyClientSample {
	public static void main(String[] args) {
		Client client = Client.create();
		WebResource webResource = client.resource("http://localhost/ccar-reporting-ui/api/sftservice/exposure_record/DUMMY_REQ_ID");
		ClientResponse response = webResource.get(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		String output = response.getEntity(String.class);

		System.out.println("Output from Server .... \n");
		System.out.println(output);
	}
}
