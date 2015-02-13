package fx.person.app;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.MessageFormat;

@Data @Builder
public class Person {
	private String firstName;
	private String lastName;
	private String emailAddress;
}
