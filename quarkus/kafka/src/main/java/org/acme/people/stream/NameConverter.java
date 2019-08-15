package org.acme.people.stream;

import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import io.smallrye.reactive.messaging.annotations.Broadcast;

@ApplicationScoped
public class NameConverter {

    private static final String[] honorifics = {"Mr.", "Mrs.", "Sir", "Madam", "Lord", "Lady", "Dr.", "Professor", "Vice-Chancellor", "Regent", "Provost", "Prefect"};

    @Incoming("names")               
    @Outgoing("my-data-stream")      
    @Broadcast                       
    public String process(String name) {
        String honorific = honorifics[(int)Math.floor(Math.random() * honorifics.length)];
        return honorific + " " + name;
    }
}
