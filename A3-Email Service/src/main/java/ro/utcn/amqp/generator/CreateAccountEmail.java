package ro.utcn.amqp.generator;

import ro.utcn.amqp.dto.UserDTO;
import ro.utcn.amqp.entity.Email;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountEmail implements EmailGenerator<UserDTO> {

    private final String from = "toysstore@toysstore.com";

    @Override
    public Email generate(UserDTO userDTO) {

        Map<String, Object> properties  = new HashMap<String, Object>();
        properties.put("user", userDTO);

        Email email = Email.builder()
                        .from(from)
                        .to(userDTO.getEmail())
                        .htmlTemplate(new Email.HtmlTemplate("createAccountEmail", properties))
                        .subject("Contul tau ToysStore")
                        .build();

        return email;
    }
}
